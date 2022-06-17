package com.axonactive.phonestore.service.dto;

import com.axonactive.phonestore.entity.Condition;
import com.axonactive.phonestore.entity.PhoneStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalPhoneDto {
    private Integer id;
    private String model;
    private Long imei;
    private String color;
    private String memorySize;
    private String warranty;
    private Condition condition;
    private PhoneStatus phoneStatus;
    private Integer importPrice;
    private LocalDate importDate;
}
