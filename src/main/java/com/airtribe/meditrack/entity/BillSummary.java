package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Immutable BillSummary class.
 * Demonstrates immutability pattern for thread-safe billing summaries.
 * All fields are private, final, and there are no setters.
 */
public final class BillSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String billId;
    private final String patientName;
    private final String doctorName;
    private final double totalAmount;
    private final double taxAmount;
    private final LocalDateTime billDate;
    private final boolean isPaid;
    
    /**
     * Instantiates a new bill summary.
     *
     * @param builder the builder
     */
    // Private constructor to prevent direct instantiation
    private BillSummary(Builder builder) {
        this.billId = builder.billId;
        this.patientName = builder.patientName;
        this.doctorName = builder.doctorName;
        this.totalAmount = builder.totalAmount;
        this.taxAmount = builder.taxAmount;
        this.billDate = builder.billDate;
        this.isPaid = builder.isPaid;
    }
    
    // Getters only - no setters
    public String getBillId() {
        return billId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    @Override
    public String toString() {
        return "BillSummary{" +
                "billId='" + billId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", totalAmount=" + String.format("%.2f", totalAmount) +
                ", taxAmount=" + String.format("%.2f", taxAmount) +
                ", billDate=" + billDate +
                ", isPaid=" + isPaid +
                '}';
    }
    
    // Builder pattern for creating immutable objects
    public static class Builder {
        private String billId;
        private String patientName;
        private String doctorName;
        private double totalAmount;
        private double taxAmount;
        private LocalDateTime billDate;
        private boolean isPaid;
        
        public Builder billId(String billId) {
            this.billId = billId;
            return this;
        }
        
        public Builder patientName(String patientName) {
            this.patientName = patientName;
            return this;
        }
        
        public Builder doctorName(String doctorName) {
            this.doctorName = doctorName;
            return this;
        }
        
        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
        
        public Builder taxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }
        
        public Builder billDate(LocalDateTime billDate) {
            this.billDate = billDate;
            return this;
        }
        
        public Builder isPaid(boolean isPaid) {
            this.isPaid = isPaid;
            return this;
        }
        
        public BillSummary build() {
            return new BillSummary(this);
        }
    }
}
