package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecificationDto {
    private String model;
    private String cpu;
    private String gpu;
    private String ram;
    private String screen;
    private String mainCamera;
    private String selfieCamera;
    private String batteryCapacity;

}
