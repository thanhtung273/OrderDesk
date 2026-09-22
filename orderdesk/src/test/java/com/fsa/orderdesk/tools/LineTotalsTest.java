package com.fsa.orderdesk.tools;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTotalsTest {

    @Test
    void testHappyPathTwoRows() {
        List<String> lines = List.of(
            "sku,quantity,unit_price",
            "KB-01,3,150000",
            "MS-04,1,99999"
        );

        LineTotals.Result result = LineTotals.processLines(lines);

        assertEquals(2, result.lines());
        assertEquals(4, result.units());
        assertEquals(new BigDecimal("549999"), result.total());
    }

    @Test
    void testHeaderOnlyReturnsZeros() {
        List<String> lines = List.of("sku,quantity,unit_price");

        LineTotals.Result result = LineTotals.processLines(lines);

        assertEquals(0, result.lines());
        assertEquals(0, result.units());
        assertEquals(BigDecimal.ZERO, result.total());
    }

    @Test
    void testSingleRowWithZeroPriceAndWhitespace() {
        List<String> lines = List.of(
            "sku,quantity,unit_price",
            "  ITEM-01 ,  5 ,  0  "
        );

        LineTotals.Result result = LineTotals.processLines(lines);

        assertEquals(1, result.lines());
        assertEquals(5, result.units());
        assertEquals(new BigDecimal("0"), result.total());
    }

    @Test
    void testQuantityZeroThrowsErrorNamingLine() {
        String badLine = "KB-01,0,150000";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }

    @Test
    void testQuantityNegativeThrowsErrorNamingLine() {
        String badLine = "KB-01,-3,150000";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }

    @Test
    void testQuantityNotAWholeNumberThrowsErrorNamingLine() {
        String badLine = "KB-01,2.5,150000";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }

    @Test
    void testPriceNegativeThrowsErrorNamingLine() {
        String badLine = "MS-04,1,-500";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }

    @Test
    void testPriceUnparseableThrowsErrorNamingLine() {
        String badLine = "MS-04,1,abc";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }

    @Test
    void testFewerThanThreeFieldsThrowsErrorNamingLine() {
        String badLine = "KB-01,3";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }

    @Test
    void testTrailingEmptyFieldNotDroppedThrowsErrorNamingLine() {
        String badLine = "KB-01,3,";
        List<String> lines = List.of("sku,quantity,unit_price", badLine);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> LineTotals.processLines(lines)
        );
        assertTrue(ex.getMessage().contains(badLine));
    }
}