package io.nuabo.hikitty.board.presentation;

import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.board.application.port.PageImageDto;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.PageBoardRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시판 조회 API")
@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "모금자 게시판 조회")
    @GetMapping
    public ResponseEntity<ApiResult<Page<PageImageDto>>> getBoards(
            @Valid @ModelAttribute PageBoardRequest pageBoardRequest
    ) {
        Page<PageImageDto> pages = boardService.getPages(pageBoardRequest);
        return ResponseEntity.ok().body(ApiUtils.success(pages));
    }
}
