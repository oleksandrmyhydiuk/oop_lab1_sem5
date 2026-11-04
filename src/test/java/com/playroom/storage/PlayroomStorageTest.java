package com.playroom.storage;

import com.playroom.model.AgeGroup;
import com.playroom.model.Ball;
import com.playroom.model.Blocks;
import com.playroom.model.Car;
import com.playroom.model.Doll;
import com.playroom.model.Size;
import com.playroom.model.Toy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayroomStorageTest {

    private PlayroomStorage storage;

    @BeforeEach
    void setUp() {
        storage = new PlayroomStorage();
        storage.addToyToCatalog(new Car("Car 1", 200.0, AgeGroup.BABY, Size.SMALL, false));
        storage.addToyToCatalog(new Ball("Ball 1", 100.0, AgeGroup.TODDLER, Size.MEDIUM, "Soccer"));
        storage.addToyToCatalog(new Car("Expensive Car", 300.0, AgeGroup.SCHOOL, Size.LARGE, true));
        storage.addToyToCatalog(new Blocks("Cubes", 50.0, AgeGroup.PRESCHOOL, Size.SMALL, 16));
    }

    @Test
    void testInitializeDataLoadsDefaultCatalog() {
        PlayroomStorage freshStorage = new PlayroomStorage();
        freshStorage.initializeData();

        List<Toy> catalog = freshStorage.getCatalog();
        assertFalse(catalog.isEmpty(), "Catalog should be populated after initializeData()");
        assertEquals(9, catalog.size());
    }

    @Test
    void testLoadCatalogFromExternalFile(@TempDir Path tempDir) throws IOException {
        Path csvFile = tempDir.resolve("custom_toys.csv");
        List<String> lines = List.of(
                "# Comment line",
                "CAR,Speedster,25.0,PRESCHOOL,SMALL,false",
                "DOLL,ragDoll,18.0,BABY,SMALL,Cotton",
                "BALL,Volley,30.0,SCHOOL,MEDIUM,Volleyball",
                "BLOCKS,TowerBlocks,40.0,TODDLER,LARGE,50"
        );
        Files.write(csvFile, lines);

        storage.loadCatalogFromFile(csvFile);
        List<Toy> catalog = storage.getCatalog();

        assertEquals(4, catalog.size());
        assertTrue(catalog.get(0) instanceof Car);
        assertTrue(catalog.get(1) instanceof Doll);
        assertTrue(catalog.get(2) instanceof Ball);
        assertTrue(catalog.get(3) instanceof Blocks);
    }

    @Test
    void testParseToyLineInvalidInputThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> storage.parseToyLine("INVALID,LINE")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> storage.parseToyLine("UNKNOWN,Item,10.0,BABY,SMALL,extra")
        );
    }

    @Test
    void testRemoveToyFromCatalog() {
        assertEquals(4, storage.getCatalog().size());
        assertTrue(storage.removeToyFromCatalog(0));
        assertEquals(3, storage.getCatalog().size());

        assertFalse(storage.removeToyFromCatalog(-1));
        assertFalse(storage.removeToyFromCatalog(99));
    }

    @Test
    void testSetupPlayroomWithinBudget() {
        // Budget 250 allows Car 1 (200.0), skips Ball 1 (100.0) and Expensive Car (300.0),
        // and fits Cubes (50.0) -> total 250.0
        storage.setupPlayroom(250.0);
        List<Toy> playroomToys = storage.getPlayroom();

        assertEquals(2, playroomToys.size());
        assertEquals("Car 1", playroomToys.get(0).getName());
        assertEquals("Cubes", playroomToys.get(1).getName());
    }

    @Test
    void testSetupPlayroomWithFixedMaxToyLimitAndBudget() {
        // Budget 1000 allows all 4 toys, but maxToys=2 limits playroom to 2 toys
        storage.setupPlayroom(1000.0, 2);
        List<Toy> playroomToys = storage.getPlayroom();

        assertEquals(2, playroomToys.size());
        assertEquals("Car 1", playroomToys.get(0).getName());
        assertEquals("Ball 1", playroomToys.get(1).getName());
    }

    @Test
    void testSetupPlayroomRejectsNegativeArguments() {
        assertThrows(IllegalArgumentException.class, () -> storage.setupPlayroom(-10.0));
        assertThrows(IllegalArgumentException.class, () -> storage.setupPlayroom(100.0, -1));
    }

    @Test
    void testSortPlayroomToysByPrice() {
        storage.setupPlayroom(1000.0);
        storage.sortPlayroomToysByPrice();
        List<Toy> sortedToys = storage.getPlayroom();

        assertEquals(50.0, sortedToys.get(0).getPrice());
        assertEquals(100.0, sortedToys.get(1).getPrice());
        assertEquals(200.0, sortedToys.get(2).getPrice());
        assertEquals(300.0, sortedToys.get(3).getPrice());
    }

    @Test
    void testFindToysInPlayroomByPrice() {
        storage.setupPlayroom(1000.0);

        List<Toy> foundToys = storage.findToysInPlayroomByPrice(150.0, 250.0);

        assertEquals(1, foundToys.size());
        assertEquals("Car 1", foundToys.get(0).getName());
    }

    @Test
    void testCatalogAndPlayroomAreUnmodifiable() {
        List<Toy> catalog = storage.getCatalog();
        List<Toy> playroom = storage.getPlayroom();

        assertThrows(UnsupportedOperationException.class, catalog::clear);
        assertThrows(UnsupportedOperationException.class, playroom::clear);
    }
}

