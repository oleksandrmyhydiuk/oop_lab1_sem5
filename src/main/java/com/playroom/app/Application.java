package com.playroom.app;

import com.playroom.storage.PlayroomStorage;
import com.playroom.ui.*;

import java.util.Scanner;

public class Application {
    private static final PlayroomStorage database = new PlayroomStorage();
    private static final Scanner scanner = new Scanner(System.in);

    private static final UserInput inputReader = new UserInput() {
        @Override
        public int readInt(String prompt) {
            System.out.print(prompt + " ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            int value = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            return value;
        }

        @Override
        public double readDouble(String prompt) {
            System.out.print(prompt + " ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            double value = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character
            return value;
        }

        @Override
        public boolean readBoolean(String prompt) {
            System.out.print(prompt + " ");
            while (!scanner.hasNextBoolean()) {
                System.out.println("Invalid input. Please enter true or false.");
                scanner.next(); // Consume invalid input
            }
            boolean value = scanner.nextBoolean();
            scanner.nextLine(); // Consume newline character
            return value;
        }

        @Override
        public String readString(String prompt) {
            System.out.print(prompt + " ");
            return scanner.nextLine();
        }
    };

    private static final ToyMenu toyOptions = new ToyMenu(database, inputReader);
    private static final PlayroomMenu playroomOptions = new PlayroomMenu(database, inputReader);

    public static void main(String[] args) {
        // Initializes default data instead of createTables()
        database.initializeData(); 

        while (true) {
            System.out.println("\n1. Add a toy to catalog");
            System.out.println("2. Display all catalog toys");
            System.out.println("3. Remove a toy from catalog");
            System.out.println("4. Prepare playroom (Auto-fill by budget)");
            System.out.println("5. Display playroom toys");
            System.out.println("6. Sort toys in playroom by price");
            System.out.println("7. Find toys in playroom by price range");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = 0;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next(); // Consume invalid input
            }
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    toyOptions.addToy();
                    break;
                case 2:
                    toyOptions.displayAllToys();
                    break;
                case 3:
                    toyOptions.removeToy();
                    break;
                case 4:
                    playroomOptions.preparePlayroom();
                    break;
                case 5:
                    playroomOptions.displayPlayroomToys();
                    break;
                case 6:
                    playroomOptions.sortToys();
                    break;
                case 7:
                    playroomOptions.findToysByPriceRange();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}