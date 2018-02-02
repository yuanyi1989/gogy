package com.github.gogy.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@EnableScheduling
@Configuration
@SpringBootApplication
public class MonitorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorClientApplication.class, args);
    }

}
