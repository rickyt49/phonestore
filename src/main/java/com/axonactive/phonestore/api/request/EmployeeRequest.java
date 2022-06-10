package com.axonactive.phonestore.api.request;

import com.axonactive.phonestore.entity.EmployeeStatus;
import com.axonactive.phonestore.entity.EmployeeType;
import com.axonactive.phonestore.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String address;
    private EmployeeType employeeType;
    private EmployeeStatus employeeStatus;
    private Integer managerId;
    private Integer storeId;
}
