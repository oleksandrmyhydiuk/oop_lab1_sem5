package com.playroom.model;

/**
 * Represents building blocks (cubes) with a specific number of pieces.
 */
public class Blocks extends Toy {

    private final int pieceCount;

    public Blocks(String name, double price, AgeGroup ageGroup, Size size, int pieceCount) {
        super(name, price, ageGroup, size);
        if (pieceCount <= 0) {
            throw new IllegalArgumentException("Piece count must be greater than zero.");
        }
        this.pieceCount = pieceCount;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    @Override
    public String getSpecificDetails() {
        return "pieceCount=" + pieceCount;
    }
}

