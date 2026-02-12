package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ID Generator using Singleton pattern (eager initialization) and AtomicInteger.
 * Demonstrates thread-safe ID generation and singleton concepts.
 */
public class IdGenerator {
    // Eager singleton initialization
    private static final IdGenerator INSTANCE = new IdGenerator();
    
    private final AtomicInteger patientCounter = new AtomicInteger(1000);
    private final AtomicInteger doctorCounter = new AtomicInteger(500);
    private final AtomicInteger appointmentCounter = new AtomicInteger(10000);
    private final AtomicInteger billCounter = new AtomicInteger(5000);
    
    /**
     * Instantiates a new id generator.
     */
    // Private constructor to prevent instantiation
    private IdGenerator() {
        System.out.println("IdGenerator Singleton initialized");
    }
    
    /**
     * Get singleton instance.
     *
     * @return single instance of IdGenerator
     */
    public static IdGenerator getInstance() {
        return INSTANCE;
    }
    
    /**
     * Generate unique patient ID.
     *
     * @return the string
     */
    public String generatePatientId() {
        return "PAT" + String.format("%05d", patientCounter.incrementAndGet());
    }
    
    /**
     * Generate unique doctor ID.
     *
     * @return the string
     */
    public String generateDoctorId() {
        return "DOC" + String.format("%05d", doctorCounter.incrementAndGet());
    }
    
    /**
     * Generate unique appointment ID.
     *
     * @return the string
     */
    public String generateAppointmentId() {
        return "APT" + String.format("%08d", appointmentCounter.incrementAndGet());
    }
    
    /**
     * Generate unique bill ID.
     *
     * @return the string
     */
    public String generateBillId() {
        return "BILL" + String.format("%08d", billCounter.incrementAndGet());
    }
    
    /**
     * Get current patient counter.
     *
     * @return the current patient count
     */
    public int getCurrentPatientCount() {
        return patientCounter.get();
    }
    
    /**
     * Get current doctor counter.
     *
     * @return the current doctor count
     */
    public int getCurrentDoctorCount() {
        return doctorCounter.get();
    }
    
    /**
     * Reset counters (useful for testing).
     */
    protected void resetCounters() {
        patientCounter.set(1000);
        doctorCounter.set(500);
        appointmentCounter.set(10000);
        billCounter.set(5000);
    }
}
