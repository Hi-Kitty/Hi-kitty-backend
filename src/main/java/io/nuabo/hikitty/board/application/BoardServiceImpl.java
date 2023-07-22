package io.nuabo.hikitty.board.application;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.application.port.DefaultImageConfig;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.board.application.port.*;
import io.nuabo.hikitty.board.domain.*;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PageNationRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AWSConnection awsConnection;
    private final DefaultImageConfig defaultImageConfig;

    private final ClockHolder clockHolder;

    private final HeartRepository heartRepository;
    @Transactional
    @Override
    public BoardFundraiserImagePlan create(BoardCreateRequest boardCreateRequest, String email, MultipartFile multipartFile, List<PlanCreateRequest> planCreateRequests) {

        FundraiserCreate fundraiserCreate = getFundraiser(email);
        Board board = Board.from(boardCreateRequest, fundraiserCreate);

        board = boardRepository.save(board);

        Image image = getImage(multipartFile, board);
        List<Plan> plans = getPlans(planCreateRequests, board);

        return BoardFundraiserImagePlan.from(board, image, plans);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PageImageGet> getPages(PageNationRequest pageNationRequest) {
        return imageRepository.findAll(getPageSortByCreatedAtDesc(pageNationRequest))
                .map(image -> PageImageGet.from(image, clockHolder));
    }

    @Transactional(readOnly = true)
    @Override
    public ImagePlanHeartGet getById(Long boardId) {
        Image image = imageRepository.getByBoardIdFetchJoinImage(boardId);
        List<Plan> plans = planRepository.findAllByBoardId(boardId);
        List<Heart> hearts = heartRepository.findAllByBoardId(boardId);

        return ImagePlanHeartGet.from(image, plans, hearts, defaultImageConfig, clockHolder);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PageImageGet> getPagesByFundraiserEmail(PageNationRequest pageNationRequest, String email) {
        User fundraiser = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);

        return imageRepository.findAllByFundraiserId(fundraiser.getId(), getPageSortByCreatedAtDesc(pageNationRequest))
                .map(image -> PageImageGet.from(image, clockHolder));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PageImageGet>   getPagesByFundraiserId(Long fundraiserId, PageNationRequest pageNationRequest) {
        User fundraiser = userRepository.getByIdAndStatus(fundraiserId, UserStatus.ACTIVE);
        return imageRepository.findAllByFundraiserId(fundraiser.getId(), getPageSortByCreatedAtDesc(pageNationRequest))
                .map(image -> PageImageGet.from(image, clockHolder));
    }


    private PageRequest getPageSortByCreatedAtDesc(PageNationRequest pageNationRequest) {
        return PageRequest.of(pageNationRequest.getPage(), pageNationRequest.getSize());
    }

    private List<Plan> getPlans(List<PlanCreateRequest> planCreateRequests, Board finalBoard) {
        List<Plan> plans = planCreateRequests.stream().map(
                planCreateRequest -> Plan.from(finalBoard, planCreateRequest)
        ).toList();
        plans = planRepository.saveAll(plans);
        return plans;
    }

    private Image getImage(MultipartFile multipartFile, Board board) {
        AmazonS3Upload amazonS3Upload = awsConnection.sendFileToAWS(multipartFile);
        Image image = Image.from(board, amazonS3Upload);
        image = imageRepository.save(image);
        return image;
    }

    private FundraiserCreate getFundraiser(String email) {
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);
        return profileRepository.findByUserId(user.getId()).map(
                profile -> FundraiserCreate.from(user)
        ).orElse(FundraiserCreate.from(user));
    }
}
