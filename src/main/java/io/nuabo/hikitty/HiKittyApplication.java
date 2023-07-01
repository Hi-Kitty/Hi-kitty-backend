package io.nuabo.hikitty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "io.nuabo")
public class HiKittyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiKittyApplication.class, args);
    }

}
