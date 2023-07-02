package io.nuabo.hikitty.board.application;

import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.board.application.port.*;
import io.nuabo.hikitty.board.domain.*;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
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
    private final FundraiserRepository fundraiserRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AWSConnection awsConnection;
    private final DefaultImageConfig defaultImageConfig;

    @Transactional
    @Override
    public BoardFundraiserImagePlan create(BoardCreateRequest boardCreateRequest, String email, MultipartFile multipartFile, List<PlanCreateRequest> planCreateRequests) {

        Board board = Board.from(boardCreateRequest);
        board = boardRepository.save(board);
        Board finalBoard = board;

        Fundraiser fundraiser = getFundraiser(email, board, finalBoard);
        Image image = getImage(multipartFile, board);
        List<Plan> plans = getPlans(planCreateRequests, finalBoard);

        return BoardFundraiserImagePlan.from(board, fundraiser, image, plans);
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

    private Fundraiser getFundraiser(String email, Board board, Board finalBoard) {
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);
        Fundraiser fundraiser = profileRepository.findByUserId(user.getId()).map(
                profile -> Fundraiser.from(finalBoard, user, profile)
        ).orElse(Fundraiser.from(board, user, defaultImageConfig.getDefaultImageFundraiserOriginalName(), defaultImageConfig.getDefaultImageFundraiserUrl()));
        fundraiser = fundraiserRepository.save(fundraiser);
        return fundraiser;
    }
}
