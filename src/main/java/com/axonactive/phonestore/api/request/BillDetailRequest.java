package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillDetailRequest {
    private Integer billId;
    private Integer phoneId;
    private Integer sellPrice;
    private Integer discountAmount;
}
