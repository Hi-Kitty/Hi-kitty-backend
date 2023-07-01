package io.nuabo.common.domain.exception;

public class MessageException extends RuntimeException {

    public MessageException(String datasource, String name) {
        super(datasource + "에서" + name + "에게 메시지를 보내는 데 실패하였습니다.");
    }

}