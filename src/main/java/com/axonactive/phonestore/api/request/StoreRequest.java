package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {

    private Integer ownerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String city;
}
