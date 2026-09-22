package com.fsa.orderdesk;

public class App {

    // Package-private pure method theo yêu cầu
    static String getVersion() {
        return "1.0-SNAPSHOT";
    }

    // Pure method tính toán đơn giản
    static int calculateDiscount(int originalPrice, int discountPercent) {
        return originalPrice - (originalPrice * discountPercent / 100);
    }

    public static void main(String[] args) {
        System.out.println("OrderDesk Version: " + getVersion());
        System.out.println("Discounted Price (100 - 10%): " + calculateDiscount(100, 10));
    }
}