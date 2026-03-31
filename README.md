# 🏥 Hospital Management System
### A Console-Based Java Application

---

## 📁 Project Structure

```
HospitalManagementSystem/
├── run.sh                          ← Build & run script
├── README.md
└── src/
    ├── HospitalManagementSystem.java   ← Main class (entry point)
    ├── model/
    │   ├── Patient.java
    │   ├── Doctor.java
    │   ├── Appointment.java
    │   └── Bill.java
    ├── service/
    │   ├── PatientService.java
    │   ├── DoctorService.java
    │   ├── AppointmentService.java
    │   └── BillingService.java
    └── util/
        ├── ConsoleUI.java              ← ANSI-colored terminal UI
        └── DataSeeder.java             ← Sample data loader
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 11 or higher installed
- Terminal / Command Prompt

### Steps

**On Linux / macOS:**
```bash
cd HospitalManagementSystem
chmod +x run.sh
./run.sh
```

**On Windows (Command Prompt):**
```cmd
cd HospitalManagementSystem
mkdir out
javac -d out src\model\*.java src\service\*.java src\util\*.java src\HospitalManagementSystem.java
cd out
java HospitalManagementSystem
```

---

## ✨ Features

### 👤 Patient Management
- Add new patients with full details (name, age, gender, blood group, disease)
- View all / admitted / discharged patients
- Search by name or disease
- View individual patient details with admission duration
- Discharge patients
- Delete patient records

### 🩺 Doctor Management
- Add doctors with specialization, experience, availability
- View all doctors with patient load
- Search by specialization
- Assign / remove patients from doctors
- View doctor details with assigned patients

### 📅 Appointment Management
- Schedule appointments with date/time
- View all / upcoming / by patient / by doctor
- Mark appointments as completed (with doctor notes)
- Cancel appointments

### 💰 Billing & Payments
- Auto-generate bills (consultation, bed charges, medicine, lab tests, surgery)
- Custom bills with itemized charges
- Process partial or full payments
- View pending bills
- Detailed bill receipts with ₹ amounts

### 📊 Dashboard
- Real-time hospital statistics
- Patient counts (admitted vs discharged)
- Financial summary (revenue collected, pending balance)
- Appointment overview

---

## 🏗️ Architecture

| Layer     | Files                          | Responsibility            |
|-----------|--------------------------------|---------------------------|
| Model     | Patient, Doctor, Appointment, Bill | Data classes with getters/setters |
| Service   | PatientService, DoctorService, etc. | Business logic & in-memory storage |
| UI        | HospitalManagementSystem.java  | Menu-driven console UI    |
| Util      | ConsoleUI, DataSeeder          | Display helpers, sample data |

---

## 📦 Sample Data (Pre-loaded)

| Doctors                          | Specialization   |
|----------------------------------|------------------|
| Dr. Anil Sharma                  | Cardiology       |
| Dr. Priya Verma                  | Neurology        |
| Dr. Rahul Gupta                  | Orthopedics      |
| Dr. Sunita Patel                 | Pediatrics       |
| Dr. Manoj Singh                  | General Surgery  |

5 patients, 4 appointments, and 3 bills are also pre-loaded.

---

## 🎨 UI Features
- ANSI color-coded terminal output (works on Linux/macOS/modern Windows)
- Organized table displays for all records
- Color-coded status indicators (green = admitted/paid, yellow = discharged/partial, red = pending)
- Clear screen between menus for a clean experience

---

## 📌 Notes
- Data is stored **in-memory** — it resets on each run
- For persistence, you can extend services to use file I/O or a database (JDBC)
- All monetary values are in Indian Rupees (₹)
