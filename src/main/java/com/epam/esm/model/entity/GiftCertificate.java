package com.epam.esm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Date createDate;
    private Date lastUpdateDate;
    private List<Tag> tags = new LinkedList<>();

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void removeTag(Tag tag){
        this.tags.remove(tag);
    }
}
