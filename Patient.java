package model;

import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private static int idCounter = 1000;

    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String phone;
    private String address;
    private String disease;
    private LocalDate admissionDate;
    private boolean admitted;

    public Patient(String name, int age, String gender, String bloodGroup,
                   String phone, String address, String disease) {
        this.patientId = ++idCounter;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.address = address;
        this.disease = disease;
        this.admissionDate = LocalDate.now();
        this.admitted = true;
    }

    // Getters
    public int getPatientId()       { return patientId; }
    public String getName()         { return name; }
    public int getAge()             { return age; }
    public String getGender()       { return gender; }
    public String getBloodGroup()   { return bloodGroup; }
    public String getPhone()        { return phone; }
    public String getAddress()      { return address; }
    public String getDisease()      { return disease; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public boolean isAdmitted()     { return admitted; }

    // Setters
    public void setName(String name)         { this.name = name; }
    public void setAge(int age)              { this.age = age; }
    public void setGender(String gender)     { this.gender = gender; }
    public void setBloodGroup(String bg)     { this.bloodGroup = bg; }
    public void setPhone(String phone)       { this.phone = phone; }
    public void setAddress(String address)   { this.address = address; }
    public void setDisease(String disease)   { this.disease = disease; }
    public void setAdmitted(boolean admitted){ this.admitted = admitted; }

    public int getDaysAdmitted() {
        return Period.between(admissionDate, LocalDate.now()).getDays();
    }

    @Override
    public String toString() {
        return String.format("| %-6d | %-20s | %-4d | %-7s | %-10s | %-15s | %-25s |",
                patientId, name, age, gender, bloodGroup, phone, disease);
    }
}
