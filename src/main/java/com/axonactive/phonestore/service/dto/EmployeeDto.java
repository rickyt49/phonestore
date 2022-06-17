package com.axonactive.phonestore.service.dto;

import com.axonactive.phonestore.entity.EmployeeStatus;
import com.axonactive.phonestore.entity.EmployeeType;
import com.axonactive.phonestore.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private EmployeeType employeeType;
    private EmployeeStatus employeeStatus;
    private String storeName;
}
