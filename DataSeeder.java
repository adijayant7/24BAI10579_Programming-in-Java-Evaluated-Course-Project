package util;

import model.*;
import service.*;
import java.time.LocalDateTime;

public class DataSeeder {

    public static void seed(PatientService ps, DoctorService ds,
                             AppointmentService as, BillingService bs) {

        // Seed Doctors
        Doctor d1 = ds.addDoctor("Dr. Anil Sharma",    "Cardiology",    "9876543210", "anil@hospital.com",   15, "Mon-Fri 9AM-5PM");
        Doctor d2 = ds.addDoctor("Dr. Priya Verma",    "Neurology",     "9876543211", "priya@hospital.com",  10, "Mon-Sat 10AM-4PM");
        Doctor d3 = ds.addDoctor("Dr. Rahul Gupta",    "Orthopedics",   "9876543212", "rahul@hospital.com",  12, "Tue-Sat 8AM-3PM");
        Doctor d4 = ds.addDoctor("Dr. Sunita Patel",   "Pediatrics",    "9876543213", "sunita@hospital.com",  8, "Mon-Fri 9AM-6PM");
        Doctor d5 = ds.addDoctor("Dr. Manoj Singh",    "General Surgery","9876543214","manoj@hospital.com",  20, "Mon-Fri 7AM-2PM");

        // Seed Patients
        Patient p1 = ps.addPatient("Ramesh Kumar",    45, "Male",   "A+", "9812345678", "12 Gandhi Nagar, Bhopal",  "Hypertension");
        Patient p2 = ps.addPatient("Sita Devi",       32, "Female", "B+", "9812345679", "45 Nehru Road, Indore",    "Migraine");
        Patient p3 = ps.addPatient("Arjun Patel",     28, "Male",   "O+", "9812345680", "78 MG Road, Bhopal",       "Fracture - Left Leg");
        Patient p4 = ps.addPatient("Meena Joshi",      7, "Female", "AB+","9812345681", "22 Shivaji Marg, Jabalpur","Fever & Cold");
        Patient p5 = ps.addPatient("Vikram Singh",    60, "Male",   "A-", "9812345682", "90 Sadar Bazaar, Gwalior", "Chest Pain");

        // Assign patients to doctors
        ds.assignPatientToDoctor(d1.getDoctorId(), p1.getPatientId());
        ds.assignPatientToDoctor(d1.getDoctorId(), p5.getPatientId());
        ds.assignPatientToDoctor(d2.getDoctorId(), p2.getPatientId());
        ds.assignPatientToDoctor(d3.getDoctorId(), p3.getPatientId());
        ds.assignPatientToDoctor(d4.getDoctorId(), p4.getPatientId());

        // Seed Appointments
        as.scheduleAppointment(p1.getPatientId(), d1.getDoctorId(), p1.getName(), d1.getName(),
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), "Regular Checkup");
        as.scheduleAppointment(p2.getPatientId(), d2.getDoctorId(), p2.getName(), d2.getName(),
                LocalDateTime.now().plusDays(2).withHour(11).withMinute(30), "Headache Consultation");
        as.scheduleAppointment(p3.getPatientId(), d3.getDoctorId(), p3.getName(), d3.getName(),
                LocalDateTime.now().plusDays(1).withHour(9).withMinute(0), "Fracture Follow-up");
        as.scheduleAppointment(p4.getPatientId(), d4.getDoctorId(), p4.getName(), d4.getName(),
                LocalDateTime.now().withHour(14).withMinute(0), "Fever Assessment");

        // Seed Bills
        Bill b1 = bs.generateBill(p1.getPatientId(), p1.getName(), 3, false);
        bs.processPayment(b1.getBillId(), 5000);

        Bill b2 = bs.generateBill(p3.getPatientId(), p3.getName(), 5, true);
        // Not yet paid

        Bill b3 = bs.generateBill(p5.getPatientId(), p5.getName(), 2, false);
        bs.processPayment(b3.getBillId(), b3.getTotalAmount()); // fully paid
    }
}
