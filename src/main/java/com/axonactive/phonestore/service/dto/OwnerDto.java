package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String address;
}
