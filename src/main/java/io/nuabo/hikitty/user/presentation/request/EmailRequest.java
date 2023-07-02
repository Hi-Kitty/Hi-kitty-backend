package io.nuabo.hikitty.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailRequest {

    @Email
    @Size(min = 1, max = 255)
    @NotNull
    private final String email;

    @Builder
    public EmailRequest(String email) {
        this.email = email;
    }
}
