package com.airtribe.meditrack.entity;

/**
 * Doctor class extending Person.
 * Demonstrates inheritance and polymorphism.
 */
public class Doctor extends Person {
    private static final long serialVersionUID = 1L;
    
    private Specialization specialization;
    private double consultationFee;
    private int yearsOfExperience;
    private String licenseNumber;
    private double rating;
    private int totalPatients;
    
    /**
     * Instantiates a new doctor.
     */
    // Default constructor
    public Doctor() {
        super();
        this.specialization = Specialization.GENERAL_PRACTITIONER;
        this.consultationFee = specialization.getDefaultFee();
        this.yearsOfExperience = 0;
        this.licenseNumber = "";
        this.rating = 0.0;
        this.totalPatients = 0;
    }
    
    /**
     * Instantiates a new doctor.
     *
     * @param id the id
     * @param name the name
     * @param email the email
     * @param phone the phone
     * @param age the age
     * @param gender the gender
     * @param address the address
     * @param specialization the specialization
     * @param consultationFee the consultation fee
     * @param yearsOfExperience the years of experience
     * @param licenseNumber the license number
     */
    // Constructor with all fields
    public Doctor(String id, String name, String email, String phone, int age, 
                  String gender, String address, Specialization specialization, 
                  double consultationFee, int yearsOfExperience, String licenseNumber) {
        super(id, name, email, phone, age, gender, address);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.yearsOfExperience = yearsOfExperience;
        this.licenseNumber = licenseNumber;
        this.rating = 0.0;
        this.totalPatients = 0;
    }
    
    // Getters and Setters
    public Specialization getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = Math.min(5.0, Math.max(0.0, rating));
    }
    
    public int getTotalPatients() {
        return totalPatients;
    }
    
    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }
    
    // Polymorphic method override
    @Override
    public String getRole() {
        return "Doctor";
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", specialization=" + specialization +
                ", consultationFee=" + consultationFee +
                ", yearsOfExperience=" + yearsOfExperience +
                ", rating=" + rating +
                '}';
    }
}
