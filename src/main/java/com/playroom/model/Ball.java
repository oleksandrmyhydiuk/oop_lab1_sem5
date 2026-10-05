package com.playroom.model;

public class Ball extends Toy {
    private final String sportType;

    public Ball(String name, double price, AgeGroup ageGroup, Size size, String sportType) {
        super(name, price, ageGroup, size);
        this.sportType = sportType;
    }
}