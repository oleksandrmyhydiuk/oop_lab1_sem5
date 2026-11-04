package com.playroom.model;

/**
 * Represents a toy car (small, medium, or large, electric or mechanical).
 */
public class Car extends Toy {

    private final boolean isElectric;

    public Car(String name, double price, AgeGroup ageGroup, Size size, boolean isElectric) {
        super(name, price, ageGroup, size);
        this.isElectric = isElectric;
    }

    public boolean isElectric() {
        return isElectric;
    }

    @Override
    public String getSpecificDetails() {
        return "electric=" + isElectric;
    }
}
