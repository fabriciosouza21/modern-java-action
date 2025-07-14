package org.example.dsl.discount;

public class Discounts {

    // Method to apply a 10% discount
    public  static double student(double price){
        return price * 0.9;
    }

    // Method to apply a 5%
    public static double loyatlty(double price){
        return price * 0.95;
    }

    // Method to apply a 15% discount
    public static double bulk(double price){
        return price * 0.85;
    }
}
