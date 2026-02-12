package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_.Payable;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Bill class representing a medical bill for an appointment.
 * Demonstrates interface implementation and billing logic.
 */
public class Bill implements Payable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String billId;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private double consultationFee;
    private double medicinesCharges;
    private double testCharges;
    private double otherCharges;
    private double taxAmount;
    private double totalAmount;
    private LocalDateTime billDate;
    private boolean isPaid;
    
    /**
     * Instantiates a new bill.
     */
    // Default constructor
    public Bill() {
        this.medicinesCharges = 0.0;
        this.testCharges = 0.0;
        this.otherCharges = 0.0;
        this.taxAmount = 0.0;
        this.totalAmount = 0.0;
        this.isPaid = false;
        this.billDate = LocalDateTime.now();
    }
    
    /**
     * Instantiates a new bill.
     *
     * @param billId the bill id
     * @param appointmentId the appointment id
     * @param patientId the patient id
     * @param doctorId the doctor id
     * @param consultationFee the consultation fee
     */
    // Constructor with essential fields
    public Bill(String billId, String appointmentId, String patientId, String doctorId, double consultationFee) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationFee = consultationFee;
        this.medicinesCharges = 0.0;
        this.testCharges = 0.0;
        this.otherCharges = 0.0;
        this.billDate = LocalDateTime.now();
        this.isPaid = false;
        calculateTotal();
    }
    
    /**
     * Instantiates a new bill.
     *
     * @param billId the bill id
     * @param appointmentId the appointment id
     * @param patientId the patient id
     * @param doctorId the doctor id
     * @param consultationFee the consultation fee
     * @param medicinesCharges the medicines charges
     * @param testCharges the test charges
     * @param otherCharges the other charges
     * @param billDate the bill date
     */
    // Full constructor
    public Bill(String billId, String appointmentId, String patientId, String doctorId, 
                double consultationFee, double medicinesCharges, double testCharges, 
                double otherCharges, LocalDateTime billDate) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationFee = consultationFee;
        this.medicinesCharges = medicinesCharges;
        this.testCharges = testCharges;
        this.otherCharges = otherCharges;
        this.billDate = billDate;
        this.isPaid = false;
        calculateTotal();
    }
    
    // Getters and Setters
    public String getBillId() {
        return billId;
    }
    
    public void setBillId(String billId) {
        this.billId = billId;
    }
    
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
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
        calculateTotal();
    }
    
    public double getMedicinesCharges() {
        return medicinesCharges;
    }
    
    public void setMedicinesCharges(double medicinesCharges) {
        this.medicinesCharges = medicinesCharges;
        calculateTotal();
    }
    
    public double getTestCharges() {
        return testCharges;
    }
    
    public void setTestCharges(double testCharges) {
        this.testCharges = testCharges;
        calculateTotal();
    }
    
    public double getOtherCharges() {
        return otherCharges;
    }
    
    public void setOtherCharges(double otherCharges) {
        this.otherCharges = otherCharges;
        calculateTotal();
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public void setPaid(boolean paid) {
        isPaid = paid;
    }
    
    /**
     * Calculate total.
     */
    // Calculate subtotal and total with tax
    private void calculateTotal() {
        double subtotal = consultationFee + medicinesCharges + testCharges + otherCharges;
        this.taxAmount = getTaxAmount(subtotal);
        this.totalAmount = subtotal + taxAmount;
    }
    
    /**
     * Generate bill.
     *
     * @return the bill
     */
    // Generate bill representation
    @Override
    public Bill generateBill() {
        return this;
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "billId='" + billId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", consultationFee=" + consultationFee +
                ", medicinesCharges=" + medicinesCharges +
                ", testCharges=" + testCharges +
                ", taxAmount=" + String.format("%.2f", taxAmount) +
                ", totalAmount=" + String.format("%.2f", totalAmount) +
                ", isPaid=" + isPaid +
                '}';
    }
}
