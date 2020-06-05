package com.rumakin.universityschedule.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
public class UiConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
