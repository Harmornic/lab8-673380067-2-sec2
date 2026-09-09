package com.example.demo.strategy;

public class StudentDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(double price) {
        return price * 0.90;
    }
}