package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAccountPasswordUpdateRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPassWordConfirmation;
}
