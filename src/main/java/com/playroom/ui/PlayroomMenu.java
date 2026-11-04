package com.playroom.ui;

import com.playroom.model.Toy;
import com.playroom.storage.PlayroomStorage;

import java.util.List;

/**
 * Handles console interactions for playroom preparation, sorting, and searching.
 */
public class PlayroomMenu {

    private final PlayroomStorage storage;
    private final UserInput input;

    public PlayroomMenu(PlayroomStorage storage, UserInput input) {
        this.storage = storage;
        this.input = input;
    }

    public void preparePlayroom() {
        double budget = input.readDouble("Enter the budget for the playroom:");
        int maxToys = input.readInt("Enter the maximum number of toys:");
        storage.setupPlayroom(budget, maxToys);
        System.out.println("Playroom prepared within the budget of $" + budget
                + " (max toys: " + maxToys + ").");
    }

    public void displayPlayroomToys() {
        System.out.println("\n--- Playroom Toys ---");
        List<Toy> playroom = storage.getPlayroom();
        if (playroom.isEmpty()) {
            System.out.println("Playroom is empty. Prepare it first!");
        } else {
            playroom.forEach(System.out::println);
        }
    }

    public void sortToys() {
        storage.sortPlayroomToysByPrice();
        System.out.println("Toys in playroom sorted by price.");
        displayPlayroomToys();
    }

    public void findToysByPriceRange() {
        double min = input.readDouble("Enter minimum price:");
        double max = input.readDouble("Enter maximum price:");

        System.out.println("\n--- Found Toys ---");
        List<Toy> found = storage.findToysInPlayroomByPrice(min, max);
        if (found.isEmpty()) {
            System.out.println("No toys found in this price range.");
        } else {
            found.forEach(System.out::println);
        }
    }
}
