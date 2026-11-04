package com.playroom.ui;

import com.playroom.model.AgeGroup;
import com.playroom.model.Ball;
import com.playroom.model.Blocks;
import com.playroom.model.Car;
import com.playroom.model.Doll;
import com.playroom.model.Size;
import com.playroom.model.Toy;
import com.playroom.storage.PlayroomStorage;

import java.util.List;

/**
 * Handles console interactions for managing the toy catalog.
 */
public class ToyMenu {

    private final PlayroomStorage storage;
    private final UserInput input;

    public ToyMenu(PlayroomStorage storage, UserInput input) {
        this.storage = storage;
        this.input = input;
    }

    public void displayAllToys() {
        System.out.println("\n--- Toy Catalog ---");
        List<Toy> catalog = storage.getCatalog();
        if (catalog.isEmpty()) {
            System.out.println("Catalog is empty.");
        } else {
            for (int i = 0; i < catalog.size(); i++) {
                System.out.println("[" + i + "] " + catalog.get(i));
            }
        }
    }

    public void addToy() {
        int type = input.readInt("Enter toy type (1-Car, 2-Doll, 3-Ball, 4-Blocks):");
        if (type < 1 || type > 4) {
            System.out.println("Invalid toy type. Toy not added.");
            return;
        }

        String name = input.readString("Enter name:");
        double price = input.readDouble("Enter price:");
        AgeGroup ageGroup = selectAgeGroup();
        Size size = selectSize();

        switch (type) {
            case 1:
                boolean isElectric = input.readBoolean("Is it electric? (true/false):");
                storage.addToyToCatalog(new Car(name, price, ageGroup, size, isElectric));
                break;
            case 2:
                String material = input.readString("Enter material (e.g., Plastic, Cloth):");
                storage.addToyToCatalog(new Doll(name, price, ageGroup, size, material));
                break;
            case 3:
                String sport = input.readString("Enter sport type (e.g., Soccer, Tennis):");
                storage.addToyToCatalog(new Ball(name, price, ageGroup, size, sport));
                break;
            case 4:
                int pieceCount = input.readInt("Enter number of pieces:");
                storage.addToyToCatalog(new Blocks(name, price, ageGroup, size, pieceCount));
                break;
            default:
                return;
        }
        System.out.println("Toy successfully added!");
    }

    private AgeGroup selectAgeGroup() {
        int choice = input.readInt(
                "Select age group (1-BABY, 2-TODDLER, 3-PRESCHOOL, 4-SCHOOL):"
        );
        switch (choice) {
            case 1:
                return AgeGroup.BABY;
            case 2:
                return AgeGroup.TODDLER;
            case 3:
                return AgeGroup.PRESCHOOL;
            case 4:
            default:
                return AgeGroup.SCHOOL;
        }
    }

    private Size selectSize() {
        int choice = input.readInt("Select size (1-SMALL, 2-MEDIUM, 3-LARGE):");
        switch (choice) {
            case 1:
                return Size.SMALL;
            case 2:
                return Size.MEDIUM;
            case 3:
            default:
                return Size.LARGE;
        }
    }

    public void removeToy() {
        displayAllToys();
        int index = input.readInt("Enter the index of the toy to remove:");
        boolean success = storage.removeToyFromCatalog(index);
        if (success) {
            System.out.println("Toy removed.");
        } else {
            System.out.println("Invalid index.");
        }
    }
}
