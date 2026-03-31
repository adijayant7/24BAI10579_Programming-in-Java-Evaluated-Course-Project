package service;

import model.Doctor;
import java.util.*;
import java.util.stream.Collectors;

public class DoctorService {
    private Map<Integer, Doctor> doctors = new LinkedHashMap<>();

    public Doctor addDoctor(String name, String specialization, String phone,
                             String email, int experience, String availability) {
        Doctor d = new Doctor(name, specialization, phone, email, experience, availability);
        doctors.put(d.getDoctorId(), d);
        return d;
    }

    public boolean removeDoctor(int id) {
        return doctors.remove(id) != null;
    }

    public Doctor getDoctor(int id) {
        return doctors.get(id);
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors.values());
    }

    public List<Doctor> searchByName(String name) {
        String lower = name.toLowerCase();
        return doctors.values().stream()
                .filter(d -> d.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Doctor> searchBySpecialization(String spec) {
        String lower = spec.toLowerCase();
        return doctors.values().stream()
                .filter(d -> d.getSpecialization().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public void assignPatientToDoctor(int doctorId, int patientId) {
        Doctor d = doctors.get(doctorId);
        if (d != null) d.assignPatient(patientId);
    }

    public void removePatientFromDoctor(int doctorId, int patientId) {
        Doctor d = doctors.get(doctorId);
        if (d != null) d.removePatient(patientId);
    }

    public int getTotalDoctors() { return doctors.size(); }
    public boolean exists(int id) { return doctors.containsKey(id); }
}
