package com.playroom.ui;

import com.playroom.model.AgeGroup;
import com.playroom.model.Car;
import com.playroom.model.Size;
import com.playroom.storage.PlayroomStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayroomMenuTest {

    @Mock
    private PlayroomStorage storageMock;

    @Mock
    private UserInput userInputMock;

    private PlayroomMenu playroomMenu;

    @BeforeEach
    void setUp() {
        playroomMenu = new PlayroomMenu(storageMock, userInputMock);
    }

    @Test
    void testPreparePlayroomWithBudgetAndMaxToys() {
        when(userInputMock.readDouble("Enter the budget for the playroom:")).thenReturn(500.0);
        when(userInputMock.readInt("Enter the maximum number of toys:")).thenReturn(5);

        playroomMenu.preparePlayroom();

        verify(storageMock, times(1)).setupPlayroom(500.0, 5);
    }

    @Test
    void testDisplayPlayroomToysWhenEmptyAndNonEmpty() {
        when(storageMock.getPlayroom()).thenReturn(Collections.emptyList());
        playroomMenu.displayPlayroomToys();

        when(storageMock.getPlayroom()).thenReturn(
                List.of(new Car("Mini Car", 20.0, AgeGroup.PRESCHOOL, Size.SMALL, false))
        );
        playroomMenu.displayPlayroomToys();

        verify(storageMock, times(2)).getPlayroom();
    }

    @Test
    void testSortToys() {
        when(storageMock.getPlayroom()).thenReturn(Collections.emptyList());

        playroomMenu.sortToys();

        verify(storageMock, times(1)).sortPlayroomToysByPrice();
        verify(storageMock, times(1)).getPlayroom();
    }

    @Test
    void testFindToysByPriceRange() {
        when(userInputMock.readDouble("Enter minimum price:")).thenReturn(100.0);
        when(userInputMock.readDouble("Enter maximum price:")).thenReturn(300.0);
        when(storageMock.findToysInPlayroomByPrice(100.0, 300.0)).thenReturn(
                List.of(new Car("Sedan", 150.0, AgeGroup.TODDLER, Size.MEDIUM, false))
        );

        playroomMenu.findToysByPriceRange();

        verify(storageMock, times(1)).findToysInPlayroomByPrice(100.0, 300.0);
    }
}
