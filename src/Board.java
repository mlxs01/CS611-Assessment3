package src;

import java.util.List;

/**
 * Board.java
 * Michelle L Sun 
 * 2/21/24
 * 
 * Abstract class representing a game board consisting of tiles and pieces.
 * Manages the arrangement of tiles and pieces on the board,
 * as well as validating and executing moves.
 */
public abstract class Board {
    //private final int MIN_ROWS = 2;
    //private final int MIN_COLS = 2;
    protected final int MIN_VALUE = 0;
    protected final int MAX_VALUE;

    protected Tile[][] tiles;
    protected int width;
    protected int height;

    public Board(int width, int height, int[][] values) {
        setWidth(width);
        setHeight(height);
        tiles = new Tile[height][width];
        MAX_VALUE = width * height - 1;
        initializeBoard();
        populateBoard(values);
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected void initializeBoard() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col] = createTile(row, col);
            }
        }
    }

    protected Tile createTile(int row, int col) {
        return new Tile(row, col); // Create a new Tile object for the given row and column
    }

    public Tile getTile(int row, int col) {
        if (areCoordinatesInbound(row, col)) {
            return tiles[row][col];
        }
        return null;
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    protected boolean isValueInbound(int value) {
        return MIN_VALUE <= value && value <= MAX_VALUE;
    }

    protected boolean areCoordinatesInbound(int row, int col) {
        return !(row < Constants.MIN_X || col < Constants.MIN_Y || row >= height || col >= width);
    }

    // Will only return a Tile for a value that is within bounds
    public Tile findTileByValue(int value) {
        if (isValueInbound(value)) {
            int row = value / width;
            int col = value % width;
            return getTile(row, col);
        }
        return null;
    }

    public void display() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                List<Piece> pieces = tiles[row][col].getPieces();
                if (!pieces.isEmpty()) {
                    for (Piece piece : pieces) {
                        System.out.print(piece.getValue() + "\t");
                    }
                } else {
                    System.out.print("-\t"); // Placeholder for empty tile
                }
            }
            System.out.println();
        }
    }

    protected abstract boolean isValidMove(int[] positions); // Abstract method to check if a move is valid

    protected abstract int[] findPosition(int pieceValue); // Abstract method to find the position of a piece

    public abstract boolean[] changePiece(int pieceValue, String color); // Abstract method to change piece value

    protected abstract void populateBoard(int[][] values); // Abstract method to populate the board with certain values
}
