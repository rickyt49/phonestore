package com.axonactive.phonestore.repository;

import com.axonactive.phonestore.entity.Store;
import com.axonactive.phonestore.service.dto.CustomerAndTotalPurchaseDto;
import com.axonactive.phonestore.service.dto.PhoneModelAndAmountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    @Query("SELECT new com.axonactive.phonestore.service.dto.PhoneModelAndAmountDto(s.model, count (p.id)) " +
            "FROM PhysicalPhone p, Specification s, Store st " +
            "WHERE p.specification.model = s.model " +
            "AND p.store.id = st.id " +
            "AND p.phoneStatus = 'AVAILABLE' " +
            "AND st.id = ?1 " +
            "GROUP BY s.model")
    List<PhoneModelAndAmountDto> getPhoneModelAndItsAmountByStoreId(Integer storeId);

    @Query("SELECT new com.axonactive.phonestore.service.dto.CustomerAndTotalPurchaseDto(c.fullName, sum(b.totalSellPrice)) " +
            "FROM Customer c, Bill b " +
            "WHERE c.id = b.customer.id " +
            "GROUP BY c.id")
    List<CustomerAndTotalPurchaseDto> getCustomerTotalPurchaseReportByStoreId(Integer storeId);
}
