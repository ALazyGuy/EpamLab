package com.epam.esm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private int id;
    private String name;
    private List<GiftCertificate> giftCertificates = new LinkedList<>();

    public void addGiftCertificate(GiftCertificate giftCertificate){
        this.giftCertificates.add(giftCertificate);
    }

    public void removeGiftCertificate(GiftCertificate giftCertificate){
        this.giftCertificates.remove(giftCertificate);
    }
}
