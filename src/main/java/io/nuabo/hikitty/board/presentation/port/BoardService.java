package io.nuabo.hikitty.board.presentation.port;

import io.nuabo.hikitty.board.domain.ImagePlanHeartGet;
import io.nuabo.hikitty.board.domain.PageImageGet;
import io.nuabo.hikitty.board.domain.BoardFundraiserImagePlan;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PageNationRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    BoardFundraiserImagePlan create(BoardCreateRequest boardCreateRequest, String email, MultipartFile multipartFile, List<PlanCreateRequest> planCreateRequests);

    Page<PageImageGet> getPages(PageNationRequest pageNationRequest);


    ImagePlanHeartGet getById(Long boardId);

    Page<PageImageGet> getPagesByFundraiserEmail(PageNationRequest pageNationRequest, String email);

    Page<PageImageGet> getPagesByFundraiserId(Long fundraiserId, PageNationRequest pageNationRequest);
}
