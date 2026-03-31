package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private static int idCounter = 200;

    private int doctorId;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private int experience; // years
    private String availability; // e.g., "Mon-Fri 9AM-5PM"
    private List<Integer> assignedPatientIds;

    public Doctor(String name, String specialization, String phone,
                  String email, int experience, String availability) {
        this.doctorId = ++idCounter;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.experience = experience;
        this.availability = availability;
        this.assignedPatientIds = new ArrayList<>();
    }

    // Getters
    public int getDoctorId()            { return doctorId; }
    public String getName()             { return name; }
    public String getSpecialization()   { return specialization; }
    public String getPhone()            { return phone; }
    public String getEmail()            { return email; }
    public int getExperience()          { return experience; }
    public String getAvailability()     { return availability; }
    public List<Integer> getAssignedPatientIds() { return assignedPatientIds; }

    // Setters
    public void setName(String name)                 { this.name = name; }
    public void setSpecialization(String spec)        { this.specialization = spec; }
    public void setPhone(String phone)               { this.phone = phone; }
    public void setEmail(String email)               { this.email = email; }
    public void setExperience(int experience)        { this.experience = experience; }
    public void setAvailability(String availability) { this.availability = availability; }

    public void assignPatient(int patientId)   { assignedPatientIds.add(patientId); }
    public void removePatient(int patientId)   { assignedPatientIds.remove(Integer.valueOf(patientId)); }
    public int getPatientCount()               { return assignedPatientIds.size(); }

    @Override
    public String toString() {
        return String.format("| %-6d | %-20s | %-20s | %-4d yrs | %-15s | %-8d |",
                doctorId, name, specialization, experience, phone, assignedPatientIds.size());
    }
}
