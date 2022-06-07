package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String model;
    private String cpu;
    private String gpu;
    private Integer ram;
    private String os;
    private String screenType;
    private String screenResolution;
    private String mainCamera;
    private String selfieCamera;
    private Integer batteryCapacity;
    @NotNull
    @Column(nullable = false)
    private String manufacturer;
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ManufacturerStatus manufacturerStatus;


}
