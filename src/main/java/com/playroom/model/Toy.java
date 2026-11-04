package com.playroom.model;

import java.util.Locale;

/**
 * Abstract base class representing a generic toy in the playroom.
 */
public abstract class Toy {

    private final String name;
    private final double price;
    private final AgeGroup ageGroup;
    private final Size size;

    protected Toy(String name, double price, AgeGroup ageGroup, Size size) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Toy name cannot be null or blank.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Toy price cannot be negative.");
        }
        if (ageGroup == null) {
            throw new IllegalArgumentException("Age group cannot be null.");
        }
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }
        this.name = name;
        this.price = price;
        this.ageGroup = ageGroup;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public Size getSize() {
        return size;
    }

    /**
     * Returns type-specific description details for polymorphic output.
     *
     * @return formatted specific attributes of the concrete toy
     */
    public abstract String getSpecificDetails();

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "%s {name='%s', price=%.2f, age=%s, size=%s, %s}",
                this.getClass().getSimpleName(),
                name,
                price,
                ageGroup,
                size,
                getSpecificDetails()
        );
    }
}
