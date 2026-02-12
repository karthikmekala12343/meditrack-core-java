package com.airtribe.meditrack.entity;

/**
 * Enum representing medical specializations for doctors.
 */
public enum Specialization {
    GENERAL_PRACTITIONER("General Practice", 300),
    CARDIOLOGIST("Cardiology", 500),
    DERMATOLOGIST("Dermatology", 400),
    PEDIATRICIAN("Pediatrics", 350),
    ORTHOPEDIC("Orthopedic", 450),
    NEUROLOGIST("Neurology", 550),
    PSYCHIATRIST("Psychiatry", 400),
    OPHTHALMOLOGIST("Ophthalmology", 420),
    ENT("ENT", 380),
    SURGEON("General Surgery", 600);
    
    private final String displayName;
    private final double defaultFee;
    
    /**
     * Instantiates a new specialization.
     *
     * @param displayName the display name
     * @param defaultFee the default fee
     */
    Specialization(String displayName, double defaultFee) {
        this.displayName = displayName;
        this.defaultFee = defaultFee;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public double getDefaultFee() {
        return defaultFee;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
