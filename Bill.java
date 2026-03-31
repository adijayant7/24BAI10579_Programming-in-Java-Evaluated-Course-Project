package model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bill {
    private static int idCounter = 9000;

    public enum PaymentStatus { PENDING, PAID, PARTIALLY_PAID }

    private int billId;
    private int patientId;
    private String patientName;
    private LocalDate billDate;
    private Map<String, Double> charges; // description -> amount
    private double totalAmount;
    private double paidAmount;
    private PaymentStatus paymentStatus;

    public Bill(int patientId, String patientName) {
        this.billId        = ++idCounter;
        this.patientId     = patientId;
        this.patientName   = patientName;
        this.billDate      = LocalDate.now();
        this.charges       = new LinkedHashMap<>();
        this.totalAmount   = 0;
        this.paidAmount    = 0;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void addCharge(String description, double amount) {
        charges.put(description, amount);
        totalAmount += amount;
    }

    public void makePayment(double amount) {
        paidAmount += amount;
        if (paidAmount >= totalAmount) {
            paymentStatus = PaymentStatus.PAID;
            paidAmount = totalAmount;
        } else if (paidAmount > 0) {
            paymentStatus = PaymentStatus.PARTIALLY_PAID;
        }
    }

    public double getBalance() { return totalAmount - paidAmount; }

    // Getters
    public int getBillId()              { return billId; }
    public int getPatientId()           { return patientId; }
    public String getPatientName()      { return patientName; }
    public LocalDate getBillDate()      { return billDate; }
    public Map<String, Double> getCharges() { return charges; }
    public double getTotalAmount()      { return totalAmount; }
    public double getPaidAmount()       { return paidAmount; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }

    @Override
    public String toString() {
        return String.format("| %-6d | %-20s | %-10s | %10.2f | %10.2f | %10.2f | %-15s |",
                billId, patientName, billDate, totalAmount, paidAmount, getBalance(), paymentStatus);
    }
}
