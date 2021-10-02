package com.epam.esm.service;

import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CertificateService {
    boolean create(CertificateCreateDTO certificateCreateDTO);
    void delete(int id);
    List<GiftCertificate> getAllCertificates();
    List<GiftCertificate> searchBy(String tagName, String namePart, String descriptionPart);
    //TODO Add update
}
