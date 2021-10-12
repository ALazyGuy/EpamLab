package com.epam.esm.configuration;

import com.epam.esm.controller.TagController;
import com.epam.esm.service.TagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@Configuration
public class ControllerTestConfiguration {

    @Bean
    public TagService tagService(){
        return mock(TagService.class);
    }

    @Bean
    public TagController tagController(){
        return new TagController(tagService());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
