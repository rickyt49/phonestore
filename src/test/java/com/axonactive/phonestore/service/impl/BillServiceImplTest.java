package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.service.BillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BillServiceImplTest {

    @Autowired
    BillService billService;

    @Test
    void testFindByEmployeeIdAndSaleDateBetween_shouldReturnValueDifferFrom0_whenInputEmpId1AndDateBetweenJun1st2022AndJun30th2022() {
        assertNotEquals(0, billService.findByEmployeeIdAndSaleDateBetween(1, LocalDate.of(2022, 6, 1), LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindByEmployeeId_shouldReturnValueDifferFrom0_whenInputEmpID1() {
        assertNotEquals(0, billService.findByEmployeeId(1).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateBefore_shouldReturnValueDifferFrom0_whenInputEmpId1AndDateJun30th2022() {
        assertNotEquals(0, billService.findByEmployeeIdAndSaleDateBefore(1, LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateBefore_shouldReturn0_whenInputEmpId1AndDateJun15th2022() {
        assertEquals(0, billService.findByEmployeeIdAndSaleDateBefore(1, LocalDate.of(2022, 6, 15)).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateAfter_shouldReturnValueDifferFrom0_whenInputEmpId1AndDateJun15th2022() {
        assertNotEquals(3, billService.findByEmployeeIdAndSaleDateAfter(1, LocalDate.of(2022, 6, 15)).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateAfter_shouldReturnValueDifferFrom0_whenInputEmpId1AndDateJun30th2022() {
        assertNotEquals(0, billService.findByEmployeeIdAndSaleDateBefore(1, LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindAllBillByStore_shouldReturnValueDifferFrom0_whenInput1() {
        assertNotEquals(0, billService.findAllBillByStore(1).size());
    }

    @Test
    void testFindAllBillByStoreAndSaleDateAfter_shouldReturnValueDifferFrom0_whenInputStoreId1AndDateJun15th2022() {
        assertNotEquals(0, billService.findAllBillByStoreAndSaleDateAfter(1, LocalDate.of(2022, 6, 15)).size());
    }

    @Test
    void testFindAllBillByStoreAndSaleDateBefore_shouldReturnValueDifferFrom0_whenInputStoreId1AndDateJun30th2022() {
        assertNotEquals(0, billService.findAllBillByStoreAndSaleDateBefore(1, LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindAllBillByStoreAndSaleDateBetween_shouldReturnValueDifferFrom0_whenInputEmpId1AndDateBetweenJun1st2022AndJun30th2022() {
        assertNotEquals(0, billService.findAllBillByStoreAndSaleDateBetween(1, LocalDate.of(2022, 6, 15), LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testGetTotalGrossProfit_shouldReturnValueDifferFrom0_whenInputStoreId1() {
        assertNotEquals(0, billService.getTotalGrossProfit(1));
    }

    @Test
    void testGetTotalGrossProfitAfter_shouldReturnValueDifferFrom0_whenInputStoreId1AndDateJun15th2022() {
        assertNotEquals(0, billService.getTotalGrossProfitAfter(1, LocalDate.of(2022, 6, 15)));
    }


    @Test
    void testGetTotalGrossProfitBefore_shouldReturnValueDifferFrom0_whenInputStoreId1AndDateJun30th2022() {
        assertNotEquals(0, billService.getTotalGrossProfitBefore(1, LocalDate.of(2022, 6, 30)));
    }

    @Test
    void testGetTotalGrossProfitBetween_shouldReturnValueDifferFrom0_whenInputStoreId1AndDateJun15th2022AndJun30th2022() {
        assertNotEquals(0, billService.getTotalGrossProfitBetween(1, LocalDate.of(2022, 6, 15), LocalDate.of(2022, 6, 30)));
    }
}