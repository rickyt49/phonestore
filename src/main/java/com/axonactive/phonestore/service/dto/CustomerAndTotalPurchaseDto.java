package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerAndTotalPurchaseDto {
    private String customerFullName;
    private Long totalPurchaseAmount;
}
