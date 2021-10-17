package com.epam.esm.extractor;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {

    private final RowMapper<GiftCertificate> rowMapper;

    private Map<Integer, GiftCertificate> certificates;

    public CertificateExtractor(RowMapper<GiftCertificate> rowMapper) {
        this.rowMapper = rowMapper;
        certificates = new HashMap<>();
    }

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        GiftCertificate giftCertificate;
        while(rs.next()) {
             giftCertificate = rowMapper.mapRow(rs, rs.getRow());
            if (certificates.containsKey(giftCertificate.getId())) {
                certificates.get(giftCertificate.getId()).addTag(giftCertificate.getTags().get(0));
            } else {
                certificates.put(giftCertificate.getId(), giftCertificate);
            }
        }

        return certificates.values().stream().collect(Collectors.toList());
    }
}
