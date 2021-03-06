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


    @NotNull
    @Column(nullable = false)
    private Integer sellPrice;

    private Integer discountAmount;

    private Integer finalSellPrice;

    @ManyToOne
    @JoinColumn(name = "physical_phone_id", nullable = false)
    @NotNull
    private PhysicalPhone physicalPhone;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Bill bill;

    public Integer getFinalSellPrice() {
        if (this.discountAmount == 0)
            return this.sellPrice;
        return this.sellPrice - this.discountAmount;
    }
}
