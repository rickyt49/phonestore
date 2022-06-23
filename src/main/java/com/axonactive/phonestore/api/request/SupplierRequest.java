package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierRequest {
    private final String PHONE_NUMBER_REGEX = "(^$|[0-9]{10})";

    private String fullName;
    @Pattern(regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;
    private String address;
}
