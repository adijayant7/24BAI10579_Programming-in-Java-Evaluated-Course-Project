package service;

import model.Bill;
import java.util.*;
import java.util.stream.Collectors;

public class BillingService {
    private Map<Integer, Bill> bills = new LinkedHashMap<>();

    // Standard charge rates
    public static final double CONSULTATION_FEE  = 500.0;
    public static final double BED_CHARGE_PER_DAY = 1500.0;
    public static final double SURGERY_BASE_FEE   = 25000.0;
    public static final double LAB_TEST_FEE       = 800.0;
    public static final double MEDICINE_CHARGE    = 2000.0;
    public static final double XRAY_FEE           = 1200.0;

    public Bill generateBill(int patientId, String patientName, int daysAdmitted, boolean hadSurgery) {
        Bill bill = new Bill(patientId, patientName);
        bill.addCharge("Consultation Fee", CONSULTATION_FEE);
        bill.addCharge("Bed Charges (" + daysAdmitted + " days)", BED_CHARGE_PER_DAY * Math.max(daysAdmitted, 1));
        bill.addCharge("Medicines", MEDICINE_CHARGE);
        bill.addCharge("Laboratory Tests", LAB_TEST_FEE);
        if (hadSurgery) {
            bill.addCharge("Surgery Fee", SURGERY_BASE_FEE);
        }
        bills.put(bill.getBillId(), bill);
        return bill;
    }

    public Bill createCustomBill(int patientId, String patientName) {
        Bill bill = new Bill(patientId, patientName);
        bills.put(bill.getBillId(), bill);
        return bill;
    }

    public boolean processPayment(int billId, double amount) {
        Bill b = bills.get(billId);
        if (b != null && b.getBalance() > 0) {
            b.makePayment(amount);
            return true;
        }
        return false;
    }

    public Bill getBill(int id)               { return bills.get(id); }
    public List<Bill> getAllBills()            { return new ArrayList<>(bills.values()); }
    public List<Bill> getPendingBills()        {
        return bills.values().stream()
                .filter(b -> b.getPaymentStatus() != Bill.PaymentStatus.PAID)
                .collect(Collectors.toList());
    }

    public List<Bill> getBillsByPatient(int patientId) {
        return bills.values().stream()
                .filter(b -> b.getPatientId() == patientId)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return bills.values().stream().mapToDouble(Bill::getPaidAmount).sum();
    }

    public double getTotalPending() {
        return bills.values().stream().mapToDouble(Bill::getBalance).sum();
    }

    public boolean exists(int id) { return bills.containsKey(id); }
}
