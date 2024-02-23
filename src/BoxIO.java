package src;

public class BoxIO extends IO{

    /**
     * Prompts the user to input the tile and piece they want to move for Dots and Boxes Game specifically.
     * 
     * Returns the index representing the chosen tile and piece, or a quit value if the user chooses to quit
     */
    @Override
    public int getPieceMove(){
        int quitValue = -1; // Value to signify quitting

        int actionTile = queryInt("Enter the tile you want (Starting with tile 0) or enter " + quitValue + " to quit: ", -1, Integer.MAX_VALUE);
        if (actionTile == quitValue) {
            displayMessage("Quitting game...");
            return quitValue; // Return the quitValue to indicate quitting
        }
        
        int actionPiece = queryInt("Enter the piece you want (north=0, east=1, south=2, west=3) or enter " + quitValue + " to quit: ", -1, 3);
        if (actionPiece == quitValue) {
            displayMessage("Quitting game...");
            return quitValue; // Return the quitValue to indicate quitting
        }

        return ((actionTile * 4) + actionPiece);
    }
}
