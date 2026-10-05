package com.playroom.ui;

public interface UserInput {
    int readInt(String prompt);
    double readDouble(String prompt);
    boolean readBoolean(String prompt);
    String readString(String prompt);
}