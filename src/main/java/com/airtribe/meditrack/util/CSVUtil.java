package com.airtribe.meditrack.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for CSV file operations.
 * Demonstrates file I/O, try-with-resources, and String manipulation.
 */
public class CSVUtil {
    public static final String CSV_DELIMITER = ",";
    public static final String CSV_QUOTE = "\"";
    
    /**
     * Read CSV file and return list of string arrays.
     *
     * @param filePath the file path
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = parseLine(line);
                records.add(values);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + filePath);
            throw e;
        }
        
        return records;
    }
    
    /**
     * Write CSV file from list of string arrays.
     *
     * @param filePath the file path
     * @param records the records
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeCSV(String filePath, List<String[]> records) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] record : records) {
                String line = joinLine(record);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + filePath);
            throw e;
        }
    }
    
    /**
     * Append a record to CSV file.
     *
     * @param filePath the file path
     * @param record the record
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void appendToCSV(String filePath, String[] record) throws IOException {
        File file = new File(filePath);
        boolean fileExists = file.exists();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String line = joinLine(record);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending to CSV file: " + filePath);
            throw e;
        }
    }
    
    /**
     * Parse a CSV line handling quoted values.
     *
     * @param line the line
     * @return the string[]
     */
    private static String[] parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                values.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        values.add(current.toString().trim());
        return values.toArray(new String[0]);
    }
    
    /**
     * Join values into a CSV line, quoting if necessary.
     *
     * @param values the values
     * @return the string
     */
    private static String joinLine(String[] values) {
        StringBuilder line = new StringBuilder();
        
        for (int i = 0; i < values.length; i++) {
            String value = values[i] != null ? values[i] : "";
            
            if (value.contains(",") || value.contains("\"")) {
                line.append(CSV_QUOTE).append(value.replace("\"", "\"\"")).append(CSV_QUOTE);
            } else {
                line.append(value);
            }
            
            if (i < values.length - 1) {
                line.append(CSV_DELIMITER);
            }
        }
        
        return line.toString();
    }
    
    // Prevent instantiation
    private CSVUtil() {
        throw new AssertionError("Cannot instantiate CSVUtil class");
    }
}
