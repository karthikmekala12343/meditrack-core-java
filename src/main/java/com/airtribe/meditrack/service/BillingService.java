package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

/**
 * Service class for managing bills and payment processing.
 * Demonstrates billing strategy and financial calculations.
 */
public class BillingService {
    private DataStore<Bill> billStore;
    private AppointmentService appointmentService;
    private IdGenerator idGenerator;
    
    /**
     * Instantiates a new billing service.
     *
     * @param appointmentService the appointment service
     */
    public BillingService(AppointmentService appointmentService) {
        this.billStore = new DataStore<>();
        this.appointmentService = appointmentService;
        this.idGenerator = IdGenerator.getInstance();
    }
    
    /**
     * Generate bill for appointment.
     *
     * @param appointmentId the appointment id
     * @return the bill
     * @throws AppointmentNotFoundException the appointment not found exception
     */
    public Bill generateBillForAppointment(String appointmentId) 
            throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        
        String billId = idGenerator.generateBillId();
        Bill bill = new Bill(billId, appointmentId, appointment.getPatientId(), 
                appointment.getDoctorId(), appointment.getConsultationFee());
        
        billStore.add(billId, bill);
        System.out.println("Bill generated: " + billId + " for appointment: " + appointmentId);
        return bill;
    }
    
    /**
     * Get bill by ID.
     *
     * @param billId the bill id
     * @return the bill by id
     */
    public Bill getBillById(String billId) {
        return billStore.get(billId);
    }
    
    /**
     * Get all bills.
     *
     * @return the all bills
     */
    public List<Bill> getAllBills() {
        return billStore.getAll();
    }
    
    /**
     * Get bills for a patient.
     *
     * @param patientId the patient id
     * @return the bills by patient id
     */
    public List<Bill> getBillsByPatientId(String patientId) {
        return billStore.search(bill -> bill.getPatientId().equals(patientId));
    }
    
    /**
     * Mark bill as paid.
     *
     * @param billId the bill id
     */
    public void markBillAsPaid(String billId) {
        Bill bill = getBillById(billId);
        if (bill != null) {
            bill.setPaid(true);
            System.out.println("Bill marked as paid: " + billId);
        }
    }
    
    /**
     * Get pending bills (unpaid).
     *
     * @return the pending bills
     */
    public List<Bill> getPendingBills() {
        return billStore.search(bill -> !bill.isPaid());
    }
    
    /**
     * Get paid bills.
     *
     * @return the paid bills
     */
    public List<Bill> getPaidBills() {
        return billStore.search(bill -> bill.isPaid());
    }
    
    /**
     * Get total revenue from paid bills.
     *
     * @return the total revenue
     */
    public double getTotalRevenue() {
        return getPaidBills().stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }
    
    /**
     * Get outstanding amount (from pending bills).
     *
     * @return the outstanding amount
     */
    public double getOutstandingAmount() {
        return getPendingBills().stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }
    
    /**
     * Get average bill amount.
     *
     * @return the average bill amount
     */
    public double getAverageBillAmount() {
        return getAllBills().stream()
                .mapToDouble(Bill::getTotalAmount)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Create bill summary from bill (immutable summary).
     *
     * @param bill the bill
     * @param patientName the patient name
     * @param doctorName the doctor name
     * @return the bill summary
     */
    public BillSummary createBillSummary(Bill bill, String patientName, String doctorName) {
        return new BillSummary.Builder()
                .billId(bill.getBillId())
                .patientName(patientName)
                .doctorName(doctorName)
                .totalAmount(bill.getTotalAmount())
                .taxAmount(bill.getTaxAmount())
                .billDate(bill.getBillDate())
                .isPaid(bill.isPaid())
                .build();
    }
    
    /**
     * View bill with details.
     *
     * @param billId the bill id
     */
    public void viewBillDetails(String billId) {
        Bill bill = getBillById(billId);
        if (bill != null) {
            System.out.println("========== BILL DETAILS ==========");
            System.out.println("Bill ID: " + bill.getBillId());
            System.out.println("Appointment ID: " + bill.getAppointmentId());
            System.out.println("Patient ID: " + bill.getPatientId());
            System.out.println("Doctor ID: " + bill.getDoctorId());
            System.out.println("Consultation Fee: ₹" + String.format("%.2f", bill.getConsultationFee()));
            System.out.println("Medicines: ₹" + String.format("%.2f", bill.getMedicinesCharges()));
            System.out.println("Tests: ₹" + String.format("%.2f", bill.getTestCharges()));
            System.out.println("Other Charges: ₹" + String.format("%.2f", bill.getOtherCharges()));
            System.out.println("Tax (18%): ₹" + String.format("%.2f", bill.getTaxAmount()));
            System.out.println("Total Amount: ₹" + String.format("%.2f", bill.getTotalAmount()));
            System.out.println("Status: " + (bill.isPaid() ? "PAID" : "PENDING"));
            System.out.println("=================================");
        }
    }
}
