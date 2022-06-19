package com.axonactive.phonestore.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    private Integer employeeId;
    private Integer customerId;
        private List<BillDetailUpdateRequest> billDetailsDto;
}
