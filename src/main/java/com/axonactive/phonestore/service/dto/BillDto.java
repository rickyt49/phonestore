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
    private String employeeFullName;
    private String customerName;
    private LocalDate saleDate;
    private List<BillDetailDto> billDetailDtos;
    private Integer totalSellPrice;
}