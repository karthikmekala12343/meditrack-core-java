package com.airtribe.meditrack.exception;

/**
 * Custom exception thrown when an appointment is not found.
 * Demonstrates exception chaining and custom exception handling.
 */
public class AppointmentNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appointmentId;
    
    public AppointmentNotFoundException(String message) {
        super(message);
    }
    
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AppointmentNotFoundException(String message, String appointmentId) {
        super(message);
        this.appointmentId = appointmentId;
    }
    
    public String getAppointmentId() {
        return appointmentId;
    }
}
