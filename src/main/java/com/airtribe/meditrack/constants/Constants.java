package com.airtribe.meditrack.constants;

/**
 * Application-wide constants for MediTrack Clinic Management System.
 * Demonstrates static initialization and constant management.
 */
public class Constants {
    // Tax rate for billing
    public static final double TAX_RATE = 0.18;
    
    // File paths for persistence
    public static final String PATIENTS_CSV = "data/patients.csv";
    public static final String DOCTORS_CSV = "data/doctors.csv";
    public static final String APPOINTMENTS_CSV = "data/appointments.csv";
    public static final String BILLS_CSV = "data/bills.csv";
    
    // Serialization paths
    public static final String PATIENTS_DAT = "data/patients.dat";
    public static final String DOCTORS_DAT = "data/doctors.dat";
    public static final String APPOINTMENTS_DAT = "data/appointments.dat";
    
    // Application settings
    public static final int MAX_PATIENTS = 1000;
    public static final int MAX_DOCTORS = 500;
    public static final int MAX_APPOINTMENTS = 5000;
    
    // Time settings (in minutes)
    public static final int APPOINTMENT_SLOT_DURATION = 30;
    public static final int CLINIC_OPENING_HOUR = 9;
    public static final int CLINIC_CLOSING_HOUR = 18;
    
    // Validation constraints
    public static final int MIN_PHONE_LENGTH = 10;
    public static final int MAX_PHONE_LENGTH = 15;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    
    // Static block for application initialization
    static {
        System.out.println("MediTrack Application Constants Initialized");
        System.out.println("Tax Rate: " + (TAX_RATE * 100) + "%");
    }
    
    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
}
