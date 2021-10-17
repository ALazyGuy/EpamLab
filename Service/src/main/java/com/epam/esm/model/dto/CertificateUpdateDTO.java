package com.epam.esm.model.dto;

import com.epam.esm.configuration.annotation.InjectString;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CertificateUpdateDTO {
    @InjectString
    @Length(max = 30, message = "Too long name")
    private String name;
    @InjectString
    @Length(max = 100, message = "Too long description")
    private String description;
    @Min(value = 0, message = "Price cannot be negative")
    private double price;
    @Min(value = 1, message = "Duration cannot be less than one day")
    private int duration;
    @NotNull(message = "")
    private List<String> tags;
}
