package com.example.demo.strategy;



public class DiscountContext {

    private DiscountStrategy strategy;

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(double price) {
        return strategy.calculateDiscount(price);
    }
}
