package com.fsa.orderdesk.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LineTotals {

    // Record lưu trữ kết quả tổng hợp
    record Result(long lines, long units, BigDecimal total) {}

    // Pure logic method (package-private để test độc lập)
    static Result processLines(List<String> rawLines) {
        if (rawLines == null || rawLines.isEmpty()) {
            return new Result(0, 0, BigDecimal.ZERO);
        }

        long linesCount = 0;
        long totalUnits = 0;
        BigDecimal grandTotal = BigDecimal.ZERO;

        // Bỏ qua dòng header ở index 0
        for (int i = 1; i < rawLines.size(); i++) {
            String line = rawLines.get(i);

            // limit = -1 cực kỳ quan trọng để không nuốt mất cột rỗng ở cuối dòng
            String[] tokens = line.split(",", -1);

            if (tokens.length < 3) {
                throw new IllegalArgumentException("Row has fewer than 3 fields: " + line);
            }

            String sku = tokens[0].strip();
            String quantityStr = tokens[1].strip();
            String priceStr = tokens[2].strip();

            if (sku.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
                throw new IllegalArgumentException("Row contains empty field: " + line);
            }

            long quantity;
            try {
                quantity = Long.parseLong(quantityStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantity is not a valid whole number: " + line, e);
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be a positive whole number: " + line);
            }

            BigDecimal unitPrice;
            try {
                unitPrice = new BigDecimal(priceStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unit price is unparseable: " + line, e);
            }

            if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Unit price cannot be negative: " + line);
            }

            linesCount++;
            totalUnits += quantity;
            grandTotal = grandTotal.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }

        return new Result(linesCount, totalUnits, grandTotal);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: Missing CSV file path argument.");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);
        if (!Files.exists(path)) {
            System.err.println("Error: File not found: " + args[0]);
            System.exit(1);
        }

        try {
            List<String> lines = Files.readAllLines(path);
            Result result = processLines(lines);

            System.out.println("lines: " + result.lines());
            System.out.println("units: " + result.units());
            System.out.println("total: " + result.total().toPlainString());
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
}