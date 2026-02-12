package com.airtribe.meditrack.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date and time operations.
 * Demonstrates static utility methods for common date operations.
 */
public class DateUtil {
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    
    /**
     * Get current date.
     *
     * @return the current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Get current date and time.
     *
     * @return the current date time
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * Format date to string.
     *
     * @param date the date
     * @return the string
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(dateFormatter) : "";
    }
    
    /**
     * Format date and time to string.
     *
     * @param dateTime the date time
     * @return the string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(dateTimeFormatter) : "";
    }
    
    /**
     * Parse date from string.
     *
     * @param dateStr the date str
     * @return the local date
     */
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, dateFormatter);
    }
    
    /**
     * Parse date and time from string.
     *
     * @param dateTimeStr the date time str
     * @return the local date time
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
    }
    
    /**
     * Check if date is today.
     *
     * @param date the date
     * @return true, if is today
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }
    
    /**
     * Check if date is in future.
     *
     * @param date the date
     * @return true, if is future
     */
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }
    
    /**
     * Check if date is in past.
     *
     * @param date the date
     * @return true, if is past
     */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    
    /**
     * Add days to a date.
     *
     * @param date the date
     * @param days the days
     * @return the local date
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date != null ? date.plusDays(days) : null;
    }
    
    /**
     * Add hours to a date-time.
     *
     * @param dateTime the date time
     * @param hours the hours
     * @return the local date time
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, int hours) {
        return dateTime != null ? dateTime.plusHours(hours) : null;
    }
    
    // Prevent instantiation
    private DateUtil() {
        throw new AssertionError("Cannot instantiate DateUtil class");
    }
}
