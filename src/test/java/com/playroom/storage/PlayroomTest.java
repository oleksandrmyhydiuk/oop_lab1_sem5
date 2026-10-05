package com.playroom.storage;

import com.playroom.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayroomStorageTest {

    private PlayroomStorage storage;

    @BeforeEach
    void setUp() {
        storage = new PlayroomStorage();
        // Setup initial catalog for testing
        storage.addToyToCatalog(new Car("Car 1", 200.0, AgeGroup.BABY, Size.SMALL, false));
        storage.addToyToCatalog(new Ball("Ball 1", 100.0, AgeGroup.BABY, Size.SMALL, "Soccer"));
        storage.addToyToCatalog(new Car("Expensive Car", 300.0, AgeGroup.SCHOOL, Size.LARGE, true));
    }

    @Test
    void testSetupPlayroom_WithinBudget() {
        // Budget 250 allows Car 1 (200.0), but Ball 1 (100.0) will exceed it.
        storage.setupPlayroom(250.0);
        List<Toy> playroomToys = storage.getPlayroom();

        assertEquals(1, playroomToys.size(), "Only 1 toy should be loaded to stay within budget");
        assertEquals("Car 1", playroomToys.get(0).getName());
    }

    @Test
    void testSortPlayroomToysByPrice() {
        storage.setupPlayroom(1000.0); // Load all 3 toys
        storage.sortPlayroomToysByPrice();
        List<Toy> sortedToys = storage.getPlayroom();

        assertEquals(100.0, sortedToys.get(0).getPrice());
        assertEquals(200.0, sortedToys.get(1).getPrice());
        assertEquals(300.0, sortedToys.get(2).getPrice());
    }

    @Test
    void testFindToysInPlayroomByPrice() {
        storage.setupPlayroom(1000.0); // Load all 3 toys
        
        List<Toy> foundToys = storage.findToysInPlayroomByPrice(150.0, 250.0);

        assertEquals(1, foundToys.size());
        assertEquals("Car 1", foundToys.get(0).getName());
    }
}