package io.nuabo.hikitty.board.presentation.port;

import io.nuabo.hikitty.board.domain.BoardFundraiserImagePlan;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    BoardFundraiserImagePlan create(BoardCreateRequest boardCreateRequest, String email, MultipartFile multipartFile, List<PlanCreateRequest> planCreateRequests);
}
