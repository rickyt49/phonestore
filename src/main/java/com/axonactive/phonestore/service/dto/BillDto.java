package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillDto {
    private Integer id;
    private String employeeFullName;
    private String customerName;
    private LocalDate saleDate;
    private List<BillDetailDto> billDetails;
    private Integer totalSellPrice;
}
