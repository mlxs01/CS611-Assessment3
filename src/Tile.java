package src;

import java.util.*;

/**
 * Tile.java
 * Michelle L Sun 
 * Date: 2/21/24
 * 
 * Represents a tile on the game board.
 * Each tile may or may not contain one or more pieces.
 */
public class Tile {
    private final int row;
    private final int column;
    private List<Piece> pieces;

    /**
     * Constructs a Tile object with the specified row and column.
     * By default, the tile is not occupied by any piece.
     * 
     * 'row' : row index of the tile.
     * 'column' : column index of the tile.
     */
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.pieces = new ArrayList<Piece>();
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isOccupied() {
        for (int i = 0; i < pieces.size(); i++){
            if (pieces.get(i).getColor() == "None"){
                return false;
            }
        }
        return true;
    }

    // If the board is initiated correctly, then the (row, col) combination of each
    // tile is unique
    // Given equals() has an input parameter of Object, to oevrride it, we must 
    // have the same signatures
    @Override
    public boolean equals(Object obj) {
        // If the two have the same memory address
        if (this == obj) {
            return true;
        }

        // If tile is null, or if tile is of a different class from this
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Having arrived here, obj is not this but has the same class as this
        Tile other = (Tile) obj;

        // Compare properties
        return row == other.getRow() && column == other.getColumn();
    }
}

