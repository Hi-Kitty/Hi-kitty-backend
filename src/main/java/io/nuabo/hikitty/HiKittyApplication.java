package io.nuabo.hikitty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients(basePackages = "io.nuabo")
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "io.nuabo")
public class HiKittyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiKittyApplication.class, args);
    }

}
