package com.playroom.ui;

import com.playroom.model.AgeGroup;
import com.playroom.model.Ball;
import com.playroom.model.Blocks;
import com.playroom.model.Car;
import com.playroom.model.Doll;
import com.playroom.model.Size;
import com.playroom.model.Toy;
import com.playroom.storage.PlayroomStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToyMenuTest {

    @Mock
    private PlayroomStorage storageMock;

    @Mock
    private UserInput userInputMock;

    private ToyMenu toyMenu;

    @BeforeEach
    void setUp() {
        toyMenu = new ToyMenu(storageMock, userInputMock);
    }

    @Test
    void testAddCarToy() {
        when(userInputMock.readInt(anyString())).thenReturn(1, 3, 2);
        when(userInputMock.readString("Enter name:")).thenReturn("Tesla Model S");
        when(userInputMock.readDouble("Enter price:")).thenReturn(80.0);
        when(userInputMock.readBoolean("Is it electric? (true/false):")).thenReturn(true);

        toyMenu.addToy();

        ArgumentCaptor<Toy> captor = ArgumentCaptor.forClass(Toy.class);
        verify(storageMock).addToyToCatalog(captor.capture());
        Toy added = captor.getValue();
        assertTrue(added instanceof Car);
        assertEquals("Tesla Model S", added.getName());
        assertEquals(AgeGroup.PRESCHOOL, added.getAgeGroup());
        assertEquals(Size.MEDIUM, added.getSize());
    }

    @Test
    void testAddDollToy() {
        when(userInputMock.readInt(anyString())).thenReturn(2, 1, 1);
        when(userInputMock.readString("Enter name:")).thenReturn("Baby Doll");
        when(userInputMock.readDouble("Enter price:")).thenReturn(25.0);
        when(userInputMock.readString("Enter material (e.g., Plastic, Cloth):")).thenReturn("Cloth");

        toyMenu.addToy();

        ArgumentCaptor<Toy> captor = ArgumentCaptor.forClass(Toy.class);
        verify(storageMock).addToyToCatalog(captor.capture());
        assertTrue(captor.getValue() instanceof Doll);
        assertEquals(AgeGroup.BABY, captor.getValue().getAgeGroup());
        assertEquals(Size.SMALL, captor.getValue().getSize());
    }

    @Test
    void testAddBallToy() {
        when(userInputMock.readInt(anyString())).thenReturn(3, 4, 3);
        when(userInputMock.readString("Enter name:")).thenReturn("Soccer Ball");
        when(userInputMock.readDouble("Enter price:")).thenReturn(35.0);
        when(userInputMock.readString("Enter sport type (e.g., Soccer, Tennis):")).thenReturn("Soccer");

        toyMenu.addToy();

        ArgumentCaptor<Toy> captor = ArgumentCaptor.forClass(Toy.class);
        verify(storageMock).addToyToCatalog(captor.capture());
        assertTrue(captor.getValue() instanceof Ball);
        assertEquals(AgeGroup.SCHOOL, captor.getValue().getAgeGroup());
        assertEquals(Size.LARGE, captor.getValue().getSize());
    }

    @Test
    void testAddBlocksToy() {
        when(userInputMock.readInt(anyString())).thenReturn(4, 2, 2, 48);
        when(userInputMock.readString("Enter name:")).thenReturn("Lego Duplo");
        when(userInputMock.readDouble("Enter price:")).thenReturn(50.0);

        toyMenu.addToy();

        ArgumentCaptor<Toy> captor = ArgumentCaptor.forClass(Toy.class);
        verify(storageMock).addToyToCatalog(captor.capture());
        assertTrue(captor.getValue() instanceof Blocks);
        assertEquals(48, ((Blocks) captor.getValue()).getPieceCount());
    }

    @Test
    void testAddToyWithInvalidTypeDoesNotAdd() {
        when(userInputMock.readInt(anyString())).thenReturn(99);

        toyMenu.addToy();

        verify(storageMock, never()).addToyToCatalog(any());
    }

    @Test
    void testDisplayAllToysEmptyAndNonEmpty() {
        when(storageMock.getCatalog()).thenReturn(Collections.emptyList());
        toyMenu.displayAllToys();

        when(storageMock.getCatalog()).thenReturn(
                List.of(new Car("Roadster", 15.0, AgeGroup.PRESCHOOL, Size.SMALL, false))
        );
        toyMenu.displayAllToys();

        verify(storageMock, times(2)).getCatalog();
    }

    @Test
    void testRemoveToySuccessAndFailure() {
        when(storageMock.getCatalog()).thenReturn(Collections.emptyList());
        when(userInputMock.readInt("Enter the index of the toy to remove:")).thenReturn(0, 5);
        when(storageMock.removeToyFromCatalog(0)).thenReturn(true);
        when(storageMock.removeToyFromCatalog(5)).thenReturn(false);

        toyMenu.removeToy();
        toyMenu.removeToy();

        verify(storageMock).removeToyFromCatalog(0);
        verify(storageMock).removeToyFromCatalog(5);
    }
}

