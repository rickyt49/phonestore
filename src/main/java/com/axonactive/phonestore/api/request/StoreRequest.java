package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {
    private final String PHONE_NUMBER_REGEX = "(^$|[0-9]{10})";


    private Integer ownerId;
    private String name;
    private String address;

    @Pattern(regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;
    private String city;
}
