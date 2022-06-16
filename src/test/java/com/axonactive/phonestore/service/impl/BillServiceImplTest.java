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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BillServiceImplTest {

    @Autowired
    BillService billService;

    @Test
    void testFindByEmployeeIdAndSaleDateBetween_shouldReturn3_whenInputEmpId1AndDateBetweenJun1st2022AndJun30th2022() {
        assertEquals(3, billService.findByEmployeeIdAndSaleDateBetween(1, LocalDate.of(2022, 6, 1), LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindByEmployeeId_shouldReturn3_whenInputEmpID1() {
        assertEquals(3, billService.findByEmployeeId(1).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateBefore_shouldReturn3_whenInputEmpId1AndDateJun30th2022() {
        assertEquals(3, billService.findByEmployeeIdAndSaleDateBefore(1, LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateBefore_shouldReturn0_whenInputEmpId1AndDateJun15th2022() {
        assertEquals(0, billService.findByEmployeeIdAndSaleDateBefore(1, LocalDate.of(2022, 6, 15)).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateAfter_shouldReturn3_whenInputEmpId1AndDateJun15th2022() {
        assertEquals(3, billService.findByEmployeeIdAndSaleDateAfter(1, LocalDate.of(2022, 6, 15)).size());
    }

    @Test
    void testFindByEmployeeIdAndSaleDateAfter_shouldReturn30_whenInputEmpId1AndDateJun30th2022() {
        assertEquals(0, billService.findByEmployeeIdAndSaleDateAfter(1, LocalDate.of(2022, 6, 30)).size());
    }

    @Test
    void testFindAllBillByStore_shouldReturn3_whenInput1() {
        assertEquals(3, billService.findAllBillByStore(1).size());
    }

    @Test
    void testFindAllBillByStoreAndSaleDateAfter() {
    }

    @Test
    void testFindAllBillByStoreAndSaleDateBefore() {
    }

    @Test
    void testFindAllBillByStoreAndSaleDateBetween() {
    }

    @Test
    void testGetTotalGrossProfit() {
    }

    @Test
    void testGetTotalGrossProfitAfter() {
    }

    @Test
    void testGetTotalGrossProfitBefore() {
    }

    @Test
    void testGetTotalGrossProfitBetween() {
    }
}