package io.nuabo.hikitty.mock;

import io.nuabo.common.application.port.DefaultImageConfig;
import lombok.Builder;

public class TestDefaultImageConfig implements DefaultImageConfig {

    private final String defaultImageDonerUrl;
    private final String defaultImageDonerOriginalName;
    private final String defaultImageFundraiserUrl;
    private final String defaultImageFundraiserOriginalName;

    @Builder
    public TestDefaultImageConfig(String defaultImageDonerUrl, String defaultImageDonerOriginalName, String defaultImageFundraiserUrl, String defaultImageFundraiserOriginalName) {
        this.defaultImageDonerUrl = defaultImageDonerUrl;
        this.defaultImageDonerOriginalName = defaultImageDonerOriginalName;
        this.defaultImageFundraiserUrl = defaultImageFundraiserUrl;
        this.defaultImageFundraiserOriginalName = defaultImageFundraiserOriginalName;
    }

    @Override
    public String getDefaultImageDonerUrl() {
        return defaultImageDonerUrl;
    }

    @Override
    public String getDefaultImageDonerOriginalName() {
        return defaultImageDonerOriginalName;
    }

    @Override
    public String getDefaultImageFundraiserUrl() {
        return defaultImageFundraiserUrl;
    }

    @Override
    public String getDefaultImageFundraiserOriginalName() {
        return defaultImageFundraiserOriginalName;
    }
}
