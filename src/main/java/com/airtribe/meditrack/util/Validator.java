package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;

/**
 * Validator class for centralized data validation.
 * Demonstrates validation patterns and exception handling.
 */
public class Validator {
    
    /**
     * Validate email format.
     *
     * @param email the email
     * @return true, if is valid email
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidEmail(String email) throws InvalidDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("email", "null or empty");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Validate phone number format.
     *
     * @param phone the phone
     * @return true, if is valid phone
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidPhone(String phone) throws InvalidDataException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("phone", "null or empty");
        }
        if (phone.length() < Constants.MIN_PHONE_LENGTH || phone.length() > Constants.MAX_PHONE_LENGTH) {
            throw new InvalidDataException("phone", "Invalid length: " + phone.length());
        }
        return phone.matches("\\d+");
    }
    
    /**
     * Validate name.
     *
     * @param name the name
     * @return true, if is valid name
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("name", "null or empty");
        }
        if (name.length() < Constants.MIN_NAME_LENGTH || name.length() > Constants.MAX_NAME_LENGTH) {
            throw new InvalidDataException("name", "Invalid length");
        }
        return true;
    }
    
    /**
     * Validate age.
     *
     * @param age the age
     * @return true, if is valid age
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidAge(int age) throws InvalidDataException {
        if (age < 1 || age > 150) {
            throw new InvalidDataException("age", String.valueOf(age));
        }
        return true;
    }
    
    /**
     * Validate ID format.
     *
     * @param id the id
     * @return true, if is valid id
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidId(String id) throws InvalidDataException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("id", "null or empty");
        }
        return true;
    }
    
    /**
     * Validate blood type.
     *
     * @param bloodType the blood type
     * @return true, if is valid blood type
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidBloodType(String bloodType) throws InvalidDataException {
        if (bloodType == null || bloodType.trim().isEmpty()) {
            throw new InvalidDataException("bloodType", "null or empty");
        }
        String[] validTypes = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        for (String type : validTypes) {
            if (bloodType.equals(type)) {
                return true;
            }
        }
        throw new InvalidDataException("bloodType", bloodType);
    }
    
    /**
     * Validate positive number.
     *
     * @param value the value
     * @return true, if is positive
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isPositive(double value) throws InvalidDataException {
        if (value <= 0) {
            throw new InvalidDataException("value", String.valueOf(value));
        }
        return true;
    }
    
    /**
     * Validate gender.
     *
     * @param gender the gender
     * @return true, if is valid gender
     * @throws InvalidDataException the invalid data exception
     */
    public static boolean isValidGender(String gender) throws InvalidDataException {
        if (gender == null || gender.trim().isEmpty()) {
            throw new InvalidDataException("gender", "null or empty");
        }
        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female") && 
            !gender.equalsIgnoreCase("Other")) {
            throw new InvalidDataException("gender", gender);
        }
        return true;
    }
    
    // Prevent instantiation
    private Validator() {
        throw new AssertionError("Cannot instantiate Validator class");
    }
}
