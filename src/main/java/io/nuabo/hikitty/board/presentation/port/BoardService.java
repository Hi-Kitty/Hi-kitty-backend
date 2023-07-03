package io.nuabo.hikitty.board.presentation.port;

import io.nuabo.hikitty.board.domain.ImagePlanHeartGet;
import io.nuabo.hikitty.board.domain.PageImageGet;
import io.nuabo.hikitty.board.domain.BoardFundraiserImagePlan;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PageBoardRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    BoardFundraiserImagePlan create(BoardCreateRequest boardCreateRequest, String email, MultipartFile multipartFile, List<PlanCreateRequest> planCreateRequests);

    Page<PageImageGet> getPages(PageBoardRequest pageBoardRequest);


    ImagePlanHeartGet getById(Long boardId);

    Page<PageImageGet> getPagesByFundraiserId(PageBoardRequest pageBoardRequest, String email);
}
