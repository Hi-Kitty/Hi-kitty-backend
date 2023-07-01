package io.nuabo.hikitty.amazons3.domain;

import io.nuabo.common.application.port.UuidHolder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileName {
    private final String originalFilename;
    private final String storeFilename;
    private final String keyName;

    @Builder
    public FileName(String originalFilename, String storeFilename, String keyName) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.keyName = keyName;
    }


    public static FileName from(MultipartFile multipartFile, UuidHolder uuidHolder) {
        String tempOriginalFilename = multipartFile.getOriginalFilename();
        int index = tempOriginalFilename.lastIndexOf(".");
        String ext = tempOriginalFilename.substring(index + 1);
        String tempStoreFilename = uuidHolder.random() + "." + ext;

        return FileName.builder()
                .originalFilename(tempOriginalFilename)
                .storeFilename(tempStoreFilename)
                .keyName("nuabo/" + tempStoreFilename)
                .build();
    }
}
