package com.playroom.model;

/**
 * Represents a doll toy made from a specific material.
 */
public class Doll extends Toy {

    private final String material;

    public Doll(String name, double price, AgeGroup ageGroup, Size size, String material) {
        super(name, price, ageGroup, size);
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("Material cannot be null or blank.");
        }
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String getSpecificDetails() {
        return "material='" + material + "'";
    }
}
