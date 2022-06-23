package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillDetailRequest {
    private final String IMEI_NUMBER_REGEX = "(^$|[0-9]{15})";

    private Integer billId;

    @Pattern(regexp = IMEI_NUMBER_REGEX, message = "IMEI number must have 15 numbers")
    private String imei;

    @Min(value = 0, message = "Sell price must be positive")
    private Integer sellPrice;
    @Min(value = 0, message = "Discount amount must be positive")
    private Integer discountAmount;
}
