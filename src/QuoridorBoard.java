package src;

public class QuoridorBoard extends BoxBoard{ 
// What if QuoridorBoard extends BoxBoard? Wouldn't that be better?

    public QuoridorBoard(int width, int height, int[][] values) {
        super(width, height, values);
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
                System.out.print("  " + ((row * width)+col) + "  ");
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

    @Override    
    protected Tile createTile(int row, int col) {
        return new QuoridorTile(row, col); // Create a new QuoridorTile object
    }

    @Override
    protected int[] findPosition(int pieceValue) {
        return null;
    }

    @Override
    protected boolean isValidMove(int[] positions) {
        return true;
    }

    @Override
    public boolean[] changePiece(int pieceValue, String color) {
        return null;
    }
    // The Overload
    public boolean[] changePiece(int pieceValue, int adjValue, String color) {
        return null;
    }

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
                getTile(i, j).addPiece(new Piece(pieceValue++, "None")); // Player Piece
            }
        }
    }
}
