package io.nuabo.hikitty.board.presentation;

import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.board.domain.BoardFundraiserImagePlan;
import io.nuabo.hikitty.board.domain.PageImageGet;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PageBoardRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import io.nuabo.hikitty.board.presentation.response.BoardFundraiserImagePlanResponse;
import io.nuabo.hikitty.security.presentation.port.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static io.nuabo.common.domain.utils.ApiUtils.success;

@Tag(name = "모금자 - 권한용 API")
@SecurityRequirement(name = "Bearer Authentication")
@Builder
@RestController
@RequestMapping("/api/v1/fundraisers")
@RequiredArgsConstructor
public class FundraiserController {

    private final BoardService boardService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "모금자 게시판 생성")
    @PostMapping(value = "/boards", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResult<BoardFundraiserImagePlanResponse>> create(
            @Valid @RequestPart BoardCreateRequest boardCreateRequest,
            @RequestPart(value = "img") MultipartFile img,
            @Valid @RequestPart List<PlanCreateRequest> planCreatesRequest
            ) {
        String email = authenticationService.getEmail();
        BoardFundraiserImagePlan boardFundraiserImagePlan = boardService.create(boardCreateRequest, email, img, planCreatesRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(BoardFundraiserImagePlanResponse.from(boardFundraiserImagePlan)));
    }

    @Operation(summary = "모금자 프로필 - 쿼리 스트링입니다!", description = "모금자 이메일, 이름 + 모금자가 작성한 게시글 가져오기")
    @GetMapping(value = "/boards")
    public ResponseEntity<ApiResult<Page<PageImageGet>>> getBoards(
            @Valid @ModelAttribute PageBoardRequest pageBoardRequest
    ) {
        String email = authenticationService.getEmail();
        return ResponseEntity.ok().body(ApiUtils.success(boardService.getPagesByFundraiserEmail(pageBoardRequest, email)));
    }

}
