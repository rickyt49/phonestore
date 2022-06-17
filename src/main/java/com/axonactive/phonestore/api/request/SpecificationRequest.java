package com.axonactive.phonestore.api.request;

import com.axonactive.phonestore.entity.ManufacturerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecificationRequest {
    private String model;
    private String cpu;
    private String gpu;
    private Integer ram;
    private String screenType;
    private String screenResolution;
    private String mainCamera;
    private String selfieCamera;
    private Integer batteryCapacity;
    private String manufacturer;
    private String os;
    private ManufacturerStatus manufacturerStatus;
}
