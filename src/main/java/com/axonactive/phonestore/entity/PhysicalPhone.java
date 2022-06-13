package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhysicalPhone {
    @Transient
    private final String IMEI_NUMBER_REGEX = "(^$|[0-9]{5,15})";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true, nullable = false, length = 15)
    @Size(min = 5, max = 15)
    @Pattern(regexp = IMEI_NUMBER_REGEX)
    private String imei;

    private String color;

    private Integer memorySize;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneStatus phoneStatus;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

    private Integer warranty;

    @NotNull
    @Column(nullable = false)
    private Integer importPrice;

    @NotNull
    @Column(nullable = false)
    private LocalDate importDate;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Specification specification;
}
