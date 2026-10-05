package com.playroom.model;

public abstract class Toy {
    private final String name;
    private final double price;
    private final AgeGroup ageGroup;
    private final Size size;

    protected Toy(String name, double price, AgeGroup ageGroup, Size size) {
        this.name = name;
        this.price = price;
        this.ageGroup = ageGroup;
        this.size = size;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public AgeGroup getAgeGroup() { return ageGroup; }
    public Size getSize() { return size; }

    @Override
    public String toString() {
        return String.format("%s {name='%s', price=%.2f, age=%s, size=%s}", 
                this.getClass().getSimpleName(), name, price, ageGroup, size);
    }
}