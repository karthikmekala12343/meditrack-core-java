package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interface_.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

/**
 * Service class for managing patients.
 * Demonstrates CRUD operations, search, and cloning.
 */
public class PatientService implements Searchable {
    private DataStore<Patient> patientStore;
    private IdGenerator idGenerator;
    
    /**
     * Instantiates a new patient service.
     */
    public PatientService() {
        this.patientStore = new DataStore<>();
        this.idGenerator = IdGenerator.getInstance();
    }
    
    /**
     * Add a new patient.
     *
     * @param name the name
     * @param email the email
     * @param phone the phone
     * @param age the age
     * @param gender the gender
     * @param address the address
     * @return the patient
     */
    public Patient addPatient(String name, String email, String phone, int age, 
                              String gender, String address) {
        String patientId = idGenerator.generatePatientId();
        Patient patient = new Patient(patientId, name, email, phone, age, gender, address);
        patientStore.add(patientId, patient);
        System.out.println("Patient added: " + patient.getName() + " [ID: " + patientId + "]");
        return patient;
    }
    
    /**
     * Get patient by ID.
     *
     * @param patientId the patient id
     * @return the patient by id
     */
    public Patient getPatientById(String patientId) {
        return patientStore.get(patientId);
    }
    
    /**
     * Search patient by ID - implements Searchable interface.
     *
     * @param id the id
     * @return the patient
     */
    @Override
    public Patient searchById(String id) {
        return patientStore.get(id);
    }
    
    /**
     * Search patient by name - implements Searchable interface.
     *
     * @param name the name
     * @return the patient
     */
    @Override
    public Patient searchByName(String name) {
        List<Patient> results = patientStore.search(patient -> patient.getName().equalsIgnoreCase(name));
        return results.isEmpty() ? null : results.get(0);
    }
    
    /**
     * Overloaded search by age (demonstrates method overloading).
     *
     * @param age the age
     * @return the list
     */
    public List<Patient> searchByAge(int age) {
        return patientStore.search(patient -> patient.getAge() == age);
    }
    
    /**
     * Search patients by blood type.
     *
     * @param bloodType the blood type
     * @return the list
     */
    public List<Patient> searchByBloodType(String bloodType) {
        return patientStore.search(patient -> patient.getBloodType().equals(bloodType));
    }
    
    /**
     * Get all patients.
     *
     * @return the all patients
     */
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }
    
    /**
     * Update patient information.
     *
     * @param patientId the patient id
     * @param updatedPatient the updated patient
     */
    public void updatePatient(String patientId, Patient updatedPatient) {
        patientStore.update(patientId, updatedPatient);
        System.out.println("Patient updated: " + updatedPatient.getName());
    }
    
    /**
     * Remove patient.
     *
     * @param patientId the patient id
     * @return true, if successful
     */
    public boolean removePatient(String patientId) {
        return patientStore.delete(patientId);
    }
    
    /**
     * Get total number of patients.
     *
     * @return the total patients
     */
    public int getTotalPatients() {
        return patientStore.size();
    }
    
    /**
     * Clone a patient (demonstrates deep cloning).
     *
     * @param patientId the patient id
     * @return the patient
     * @throws CloneNotSupportedException the clone not supported exception
     */
    public Patient clonePatient(String patientId) throws CloneNotSupportedException {
        Patient original = getPatientById(patientId);
        if (original == null) {
            return null;
        }
        Patient cloned = original.clone();
        // Optionally give cloned patient a new ID
        cloned.setId(idGenerator.generatePatientId());
        return cloned;
    }
    
    /**
     * Add allergy to patient.
     *
     * @param patientId the patient id
     * @param allergy the allergy
     */
    public void addAllergy(String patientId, String allergy) {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            patient.addAllergy(allergy);
            System.out.println("Allergy added for patient: " + patient.getName());
        }
    }
    
    /**
     * Get patients with BMI over threshold (overweight/obese).
     *
     * @param threshold the threshold
     * @return the patients with high BMI
     */
    public List<Patient> getPatientsWithHighBMI(double threshold) {
        return patientStore.search(patient -> patient.getBMI() > threshold);
    }
    
    /**
     * Get medical history for patient.
     *
     * @param patientId the patient id
     * @return the patient medical history
     */
    public String getPatientMedicalHistory(String patientId) {
        Patient patient = getPatientById(patientId);
        return patient != null ? patient.getMedicalHistory() : "";
    }
    
    /**
     * Update medical history.
     *
     * @param patientId the patient id
     * @param history the history
     */
    public void updateMedicalHistory(String patientId, String history) {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            patient.setMedicalHistory(history);
        }
    }
}
