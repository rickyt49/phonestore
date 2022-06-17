package com.axonactive.phonestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAccountDto {
    private Integer id;
    private Integer employeeId;
    private String username;
    private String password;
}
