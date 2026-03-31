package service;

import model.Appointment;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentService {
    private Map<Integer, Appointment> appointments = new LinkedHashMap<>();

    public Appointment scheduleAppointment(int patientId, int doctorId,
                                            String patientName, String doctorName,
                                            LocalDateTime dateTime, String reason) {
        Appointment a = new Appointment(patientId, doctorId, patientName, doctorName, dateTime, reason);
        appointments.put(a.getAppointmentId(), a);
        return a;
    }

    public boolean cancelAppointment(int id) {
        Appointment a = appointments.get(id);
        if (a != null && a.getStatus() == Appointment.Status.SCHEDULED) {
            a.setStatus(Appointment.Status.CANCELLED);
            return true;
        }
        return false;
    }

    public boolean completeAppointment(int id, String notes) {
        Appointment a = appointments.get(id);
        if (a != null && a.getStatus() == Appointment.Status.SCHEDULED) {
            a.setStatus(Appointment.Status.COMPLETED);
            a.setNotes(notes);
            return true;
        }
        return false;
    }

    public Appointment getAppointment(int id) {
        return appointments.get(id);
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public List<Appointment> getByPatient(int patientId) {
        return appointments.values().stream()
                .filter(a -> a.getPatientId() == patientId)
                .collect(Collectors.toList());
    }

    public List<Appointment> getByDoctor(int doctorId) {
        return appointments.values().stream()
                .filter(a -> a.getDoctorId() == doctorId)
                .collect(Collectors.toList());
    }

    public List<Appointment> getScheduled() {
        return appointments.values().stream()
                .filter(a -> a.getStatus() == Appointment.Status.SCHEDULED)
                .collect(Collectors.toList());
    }

    public int getTotalAppointments()   { return appointments.size(); }
    public boolean exists(int id)        { return appointments.containsKey(id); }
}
