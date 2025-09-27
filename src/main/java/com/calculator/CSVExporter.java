package com.calculator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for exporting calculation history to CSV format
 */
public class CSVExporter {
    
    private static final String CSV_HEADER = "Timestamp,Operation,Result,Status\n";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static void exportToCSV(List<String> history, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(CSV_HEADER);
            
            for (String entry : history) {
                String timestamp = LocalDateTime.now().format(FORMATTER);
                writer.write(String.format("%s,\"%s\",SUCCESS\n", timestamp, entry));
            }
        }
    }
    
    public static String generateFilename() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "calculator_history_" + timestamp + ".csv";
    }
}