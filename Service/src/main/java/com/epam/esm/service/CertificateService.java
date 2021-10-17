package com.epam.esm.service;

import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.dto.CertificateUpdateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CertificateService {
    Optional<GiftCertificate> create(CertificateCreateDTO certificateCreateDTO);
    boolean delete(int id);
    Optional<GiftCertificate> getById(int id);
    List<GiftCertificate> getAllCertificates();
    List<GiftCertificate> searchBy(String tagName, String namePart, String descriptionPart);
    Optional<GiftCertificate> updateCertificate(int id, CertificateUpdateDTO certificateUpdateDTO);
}
