package io.nuabo.hikitty.board.infrastructure;


import io.nuabo.hikitty.board.application.port.DefaultImageConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultImageConfigImpl extends DefaultImageConfig {

    @Value("${default.image.fundraiser.url}")
    private String defaultImageFundraiserUrl;

    @Value("${default.image.fundraiser.original-name}")
    private String defaultImageFundraiserOriginalName;

    @Override
    public String getDefaultImageFundraiserUrl() {
        return defaultImageFundraiserUrl;
    }

    @Override
    public String getDefaultImageFundraiserOriginalName() {
        return defaultImageFundraiserOriginalName;
    }
}
