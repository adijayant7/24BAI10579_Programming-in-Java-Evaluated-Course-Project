package service;

import model.Patient;
import java.util.*;
import java.util.stream.Collectors;

public class PatientService {
    private Map<Integer, Patient> patients = new LinkedHashMap<>();

    public Patient addPatient(String name, int age, String gender, String bloodGroup,
                               String phone, String address, String disease) {
        Patient p = new Patient(name, age, gender, bloodGroup, phone, address, disease);
        patients.put(p.getPatientId(), p);
        return p;
    }

    public boolean removePatient(int id) {
        return patients.remove(id) != null;
    }

    public Patient getPatient(int id) {
        return patients.get(id);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public List<Patient> getAdmittedPatients() {
        return patients.values().stream()
                .filter(Patient::isAdmitted)
                .collect(Collectors.toList());
    }

    public List<Patient> searchByName(String name) {
        String lower = name.toLowerCase();
        return patients.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Patient> searchByDisease(String disease) {
        String lower = disease.toLowerCase();
        return patients.values().stream()
                .filter(p -> p.getDisease().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public boolean dischargePatient(int id) {
        Patient p = patients.get(id);
        if (p != null && p.isAdmitted()) {
            p.setAdmitted(false);
            return true;
        }
        return false;
    }

    public int getTotalPatients()    { return patients.size(); }
    public int getAdmittedCount()    { return (int) patients.values().stream().filter(Patient::isAdmitted).count(); }
    public int getDischargedCount()  { return (int) patients.values().stream().filter(p -> !p.isAdmitted()).count(); }

    public boolean exists(int id) { return patients.containsKey(id); }
}
