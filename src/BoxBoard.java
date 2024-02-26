package src;

import java.util.*;

/**
 * BoxBoard.java
 * Michelle L Sun 
 * 2/21/24
 * 
 * Represents a game board for the Box Game.
 * Manages the arrangement of tiles and pieces on the board, as well as validating and executing moves.
 * This class initializes the board, populates it with pieces, and facilitates piece movements.
 */
public class BoxBoard extends Board{
    protected Colors colors;

    public BoxBoard(int width, int height, int[][] values) {
        super(width, height, values);
        this.colors = new Colors();
    }

    @Override
    public void display() {
        for (int row = 0; row < height; row++) {
            // Print first line
            for (int col = 0; col < width; col++) {
                System.out.print("o");
                System.out.print(getLineColor(row, col, true));
                if (col + 1 == width) {
                    System.out.print("o"); // For edge case
                }
            }
            System.out.println();
            for (int col = 0; col < width; col++) {
                System.out.print(getVerticalLineColor(row, col, true));
                System.out.print("     ");
                if (col + 1 == width) {
                    System.out.print(getVerticalLineColor(row, col, false)); // For edge case
                }
            }
            System.out.println();

            if (row + 1 == height) { // For edge case
                for (int col = 0; col < width; col++) {
                    System.out.print("o");
                    System.out.print(getLineColor(row, col, false));
                    if (col + 1 == width) {
                        System.out.print("o"); // For edge case
                    }
                }
                System.out.println();
            }
        }
    }
    
        
    /**
     * Retrieves the line color for a given tile and piece direction.
     * 
     * "row": the row index of the tile
     * "col": the column index of the tile
     * "isNorth": indicates whether the piece direction is north (true) or south (false)
     * Returns the ANSI color code for the line
     */
    protected String getLineColor(int row, int col, boolean isNorth) {

        List<Piece> pieces = tiles[row][col].getPieces();
        Piece piece = (isNorth) ? pieces.get(0): pieces.get(2);

        String colorName = piece.getColor();
        String colorCode = colors.getColor(colorName);

        // Return with the color associated
        return colorCode + "-----" + Colors.ANSI_RESET; // Reset color after printing the dashes
    }    

    /**
     * Retrieves the vertical line color for a given tile and piece direction.
     * 
     * "row": the row index of the tile
     * "col": the column index of the tile
     * "isWest": indicates whether the piece direction is west (true) or east (false)
     * Returns the ANSI color code for the vertical line
     */
    protected String getVerticalLineColor(int row, int col, boolean isWest) {

        List<Piece> pieces = tiles[row][col].getPieces();
        Piece piece = (isWest) ? pieces.get(3): pieces.get(1);

        String colorName = piece.getColor();
        String colorCode = colors.getColor(colorName);

        // Return with the color associated
        return colorCode + "|" + Colors.ANSI_RESET; // Reset color after printing the vertical line
    }    
 
    @Override
    protected int[] findPosition(int pieceValue){
        // Find the position of the piece to be moved
        int piece = pieceValue % 4; 

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int value = getTile(row, col).getPieces().get(piece).getValue();

                if (value == pieceValue) {
                    return new int[] { row, col, piece };
                }
            }
        }
        return null; // Couldn't find tile and piece, something is wrong
    }; 

    @Override
    protected boolean isValidMove(int[]positions) { 
        // Reason for this array input is to be same signature as Board.
        
        int row = positions[0];
        int col = positions[1];
        int piecePosition = positions[2];

        // Check if the tile is already occupied or piece is already colored
        if (getTile(row, col).isOccupied() || !getTile(row, col).getPieces().get(piecePosition).getColor().equals("None")) {
            return false;
        }
        return true;
    }

    /**
     * Changes the color of a piece on the board and checks if the move completes a box.
     * 
     * "pieceValue": the value of the piece to be moved
     * "color": the color to set the piece
     * Return an array containing the success of the move, whether it completes a box,
     * and whether an additional increment to the team's score is needed
     * [success, completesBox, needsIncrement]
     */
    @Override
    public boolean[] changePiece(int pieceValue, String color) {
        boolean completeBox = false; // Boolean value for complete box
        boolean[] results = new boolean[]{false, completeBox, false};
    
        // Get the position of the current piece
        int[] currentPosition = findPosition(pieceValue);
        if (currentPosition == null) {
            return results;
        }

        int row = currentPosition[0];
        int col = currentPosition[1];
        int piecePosition = currentPosition[2];
        
        if (!isValidMove(currentPosition)){
            return results;
        }
    
        // Set the color of the current piece
        getTile(row, col).getPieces().get(piecePosition).setColor(color);
        results[0] = true;
        
        // Check if this move completes a box
        if (getTile(row, col).isOccupied()) {
            completeBox = true;
        }
    
        // Find the counterpart piece and set its color if applicable
        int counterpartPieceValue = getCounterpartPieceValue(pieceValue, row, col, piecePosition);
        if (counterpartPieceValue != -1) {
            int[] counterpartPosition = findPosition(counterpartPieceValue);

            if (counterpartPosition != null) {
                getTile(counterpartPosition[0], counterpartPosition[1]).getPieces().get(counterpartPosition[2]).setColor(color);
                if (getTile(counterpartPosition[0], counterpartPosition[1]).isOccupied()) { 
                    // Give true if an addtional numInc is needed since Team got 2 tiles with 1 piece.
                    results[2] = true;
                }
            }
        }
    
        results[1] = completeBox;
        return results;
    }
    
    // Helper method to find the value of the counterpart piece
    private int getCounterpartPieceValue(int pieceValue, int row, int col, int piecePosition) {
        if (piecePosition == 0 && row > 0) { 
            // North piece, so get the south piece of above tile
            return getTile(row-1, col).getPieces().get(2).getValue(); 
        } else if (piecePosition == 1 && col < width - 1) {
            // East piece, so get west piece of prev tile
            return getTile(row, col+1).getPieces().get(3).getValue();
        } else if (piecePosition == 2 && row < height - 1) { 
            // South piece, so get north piece of below tile
            return getTile(row+1, col).getPieces().get(0).getValue();
        } else if (piecePosition == 3 && col > 0) { 
            // West piece, so get east piece of next tile
            return getTile(row, col-1).getPieces().get(1).getValue();
        }
        return -1; // No counterpart piece, it was an edge piece
    }
    
    /**
     * Populates the game board with tiles and pieces based on the provided values array.
     * 
     * "values": a 2D array representing the initial values for the pieces on the board
     */
    @Override
    protected void populateBoard(int[][] values) {
        int pieceValue = 0; // Initialize the piece value
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Make sure every Tile has four pieces with unique values
                getTile(i, j).addPiece(new Piece(pieceValue++, "None")); // North
                getTile(i, j).addPiece(new Piece(pieceValue++, "None")); // East
                getTile(i, j).addPiece(new Piece(pieceValue++, "None")); // South
                getTile(i, j).addPiece(new Piece(pieceValue++, "None")); // West
            }
        }
    }    
}
