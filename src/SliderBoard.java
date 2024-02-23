package src;

/**
 * SliderBoard.java
 * Michelle L Sun 
 * 2/17/24
 * 
 * Represents the game board for the Sliding Window Game.
 * Manages the arrangement of tiles and pieces on the board, as well as validating and executing moves.
 */
public class SliderBoard extends Board{

    public SliderBoard(int width, int height, int[][] puzzle) {
        super(width, height, puzzle);
    }

    /**
     * Checks if a move to a specified position is valid for the sliding puzzle game.
     * 
     * "checkPosition": an array containing the row index, column index, empty tile's row index,
     *                  empty tile's column index [row, col, emptyRow, emptyCol]
     * Returns true if the move is valid, false otherwise
     */
    @Override
    protected boolean isValidMove(int [] checkPosition) {
        // Implement the logic to check if the move is valid for the sliding puzzle game
        int row = checkPosition[0];
        int col = checkPosition[1];
        int emptyRow = checkPosition[2];
        int emptyCol = checkPosition[3];

        // Check if the move is adjacent to the empty space
        return (Math.abs(emptyRow - row) == 1 && emptyCol == col) ||
               (Math.abs(emptyCol - col) == 1 && emptyRow == row);
    }

    @Override
    protected int[] findPosition(int pieceValue) {
        // Implement the logic to find the position of a tile with the specified piece value
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Piece piece = getTile(row, col).getPieces().get(0);

                if (piece.getValue() == pieceValue) {
                    // Tile with the specified piece value found
                    return new int[]{row, col};
                }
            }
        }
        // Piece not found
        return null;
    }
    /**
     * Moves a piece on the board to the empty tile if the move is valid.
     * 
     * "pieceValue": the value of the piece to be moved
     * "color": the color of the piece
     * Returns an array indicating the success of the move [success],
     * where true indicates a successful move and false indicates failure
     */
    @Override
    public boolean[] changePiece(int pieceValue, String color) {
        // Find the position of the piece to be moved

        int[] piecePosition = findPosition(pieceValue);
        if (piecePosition == null) {
            return new boolean[] {false}; // Piece not found
        }
    
        // Find the position of the empty tile
        int[] emptyPosition = findPosition(0);
        if (emptyPosition == null) {
            return new boolean[] {false}; // Empty tile not found
        }

        int pieceRow = piecePosition[0];
        int pieceCol = piecePosition[1];
        int emptyRow = emptyPosition[0];
        int emptyCol = emptyPosition[1];

        // Check if the move is valid
        if (!isValidMove(new int [] {pieceRow, pieceCol, emptyRow, emptyCol})) {
            return new boolean[] {false}; // Move is not valid
        }
    
        // Move the piece to the empty tile
        getTile(pieceRow, pieceCol).addPiece(tiles[emptyRow][emptyCol].getPieces().get(0)); // Add the 0 piece
        getTile(emptyRow, emptyCol).addPiece(tiles[pieceRow][pieceCol].getPieces().get(0)); // Add the prev piece

        getTile(emptyRow, emptyCol).removePiece(tiles[emptyRow][emptyCol].getPieces().get(0)); // Remove the 0 piece
        getTile(pieceRow, pieceCol).removePiece(tiles[pieceRow][pieceCol].getPieces().get(0)); // Remove the prev piece

        return new boolean[] {true}; // Move successful
    }

    /**
     * Populates the game board with tiles and pieces based on the provided puzzle.
     * 
     * "puzzle": a 2D array representing the initial arrangement of pieces on the board
     */
    @Override
    protected void populateBoard(int[][] puzzle) {
        // Check if puzzle dimensions match board dimensions
        if (puzzle.length != height || puzzle[0].length != width) {
            System.out.println("Puzzle dimensions wrong?");
            throw new IllegalArgumentException("Puzzle dimensions do not match board dimensions");
        }

        // Populate the board with pieces based on the puzzle
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int pieceValue = puzzle[row][col];
                getTile(row, col).addPiece(new Piece(pieceValue, "None"));
            }
        }
    }


}
