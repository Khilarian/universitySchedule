package com.rumakin.universityschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Import;

import com.rumakin.universityschedule.config.Swagger2Config;
import com.rumakin.universityschedule.config.UiConfig;

@SpringBootApplication
@Import({ UiConfig.class, Swagger2Config.class})
@EnableAutoConfiguration
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
