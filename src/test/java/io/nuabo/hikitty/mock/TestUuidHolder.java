package io.nuabo.hikitty.mock;


import io.nuabo.common.application.port.UuidHolder;
import lombok.Setter;

@Setter
public class TestUuidHolder implements UuidHolder {

    private String uuid;

    public TestUuidHolder(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String randomNumber() {
        return uuid;
    }

    @Override
    public String random() {
        return uuid;
    }
}
