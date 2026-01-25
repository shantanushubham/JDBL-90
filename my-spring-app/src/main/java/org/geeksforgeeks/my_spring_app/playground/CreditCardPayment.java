//package org.geeksforgeeks.my_spring_app.playground;
//
//
//// Example of Tightly coupled code
//public class CreditCardPayment {
//
//    public void pay(int amount) {
//        System.out.println("Paid: " + amount + " using Credit Card");
//    }
//
//}
//
//class UPIPayment {
//
//    public void pay(int amount) {
//        System.out.println("Paid: " + amount + " using UPI");
//    }
//
//}
//
//class PaymentSystem {
//
////    private final CreditCardPayment creditCardPayment;
//    private final UPIPayment upiPayment;
////
////    public PaymentSystem(CreditCardPayment creditCardPayment) {
////        this.creditCardPayment = creditCardPayment;
////    }
//
//    public PaymentSystem(UPIPayment upiPayment) {
//        this.upiPayment = upiPayment;
//    }
//
//    public void makePayment(int amount) {
////        this.creditCardPayment.pay(amount);
//        this.upiPayment.pay(amount);
//    }
//}
