package com.playroom.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToyModelTest {

    @Test
    void testCarCreationAndPolymorphicDetails() {
        Car car = new Car("RC Racer", 99.5, AgeGroup.SCHOOL, Size.LARGE, true);

        assertEquals("RC Racer", car.getName());
        assertEquals(99.5, car.getPrice());
        assertEquals(AgeGroup.SCHOOL, car.getAgeGroup());
        assertEquals(Size.LARGE, car.getSize());
        assertTrue(car.isElectric());
        assertEquals("electric=true", car.getSpecificDetails());
        assertTrue(car.toString().contains("Car {name='RC Racer'"));
        assertTrue(car.toString().contains("electric=true"));
    }

    @Test
    void testDollCreationAndPolymorphicDetails() {
        Doll doll = new Doll("Porcelain Doll", 55.0, AgeGroup.PRESCHOOL, Size.MEDIUM, "Porcelain");

        assertEquals("Porcelain Doll", doll.getName());
        assertEquals("Porcelain", doll.getMaterial());
        assertEquals("material='Porcelain'", doll.getSpecificDetails());
        assertTrue(doll.toString().contains("material='Porcelain'"));
    }

    @Test
    void testBallCreationAndPolymorphicDetails() {
        Ball ball = new Ball("Basketball", 35.0, AgeGroup.SCHOOL, Size.LARGE, "Basketball");

        assertEquals("Basketball", ball.getName());
        assertEquals("Basketball", ball.getSportType());
        assertEquals("sportType='Basketball'", ball.getSpecificDetails());
        assertTrue(ball.toString().contains("sportType='Basketball'"));
    }

    @Test
    void testBlocksCreationAndPolymorphicDetails() {
        Blocks blocks = new Blocks("Wooden Cubes", 22.0, AgeGroup.BABY, Size.SMALL, 24);

        assertEquals("Wooden Cubes", blocks.getName());
        assertEquals(24, blocks.getPieceCount());
        assertEquals("pieceCount=24", blocks.getSpecificDetails());
        assertTrue(blocks.toString().contains("pieceCount=24"));
    }

    @Test
    void testValidationThrowsOnInvalidArguments() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Car("", 10.0, AgeGroup.BABY, Size.SMALL, false)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Car("Mini Car", -5.0, AgeGroup.BABY, Size.SMALL, false)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Car("Mini Car", 10.0, null, Size.SMALL, false)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Car("Mini Car", 10.0, AgeGroup.BABY, null, false)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Doll("Doll", 10.0, AgeGroup.BABY, Size.SMALL, " ")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Ball("Ball", 10.0, AgeGroup.BABY, Size.SMALL, null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Blocks("Blocks", 10.0, AgeGroup.BABY, Size.SMALL, 0)
        );
    }

    @Test
    void testMechanicalCarIsNotElectric() {
        Car car = new Car("Mini Metal Car", 12.0, AgeGroup.TODDLER, Size.SMALL, false);
        assertFalse(car.isElectric());
    }
}

