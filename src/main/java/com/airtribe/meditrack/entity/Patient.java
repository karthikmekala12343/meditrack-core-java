package com.airtribe.meditrack.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient class extending Person and implementing Cloneable.
 * Demonstrates inheritance, deep cloning, and collection handling.
 */
public class Patient extends Person implements Cloneable {
    private static final long serialVersionUID = 1L;
    
    private String medicalHistory;
    private List<String> allergies;
    private double height;
    private double weight;
    private String bloodType;
    private String emergencyContact;
    
    /**
     * Instantiates a new patient.
     */
    // Default constructor
    public Patient() {
        super();
        this.allergies = new ArrayList<>();
        this.medicalHistory = "";
        this.height = 0.0;
        this.weight = 0.0;
        this.bloodType = "";
        this.emergencyContact = "";
    }
    
    /**
     * Instantiates a new patient.
     *
     * @param id the id
     * @param name the name
     * @param email the email
     * @param phone the phone
     * @param age the age
     * @param gender the gender
     * @param address the address
     */
    // Constructor with essential fields
    public Patient(String id, String name, String email, String phone, int age, 
                   String gender, String address) {
        super(id, name, email, phone, age, gender, address);
        this.allergies = new ArrayList<>();
        this.medicalHistory = "";
        this.height = 0.0;
        this.weight = 0.0;
        this.bloodType = "";
        this.emergencyContact = "";
    }
    
    /**
     * Instantiates a new patient.
     *
     * @param id the id
     * @param name the name
     * @param email the email
     * @param phone the phone
     * @param age the age
     * @param gender the gender
     * @param address the address
     * @param medicalHistory the medical history
     * @param allergies the allergies
     * @param height the height
     * @param weight the weight
     * @param bloodType the blood type
     * @param emergencyContact the emergency contact
     */
    // Constructor with all fields
    public Patient(String id, String name, String email, String phone, int age, 
                   String gender, String address, String medicalHistory, 
                   List<String> allergies, double height, double weight, 
                   String bloodType, String emergencyContact) {
        super(id, name, email, phone, age, gender, address);
        this.medicalHistory = medicalHistory;
        this.allergies = allergies != null ? new ArrayList<>(allergies) : new ArrayList<>();
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.emergencyContact = emergencyContact;
    }
    
    // Getters and Setters
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public List<String> getAllergies() {
        return allergies;
    }
    
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies != null ? new ArrayList<>(allergies) : new ArrayList<>();
    }
    
    public void addAllergy(String allergy) {
        if (!allergies.contains(allergy)) {
            allergies.add(allergy);
        }
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public String getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public double getBMI() {
        if (height == 0) return 0;
        double heightInMeters = height / 100;
        return weight / (heightInMeters * heightInMeters);
    }
    
    // Polymorphic method override
    @Override
    public String getRole() {
        return "Patient";
    }
    
    // Deep clone implementation
    @Override
    public Patient clone() throws CloneNotSupportedException {
        Patient cloned = (Patient) super.clone();
        // Deep copy the allergies list
        cloned.allergies = new ArrayList<>(this.allergies);
        return cloned;
    }
    
    @Override
    public String toString() {
        return "Patient{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", bmi=" + String.format("%.2f", getBMI()) +
                '}';
    }
}
