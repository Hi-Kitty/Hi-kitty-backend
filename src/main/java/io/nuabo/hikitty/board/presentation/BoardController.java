package io.nuabo.hikitty.board.presentation;

import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.board.domain.ImagePlanHeartGet;
import io.nuabo.hikitty.board.domain.PageImageGet;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.PageNationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시판 조회 API")
@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "모금자 게시판 전체 조회 - page 0부터 시작 - 쿼리 스트링 입니다!")
    @GetMapping
    public ResponseEntity<ApiResult<Page<PageImageGet>>> getBoards(
            @Valid @ModelAttribute PageNationRequest pageNationRequest
    ) {
        return ResponseEntity.ok().body(ApiUtils.success(boardService.getPages(pageNationRequest)));
    }

    @Operation(summary = "모금자 게시판 하나 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<ImagePlanHeartGet> getBoard(
            @PathVariable Long boardId
    ) {

        return ResponseEntity.ok().body(boardService.getById(boardId));
    }

    @Operation(summary = "모금자 글쓴이 프로필 조회 - 쿼리 스트링입니다!", description = "모금자가 작성한 게시글 가져오기")
    @GetMapping(value = "/fundraisers/{fundraiserId}")
    public ResponseEntity<ApiResult<Page<PageImageGet>>> getBoards(
            @PathVariable Long fundraiserId,
            @Valid @ModelAttribute PageNationRequest pageNationRequest
    ) {
        return ResponseEntity.ok().body(ApiUtils.success(boardService.getPagesByFundraiserId(fundraiserId, pageNationRequest)));
    }
}
