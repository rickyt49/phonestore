package com.axonactive.phonestore.api.request;


import com.axonactive.phonestore.entity.Condition;
import com.axonactive.phonestore.entity.PhoneStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalPhoneRequest {
    private String imei;
    private String color;
    private Integer memorySize;
    private PhoneStatus phoneStatus;
    private Condition condition;
    private Integer warranty;
    private Integer importPrice;
    private LocalDate importDate;
    private Integer storeId;
    private Integer supplierId;
    private Integer specificationId;
}
