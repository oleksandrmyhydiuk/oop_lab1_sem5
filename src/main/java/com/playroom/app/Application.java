package com.playroom.app;

import com.playroom.storage.PlayroomStorage;
import com.playroom.ui.PlayroomMenu;
import com.playroom.ui.ToyMenu;
import com.playroom.ui.UserInput;

import java.util.Scanner;

/**
 * Entry point for the Playroom Console Application.
 */
public class Application {

    private static final PlayroomStorage STORAGE = new PlayroomStorage();
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final UserInput INPUT_READER = new UserInput() {
        @Override
        public int readInt(String prompt) {
            System.out.print(prompt + " ");
            while (!SCANNER.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                SCANNER.next();
            }
            int value = SCANNER.nextInt();
            SCANNER.nextLine();
            return value;
        }

        @Override
        public double readDouble(String prompt) {
            System.out.print(prompt + " ");
            while (!SCANNER.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a number.");
                SCANNER.next();
            }
            double value = SCANNER.nextDouble();
            SCANNER.nextLine();
            return value;
        }

        @Override
        public boolean readBoolean(String prompt) {
            System.out.print(prompt + " ");
            while (!SCANNER.hasNextBoolean()) {
                System.out.println("Invalid input. Please enter true or false.");
                SCANNER.next();
            }
            boolean value = SCANNER.nextBoolean();
            SCANNER.nextLine();
            return value;
        }

        @Override
        public String readString(String prompt) {
            System.out.print(prompt + " ");
            return SCANNER.nextLine();
        }
    };

    private static final ToyMenu TOY_MENU = new ToyMenu(STORAGE, INPUT_READER);
    private static final PlayroomMenu PLAYROOM_MENU = new PlayroomMenu(STORAGE, INPUT_READER);

    public static void main(String[] args) {
        STORAGE.initializeData();

        while (true) {
            printMainMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    TOY_MENU.addToy();
                    break;
                case 2:
                    TOY_MENU.displayAllToys();
                    break;
                case 3:
                    TOY_MENU.removeToy();
                    break;
                case 4:
                    PLAYROOM_MENU.preparePlayroom();
                    break;
                case 5:
                    PLAYROOM_MENU.displayPlayroomToys();
                    break;
                case 6:
                    PLAYROOM_MENU.sortToys();
                    break;
                case 7:
                    PLAYROOM_MENU.findToysByPriceRange();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n1. Add a toy to catalog");
        System.out.println("2. Display all catalog toys");
        System.out.println("3. Remove a toy from catalog");
        System.out.println("4. Prepare playroom (Auto-fill by budget & toy limit)");
        System.out.println("5. Display playroom toys");
        System.out.println("6. Sort toys in playroom by price");
        System.out.println("7. Find toys in playroom by price range");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private static int readMenuChoice() {
        int choice = 0;
        if (SCANNER.hasNextInt()) {
            choice = SCANNER.nextInt();
        } else {
            SCANNER.next();
        }
        SCANNER.nextLine();
        return choice;
    }
}
