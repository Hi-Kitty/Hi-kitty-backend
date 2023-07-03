package io.nuabo.hikitty.board.application;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.application.port.DefaultImageConfig;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.board.application.port.*;
import io.nuabo.hikitty.board.domain.*;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PageBoardRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public Page<PageImageDto> getPages(PageBoardRequest pageBoardRequest) {
        return imageRepository.findAll(getPageSortByCreatedAtDesc(pageBoardRequest))
                .map(image -> PageImageDto.from(image, clockHolder));
    }

    private PageRequest getPageSortByCreatedAtDesc(PageBoardRequest pageBoardRequest) {
        return PageRequest.of(pageBoardRequest.getPage(), pageBoardRequest.getSize());
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
                profile -> FundraiserCreate.from(user, profile)
        ).orElse(FundraiserCreate.from(user, defaultImageConfig.getDefaultImageFundraiserOriginalName(), defaultImageConfig.getDefaultImageFundraiserUrl()));
    }
}
