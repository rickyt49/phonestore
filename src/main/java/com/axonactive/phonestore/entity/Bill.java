package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate saleDate = LocalDate.now();

    private Integer totalSellPrice;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<BillDetail> billDetails;

    public void setTotalSellPrice() {
        int totalPrice = 0;
        if (null == this.billDetails) {
            this.totalSellPrice = 0;
        } else {
            for (BillDetail billDetail : this.billDetails) {
                totalPrice += billDetail.getFinalSellPrice();
            }
        this.totalSellPrice = totalPrice;
        }
    }
}
