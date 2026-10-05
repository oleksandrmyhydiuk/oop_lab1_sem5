package com.playroom.ui;

import com.playroom.storage.PlayroomStorage;
import com.playroom.model.Toy;

public class PlayroomMenu {
    private final PlayroomStorage database;
    private final UserInput input;

    public PlayroomMenu(PlayroomStorage database, UserInput input) {
        this.database = database;
        this.input = input;
    }

    public void preparePlayroom() {
        double budget = input.readDouble("Enter the budget for the playroom:");
        database.setupPlayroom(budget);
        System.out.println("Playroom prepared within the budget of $" + budget);
    }

    public void displayPlayroomToys() {
        System.out.println("\n--- Playroom Toys ---");
        var playroom = database.getPlayroom();
        if (playroom.isEmpty()) {
            System.out.println("Playroom is empty. Prepare it first!");
        } else {
            playroom.forEach(System.out::println);
        }
    }

    public void sortToys() {
        database.sortPlayroomToysByPrice();
        System.out.println("Toys in playroom sorted by price.");
        displayPlayroomToys();
    }

    public void findToysByPriceRange() {
        double min = input.readDouble("Enter minimum price:");
        double max = input.readDouble("Enter maximum price:");

        System.out.println("\n--- Found Toys ---");
        var found = database.findToysInPlayroomByPrice(min, max);
        if (found.isEmpty()) {
            System.out.println("No toys found in this price range.");
        } else {
            found.forEach(System.out::println);
        }
    }
}