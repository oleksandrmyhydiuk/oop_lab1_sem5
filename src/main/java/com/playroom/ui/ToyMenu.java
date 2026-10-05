package com.playroom.ui;

import com.playroom.storage.PlayroomStorage;
import com.playroom.model.*;

public class ToyMenu {
    private final PlayroomStorage database;
    private final UserInput input;

    public ToyMenu(PlayroomStorage database, UserInput input) {
        this.database = database;
        this.input = input;
    }

    public void displayAllToys() {
        System.out.println("\n--- Toy Catalog ---");
        var catalog = database.getCatalog();
        if (catalog.isEmpty()) {
            System.out.println("Catalog is empty.");
        } else {
            for (int i = 0; i < catalog.size(); i++) {
                System.out.println("[" + i + "] " + catalog.get(i));
            }
        }
    }

    public void addToy() {
        int type = input.readInt("Enter toy type (1-Car, 2-Doll, 3-Ball):");
        String name = input.readString("Enter name:");
        double price = input.readDouble("Enter price:");

        switch (type) {
            case 1:
                boolean isElectric = input.readBoolean("Is it electric? (true/false):");
                database.addToyToCatalog(new Car(name, price, AgeGroup.PRESCHOOL, Size.MEDIUM, isElectric));
                break;
            case 2:
                String material = input.readString("Enter material (e.g., Plastic, Cloth):");
                database.addToyToCatalog(new Doll(name, price, AgeGroup.PRESCHOOL, Size.MEDIUM, material));
                break;
            case 3:
                String sport = input.readString("Enter sport type (e.g., Soccer, Tennis):");
                database.addToyToCatalog(new Ball(name, price, AgeGroup.SCHOOL, Size.SMALL, sport));
                break;
            default:
                System.out.println("Invalid toy type. Toy not added.");
                return;
        }
        System.out.println("Toy successfully added!");
    }

    public void removeToy() {
        displayAllToys();
        int index = input.readInt("Enter the index of the toy to remove:");
        boolean success = database.removeToyFromCatalog(index);
        if (success) {
            System.out.println("Toy removed.");
        } else {
            System.out.println("Invalid index.");
        }
    }
}