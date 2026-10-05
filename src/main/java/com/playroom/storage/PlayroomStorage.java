package com.playroom.storage;

import com.playroom.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayroomStorage {
    private final List<Toy> catalog = new ArrayList<>();
    private final List<Toy> playroom = new ArrayList<>();

    // Mock data initialization (replaces database.createTables())
    public void initializeData() {
        catalog.add(new Car("Hot Wheels", 15.0, AgeGroup.PRESCHOOL, Size.SMALL, false));
        catalog.add(new Car("RC Truck", 120.0, AgeGroup.SCHOOL, Size.LARGE, true));
        catalog.add(new Doll("Barbie", 45.0, AgeGroup.PRESCHOOL, Size.MEDIUM, "Plastic"));
        catalog.add(new Ball("Football", 30.0, AgeGroup.SCHOOL, Size.MEDIUM, "Soccer"));
        catalog.add(new Ball("Tennis ball", 5.0, AgeGroup.SCHOOL, Size.SMALL, "Tennis"));
    }

    public List<Toy> getCatalog() { return catalog; }
    public List<Toy> getPlayroom() { return playroom; }

    public void addToyToCatalog(Toy toy) {
        catalog.add(toy);
    }

    public boolean removeToyFromCatalog(int index) {
        if (index >= 0 && index < catalog.size()) {
            catalog.remove(index);
            return true;
        }
        return false;
    }

    public void setupPlayroom(double budget) {
        playroom.clear();
        double currentTotal = 0;

        for (Toy toy : catalog) {
            if (currentTotal + toy.getPrice() <= budget) {
                playroom.add(toy);
                currentTotal += toy.getPrice();
            }
        }
    }

    public void sortPlayroomToysByPrice() {
        playroom.sort(Comparator.comparingDouble(Toy::getPrice));
    }

    public List<Toy> findToysInPlayroomByPrice(double min, double max) {
        return playroom.stream()
                .filter(toy -> toy.getPrice() >= min && toy.getPrice() <= max)
                .collect(Collectors.toList());
    }
}