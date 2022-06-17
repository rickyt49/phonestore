package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDto {
    private Integer id;
    private String fullName;

    private String phoneNumber;

    private String address;
}
