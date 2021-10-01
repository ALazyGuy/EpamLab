package com.epam.esm.mapper;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class CertificateMapper implements RowMapper<GiftCertificate> {

    private final TagDao tagDao;

    public CertificateMapper(TagDao tagDao) {
        this.tagDao = tagDao;
    }

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

        Arrays.stream(resultSet.getString("tId").split(" "))
                .map(id -> tagDao.loadById(Integer.valueOf(id)))
                .forEach(tag-> result.addTag(tag.get()));

        return result;
    }

}
