package com.epam.esm.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
public class CertificateUpdateDTO {
    private String name = "";
    private String description = "";
    private double price;
    private int duration;
}
