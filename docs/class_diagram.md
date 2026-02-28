classDiagram

%% =========================
%% Core Entities
%% =========================

class Appointment {
    -String appointmentId
    -String patientId
    -String doctorId
    -LocalDateTime appointmentDateTime
    -String reason
    -AppointmentStatus status
    -String notes
    -double consultationFee
}

class Bill {
    -String billId
    -String appointmentId
    -String patientId
    -String doctorId
    -double consultationFee
    -double medicinesCharges
    -double testCharges
    -double otherCharges
    -double taxAmount
    -double totalAmount
    -LocalDateTime billDate
    -boolean isPaid
}

class BillSummary {
    -String billId
    -String patientName
    -String doctorName
    -double totalAmount
    -double taxAmount
    -LocalDateTime billDate
    -boolean isPaid
}

%% =========================
%% Person Hierarchy
%% =========================

class Person {
    <<abstract>>
    -String id
    -String name
    -String email
    -String phone
    -int age
    -String gender
    -String address
}

class Doctor {
    -Specialization specialization
    -double consultationFee
    -int yearsOfExperience
    -String licenseNumber
    -double rating
    -int totalPatients
}

class Patient {
    -String medicalHistory
    -List~String~ allergies
    -double height
    -double weight
    -String bloodType
    -String emergencyContact
}

Person <|-- Doctor
Person <|-- Patient

%% =========================
%% Enums
%% =========================

class AppointmentStatus {
    <<enum>>
    PENDING
    CONFIRMED
    COMPLETED
    CANCELLED
    NO_SHOW
}

class Specialization {
    <<enum>>
    GENERAL_PRACTITIONER
    CARDIOLOGIST
    DERMATOLOGIST
    PEDIATRICIAN
    ORTHOPEDIC
    NEUROLOGIST
    PSYCHIATRIST
    OPHTHALMOLOGIST
    ENT
    SURGEON
}

%% =========================
%% Relationships
%% =========================

Appointment --> AppointmentStatus
Doctor --> Specialization

Appointment --> Patient : patientId
Appointment --> Doctor : doctorId

Bill --> Appointment : appointmentId
Bill --> Patient : patientId
Bill --> Doctor : doctorId

BillSummary --> Bill : summarizes