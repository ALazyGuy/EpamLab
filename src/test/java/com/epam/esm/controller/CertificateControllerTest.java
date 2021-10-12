package com.epam.esm.controller;

import com.epam.esm.configuration.ControllerTestConfiguration;
import com.epam.esm.configuration.DefaultValueHandlerMethodArgumentResolver;
import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.dto.CertificateUpdateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ControllerTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificateControllerTest {

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateController certificateController;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeAll
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(certificateController).setCustomArgumentResolvers(new DefaultValueHandlerMethodArgumentResolver()).build();
    }

    @AfterEach
    public void postEach(){
        reset(certificateService);
    }

    @Test
    @SneakyThrows
    public void deleteSuccessTest(){
        when(certificateService.delete(1)).thenReturn(true);
        mockMvc.perform(delete("/v1/certificate/1"))
                .andExpect(status().isOk());

        verify(certificateService, times(1)).delete(1);
    }

    @Test
    @SneakyThrows
    public void deleteFailTest(){
        when(certificateService.delete(2)).thenReturn(false);
        mockMvc.perform(delete("/v1/certificate/2"))
                .andExpect(status().is(404));

        verify(certificateService, times(1)).delete(2);
    }

    @Test
    @SneakyThrows
    public void searchEmptyTest(){
        when(certificateService.searchBy("", "", "")).thenReturn(List.of());
        mockMvc.perform(get("/v1/certificate"))
                .andExpect(status().is(204));

        verify(certificateService, times(1)).searchBy("", "", "");
    }

    @Test
    @SneakyThrows
    public void searchListTest(){
        List<GiftCertificate> certificates = List.of(
                new GiftCertificate(-1, "qwe", "desc", 1, 1, null, null, List.of(
                        new Tag(-10, "q")
                ))
        );

        when(certificateService.searchBy("q", "w", "e")).thenReturn(certificates);
        mockMvc.perform(get("/v1/certificate?tagName=q&namePart=w&descriptionPart=e"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(certificates)));

        verify(certificateService, times(1)).searchBy("q", "w", "e");
    }

    @Test
    @SneakyThrows
    public void createNotValidTest(){
        mockMvc.perform(post("/v1/certificate")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));

        verify(certificateService, never()).create(any());
    }

    @Test
    @SneakyThrows
    public void createConflictTest(){
        CertificateCreateDTO certificateCreateDTO = new CertificateCreateDTO();
        certificateCreateDTO.setName("c1");
        certificateCreateDTO.setDescription("desk");
        certificateCreateDTO.setPrice(10);
        certificateCreateDTO.setDuration(10);
        when(certificateService.create(certificateCreateDTO)).thenReturn(false);

        mockMvc.perform(post("/v1/certificate")
                        .content(objectMapper.writeValueAsString(certificateCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409));

        verify(certificateService, times(1)).create(certificateCreateDTO);
    }

    @Test
    @SneakyThrows
    public void createSuccessTest(){
        CertificateCreateDTO certificateCreateDTO = new CertificateCreateDTO();
        certificateCreateDTO.setName("c1");
        certificateCreateDTO.setDescription("desk");
        certificateCreateDTO.setPrice(10);
        certificateCreateDTO.setDuration(10);
        when(certificateService.create(certificateCreateDTO)).thenReturn(true);

        mockMvc.perform(post("/v1/certificate")
                        .content(objectMapper.writeValueAsString(certificateCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        verify(certificateService, times(1)).create(certificateCreateDTO);
    }

    @Test
    @SneakyThrows
    public void updateFailTest(){
        CertificateUpdateDTO certificateUpdateDTO = new CertificateUpdateDTO();
        certificateUpdateDTO.setName("c1");
        certificateUpdateDTO.setDescription("desk");
        certificateUpdateDTO.setDuration(10);
        certificateUpdateDTO.setPrice(10);

        when(certificateService.updateCertificate(1, certificateUpdateDTO)).thenReturn(false);

        mockMvc.perform(patch("/v1/certificate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificateUpdateDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));

        verify(certificateService, times(1)).updateCertificate(1, certificateUpdateDTO);
    }

    @Test
    @SneakyThrows
    public void updateSuccessTest(){
        CertificateUpdateDTO certificateUpdateDTO = new CertificateUpdateDTO();
        certificateUpdateDTO.setName("c1");
        certificateUpdateDTO.setDescription("desk");
        certificateUpdateDTO.setDuration(10);
        certificateUpdateDTO.setPrice(10);

        when(certificateService.updateCertificate(1, certificateUpdateDTO)).thenReturn(true);

        mockMvc.perform(patch("/v1/certificate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificateUpdateDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(certificateService, times(1)).updateCertificate(1, certificateUpdateDTO);
    }
}
