package io.nuabo.hikitty.amazons3.presentation.response;

import io.nuabo.common.domain.utils.ApiUtils.ApiResult;

public class ApiAmazonS3Response extends ApiResult<AmazonS3Response> {
    public ApiAmazonS3Response(AmazonS3Response data) {
        super(true, data, null);
    }
}
