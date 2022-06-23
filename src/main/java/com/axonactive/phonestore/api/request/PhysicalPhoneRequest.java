package com.axonactive.phonestore.api.request;


import com.axonactive.phonestore.entity.enumerate.Condition;
import com.axonactive.phonestore.entity.enumerate.PhoneStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalPhoneRequest {
    private final String IMEI_NUMBER_REGEX = "(^$|[0-9]{15})";

    @Pattern(regexp = IMEI_NUMBER_REGEX, message = "IMEI number must have 15 numbers")
    private String imei;
    private String color;
    private Integer memorySize;
    private PhoneStatus phoneStatus;
    private Condition condition;
    private Integer warranty;
    private Integer importPrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate importDate;
    private Integer storeId;
    private Integer supplierId;
    private String specificationModel;
}
