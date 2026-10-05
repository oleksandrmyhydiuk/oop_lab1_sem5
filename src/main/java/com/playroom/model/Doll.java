package com.playroom.model;

public class Doll extends Toy {
    private final String material;

    public Doll(String name, double price, AgeGroup ageGroup, Size size, String material) {
        super(name, price, ageGroup, size);
        this.material = material;
    }
}