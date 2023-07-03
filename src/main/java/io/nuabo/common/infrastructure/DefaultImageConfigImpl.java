package io.nuabo.common.infrastructure;


import io.nuabo.common.application.port.DefaultImageConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultImageConfigImpl implements DefaultImageConfig {

    @Value("${default.image.fundraiser.url}")
    private String defaultImageFundraiserUrl;

    @Value("${default.image.fundraiser.original-name}")
    private String defaultImageFundraiserOriginalName;

    @Value("${default.image.doner.url}")
    private String defaultImageDonerUrl;

    @Value("${default.image.doner.original-name}")
    private String defaultImageDonerOriginalName;

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
