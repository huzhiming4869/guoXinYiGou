package com.xcy.gxyg.task;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhiMing
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.xcy.gxyg.common.mapper")
@ComponentScan({"com.xcy.gxyg"})
@EnableFeignClients
@EnableCaching
@EnableScheduling
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
