package com.epam.esm.configuration;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ServiceTestConfiguration {

    @Bean
    public TagDao tagDao(){
        return mock(TagDao.class);
    }

    @Bean
    public TagService tagService(){
        return new TagServiceImpl(tagDao());
    }

    @Bean
    public CertificateDao certificateDao(){
        return mock(CertificateDao.class);
    }

    @Bean
    CertificateService certificateService(){
        return new CertificateServiceImpl(certificateDao());
    }

}
