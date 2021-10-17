package com.epam.esm.mapper;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate result = new GiftCertificate();
        result.setId(resultSet.getInt("id"));
        result.setName(resultSet.getString("name"));
        result.setDescription(resultSet.getString("description"));
        result.setPrice(resultSet.getDouble("price"));
        result.setDuration(resultSet.getInt("duration"));
        result.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
        result.setLastUpdateDate(resultSet.getTimestamp("last_update_date").toLocalDateTime());
        result.addTag(new Tag(resultSet.getInt("tId"), resultSet.getString("tName")));

        return result;
    }

}
