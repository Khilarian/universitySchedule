package com.rumakin.universityschedule.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@PropertySource("classpath:swagger.properties")
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config {

    @Value("${description.field.response.message.201}")
    private String message201;
    @Value("${description.field.response.message.400}")
    private String message400;
    @Value("${description.field.response.message.404}")
    private String message404;
    @Value("${description.field.response.message.500}")
    private String message500;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.rumakin.universityschedule.restcontroller"))
                .paths(PathSelectors.any()).build().apiInfo(metaInfo()).produces(getProduces())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getCustomizedGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getCustomizedPostResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getCustomizedPutResponseMessages());
    }

    private ApiInfo metaInfo() {

        return new ApiInfoBuilder().title("University REST API")
                .description("Written in sweat and blood by Kirill Rumakin").version("0.1")
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Kirill Rumakin", "", "kirillrumakin@gmail.com")).build();
    }

    @Bean
    public UiConfiguration swaggerUiConfig() {
        return UiConfigurationBuilder.builder().operationsSorter(OperationsSorter.ALPHA).tagsSorter(TagsSorter.ALPHA)
                .defaultModelRendering(ModelRendering.MODEL).displayRequestDuration(true).build();
    }

    private List<ResponseMessage> getCustomizedGetResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(404).message(message404).build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message(message500).build());
        return responseMessages;
    }

    private List<ResponseMessage> getCustomizedPostResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(201).message(message201).build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message(message400).build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message(message500).build());
        return responseMessages;
    }

    private List<ResponseMessage> getCustomizedPutResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(400).message(message400).build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message(message404).build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message(message500).build());
        return responseMessages;
    }

    private Set<String> getProduces() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }
}
