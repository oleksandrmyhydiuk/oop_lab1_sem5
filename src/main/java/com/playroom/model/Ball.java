package com.playroom.model;

/**
 * Represents a ball toy designed for a specific sport or activity.
 */
public class Ball extends Toy {

    private final String sportType;

    public Ball(String name, double price, AgeGroup ageGroup, Size size, String sportType) {
        super(name, price, ageGroup, size);
        if (sportType == null || sportType.isBlank()) {
            throw new IllegalArgumentException("Sport type cannot be null or blank.");
        }
        this.sportType = sportType;
    }

    public String getSportType() {
        return sportType;
    }

    @Override
    public String getSpecificDetails() {
        return "sportType='" + sportType + "'";
    }
}
