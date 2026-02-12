package com.airtribe.meditrack.exception;

/**
 * Custom exception thrown when invalid data is provided.
 * Demonstrates exception creation and validation error handling.
 */
public class InvalidDataException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldName;
    private String invalidValue;
    
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidDataException(String fieldName, String invalidValue) {
        super("Invalid value for field: " + fieldName + " = " + invalidValue);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getInvalidValue() {
        return invalidValue;
    }
}
