package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailUpdateRequest {
    private String Imei;

    @Min(value = 0, message = "Sell price must be positive")
    private Integer sellPrice;
    @Min(value = 0, message = "Discount amount must be positive")
    private Integer discountAmount;
}
