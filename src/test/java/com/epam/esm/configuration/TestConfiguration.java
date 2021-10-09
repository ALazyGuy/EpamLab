package com.epam.esm.configuration;

import com.epam.esm.dao.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfiguration {

    @Bean
    public TagDao tagDaoMock(){
        return mock(TagDao.class);
    }

    @Bean
    public TagService tagService(){
        return new TagServiceImpl(tagDaoMock());
    }

}
