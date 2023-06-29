package io.nuabo.hikitty.security.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "유저 - 기부자, 모금자 인증&인가(Authentication&Authorization)")
@RestController
@RequestMapping("/api/v0/auth")
@RequiredArgsConstructor
public class SecurityController {


}
