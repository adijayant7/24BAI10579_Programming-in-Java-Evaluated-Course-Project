#!/bin/bash
# Hospital Management System - Build & Run Script

echo "============================================"
echo "  Hospital Management System - Build Script"
echo "============================================"

# Create output directory
mkdir -p out

# Compile all Java files
echo "Compiling Java sources..."
javac -d out \
    src/model/Patient.java \
    src/model/Doctor.java \
    src/model/Appointment.java \
    src/model/Bill.java \
    src/service/PatientService.java \
    src/service/DoctorService.java \
    src/service/AppointmentService.java \
    src/service/BillingService.java \
    src/util/ConsoleUI.java \
    src/util/DataSeeder.java \
    src/HospitalManagementSystem.java

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo ""
    echo "Running Hospital Management System..."
    echo "============================================"
    cd out
    java HospitalManagementSystem
else
    echo "Build FAILED. Check errors above."
fi
