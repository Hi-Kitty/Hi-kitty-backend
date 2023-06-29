package io.nuabo.hikitty.amazons3.mock;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.hikitty.amazons3.application.port.FileName;
import lombok.Getter;

@Getter
public class FakeFileName implements FileName {
    private final String originalFilename;
    private final String storeFilename;
    private final String keyName;

    public FakeFileName(String originalFilename,  UuidHolder uuidHolder) {
        this.originalFilename = originalFilename;
        int index = getOriginalFilename().lastIndexOf(".");
        String ext = getOriginalFilename().substring(index + 1);
        this.storeFilename = uuidHolder.random() + "." + ext;
        this.keyName = "nuabo/" + getStoreFilename();
    }
}
