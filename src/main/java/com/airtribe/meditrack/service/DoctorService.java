package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.interface_.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing doctors.
 * Demonstrates CRUD operations and search functionality.
 */
public class DoctorService implements Searchable {
    private DataStore<Doctor> doctorStore;
    private IdGenerator idGenerator;
    
    /**
     * Instantiates a new doctor service.
     */
    public DoctorService() {
        this.doctorStore = new DataStore<>();
        this.idGenerator = IdGenerator.getInstance();
    }
    
    /**
     * Add a new doctor.
     *
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
     * @return the doctor
     */
    public Doctor addDoctor(String name, String email, String phone, int age, 
                            String gender, String address, Specialization specialization,
                            double consultationFee, int yearsOfExperience, String licenseNumber) {
        String doctorId = idGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, email, phone, age, gender, address,
                specialization, consultationFee, yearsOfExperience, licenseNumber);
        doctorStore.add(doctorId, doctor);
        System.out.println("Doctor added: " + doctor.getName() + " [ID: " + doctorId + "]");
        return doctor;
    }
    
    /**
     * Get doctor by ID.
     *
     * @param doctorId the doctor id
     * @return the doctor by id
     */
    public Doctor getDoctorById(String doctorId) {
        return doctorStore.get(doctorId);
    }
    
    /**
     * Search doctor by ID - implements Searchable interface.
     *
     * @param id the id
     * @return the doctor
     */
    @Override
    public Doctor searchById(String id) {
        return doctorStore.get(id);
    }
    
    /**
     * Search doctor by name - implements Searchable interface.
     *
     * @param name the name
     * @return the doctor
     */
    @Override
    public Doctor searchByName(String name) {
        List<Doctor> results = doctorStore.search(doctor -> doctor.getName().equalsIgnoreCase(name));
        return results.isEmpty() ? null : results.get(0);
    }
    
    /**
     * Search doctors by specialization (overloading).
     *
     * @param specialization the specialization
     * @return the list
     */
    public List<Doctor> searchBySpecialization(Specialization specialization) {
        return doctorStore.search(doctor -> doctor.getSpecialization() == specialization);
    }
    
    /**
     * Search doctors with rating above threshold.
     *
     * @param minRating the min rating
     * @return the list
     */
    public List<Doctor> searchByRating(double minRating) {
        return doctorStore.search(doctor -> doctor.getRating() >= minRating);
    }
    
    /**
     * Get all doctors.
     *
     * @return the all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }
    
    /**
     * Update doctor information.
     *
     * @param doctorId the doctor id
     * @param updatedDoctor the updated doctor
     */
    public void updateDoctor(String doctorId, Doctor updatedDoctor) {
        doctorStore.update(doctorId, updatedDoctor);
        System.out.println("Doctor updated: " + updatedDoctor.getName());
    }
    
    /**
     * Remove doctor.
     *
     * @param doctorId the doctor id
     * @return true, if successful
     */
    public boolean removeDoctor(String doctorId) {
        return doctorStore.delete(doctorId);
    }
    
    /**
     * Get total number of doctors.
     *
     * @return the total doctors
     */
    public int getTotalDoctors() {
        return doctorStore.size();
    }
    
    /**
     * Get average consultation fee for a specialization.
     *
     * @param specialization the specialization
     * @return the average consultation fee
     */
    public double getAverageConsultationFee(Specialization specialization) {
        return searchBySpecialization(specialization).stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Get doctors sorted by rating (descending).
     *
     * @return the doctors sorted by rating
     */
    public List<Doctor> getDoctorsSortedByRating() {
        return getAllDoctors().stream()
                .sorted((d1, d2) -> Double.compare(d2.getRating(), d1.getRating()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get doctors sorted by experience (descending).
     *
     * @return the doctors sorted by experience
     */
    public List<Doctor> getDoctorsSortedByExperience() {
        return getAllDoctors().stream()
                .sorted((d1, d2) -> Integer.compare(d2.getYearsOfExperience(), d1.getYearsOfExperience()))
                .collect(Collectors.toList());
    }
}
