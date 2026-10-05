package com.playroom.ui;

import com.playroom.storage.PlayroomStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    void testPreparePlayroom() {
        // Arrange: tell the mock UserInput to return 500.0 when asked for a double
        when(userInputMock.readDouble(anyString())).thenReturn(500.0);

        // Act
        playroomMenu.preparePlayroom();

        // Assert: verify that setupPlayroom was called on the storage with exactly 500.0
        verify(storageMock, times(1)).setupPlayroom(500.0);
    }

    @Test
    void testFindToysByPriceRange() {
        // Arrange: simulate user entering min price 100.0 and max price 300.0
        when(userInputMock.readDouble("Enter minimum price:")).thenReturn(100.0);
        when(userInputMock.readDouble("Enter maximum price:")).thenReturn(300.0);

        // Act
        playroomMenu.findToysByPriceRange();

        // Assert: verify that the storage was asked to find toys in that exact range
        verify(storageMock, times(1)).findToysInPlayroomByPrice(100.0, 300.0);
    }
}