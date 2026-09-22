package com.fsa.orderdesk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void testGetVersion() {
        assertEquals("1.0-SNAPSHOT", App.getVersion());
    }

    @Test
    void testCalculateDiscount() {
        assertEquals(90, App.calculateDiscount(100, 10));
    }
}