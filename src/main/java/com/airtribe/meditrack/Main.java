package com.airtribe.meditrack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillingService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.Validator;

/**
 * Main application class for MediTrack Clinic Management System.
 * Provides menu-driven user interface for all operations.
 */
public class Main {
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static BillingService billingService;
    private static Scanner scanner;
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        // Initialize services
        initializeServices();
        
        // Load sample data if argument provided
        if (args.length > 0 && args[0].equals("--loadData")) {
            loadSampleData();
        }
        
        // Start menu-driven interface
        displayMainMenu();
    }
    
    /**
     * Initialize all services.
     */
    private static void initializeServices() {
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService(doctorService, patientService);
        billingService = new BillingService(appointmentService);
        scanner = new Scanner(System.in);
        System.out.println("MediTrack Clinic Management System Initialized");
    }
    
    /**
     * Main menu display.
     */
    private static void displayMainMenu() {
        while (true) {
            System.out.println("\n========== MEDITRACK MAIN MENU ==========");
            System.out.println("1. Doctor Management");
            System.out.println("2. Patient Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Billing Management");
            System.out.println("5. Reports & Analytics");
            System.out.println("6. Exit");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    doctorMenu();
                    break;
                case "2":
                    patientMenu();
                    break;
                case "3":
                    appointmentMenu();
                    break;
                case "4":
                    billingMenu();
                    break;
                case "5":
                    reportsMenu();
                    break;
                case "6":
                    System.out.println("Thank you for using MediTrack!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Doctor management menu.
     */
    private static void doctorMenu() {
        while (true) {
            System.out.println("\n========== DOCTOR MANAGEMENT ==========");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. Update Doctor");
            System.out.println("5. Remove Doctor");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addDoctor();
                    break;
                case "2":
                    viewAllDoctors();
                    break;
                case "3":
                    searchDoctor();
                    break;
                case "4":
                    updateDoctor();
                    break;
                case "5":
                    removeDoctor();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Add a new doctor.
     */
    private static void addDoctor() {
        try {
            System.out.println("\n--- Add New Doctor ---");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            Validator.isValidName(name);
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            Validator.isValidEmail(email);
            
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            Validator.isValidPhone(phone);
            
            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            Validator.isValidAge(age);
            
            System.out.print("Gender (Male/Female/Other): ");
            String gender = scanner.nextLine();
            Validator.isValidGender(gender);
            
            System.out.print("Address: ");
            String address = scanner.nextLine();
            
            System.out.println("Specializations:");
            Specialization[] specs = Specialization.values();
            for (int i = 0; i < specs.length; i++) {
                System.out.println((i + 1) + ". " + specs[i]);
            }
            System.out.print("Select specialization (1-" + specs.length + "): ");
            int specChoice = Integer.parseInt(scanner.nextLine()) - 1;
            Specialization specialization = specs[specChoice];
            
            System.out.print("Consultation Fee: ");
            double fee = Double.parseDouble(scanner.nextLine());
            Validator.isPositive(fee);
            
            System.out.print("Years of Experience: ");
            int experience = Integer.parseInt(scanner.nextLine());
            
            System.out.print("License Number: ");
            String license = scanner.nextLine();
            
            doctorService.addDoctor(name, email, phone, age, gender, address,
                    specialization, fee, experience, license);
            
        } catch (InvalidDataException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * View all doctors.
     */
    private static void viewAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        
        System.out.println("\n========== DOCTORS LIST ==========");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }
    
    /**
     * Search for a doctor.
     */
    private static void searchDoctor() {
        System.out.println("\n--- Search Doctor ---");
        System.out.print("Enter doctor name or ID: ");
        String query = scanner.nextLine();
        
        Doctor result = doctorService.searchByName(query);
        if (result == null) {
            result = doctorService.searchById(query);
        }
        
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Doctor not found.");
        }
    }
    
    /**
     * Update doctor information.
     */
    private static void updateDoctor() {
        System.out.println("\n--- Update Doctor ---");
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        
        System.out.print("New Consultation Fee (press Enter to skip): ");
        String feeStr = scanner.nextLine();
        if (!feeStr.isEmpty()) {
            try {
                double fee = Double.parseDouble(feeStr);
                Validator.isPositive(fee);
                doctor.setConsultationFee(fee);
            } catch (InvalidDataException e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }
        }
        
        System.out.print("New Rating (0-5): ");
        String ratingStr = scanner.nextLine();
        if (!ratingStr.isEmpty()) {
            try {
                double rating = Double.parseDouble(ratingStr);
                doctor.setRating(rating);
            } catch (NumberFormatException e) {
                System.err.println("Invalid rating format.");
                return;
            }
        }
        
        doctorService.updateDoctor(doctorId, doctor);
    }
    
    /**
     * Remove a doctor.
     */
    private static void removeDoctor() {
        System.out.println("\n--- Remove Doctor ---");
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        
        if (doctorService.removeDoctor(doctorId)) {
            System.out.println("Doctor removed successfully.");
        } else {
            System.out.println("Doctor not found.");
        }
    }
    
    /**
     * Patient management menu.
     */
    private static void patientMenu() {
        while (true) {
            System.out.println("\n========== PATIENT MANAGEMENT ==========");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Add Allergy");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addPatient();
                    break;
                case "2":
                    viewAllPatients();
                    break;
                case "3":
                    searchPatient();
                    break;
                case "4":
                    updatePatient();
                    break;
                case "5":
                    addAllergy();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Add a new patient.
     */
    private static void addPatient() {
        try {
            System.out.println("\n--- Add New Patient ---");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            Validator.isValidName(name);
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            Validator.isValidEmail(email);
            
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            Validator.isValidPhone(phone);
            
            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            Validator.isValidAge(age);
            
            System.out.print("Gender (Male/Female/Other): ");
            String gender = scanner.nextLine();
            Validator.isValidGender(gender);
            
            System.out.print("Address: ");
            String address = scanner.nextLine();
            
            patientService.addPatient(name, email, phone, age, gender, address);
            
        } catch (InvalidDataException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * View all patients.
     */
    private static void viewAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        
        System.out.println("\n========== PATIENTS LIST ==========");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }
    
    /**
     * Search for a patient.
     */
    private static void searchPatient() {
        System.out.println("\n--- Search Patient ---");
        System.out.print("Enter patient name or ID: ");
        String query = scanner.nextLine();
        
        Patient result = patientService.searchByName(query);
        if (result == null) {
            result = patientService.searchById(query);
        }
        
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Patient not found.");
        }
    }
    
    /**
     * Update patient information.
     */
    private static void updatePatient() {
        System.out.println("\n--- Update Patient ---");
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        System.out.print("New Blood Type (press Enter to skip): ");
        String bloodType = scanner.nextLine();
        if (!bloodType.isEmpty()) {
            try {
                Validator.isValidBloodType(bloodType);
                patient.setBloodType(bloodType);
            } catch (InvalidDataException e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }
        }
        
        System.out.print("Height (cm): ");
        String heightStr = scanner.nextLine();
        if (!heightStr.isEmpty()) {
            try {
                patient.setHeight(Double.parseDouble(heightStr));
            } catch (NumberFormatException e) {
                System.err.println("Invalid height format.");
                return;
            }
        }
        
        System.out.print("Weight (kg): ");
        String weightStr = scanner.nextLine();
        if (!weightStr.isEmpty()) {
            try {
                patient.setWeight(Double.parseDouble(weightStr));
                System.out.println("BMI: " + String.format("%.2f", patient.getBMI()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid weight format.");
                return;
            }
        }
        
        patientService.updatePatient(patientId, patient);
    }
    
    /**
     * Add allergy to patient
     */
    private static void addAllergy() {
        System.out.println("\n--- Add Allergy ---");
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        System.out.print("Allergy name: ");
        String allergy = scanner.nextLine();
        
        patientService.addAllergy(patientId, allergy);
    }
    
    /**
     * Appointment management menu
     */
    private static void appointmentMenu() {
        while (true) {
            System.out.println("\n========== APPOINTMENT MANAGEMENT ==========");
            System.out.println("1. Create Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Confirm Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Complete Appointment");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    createAppointment();
                    break;
                case "2":
                    viewAllAppointments();
                    break;
                case "3":
                    confirmAppointment();
                    break;
                case "4":
                    cancelAppointment();
                    break;
                case "5":
                    completeAppointment();
                    break;
                case "6":
                    viewUpcomingAppointments();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Create new appointment
     */
    private static void createAppointment() {
        try {
            System.out.println("\n--- Create Appointment ---");
            System.out.print("Patient ID: ");
            String patientId = scanner.nextLine();
            
            if (patientService.getPatientById(patientId) == null) {
                System.out.println("Patient not found.");
                return;
            }
            
            System.out.print("Doctor ID: ");
            String doctorId = scanner.nextLine();
            
            if (doctorService.getDoctorById(doctorId) == null) {
                System.out.println("Doctor not found.");
                return;
            }
            
            System.out.print("Appointment Date (dd-MM-yyyy): ");
            String dateStr = scanner.nextLine();
            LocalDate date = DateUtil.parseDate(dateStr);
            
            System.out.print("Appointment Time (HH:mm): ");
            String timeStr = scanner.nextLine();
            LocalTime time = LocalTime.parse(timeStr);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            
            System.out.print("Reason: ");
            String reason = scanner.nextLine();
            
            appointmentService.createAppointment(patientId, doctorId, dateTime, reason);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * View all appointments
     */
    private static void viewAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        
        System.out.println("\n========== APPOINTMENTS LIST ==========");
        for (Appointment apt : appointments) {
            System.out.println(apt);
        }
    }
    
    /**
     * Confirm appointment
     */
    private static void confirmAppointment() {
        System.out.println("\n--- Confirm Appointment ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine();
        
        try {
            appointmentService.confirmAppointment(appointmentId);
        } catch (AppointmentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Cancel appointment
     */
    private static void cancelAppointment() {
        System.out.println("\n--- Cancel Appointment ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine();
        
        try {
            appointmentService.cancelAppointment(appointmentId);
        } catch (AppointmentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Complete appointment
     */
    private static void completeAppointment() {
        System.out.println("\n--- Complete Appointment ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine();
        
        System.out.print("Add notes (optional): ");
        String notes = scanner.nextLine();
        
        try {
            appointmentService.completeAppointment(appointmentId, notes);
        } catch (AppointmentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * View upcoming appointments
     */
    private static void viewUpcomingAppointments() {
        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointments();
        
        if (upcomingAppointments.isEmpty()) {
            System.out.println("No upcoming appointments.");
            return;
        }
        
        System.out.println("\n========== UPCOMING APPOINTMENTS ==========");
        for (Appointment apt : upcomingAppointments) {
            System.out.println(apt);
        }
    }
    
    /**
     * Billing management menu
     */
    private static void billingMenu() {
        while (true) {
            System.out.println("\n========== BILLING MANAGEMENT ==========");
            System.out.println("1. Generate Bill for Appointment");
            System.out.println("2. View All Bills");
            System.out.println("3. View Bill Details");
            System.out.println("4. Mark Bill as Paid");
            System.out.println("5. View Pending Bills");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    generateBill();
                    break;
                case "2":
                    viewAllBills();
                    break;
                case "3":
                    viewBillDetails();
                    break;
                case "4":
                    markBillPaid();
                    break;
                case "5":
                    viewPendingBills();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Generate bill for appointment
     */
    private static void generateBill() {
        System.out.println("\n--- Generate Bill ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine();
        
        try {
            Bill bill = billingService.generateBillForAppointment(appointmentId);
            System.out.println(bill);
        } catch (AppointmentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * View all bills
     */
    private static void viewAllBills() {
        List<Bill> bills = billingService.getAllBills();
        
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        
        System.out.println("\n========== BILLS LIST ==========");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
    }
    
    /**
     * View bill details
     */
    private static void viewBillDetails() {
        System.out.println("\n--- View Bill Details ---");
        System.out.print("Enter Bill ID: ");
        String billId = scanner.nextLine();
        
        billingService.viewBillDetails(billId);
    }
    
    /**
     * Mark bill as paid
     */
    private static void markBillPaid() {
        System.out.println("\n--- Mark Bill as Paid ---");
        System.out.print("Enter Bill ID: ");
        String billId = scanner.nextLine();
        
        billingService.markBillAsPaid(billId);
    }
    
    /**
     * View pending bills
     */
    private static void viewPendingBills() {
        List<Bill> pending = billingService.getPendingBills();
        
        if (pending.isEmpty()) {
            System.out.println("No pending bills.");
            return;
        }
        
        System.out.println("\n========== PENDING BILLS ==========");
        System.out.println("Total Outstanding: ₹" + String.format("%.2f", billingService.getOutstandingAmount()));
        for (Bill bill : pending) {
            System.out.println(bill);
        }
    }
    
    /**
     * Reports and analytics menu
     */
    private static void reportsMenu() {
        while (true) {
            System.out.println("\n========== REPORTS & ANALYTICS ==========");
            System.out.println("1. System Statistics");
            System.out.println("2. Doctor Analytics");
            System.out.println("3. Financial Reports");
            System.out.println("4. Appointment Statistics");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    systemStatistics();
                    break;
                case "2":
                    doctorAnalytics();
                    break;
                case "3":
                    financialReports();
                    break;
                case "4":
                    appointmentStatistics();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Display system statistics
     */
    private static void systemStatistics() {
        System.out.println("\n========== SYSTEM STATISTICS ==========");
        System.out.println("Total Doctors: " + doctorService.getTotalDoctors());
        System.out.println("Total Patients: " + patientService.getTotalPatients());
        System.out.println("Total Appointments: " + appointmentService.getTotalAppointments());
        System.out.println("Total Bills: " + billingService.getAllBills().size());
    }
    
    /**
     * Display doctor analytics
     */
    private static void doctorAnalytics() {
        System.out.println("\n========== DOCTOR ANALYTICS ==========");
        
        for (Specialization spec : Specialization.values()) {
            double avg = doctorService.getAverageConsultationFee(spec);
            if (avg > 0) {
                System.out.println(spec + ": Average Fee ₹" + String.format("%.2f", avg));
            }
        }
    }
    
    /**
     * Display financial reports
     */
    private static void financialReports() {
        System.out.println("\n========== FINANCIAL REPORTS ==========");
        System.out.println("Total Revenue (Paid): ₹" + String.format("%.2f", billingService.getTotalRevenue()));
        System.out.println("Outstanding Amount: ₹" + String.format("%.2f", billingService.getOutstandingAmount()));
        System.out.println("Average Bill Amount: ₹" + String.format("%.2f", billingService.getAverageBillAmount()));
        System.out.println("Paid Bills: " + billingService.getPaidBills().size());
        System.out.println("Pending Bills: " + billingService.getPendingBills().size());
    }
    
    /**
     * Display appointment statistics
     */
    private static void appointmentStatistics() {
        System.out.println("\n========== APPOINTMENT STATISTICS ==========");
        System.out.println("Total Appointments: " + appointmentService.getTotalAppointments());
        System.out.println("Confirmed: " + appointmentService.getAppointmentsByStatus(AppointmentStatus.CONFIRMED).size());
        System.out.println("Pending: " + appointmentService.getAppointmentsByStatus(AppointmentStatus.PENDING).size());
        System.out.println("Completed: " + appointmentService.getAppointmentsByStatus(AppointmentStatus.COMPLETED).size());
        System.out.println("Cancelled: " + appointmentService.getAppointmentsByStatus(AppointmentStatus.CANCELLED).size());
        System.out.println("Upcoming: " + appointmentService.getUpcomingAppointments().size());
    }
    
    /**
     * Load sample/demo data
     */
    private static void loadSampleData() {
        System.out.println("Loading sample data...");
        
        try {
            // Add sample doctors
            doctorService.addDoctor("Dr. Rajesh Kumar", "rajesh@meditrack.com", "9876543210", 45,
                    "Male", "Mumbai", Specialization.CARDIOLOGIST, 500, 15, "LIC001");
            doctorService.addDoctor("Dr. Priya Sharma", "priya@meditrack.com", "9876543211", 38,
                    "Female", "Mumbai", Specialization.DERMATOLOGIST, 400, 10, "LIC002");
            
            // Add sample patients
            patientService.addPatient("Amit Singh", "amit@email.com", "9123456789", 35, "Male", "Delhi");
            patientService.addPatient("Neha Patel", "neha@email.com", "9123456790", 28, "Female", "Bangalore");
            
            System.out.println("Sample data loaded successfully!");
            
        } catch (Exception e) {
            System.err.println("Error loading sample data: " + e.getMessage());
        }
    }
}
