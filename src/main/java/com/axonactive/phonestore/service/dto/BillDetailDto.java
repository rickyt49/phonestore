package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillDetailDto {
    private Integer id;
    private String model;
    private Long imei;
    private Integer sellPrice;
    private Integer discountAmount;
    private Integer finalSellPrice;
}
