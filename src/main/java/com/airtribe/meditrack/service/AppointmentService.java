package com.airtribe.meditrack.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

/**
 * Service class for managing appointments.
 * Demonstrates appointment creation, cancellation, and status management.
 */
public class AppointmentService {
    private DataStore<Appointment> appointmentStore;
    private DoctorService doctorService;
    private PatientService patientService;
    private IdGenerator idGenerator;
    
    /**
     * Instantiates a new appointment service.
     *
     * @param doctorService the doctor service
     * @param patientService the patient service
     */
    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.appointmentStore = new DataStore<>();
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.idGenerator = IdGenerator.getInstance();
    }
    
    /**
     * Create a new appointment.
     *
     * @param patientId the patient id
     * @param doctorId the doctor id
     * @param appointmentDateTime the appointment date time
     * @param reason the reason
     * @return the appointment
     * @throws AppointmentNotFoundException the appointment not found exception
     */
    public Appointment createAppointment(String patientId, String doctorId, 
                                        LocalDateTime appointmentDateTime, String reason) 
            throws AppointmentNotFoundException {
        
        // Verify doctor and patient exist
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientById(patientId);
        
        if (doctor == null || patient == null) {
            throw new AppointmentNotFoundException("Invalid doctor or patient ID");
        }
        
        String appointmentId = idGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, 
                appointmentDateTime, reason);
        appointment.setConsultationFee(doctor.getConsultationFee());
        
        appointmentStore.add(appointmentId, appointment);
        System.out.println("Appointment created: " + appointmentId + " - " + patient.getName() + 
                " with Dr. " + doctor.getName());
        return appointment;
    }
    
    /**
     * Get appointment by ID.
     *
     * @param appointmentId the appointment id
     * @return the appointment by id
     * @throws AppointmentNotFoundException the appointment not found exception
     */
    public Appointment getAppointmentById(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.get(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found", appointmentId);
        }
        return appointment;
    }
    
    /**
     * Get all appointments.
     *
     * @return the all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }
    
    /**
     * Get appointments for a specific patient.
     *
     * @param patientId the patient id
     * @return the appointments by patient id
     */
    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        return appointmentStore.search(apt -> apt.getPatientId().equals(patientId));
    }
    
    /**
     * Get appointments for a specific doctor.
     *
     * @param doctorId the doctor id
     * @return the appointments by doctor id
     */
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        return appointmentStore.search(apt -> apt.getDoctorId().equals(doctorId));
    }
    
    /**
     * Confirm appointment.
     *
     * @param appointmentId the appointment id
     * @throws AppointmentNotFoundException the appointment not found exception
     */
    public void confirmAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        System.out.println("Appointment confirmed: " + appointmentId);
    }
    
    /**
     * Cancel appointment.
     *
     * @param appointmentId the appointment id
     * @throws AppointmentNotFoundException the appointment not found exception
     */
	public void cancelAppointment(String appointmentId) throws AppointmentNotFoundException {
		Appointment appointment = getAppointmentById(appointmentId);
		if (appointment.getStatus() != null && appointment.getStatus().equals(AppointmentStatus.COMPLETED)) {
			throw new AppointmentNotFoundException("Appointment is completed, cannot be cancelled", appointmentId);
		}
		appointment.setStatus(AppointmentStatus.CANCELLED);
		System.out.println("Appointment cancelled: " + appointmentId);
	}
    
    /**
     * Mark appointment as completed.
     *
     * @param appointmentId the appointment id
     * @param notes the notes
     * @throws AppointmentNotFoundException the appointment not found exception
     */
    public void completeAppointment(String appointmentId, String notes) 
            throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        if (notes != null && !notes.isEmpty()) {
            appointment.setNotes(notes);
        }
        System.out.println("Appointment completed: " + appointmentId);
    }
    
    /**
     * Get appointments by status.
     *
     * @param status the status
     * @return the appointments by status
     */
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentStore.search(apt -> apt.getStatus() == status);
    }
    
    /**
     * Get upcoming appointments.
     *
     * @return the upcoming appointments
     */
    public List<Appointment> getUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        return appointmentStore.search(apt -> 
            apt.getAppointmentDateTime().isAfter(now) && 
            apt.getStatus() != AppointmentStatus.CANCELLED);
    }
    
    /**
     * Get total number of appointments.
     *
     * @return the total appointments
     */
    public int getTotalAppointments() {
        return appointmentStore.size();
    }
    
    /**
     * Get total appointments for a doctor.
     *
     * @param doctorId the doctor id
     * @return the doctor appointment count
     */
    public int getDoctorAppointmentCount(String doctorId) {
        return (int) appointmentStore.getAll().stream()
                .filter(apt -> apt.getDoctorId().equals(doctorId))
                .count();
    }

    /**
     * Suggest available appointment slots for a doctor within the next `daysAhead` days.
     * Generates slots during 09:00-17:00 in 30-minute increments and excludes times already booked (non-cancelled).
     * Returns up to `maxSlots` suggestions.
     */
    public List<LocalDateTime> suggestAvailableSlots(String doctorId, int daysAhead, int maxSlots) {
        List<LocalDateTime> suggestions = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        List<LocalDateTime> booked = getAppointmentsByDoctorId(doctorId).stream()
                .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED)
                .map(Appointment::getAppointmentDateTime)
                .collect(Collectors.toList());

        for (int d = 0; d < Math.max(1, daysAhead) && suggestions.size() < maxSlots; d++) {
            LocalDateTime day = now.plusDays(d).with(start);
            for (LocalDateTime slot = day; slot.toLocalTime().isBefore(end) && suggestions.size() < maxSlots; slot = slot.plusMinutes(30)) {
                if (slot.isBefore(now)) continue;
                boolean conflict = false;
                for (LocalDateTime b : booked) {
                    if (b.equals(slot)) { conflict = true; break; }
                }
                if (!conflict) suggestions.add(slot);
            }
        }
        return suggestions;
    }

    /**
     * Recommend doctors by symptoms (delegates to DoctorService) and suggest slots for each recommended doctor.
     */
    public Map<Doctor, List<LocalDateTime>> suggestSlotsForSymptoms(List<String> symptoms, int maxDoctors, int daysAhead, int slotsPerDoctor) {
        Map<Doctor, List<LocalDateTime>> result = new HashMap<>();
        List<Doctor> recommended = doctorService.recommendBySymptoms(symptoms, maxDoctors);
        for (Doctor d : recommended) {
            List<LocalDateTime> slots = suggestAvailableSlots(d.getId(), daysAhead, slotsPerDoctor);
            result.put(d, slots);
        }
        return result;
    }
    
    /**
     * Clone an appointment (demonstrates deep cloning).
     *
     * @param appointmentId the appointment id
     * @return the appointment
     * @throws AppointmentNotFoundException the appointment not found exception
     * @throws CloneNotSupportedException the clone not supported exception
     */
    public Appointment cloneAppointment(String appointmentId) throws AppointmentNotFoundException, CloneNotSupportedException {
        Appointment original = getAppointmentById(appointmentId);
        Appointment cloned = original.clone();
        cloned.setAppointmentId(idGenerator.generateAppointmentId());
        cloned.setStatus(AppointmentStatus.PENDING);
        return cloned;
    }
    
    /**
     * Reschedule appointment.
     *
     * @param appointmentId the appointment id
     * @param newDateTime the new date time
     * @throws AppointmentNotFoundException the appointment not found exception
     */
    public void rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) 
            throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setAppointmentDateTime(newDateTime);
        System.out.println("Appointment rescheduled: " + appointmentId);
    }
}
