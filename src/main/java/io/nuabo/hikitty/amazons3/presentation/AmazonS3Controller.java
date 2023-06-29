package io.nuabo.hikitty.amazons3.presentation;

import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.amazons3.presentation.response.AmazonS3Response;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.application.AmazonS3Service;
import io.nuabo.hikitty.amazons3.presentation.response.ApiAmazonS3Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "AmazonS3")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "success",content = @Content(schema = @Schema(implementation = ApiAmazonS3Response.class))),
    @ApiResponse(responseCode = "201", description = "created successfully", content = @Content(schema = @Schema(implementation = ApiAmazonS3Response.class))),
    @ApiResponse(responseCode = "404", description = "bad request", content = @Content(schema = @Schema(implementation = ApiResult.class))),
    @ApiResponse(responseCode = "500", description = "server error", content = @Content(schema = @Schema(implementation = ApiResult.class))),
})
@Builder
@RestController
@RequestMapping("/api/v1/amazons3")
@RequiredArgsConstructor
public class AmazonS3Controller {

    private final AmazonS3Service amazonS3Service;

    @Operation(summary = "디폴트 이미지 등록 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<AmazonS3Response>> upload(@RequestParam MultipartFile multipartFile) {
        AmazonS3Upload upload = amazonS3Service.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success(AmazonS3Response.from(upload)));
    }

    @Operation(summary = "id 값으로 찾는 API")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<AmazonS3Response>> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiUtils.success(AmazonS3Response.from(amazonS3Service.getById(id))));
    }

    @Operation(summary = "originalFilenames 값으로 찾는 API")
    @GetMapping("/originalFilenames/{originalName}")
    public ResponseEntity<ApiResult<AmazonS3Response>> getByOriginalName(@PathVariable String originalName) {
        return ResponseEntity.ok().body(ApiUtils.success(AmazonS3Response.from(amazonS3Service.getByOriginalName(originalName))));
    }
}
