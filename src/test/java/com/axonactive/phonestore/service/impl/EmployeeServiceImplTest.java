package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    void testGetAllActiveEmployee_shouldReturnValueDifferFrom0_whenUsed() {
        assertNotEquals(0, employeeService.getAllActiveEmployee().size());
    }
}