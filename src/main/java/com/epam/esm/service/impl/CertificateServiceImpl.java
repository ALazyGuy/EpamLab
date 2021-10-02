package com.epam.esm.service.impl;

import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.dto.CertificateDeleteDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public void create(CertificateCreateDTO certificateCreateDTO) {
        certificateDao.create(certificateCreateDTO.getName(),
                certificateCreateDTO.getDescription(),
                certificateCreateDTO.getPrice(),
                certificateCreateDTO.getDuration(),
                certificateCreateDTO.getTags());
    }

    @Override
    public void delete(CertificateDeleteDTO certificateDeleteDTO) {
        certificateDao.delete(certificateDeleteDTO.getId());
    }

    @Override
    public List<GiftCertificate> getAllCertificates() {
        return certificateDao.loadAll();
    }

    @Override
    public List<GiftCertificate> searchBy(String tagName, String namePart, String descriptionPart) {
        SQLQueryParamBuilder sqlQueryParamBuilder = SQLQueryParamBuilder
                .initEquals("t.name", tagName)
                .like("certificates.name", namePart)
                .like("certificates.description", descriptionPart);
        return certificateDao.search(sqlQueryParamBuilder);
    }
}
