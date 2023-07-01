package io.nuabo.hikitty.user.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {


    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // 기본 Thread 개수
        executor.setMaxPoolSize(10); // 최대 Thread 개수
        executor.setQueueCapacity(500); // MaxPoolSize가 도달하면 Queue에 쌓이는데, Queue의 크기

        executor.setThreadNamePrefix("async-"); // Spring에서 생성하는 Thread 이름의 접두사
        executor.initialize();
        return executor;
    }
}