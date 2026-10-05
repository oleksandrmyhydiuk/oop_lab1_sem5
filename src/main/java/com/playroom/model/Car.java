package com.playroom.model;

public class Car extends Toy {
    private final boolean isElectric;

    public Car(String name, double price, AgeGroup ageGroup, Size size, boolean isElectric) {
        super(name, price, ageGroup, size);
        this.isElectric = isElectric;
    }
    
    public boolean isElectric() { return isElectric; }
}