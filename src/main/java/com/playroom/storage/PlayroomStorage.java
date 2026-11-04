package com.playroom.storage;

import com.playroom.model.AgeGroup;
import com.playroom.model.Ball;
import com.playroom.model.Blocks;
import com.playroom.model.Car;
import com.playroom.model.Doll;
import com.playroom.model.Size;
import com.playroom.model.Toy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the toy catalog and the active playroom inventory.
 */
public class PlayroomStorage {

    private static final String DEFAULT_RESOURCE_FILE = "/toys.csv";

    private final List<Toy> catalog = new ArrayList<>();
    private final List<Toy> playroom = new ArrayList<>();

    /**
     * Initializes the catalog from the default classpath CSV resource,
     * or falls back to built-in default toys if the resource is unavailable.
     */
    public void initializeData() {
        catalog.clear();
        try (InputStream stream = PlayroomStorage.class.getResourceAsStream(DEFAULT_RESOURCE_FILE)) {
            if (stream != null) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                    loadFromReader(reader);
                    return;
                }
            }
        } catch (IOException ignored) {
            // Fall back to default in-memory catalog
        }
        loadDefaultToys();
    }

    /**
     * Loads catalog toys from an external CSV configuration file.
     *
     * @param filePath path to the CSV file
     * @throws IOException if the file cannot be read
     */
    public void loadCatalogFromFile(Path filePath) throws IOException {
        catalog.clear();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            loadFromReader(reader);
        }
    }

    private void loadFromReader(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            catalog.add(parseToyLine(trimmed));
        }
    }

    /**
     * Parses a single CSV line into a {@link Toy} instance.
     *
     * @param line CSV line formatted as: TYPE,name,price,ageGroup,size,specificProperty
     * @return parsed Toy object
     */
    public Toy parseToyLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid toy configuration line: " + line);
        }
        String type = parts[0].trim().toUpperCase();
        String name = parts[1].trim();
        double price = Double.parseDouble(parts[2].trim());
        AgeGroup ageGroup = AgeGroup.valueOf(parts[3].trim().toUpperCase());
        Size size = Size.valueOf(parts[4].trim().toUpperCase());
        String extra = parts[5].trim();

        switch (type) {
            case "CAR":
                return new Car(name, price, ageGroup, size, Boolean.parseBoolean(extra));
            case "DOLL":
                return new Doll(name, price, ageGroup, size, extra);
            case "BALL":
                return new Ball(name, price, ageGroup, size, extra);
            case "BLOCKS":
                return new Blocks(name, price, ageGroup, size, Integer.parseInt(extra));
            default:
                throw new IllegalArgumentException("Unknown toy type: " + type);
        }
    }

    private void loadDefaultToys() {
        catalog.add(new Car("Hot Wheels Mini", 15.0, AgeGroup.PRESCHOOL, Size.SMALL, false));
        catalog.add(new Car("City Sedan", 40.0, AgeGroup.TODDLER, Size.MEDIUM, false));
        catalog.add(new Car("RC Monster Truck", 120.0, AgeGroup.SCHOOL, Size.LARGE, true));
        catalog.add(new Doll("Barbie Classic", 45.0, AgeGroup.PRESCHOOL, Size.MEDIUM, "Plastic"));
        catalog.add(new Doll("Soft Plush Baby Doll", 25.0, AgeGroup.BABY, Size.SMALL, "Cloth"));
        catalog.add(new Ball("Football Pro", 30.0, AgeGroup.SCHOOL, Size.MEDIUM, "Soccer"));
        catalog.add(new Ball("Tennis Ball", 5.0, AgeGroup.SCHOOL, Size.SMALL, "Tennis"));
        catalog.add(new Blocks("Wooden Alphabet Cubes", 20.0, AgeGroup.BABY, Size.SMALL, 12));
        catalog.add(new Blocks("Mega Construction Set", 60.0, AgeGroup.TODDLER, Size.LARGE, 100));
    }

    public List<Toy> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    public List<Toy> getPlayroom() {
        return Collections.unmodifiableList(playroom);
    }

    public void addToyToCatalog(Toy toy) {
        if (toy == null) {
            throw new IllegalArgumentException("Toy cannot be null.");
        }
        catalog.add(toy);
    }

    public boolean removeToyFromCatalog(int index) {
        if (index >= 0 && index < catalog.size()) {
            catalog.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Populates the playroom from the catalog within the allocated budget.
     *
     * @param budget maximum total cost of toys
     */
    public void setupPlayroom(double budget) {
        setupPlayroom(budget, Integer.MAX_VALUE);
    }

    /**
     * Populates the playroom with a fixed maximum number of toys within the allocated budget.
     *
     * @param budget  maximum total cost of toys
     * @param maxToys maximum number of toys to place in the playroom
     */
    public void setupPlayroom(double budget, int maxToys) {
        if (budget < 0) {
            throw new IllegalArgumentException("Budget cannot be negative.");
        }
        if (maxToys < 0) {
            throw new IllegalArgumentException("Maximum toy count cannot be negative.");
        }
        playroom.clear();
        double currentTotal = 0.0;

        for (Toy toy : catalog) {
            if (playroom.size() >= maxToys) {
                break;
            }
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
