package com.airtribe.meditrack.interface_;

/**
 * Interface for entities that can generate bills/payments.
 * Demonstrates polymorphic behavior for billing.
 */
public interface Payable {
    
    /**
     * Generate bill for the entity.
     *
     * @return the object
     */
    Object generateBill();
    
    /**
     * Get total amount due.
     *
     * @return the total amount
     */
    double getTotalAmount();
    
    /**
     * Get tax amount.
     *
     * @param amount the amount
     * @return the tax amount
     */
    default double getTaxAmount(double amount) {
        return amount * 0.18; // 18% GST
    }
}
