package com.playroom.ui;

/**
 * Abstraction for reading user input from the console or test mocks.
 */
public interface UserInput {

    int readInt(String prompt);

    double readDouble(String prompt);

    boolean readBoolean(String prompt);

    String readString(String prompt);
}
