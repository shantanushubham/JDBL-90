//package org.geeksforgeeks.my_spring_app.playground;
//
//public class PaymentLoose {
//
//    public static void main(String[] args) {
//        PaymentSystem ccPayment = new PaymentSystem(new CreditCardPayment());
//        PaymentSystem upiPayment = new PaymentSystem(new UPIPayment());
//        upiPayment.makePayment(100); // UPI
//        ccPayment.makePayment(200); // CC
//    }
//}
//
//interface Payment {
//
//    void pay(int amount);
//
//}
//
//class CreditCardPayment implements Payment {
//
//    @Override
//    public void pay(int amount) {
//        System.out.println("Paid: " + amount + " using Credit Card");
//    }
//}
//
//class UPIPayment implements Payment {
//
//    @Override
//    public void pay(int amount) {
//        System.out.println("Paid: " + amount + " using UPI");
//    }
//
//}
//
//class PaymentSystem {
//
//    private final Payment payment;
//
//    public PaymentSystem(Payment payment) {
//        this.payment = payment;
//    }
//
//    public void makePayment(int amount) {
//        this.payment.pay(amount);
//    }
//}