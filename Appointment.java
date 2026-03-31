package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private static int idCounter = 5000;

    public enum Status { SCHEDULED, COMPLETED, CANCELLED }

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String patientName;
    private String doctorName;
    private LocalDateTime dateTime;
    private String reason;
    private Status status;
    private String notes;

    public Appointment(int patientId, int doctorId, String patientName,
                       String doctorName, LocalDateTime dateTime, String reason) {
        this.appointmentId = ++idCounter;
        this.patientId    = patientId;
        this.doctorId     = doctorId;
        this.patientName  = patientName;
        this.doctorName   = doctorName;
        this.dateTime     = dateTime;
        this.reason       = reason;
        this.status       = Status.SCHEDULED;
        this.notes        = "";
    }

    // Getters
    public int getAppointmentId()   { return appointmentId; }
    public int getPatientId()       { return patientId; }
    public int getDoctorId()        { return doctorId; }
    public String getPatientName()  { return patientName; }
    public String getDoctorName()   { return doctorName; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getReason()       { return reason; }
    public Status getStatus()       { return status; }
    public String getNotes()        { return notes; }

    // Setters
    public void setStatus(Status status) { this.status = status; }
    public void setNotes(String notes)   { this.notes = notes; }
    public void setDateTime(LocalDateTime dt) { this.dateTime = dt; }

    public String getFormattedDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("| %-6d | %-20s | %-20s | %-16s | %-12s | %-11s |",
                appointmentId, patientName, doctorName,
                getFormattedDateTime(), reason, status);
    }
}
