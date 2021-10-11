package com.epam.esm.model.dto;

import com.epam.esm.configuration.annotation.InjectString;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class CertificateUpdateDTO {
    @InjectString
    private String name;
    @InjectString
    private String description;
    @Min(value = 0, message = "Price cannot be negative")
    private double price;
    @Min(value = 1, message = "Duration cannot be less than one day")
    private int duration;
}
