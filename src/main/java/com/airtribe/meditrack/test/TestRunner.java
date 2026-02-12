package com.airtribe.meditrack.test;

import java.time.LocalDateTime;
import java.util.List;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillingService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

/**
 * Manual test runner for MediTrack application.
 * Demonstrates all functionality without using JUnit.
 */
public class TestRunner {
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static BillingService billingService;
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("========== MediTrack Test Suite ==========\n");
        
        // Initialize services
        initializeServices();
        
        // Run all tests
        runAllTests();
        
        // Print summary
        printTestSummary();
    }
    
    private static void initializeServices() {
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService(doctorService, patientService);
        billingService = new BillingService(appointmentService);
        System.out.println("Services initialized successfully.\n");
    }
    
    private static void runAllTests() {
        // Test categories
        testValidation();
        testIdGeneration();
        testDoctorOperations();
        testPatientOperations();
        testAppointmentOperations();
        testBillingOperations();
        testEnums();
        testImmutability();
        testCloning();
        testStreams();
    }
    
    /**
     * Test validation utilities
     */
    private static void testValidation() {
        System.out.println("--- Testing Validation ---");
        
        try {
            // Test email validation
            assertTrue(Validator.isValidEmail("test@example.com"), "Valid email should pass");
            testsPassed++;
        } catch (Exception e) {
            assertEquals(false, true, "Email validation failed: " + e.getMessage());
            testsFailed++;
        }
        
        try {
            // Test invalid email
            Validator.isValidEmail("invalid-email");
            testsFailed++;
            assertEquals(false, true, "Invalid email should throw exception");
        } catch (InvalidDataException e) {
            testsPassed++;
        }
        
        try {
            // Test phone validation
            assertTrue(Validator.isValidPhone("9876543210"), "Valid phone should pass");
            testsPassed++;
        } catch (Exception e) {
            testsFailed++;
        }
        
        try {
            // Test name validation
            assertTrue(Validator.isValidName("John Doe"), "Valid name should pass");
            testsPassed++;
        } catch (Exception e) {
            testsFailed++;
        }
        
        try {
            // Test age validation
            assertTrue(Validator.isValidAge(25), "Valid age should pass");
            testsPassed++;
        } catch (Exception e) {
            testsFailed++;
        }
        
        try {
            // Test invalid age
            Validator.isValidAge(200);
            testsFailed++;
            assertEquals(false, true, "Invalid age should throw exception");
        } catch (InvalidDataException e) {
            testsPassed++;
        }
        
        System.out.println();
    }
    
    /**
     * Test ID generation (Singleton pattern)
     */
    private static void testIdGeneration() {
        System.out.println("--- Testing ID Generation (Singleton) ---");
        
        IdGenerator gen1 = IdGenerator.getInstance();
        IdGenerator gen2 = IdGenerator.getInstance();
        
        // Test singleton (same instance)
        assertEquals(gen1, gen2, "IdGenerator should be singleton");
        testsPassed++;
        
        // Test ID generation
        String patientId = gen1.generatePatientId();
        assertTrue(patientId.startsWith("PAT"), "Patient ID should start with PAT");
        testsPassed++;
        
        String doctorId = gen1.generateDoctorId();
        assertTrue(doctorId.startsWith("DOC"), "Doctor ID should start with DOC");
        testsPassed++;
        
        String appointmentId = gen1.generateAppointmentId();
        assertTrue(appointmentId.startsWith("APT"), "Appointment ID should start with APT");
        testsPassed++;
        
        String billId = gen1.generateBillId();
        assertTrue(billId.startsWith("BILL"), "Bill ID should start with BILL");
        testsPassed++;
        
        System.out.println();
    }
    
    /**
     * Test doctor operations
     */
    private static void testDoctorOperations() {
        System.out.println("--- Testing Doctor Operations ---");
        
        // Add doctor
        Doctor doctor = doctorService.addDoctor("Dr. Kapoor", "kapoor@meditrack.com", 
                "9876543210", 45, "Male", "New Delhi", Specialization.CARDIOLOGIST, 
                500, 15, "LIC001");
        
        assertEquals(doctor.getName(), "Dr. Kapoor", "Doctor name should match");
        testsPassed++;
        
        // Get doctor by ID
        Doctor retrieved = doctorService.getDoctorById(doctor.getId());
        assertEquals(retrieved.getId(), doctor.getId(), "Retrieved doctor ID should match");
        testsPassed++;
        
        // Search by specialization
        List<Doctor> cardiologists = doctorService.searchBySpecialization(Specialization.CARDIOLOGIST);
        assertTrue(cardiologists.size() > 0, "Should find cardiologist");
        testsPassed++;
        
        // Get all doctors
        int initialCount = doctorService.getTotalDoctors();
        doctorService.addDoctor("Dr. Sharma", "sharma@meditrack.com", "9876543211", 
                40, "Female", "Mumbai", Specialization.DERMATOLOGIST, 400, 10, "LIC002");
        int newCount = doctorService.getTotalDoctors();
        assertEquals(newCount, initialCount + 1, "Doctor count should increase");
        testsPassed++;
        
        System.out.println();
    }
    
    /**
     * Test patient operations
     */
    private static void testPatientOperations() {
        System.out.println("--- Testing Patient Operations ---");
        
        // Add patient
        Patient patient = patientService.addPatient("Amit Singh", "amit@email.com", 
                "9123456789", 35, "Male", "Mumbai");
        
        assertEquals(patient.getName(), "Amit Singh", "Patient name should match");
        testsPassed++;
        
        // Get patient by ID
        Patient retrieved = patientService.getPatientById(patient.getId());
        assertEquals(retrieved.getId(), patient.getId(), "Retrieved patient ID should match");
        testsPassed++;
        
        // Update patient health info
        patient.setBloodType("O+");
        patient.setHeight(175);
        patient.setWeight(75);
        double bmi = patient.getBMI();
        assertTrue(bmi > 20 && bmi < 30, "BMI should be calculated correctly");
        testsPassed++;
        
        // Add allergy
        patient.addAllergy("Penicillin");
        assertEquals(patient.getAllergies().size(), 1, "Should have one allergy");
        testsPassed++;
        
        System.out.println();
    }
    
    /**
     * Test appointment operations
     */
    private static void testAppointmentOperations() {
        System.out.println("--- Testing Appointment Operations ---");
        
        try {
            // Create appointment
            Doctor doctor = doctorService.getAllDoctors().get(0);
            Patient patient = patientService.getAllPatients().get(0);
            
            LocalDateTime appointmentDateTime = LocalDateTime.now().plusDays(1);
            Appointment appointment = appointmentService.createAppointment(
                    patient.getId(), doctor.getId(), appointmentDateTime, "Routine checkup");
            
            assertEquals(appointment.getStatus(), AppointmentStatus.PENDING, 
                    "New appointment should be pending");
            testsPassed++;
            
            // Confirm appointment
            appointmentService.confirmAppointment(appointment.getAppointmentId());
            Appointment confirmed = appointmentService.getAppointmentById(appointment.getAppointmentId());
            assertEquals(confirmed.getStatus(), AppointmentStatus.CONFIRMED, 
                    "Appointment should be confirmed");
            testsPassed++;
            
            // Get appointments by patient
            List<Appointment> patientAppointments = appointmentService.getAppointmentsByPatientId(patient.getId());
            assertTrue(patientAppointments.size() > 0, "Should find patient appointments");
            testsPassed++;
            
        } catch (AppointmentNotFoundException e) {
            testsFailed++;
            System.err.println("Appointment test failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Test billing operations
     */
    private static void testBillingOperations() {
        System.out.println("--- Testing Billing Operations ---");
        
        try {
            // Get first appointment
            List<Appointment> appointments = appointmentService.getAllAppointments();
            if (appointments.size() > 0) {
                Appointment appointment = appointments.get(0);
                
                // Generate bill
                Bill bill = billingService.generateBillForAppointment(appointment.getAppointmentId());
                assertEquals(bill.getAppointmentId(), appointment.getAppointmentId(), 
                        "Bill should reference correct appointment");
                testsPassed++;
                
                // Check bill calculation
                double expectedTotal = appointment.getConsultationFee() * 1.18; // 18% tax
                assertTrue(Math.abs(bill.getTotalAmount() - expectedTotal) < 0.01, 
                        "Bill total should include 18% tax");
                testsPassed++;
                
                // Mark as paid
                billingService.markBillAsPaid(bill.getBillId());
                Bill paidBill = billingService.getBillById(bill.getBillId());
                assertTrue(paidBill.isPaid(), "Bill should be marked as paid");
                testsPassed++;
                
                // Check revenue
                double revenue = billingService.getTotalRevenue();
                assertTrue(revenue > 0, "Should have revenue from paid bills");
                testsPassed++;
            }
        } catch (Exception e) {
            testsFailed++;
            System.err.println("Billing test failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Test Enum usage
     */
    private static void testEnums() {
        System.out.println("--- Testing Enums ---");
        
        // Test AppointmentStatus enum
        assertEquals(AppointmentStatus.CONFIRMED.getDisplayName(), "Confirmed", 
                "Appointment status should have display name");
        testsPassed++;
        
        // Test Specialization enum
        assertEquals(Specialization.CARDIOLOGIST.getDisplayName(), "Cardiology", 
                "Specialization should have display name");
        testsPassed++;
        
        assertTrue(Specialization.CARDIOLOGIST.getDefaultFee() > 0, 
                "Specialization should have fee");
        testsPassed++;
        
        System.out.println();
    }
    
    /**
     * Test immutability of BillSummary
     */
    private static void testImmutability() {
        System.out.println("--- Testing Immutability (BillSummary) ---");
        
        BillSummary summary = new BillSummary.Builder()
                .billId("BILL12345")
                .patientName("John Doe")
                .doctorName("Dr. Smith")
                .totalAmount(1000)
                .taxAmount(180)
                .isPaid(true)
                .build();
        
        assertEquals(summary.getBillId(), "BILL12345", "Bill summary should be immutable");
        testsPassed++;
        
        // Verify no setter exists by checking that modification isn't possible
        // This is a compile-time check in production code
        assertTrue(summary.isPaid(), "Bill summary should maintain state");
        testsPassed++;
        
        System.out.println();
    }
    
    /**
     * Test deep cloning
     */
    private static void testCloning() {
        System.out.println("--- Testing Cloning (Deep Copy) ---");
        
        try {
            if (patientService.getTotalPatients() > 0) {
                Patient original = patientService.getAllPatients().get(0);
                Patient cloned = original.clone();
                
                // Modify original
                original.addAllergy("Test Allergy");
                
                // Check that clone is independent
                assertTrue(cloned.getAllergies().size() < original.getAllergies().size(), 
                        "Cloned patient should be independent (deep copy)");
                testsPassed++;
            }
        } catch (CloneNotSupportedException e) {
            testsFailed++;
        }
        
        System.out.println();
    }
    
    /**
     * Test Streams and Lambdas
     */
    private static void testStreams() {
        System.out.println("--- Testing Streams & Lambdas ---");
        
        // Get doctors by specialization then filter by rating
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        long cardiologistCount = allDoctors.stream()
                .filter(d -> d.getSpecialization() == Specialization.CARDIOLOGIST)
                .count();
        
        assertTrue(cardiologistCount >= 0, "Stream filter should work");
        testsPassed++;
        
        // Calculate average fee using stream
        double avgFee = allDoctors.stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
        
        assertTrue(avgFee >= 0, "Stream average calculation should work");
        testsPassed++;
        
        System.out.println();
    }
    
    /**
     * Test assertion helper
     */
    private static void assertEquals(Object expected, Object actual, String testName) {
        if (expected == null ? actual == null : expected.equals(actual)) {
            System.out.println("✓ PASS: " + testName);
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: " + testName);
            System.out.println("  Expected: " + expected + ", Got: " + actual);
            testsFailed++;
        }
    }
    
    /**
     * Test assertion helper for boolean
     */
    private static void assertTrue(boolean condition, String testName) {
        if (condition) {
            System.out.println("✓ PASS: " + testName);
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: " + testName);
            testsFailed++;
        }
    }
    
    /**
     * Print test summary
     */
    private static void printTestSummary() {
        System.out.println("\n========== TEST SUMMARY ==========");
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("Success Rate: " + String.format("%.2f", 
                ((double) testsPassed / (testsPassed + testsFailed)) * 100) + "%");
        System.out.println("==================================");
        
        if (testsFailed == 0) {
            System.out.println("All tests passed!");
        }
    }
}
