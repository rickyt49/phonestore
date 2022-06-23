package com.axonactive.phonestore.api.request;

import com.axonactive.phonestore.entity.enumerate.EmployeeStatus;
import com.axonactive.phonestore.entity.enumerate.EmployeeType;
import com.axonactive.phonestore.entity.enumerate.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequest {
    private final String PHONE_NUMBER_REGEX = "(^$|[0-9]{10})";
    private String firstName;
    private String lastName;
    private Gender gender;

    @Email
    private String email;

    @Pattern(regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;
    private String address;
    private EmployeeType employeeType;
    private EmployeeStatus employeeStatus;
    private Integer managerId;
    private Integer storeId;
}
