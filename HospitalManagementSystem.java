import model.*;
import service.*;
import util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class HospitalManagementSystem {

    private PatientService     patientService     = new PatientService();
    private DoctorService      doctorService      = new DoctorService();
    private AppointmentService appointmentService = new AppointmentService();
    private BillingService     billingService     = new BillingService();

    public static void main(String[] args) {
        HospitalManagementSystem hms = new HospitalManagementSystem();
        DataSeeder.seed(hms.patientService, hms.doctorService,
                        hms.appointmentService, hms.billingService);
        hms.run();
    }

    // =========================================================
    // MAIN LOOP
    // =========================================================
    private void run() {
        printWelcome();
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = ConsoleUI.readInt("Enter choice: ");
            switch (choice) {
                case 1  -> patientMenu();
                case 2  -> doctorMenu();
                case 3  -> appointmentMenu();
                case 4  -> billingMenu();
                case 5  -> showDashboard();
                case 0  -> exit = true;
                default -> ConsoleUI.printError("Invalid option. Please try again.");
            }
        }
        ConsoleUI.printHeader("Thank you for using Hospital Management System");
        System.out.println();
    }

    // =========================================================
    // WELCOME & MENUS
    // =========================================================
    private void printWelcome() {
        ConsoleUI.clearScreen();
        System.out.println(ConsoleUI.BLUE + ConsoleUI.BOLD);
        System.out.println("  ╔══════════════════════════════════════════════════════════════╗");
        System.out.println("  ║          HOSPITAL MANAGEMENT SYSTEM  v1.0                  ║");
        System.out.println("  ║          Powered by Java  |  City General Hospital          ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════════╝");
        System.out.println(ConsoleUI.RESET);
        ConsoleUI.printInfo("Sample data has been loaded. Use the menu to get started.");
        ConsoleUI.pause();
    }

    private void printMainMenu() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("MAIN MENU");
        ConsoleUI.printMenuOption(1, "Patient Management");
        ConsoleUI.printMenuOption(2, "Doctor Management");
        ConsoleUI.printMenuOption(3, "Appointment Management");
        ConsoleUI.printMenuOption(4, "Billing & Payments");
        ConsoleUI.printMenuOption(5, "Dashboard / Reports");
        ConsoleUI.printMenuOption(0, "Exit");
        ConsoleUI.printLine();
    }

    // =========================================================
    // PATIENT MENU
    // =========================================================
    private void patientMenu() {
        boolean back = false;
        while (!back) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("PATIENT MANAGEMENT");
            ConsoleUI.printMenuOption(1, "Add New Patient");
            ConsoleUI.printMenuOption(2, "View All Patients");
            ConsoleUI.printMenuOption(3, "View Admitted Patients");
            ConsoleUI.printMenuOption(4, "Search Patient by Name");
            ConsoleUI.printMenuOption(5, "Search Patient by Disease");
            ConsoleUI.printMenuOption(6, "View Patient Details");
            ConsoleUI.printMenuOption(7, "Discharge Patient");
            ConsoleUI.printMenuOption(8, "Delete Patient Record");
            ConsoleUI.printMenuOption(0, "Back to Main Menu");
            ConsoleUI.printLine();
            int ch = ConsoleUI.readInt("Enter choice: ");
            switch (ch) {
                case 1 -> addPatient();
                case 2 -> listPatients(patientService.getAllPatients(), "ALL PATIENTS");
                case 3 -> listPatients(patientService.getAdmittedPatients(), "ADMITTED PATIENTS");
                case 4 -> { String n = ConsoleUI.readString("Enter name to search: ");
                            listPatients(patientService.searchByName(n), "SEARCH RESULTS"); }
                case 5 -> { String d = ConsoleUI.readString("Enter disease to search: ");
                            listPatients(patientService.searchByDisease(d), "SEARCH RESULTS"); }
                case 6 -> viewPatientDetails();
                case 7 -> dischargePatient();
                case 8 -> deletePatient();
                case 0 -> back = true;
                default -> ConsoleUI.printError("Invalid option.");
            }
        }
    }

    private void addPatient() {
        ConsoleUI.printSubHeader("ADD NEW PATIENT");
        String name     = ConsoleUI.readString("Full Name       : ");
        int    age      = ConsoleUI.readInt   ("Age             : ");
        String gender   = ConsoleUI.readString("Gender (M/F/O)  : ");
        String blood    = ConsoleUI.readString("Blood Group     : ");
        String phone    = ConsoleUI.readString("Phone Number    : ");
        String address  = ConsoleUI.readString("Address         : ");
        String disease  = ConsoleUI.readString("Disease/Condition: ");
        Patient p = patientService.addPatient(name, age, gender, blood, phone, address, disease);
        ConsoleUI.printSuccess("Patient admitted! ID: " + p.getPatientId());
        ConsoleUI.pause();
    }

    private void listPatients(List<Patient> list, String title) {
        ConsoleUI.printSubHeader(title);
        if (list.isEmpty()) { ConsoleUI.printInfo("No patients found."); ConsoleUI.pause(); return; }
        System.out.println(ConsoleUI.CYAN);
        System.out.println("+---------+----------------------+------+---------+------------+-----------------+---------------------------+");
        System.out.println("| Pat.ID  | Name                 | Age  | Gender  | Blood Grp  | Phone           | Disease                   |");
        System.out.println("+---------+----------------------+------+---------+------------+-----------------+---------------------------+");
        System.out.print(ConsoleUI.RESET);
        for (Patient p : list) System.out.println(p);
        System.out.println(ConsoleUI.CYAN + "+---------+----------------------+------+---------+------------+-----------------+---------------------------+" + ConsoleUI.RESET);
        ConsoleUI.printInfo("Total: " + list.size() + " patient(s)");
        ConsoleUI.pause();
    }

    private void viewPatientDetails() {
        int id = ConsoleUI.readInt("Enter Patient ID: ");
        Patient p = patientService.getPatient(id);
        if (p == null) { ConsoleUI.printError("Patient not found."); ConsoleUI.pause(); return; }
        ConsoleUI.printSubHeader("PATIENT DETAILS");
        System.out.printf("  %-20s : %d%n",    "Patient ID",    p.getPatientId());
        System.out.printf("  %-20s : %s%n",    "Name",          p.getName());
        System.out.printf("  %-20s : %d%n",    "Age",           p.getAge());
        System.out.printf("  %-20s : %s%n",    "Gender",        p.getGender());
        System.out.printf("  %-20s : %s%n",    "Blood Group",   p.getBloodGroup());
        System.out.printf("  %-20s : %s%n",    "Phone",         p.getPhone());
        System.out.printf("  %-20s : %s%n",    "Address",       p.getAddress());
        System.out.printf("  %-20s : %s%n",    "Disease",       p.getDisease());
        System.out.printf("  %-20s : %s%n",    "Admitted On",   p.getAdmissionDate());
        System.out.printf("  %-20s : %d days%n","Days Admitted", p.getDaysAdmitted());
        System.out.printf("  %-20s : %s%n",    "Status",
                p.isAdmitted() ? ConsoleUI.GREEN + "ADMITTED" + ConsoleUI.RESET
                               : ConsoleUI.YELLOW + "DISCHARGED" + ConsoleUI.RESET);
        ConsoleUI.pause();
    }

    private void dischargePatient() {
        int id = ConsoleUI.readInt("Enter Patient ID to discharge: ");
        if (patientService.dischargePatient(id))
            ConsoleUI.printSuccess("Patient discharged successfully.");
        else
            ConsoleUI.printError("Patient not found or already discharged.");
        ConsoleUI.pause();
    }

    private void deletePatient() {
        int id = ConsoleUI.readInt("Enter Patient ID to delete: ");
        if (ConsoleUI.readYesNo("Are you sure?")) {
            if (patientService.removePatient(id))
                ConsoleUI.printSuccess("Patient record deleted.");
            else
                ConsoleUI.printError("Patient not found.");
        }
        ConsoleUI.pause();
    }

    // =========================================================
    // DOCTOR MENU
    // =========================================================
    private void doctorMenu() {
        boolean back = false;
        while (!back) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("DOCTOR MANAGEMENT");
            ConsoleUI.printMenuOption(1, "Add New Doctor");
            ConsoleUI.printMenuOption(2, "View All Doctors");
            ConsoleUI.printMenuOption(3, "Search by Specialization");
            ConsoleUI.printMenuOption(4, "View Doctor Details & Patients");
            ConsoleUI.printMenuOption(5, "Assign Patient to Doctor");
            ConsoleUI.printMenuOption(6, "Remove Patient from Doctor");
            ConsoleUI.printMenuOption(0, "Back to Main Menu");
            ConsoleUI.printLine();
            int ch = ConsoleUI.readInt("Enter choice: ");
            switch (ch) {
                case 1 -> addDoctor();
                case 2 -> listDoctors(doctorService.getAllDoctors());
                case 3 -> { String s = ConsoleUI.readString("Enter specialization: ");
                            listDoctors(doctorService.searchBySpecialization(s)); }
                case 4 -> viewDoctorDetails();
                case 5 -> assignPatientToDoctor();
                case 6 -> removePatientFromDoctor();
                case 0 -> back = true;
                default -> ConsoleUI.printError("Invalid option.");
            }
        }
    }

    private void addDoctor() {
        ConsoleUI.printSubHeader("ADD NEW DOCTOR");
        String name   = ConsoleUI.readString("Full Name         : ");
        String spec   = ConsoleUI.readString("Specialization    : ");
        String phone  = ConsoleUI.readString("Phone             : ");
        String email  = ConsoleUI.readString("Email             : ");
        int    exp    = ConsoleUI.readInt   ("Experience (yrs)  : ");
        String avail  = ConsoleUI.readString("Availability      : ");
        Doctor d = doctorService.addDoctor(name, spec, phone, email, exp, avail);
        ConsoleUI.printSuccess("Doctor added! ID: " + d.getDoctorId());
        ConsoleUI.pause();
    }

    private void listDoctors(List<Doctor> list) {
        ConsoleUI.printSubHeader("DOCTOR LIST");
        if (list.isEmpty()) { ConsoleUI.printInfo("No doctors found."); ConsoleUI.pause(); return; }
        System.out.println(ConsoleUI.CYAN);
        System.out.println("+--------+----------------------+----------------------+----------+-----------------+----------+");
        System.out.println("| Doc.ID | Name                 | Specialization       | Exp(Yrs) | Phone           | Patients |");
        System.out.println("+--------+----------------------+----------------------+----------+-----------------+----------+");
        System.out.print(ConsoleUI.RESET);
        for (Doctor d : list) System.out.println(d);
        System.out.println(ConsoleUI.CYAN + "+--------+----------------------+----------------------+----------+-----------------+----------+" + ConsoleUI.RESET);
        ConsoleUI.pause();
    }

    private void viewDoctorDetails() {
        int id = ConsoleUI.readInt("Enter Doctor ID: ");
        Doctor d = doctorService.getDoctor(id);
        if (d == null) { ConsoleUI.printError("Doctor not found."); ConsoleUI.pause(); return; }
        ConsoleUI.printSubHeader("DOCTOR DETAILS");
        System.out.printf("  %-20s : %d%n",  "Doctor ID",      d.getDoctorId());
        System.out.printf("  %-20s : %s%n",  "Name",           d.getName());
        System.out.printf("  %-20s : %s%n",  "Specialization", d.getSpecialization());
        System.out.printf("  %-20s : %s%n",  "Phone",          d.getPhone());
        System.out.printf("  %-20s : %s%n",  "Email",          d.getEmail());
        System.out.printf("  %-20s : %d yrs%n","Experience",   d.getExperience());
        System.out.printf("  %-20s : %s%n",  "Availability",   d.getAvailability());
        System.out.printf("  %-20s : %d%n",  "Patients Assigned", d.getPatientCount());
        if (!d.getAssignedPatientIds().isEmpty()) {
            System.out.println("  Assigned Patient IDs: " + d.getAssignedPatientIds());
        }
        ConsoleUI.pause();
    }

    private void assignPatientToDoctor() {
        int did = ConsoleUI.readInt("Enter Doctor ID  : ");
        int pid = ConsoleUI.readInt("Enter Patient ID : ");
        if (!doctorService.exists(did))  { ConsoleUI.printError("Doctor not found.");  ConsoleUI.pause(); return; }
        if (!patientService.exists(pid)) { ConsoleUI.printError("Patient not found."); ConsoleUI.pause(); return; }
        doctorService.assignPatientToDoctor(did, pid);
        ConsoleUI.printSuccess("Patient assigned to doctor.");
        ConsoleUI.pause();
    }

    private void removePatientFromDoctor() {
        int did = ConsoleUI.readInt("Enter Doctor ID  : ");
        int pid = ConsoleUI.readInt("Enter Patient ID : ");
        doctorService.removePatientFromDoctor(did, pid);
        ConsoleUI.printSuccess("Patient removed from doctor.");
        ConsoleUI.pause();
    }

    // =========================================================
    // APPOINTMENT MENU
    // =========================================================
    private void appointmentMenu() {
        boolean back = false;
        while (!back) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("APPOINTMENT MANAGEMENT");
            ConsoleUI.printMenuOption(1, "Schedule New Appointment");
            ConsoleUI.printMenuOption(2, "View All Appointments");
            ConsoleUI.printMenuOption(3, "View Scheduled (Upcoming)");
            ConsoleUI.printMenuOption(4, "View by Patient ID");
            ConsoleUI.printMenuOption(5, "View by Doctor ID");
            ConsoleUI.printMenuOption(6, "Complete Appointment");
            ConsoleUI.printMenuOption(7, "Cancel Appointment");
            ConsoleUI.printMenuOption(0, "Back to Main Menu");
            ConsoleUI.printLine();
            int ch = ConsoleUI.readInt("Enter choice: ");
            switch (ch) {
                case 1 -> scheduleAppointment();
                case 2 -> listAppointments(appointmentService.getAllAppointments(), "ALL APPOINTMENTS");
                case 3 -> listAppointments(appointmentService.getScheduled(), "SCHEDULED APPOINTMENTS");
                case 4 -> { int pid = ConsoleUI.readInt("Patient ID: ");
                            listAppointments(appointmentService.getByPatient(pid), "PATIENT APPOINTMENTS"); }
                case 5 -> { int did = ConsoleUI.readInt("Doctor ID: ");
                            listAppointments(appointmentService.getByDoctor(did), "DOCTOR APPOINTMENTS"); }
                case 6 -> completeAppointment();
                case 7 -> cancelAppointment();
                case 0 -> back = true;
                default -> ConsoleUI.printError("Invalid option.");
            }
        }
    }

    private void scheduleAppointment() {
        ConsoleUI.printSubHeader("SCHEDULE APPOINTMENT");
        int pid = ConsoleUI.readInt("Patient ID : ");
        int did = ConsoleUI.readInt("Doctor ID  : ");
        Patient p = patientService.getPatient(pid);
        Doctor  d = doctorService.getDoctor(did);
        if (p == null) { ConsoleUI.printError("Patient not found."); ConsoleUI.pause(); return; }
        if (d == null) { ConsoleUI.printError("Doctor not found.");  ConsoleUI.pause(); return; }
        String dateStr = ConsoleUI.readString("Date & Time (dd-MM-yyyy HH:mm): ");
        String reason  = ConsoleUI.readString("Reason: ");
        try {
            LocalDateTime dt = LocalDateTime.parse(dateStr,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            Appointment a = appointmentService.scheduleAppointment(
                    pid, did, p.getName(), d.getName(), dt, reason);
            ConsoleUI.printSuccess("Appointment scheduled! ID: " + a.getAppointmentId());
        } catch (DateTimeParseException e) {
            ConsoleUI.printError("Invalid date format. Use dd-MM-yyyy HH:mm");
        }
        ConsoleUI.pause();
    }

    private void listAppointments(List<Appointment> list, String title) {
        ConsoleUI.printSubHeader(title);
        if (list.isEmpty()) { ConsoleUI.printInfo("No appointments found."); ConsoleUI.pause(); return; }
        System.out.println(ConsoleUI.CYAN);
        System.out.println("+--------+----------------------+----------------------+------------------+--------------+-------------+");
        System.out.println("| App.ID | Patient              | Doctor               | Date & Time      | Reason       | Status      |");
        System.out.println("+--------+----------------------+----------------------+------------------+--------------+-------------+");
        System.out.print(ConsoleUI.RESET);
        for (Appointment a : list) System.out.println(a);
        System.out.println(ConsoleUI.CYAN + "+--------+----------------------+----------------------+------------------+--------------+-------------+" + ConsoleUI.RESET);
        ConsoleUI.pause();
    }

    private void completeAppointment() {
        int id = ConsoleUI.readInt("Enter Appointment ID: ");
        String notes = ConsoleUI.readString("Doctor's Notes: ");
        if (appointmentService.completeAppointment(id, notes))
            ConsoleUI.printSuccess("Appointment marked as completed.");
        else
            ConsoleUI.printError("Cannot complete — appointment not found or not scheduled.");
        ConsoleUI.pause();
    }

    private void cancelAppointment() {
        int id = ConsoleUI.readInt("Enter Appointment ID: ");
        if (appointmentService.cancelAppointment(id))
            ConsoleUI.printSuccess("Appointment cancelled.");
        else
            ConsoleUI.printError("Cannot cancel — appointment not found or not scheduled.");
        ConsoleUI.pause();
    }

    // =========================================================
    // BILLING MENU
    // =========================================================
    private void billingMenu() {
        boolean back = false;
        while (!back) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("BILLING & PAYMENTS");
            ConsoleUI.printMenuOption(1, "Generate Bill for Patient");
            ConsoleUI.printMenuOption(2, "View All Bills");
            ConsoleUI.printMenuOption(3, "View Pending Bills");
            ConsoleUI.printMenuOption(4, "View Bills by Patient ID");
            ConsoleUI.printMenuOption(5, "View Bill Details");
            ConsoleUI.printMenuOption(6, "Process Payment");
            ConsoleUI.printMenuOption(0, "Back to Main Menu");
            ConsoleUI.printLine();
            int ch = ConsoleUI.readInt("Enter choice: ");
            switch (ch) {
                case 1 -> generateBill();
                case 2 -> listBills(billingService.getAllBills(), "ALL BILLS");
                case 3 -> listBills(billingService.getPendingBills(), "PENDING BILLS");
                case 4 -> { int pid = ConsoleUI.readInt("Patient ID: ");
                            listBills(billingService.getBillsByPatient(pid), "PATIENT BILLS"); }
                case 5 -> viewBillDetails();
                case 6 -> processPayment();
                case 0 -> back = true;
                default -> ConsoleUI.printError("Invalid option.");
            }
        }
    }

    private void generateBill() {
        ConsoleUI.printSubHeader("GENERATE BILL");
        int pid = ConsoleUI.readInt("Patient ID: ");
        Patient p = patientService.getPatient(pid);
        if (p == null) { ConsoleUI.printError("Patient not found."); ConsoleUI.pause(); return; }
        boolean surgery = ConsoleUI.readYesNo("Did patient have surgery?");
        Bill bill = billingService.generateBill(pid, p.getName(), p.getDaysAdmitted(), surgery);
        ConsoleUI.printSuccess("Bill generated! Bill ID: " + bill.getBillId());
        printBillDetails(bill);
        ConsoleUI.pause();
    }

    private void listBills(List<Bill> list, String title) {
        ConsoleUI.printSubHeader(title);
        if (list.isEmpty()) { ConsoleUI.printInfo("No bills found."); ConsoleUI.pause(); return; }
        System.out.println(ConsoleUI.CYAN);
        System.out.println("+--------+----------------------+------------+------------+------------+------------+-----------------+");
        System.out.println("| BillID | Patient              | Date       | Total(₹)   | Paid(₹)    | Balance(₹) | Status          |");
        System.out.println("+--------+----------------------+------------+------------+------------+------------+-----------------+");
        System.out.print(ConsoleUI.RESET);
        for (Bill b : list) System.out.println(b);
        System.out.println(ConsoleUI.CYAN + "+--------+----------------------+------------+------------+------------+------------+-----------------+" + ConsoleUI.RESET);
        ConsoleUI.pause();
    }

    private void viewBillDetails() {
        int id = ConsoleUI.readInt("Enter Bill ID: ");
        Bill b = billingService.getBill(id);
        if (b == null) { ConsoleUI.printError("Bill not found."); ConsoleUI.pause(); return; }
        printBillDetails(b);
        ConsoleUI.pause();
    }

    private void printBillDetails(Bill b) {
        ConsoleUI.printSubHeader("BILL DETAILS  [ID: " + b.getBillId() + "]");
        System.out.printf("  %-25s : %s%n",  "Patient",     b.getPatientName());
        System.out.printf("  %-25s : %s%n",  "Date",        b.getBillDate());
        System.out.println("  " + "-".repeat(45));
        System.out.printf("  %-30s   %s%n", "DESCRIPTION", "AMOUNT (₹)");
        System.out.println("  " + "-".repeat(45));
        for (Map.Entry<String, Double> e : b.getCharges().entrySet()) {
            System.out.printf("  %-30s : %10.2f%n", e.getKey(), e.getValue());
        }
        System.out.println("  " + "=".repeat(45));
        System.out.printf("  %-30s : %10.2f%n", "TOTAL",   b.getTotalAmount());
        System.out.printf("  %-30s : %10.2f%n", "PAID",    b.getPaidAmount());
        System.out.printf("  %-30s : %10.2f%n", "BALANCE", b.getBalance());
        System.out.println("  " + "=".repeat(45));
        String statusColor = b.getPaymentStatus() == Bill.PaymentStatus.PAID ? ConsoleUI.GREEN
                           : b.getPaymentStatus() == Bill.PaymentStatus.PARTIALLY_PAID ? ConsoleUI.YELLOW
                           : ConsoleUI.RED;
        System.out.printf("  %-30s : %s%s%s%n", "STATUS", statusColor, b.getPaymentStatus(), ConsoleUI.RESET);
    }

    private void processPayment() {
        int id = ConsoleUI.readInt("Enter Bill ID: ");
        Bill b = billingService.getBill(id);
        if (b == null) { ConsoleUI.printError("Bill not found."); ConsoleUI.pause(); return; }
        System.out.printf("  Balance due: ₹%.2f%n", b.getBalance());
        double amount = ConsoleUI.readDouble("Enter payment amount (₹): ");
        if (billingService.processPayment(id, amount)) {
            ConsoleUI.printSuccess(String.format("Payment of ₹%.2f processed. Remaining balance: ₹%.2f",
                    amount, billingService.getBill(id).getBalance()));
        } else {
            ConsoleUI.printError("Payment failed. Bill may already be fully paid.");
        }
        ConsoleUI.pause();
    }

    // =========================================================
    // DASHBOARD
    // =========================================================
    private void showDashboard() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("HOSPITAL DASHBOARD & REPORTS");
        System.out.println(ConsoleUI.CYAN + ConsoleUI.BOLD);
        System.out.println("  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │                   PATIENT STATISTICS                   │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println(ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Total Patients",
                ConsoleUI.BOLD, patientService.getTotalPatients(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Currently Admitted",
                ConsoleUI.GREEN + ConsoleUI.BOLD, patientService.getAdmittedCount(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Discharged",
                ConsoleUI.YELLOW + ConsoleUI.BOLD, patientService.getDischargedCount(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Total Doctors",
                ConsoleUI.BOLD, doctorService.getTotalDoctors(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Total Appointments",
                ConsoleUI.BOLD, appointmentService.getTotalAppointments(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Upcoming Appointments",
                ConsoleUI.BOLD, appointmentService.getScheduled().size(), ConsoleUI.RESET);

        System.out.println();
        System.out.println(ConsoleUI.CYAN + ConsoleUI.BOLD);
        System.out.println("  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │                   FINANCIAL SUMMARY                    │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println(ConsoleUI.RESET);
        System.out.printf("  %-30s : %s₹ %,.2f%s%n", "Total Revenue Collected",
                ConsoleUI.GREEN + ConsoleUI.BOLD, billingService.getTotalRevenue(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s₹ %,.2f%s%n", "Total Pending Balance",
                ConsoleUI.RED + ConsoleUI.BOLD, billingService.getTotalPending(), ConsoleUI.RESET);
        System.out.printf("  %-30s : %s%d%s%n", "Pending Bills",
                ConsoleUI.BOLD, billingService.getPendingBills().size(), ConsoleUI.RESET);
        ConsoleUI.printLine();
        ConsoleUI.pause();
    }
}
