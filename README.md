# MediTrack - Clinic & Appointment Management System

## Overview

**MediTrack** is a comprehensive Clinic & Appointment Management System built in **Core Java**. It demonstrates professional software engineering practices, SOLID principles, and advanced Java fundamentals including OOP, design patterns, collections, exceptions, and file I/O.

The system enables clinics to:
- Manage doctors and their specializations
- Register and track patients
- Schedule and manage appointments
- Generate and process bills
- Generate reports and analytics

# Steps to compile and run :- 

## compile (Main)
	javac -d out -sourcepath src/main/java src/main/java/com/airtribe/meditrack/Main.java
## Run
	java -cp out com.airtribe.meditrack.Main
		
## compile (Console UI Main)
	javac -d out -sourcepath src/main/java src/main/java/com/airtribe/meditrack/ui/Main.java
## Run
	java -cp out com.airtribe.meditrack.ui.Main

# Results samples
--> docs/results	

## Usage

### Main Menu
```
========== MEDITRACK MAIN MENU ==========
1. Doctor Management
2. Patient Management
3. Appointment Management
4. Billing Management
5. Reports & Analytics
6. Exit
Select option: _
```

### Doctor Management
- **Add Doctor**: Register new doctor with specialization and fees
- **View All Doctors**: List all registered doctors
- **Search Doctor**: Find doctor by ID or name
- **Update Doctor**: Modify doctor fees or rating
- **Remove Doctor**: Deregister a doctor

### Patient Management
- **Add Patient**: Register new patient
- **View All Patients**: List all registered patients
- **Search Patient**: Find patient by ID, name, or age
- **Update Patient**: Update health information (blood type, height, weight)
- **Add Allergy**: Record patient allergies
- **View BMI**: Calculate and display BMI

### Appointment Management
- **Create Appointment**: Schedule appointment between doctor and patient
- **View All Appointments**: List all appointments
- **Confirm Appointment**: Mark appointment as confirmed
- **Cancel Appointment**: Cancel appointment
- **Complete Appointment**: Mark appointment as completed with notes
- **View Upcoming**: List future appointments

### Billing Management
- **Generate Bill**: Create bill for completed appointment
- **View All Bills**: List all generated bills
- **View Bill Details**: Show detailed bill breakdown
- **Mark Paid**: Mark bill as paid
- **View Pending Bills**: List outstanding bills

### Reports & Analytics
- **System Statistics**: Total doctors, patients, appointments, bills
- **Doctor Analytics**: Average fees by specialization
- **Financial Reports**: Revenue, outstanding, average bill amount
- **Appointment Statistics**: Appointments by status, upcoming appointments
