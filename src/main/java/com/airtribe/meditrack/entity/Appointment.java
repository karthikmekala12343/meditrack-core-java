package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Appointment class representing a doctor-patient appointment.
 * Demonstrates composition, enums, and complex object modeling.
 */
public class Appointment implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentDateTime;
    private String reason;
    private AppointmentStatus status;
    private String notes;
    private double consultationFee;
    
    /**
     * Instantiates a new appointment.
     */
    // Default constructor
    public Appointment() {
        this.status = AppointmentStatus.PENDING;
        this.notes = "";
        this.consultationFee = 0.0;
    }
    
    /**
     * Instantiates a new appointment.
     *
     * @param appointmentId the appointment id
     * @param patientId the patient id
     * @param doctorId the doctor id
     * @param appointmentDateTime the appointment date time
     * @param reason the reason
     */
    // Constructor with essential fields
    public Appointment(String appointmentId, String patientId, String doctorId, 
                      LocalDateTime appointmentDateTime, String reason) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
        this.status = AppointmentStatus.PENDING;
        this.notes = "";
        this.consultationFee = 0.0;
    }
    
    /**
     * Instantiates a new appointment.
     *
     * @param appointmentId the appointment id
     * @param patientId the patient id
     * @param doctorId the doctor id
     * @param appointmentDateTime the appointment date time
     * @param reason the reason
     * @param status the status
     * @param notes the notes
     * @param consultationFee the consultation fee
     */
    // Full constructor
    public Appointment(String appointmentId, String patientId, String doctorId, 
                      LocalDateTime appointmentDateTime, String reason, 
                      AppointmentStatus status, String notes, double consultationFee) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
        this.status = status;
        this.notes = notes;
        this.consultationFee = consultationFee;
    }
    
    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public boolean isConfirmed() {
        return status == AppointmentStatus.CONFIRMED;
    }
    
    public boolean isCancelled() {
        return status == AppointmentStatus.CANCELLED;
    }
    
    // Deep clone implementation
    @Override
    public Appointment clone() throws CloneNotSupportedException {
        return (Appointment) super.clone();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Appointment that = (Appointment) o;
        return appointmentId != null && appointmentId.equals(that.appointmentId);
    }
    
    @Override
    public int hashCode() {
        return appointmentId != null ? appointmentId.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", appointmentDateTime=" + appointmentDateTime +
                ", status=" + status +
                ", consultationFee=" + consultationFee +
                '}';
    }
}
