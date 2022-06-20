package com.axonactive.phonestore.service.impl;

import com.axonactive.phonestore.service.StoreService;
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
class StoreServiceImplTest {

    @Autowired
    StoreService storeService;

    @Test
    void testGetPhoneModelAndItsAmountByStoreId_shouldReturnValueDifferFrom0AndNotNull_whenInputStoreId1() {
        assertNotEquals(0, storeService.getPhoneModelAndItsAmountByStoreId(1).size());
        assertNotNull(storeService.getPhoneModelAndItsAmountByStoreId(1));

    }
}