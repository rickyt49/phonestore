package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer discountAmount;

    @NotNull
    @Column(nullable = false)
    private Integer sellPrice;

    @ManyToOne
    @JoinColumn(name = "physical_phone_id")
    private PhysicalPhone physicalPhone;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
