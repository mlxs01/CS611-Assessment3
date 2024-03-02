package src;

import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

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

    /*
     * 
     * 
     * New methods wrote
     * 
     * 
     */

    // NOTE, "YELLOW" is not in the color bank yet
    private static final String WALLCOLOR = "YELLOW";
    private static final String DEFAULTCOLOR = "None";

    /*
     *
     * You should use this method to attempt to place a wall
     * 
     *  INPUT:
     * 1.) tileValue: value corresponding to a tile on the board
     * 2.) piecePosition: ranging from 0-3, one of the four cardinal edges of a tile
     * 3.) wallDir: ranging from 0-3 to represent each cardinal direction,
     *      if we are to put down a wall on the previously defined
     *      tile's piece, then to which direction do we want to extend the wall
     * 4.) colors: set of colors for all teams currently playing
     * 
     * OUTPUT: True, if we successfully put down a wall
     *         False, if we failed to put down a wall w/ the given parameters
     */
    public boolean placeWall(int tileValue, int piecePosition, int wallDir, String[] colors) {
        // Check if a wall could be placed down with the input parameters
        boolean isValidWallMove = isValidWallMove(tileValue, piecePosition, wallDir);

        if (!isValidWallMove) {
            return false; // Failed to place down a wall
        }

        // Get the two tiles
        QuoridorTile targetTile = findTileByValue(tileValue);
        int[] dir = getCardinalDir(wallDir);
        int newRow = targetTile.getRow() + dir[0];
        int newCol = targetTile.getColumn() + dir[1];
        QuoridorTile neighborTile = getTile(newRow, newCol);

        // Go on to place down a wall
        targetTile.getPieces().get(piecePosition).setColor(WALLCOLOR);
        neighborTile.getPieces().get(piecePosition).setColor(WALLCOLOR);
        // However, in a board, an edge is shared between at most two tiles
        updateNeighborTile(targetTile, piecePosition, WALLCOLOR);
        updateNeighborTile(neighborTile, piecePosition, WALLCOLOR);

        // Get where the pawn of each team is at
        Set<QuoridorTile> pawnTiles = getPawnTiles(colors);
        // Check if the placement of this wall will prevent any pawn from being
        // able to reach its destination
        if (!quoridorBoardBFSWrapper(pawnTiles)) {
            // Having arrived here, meaning the placement of this wall will prevent at least one pawn
            // from being able to reach its destination, so we undo the wall we added
            targetTile.getPieces().get(piecePosition).setColor(DEFAULTCOLOR);
            neighborTile.getPieces().get(piecePosition).setColor(DEFAULTCOLOR);
            updateNeighborTile(targetTile, piecePosition, DEFAULTCOLOR);
            updateNeighborTile(neighborTile, piecePosition, DEFAULTCOLOR);
        } // Else, the placement of this wall does allow possible traversals to the destinations
        
        // Having arrived here, we have successfully placed down a valid wall
        return true;
    }

    /*
     * Helper method to placeWall
     */
    private boolean isValidWallMove(int tileValue, int piecePosition, int wallDir) {
        QuoridorTile targetTile = findTileByValue(tileValue);
        if (targetTile == null) {
            return false;
        }

        // See if we could put down one piece of the wall on the specified piece
        if (targetTile.hasFence(piecePosition)) {
            return false;
        }

        // A wall has to be consecutive in one cardinal direction
        switch (piecePosition) {
            case Constants.NORTHEDGE:
            case Constants.SOUTHEDGE:
                // Can only extend the wall to EAST or WEST
                if (wallDir != Constants.EASTEDGE || wallDir != Constants.WESTEDGE) {
                    return false;
                }
                break;
            case Constants.EASTEDGE:
            case Constants.WESTEDGE:
                // Can only extend the wall to NORTH or SOUTH
                if (wallDir != Constants.NORTHEDGE || wallDir != Constants.SOUTHEDGE) {
                    return false;
                }
                break;
            default:
                break;
        }
        // Having arrived here, we've determined that the player has input
        // a wall that is consecutive in one cardinal direction

        // We will go on to check if the neighborTile along the wallDir is inbound
        int[] dir = getCardinalDir(wallDir);
        int newRow = targetTile.getRow() + dir[0];
        int newCol = targetTile.getColumn() + dir[1];
        QuoridorTile neighborTile = getTile(newRow, newCol);
        if (neighborTile == null) {
            return false;
        }

        // Fence check for neighborTile
        if (neighborTile.hasFence(piecePosition)) {
            return false;
        }

        /*
         * Having arrived here, we have determined the following:
         * 1.) The edge in the currentTile and in the neighborTile are empty
         * 2.) The wall is consecutive in one cardinal direction
         */
        return true;
    }

    /*
     * You should use this method to attempt to move a pawn
     * 
     * INPUT: 1.) tileValue: where the player wants to move to 
     *        2.) currentTile: the tile of where the current team's pawn is on
     * 
     * OUTPUT: true, successfully moved a pawn to the targetTile
     *         false, failed to move a pawn to the targetTile
     */
    public boolean movePawn(int tileValue, QuoridorTile currentTile) {
        // We CANNOT move this pawn as the targetTile from the tileValue is invalid
        if (!isValidPawnMove(tileValue, currentTile)) {
            return false;
        }

        // Having arrived here, we can move the pawn to the targetTile
        QuoridorTile targetTile = findTileByValue(tileValue);

        // Assuming pieces[5] is used to represent whether a player is on the tile
        String temp = currentTile.getTileTeam();
        // Clear the currentTile
        currentTile.getPieces().get(Constants.TEAMPIECE).setColor(DEFAULTCOLOR);
        // The MOVING of the pawn to the targetTile
        targetTile.getPieces().get(Constants.TEAMPIECE).setColor(temp);

        return true;
    }

    /*
     * Helper method to movePawn()
     * 
     * Input: tileValue, value of the target tile for movement
     *        currentTile, quoridorTile which the current player's pawn is on
     * 
     * Output: true, the player can move to the target tile
     *         false, the player cannot move to the target tile
     */
    private boolean isValidPawnMove(int tileValue, QuoridorTile currentTile) {
        QuoridorTile targetTile = findTileByValue(tileValue);
        if (targetTile == null) {
            return false;
        }

        Set<QuoridorTile> possiblePawnMoves = possiblePawnMoves(currentTile);

        if (possiblePawnMoves.contains(targetTile)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * According to the rules of Quoridor and the state of the current board,
     * return the set of possible moves for the input pawn
     * 
     * INPUT: currentTile: tile of the pawn we are currently considering
     * 
     * OUTPUT: Set<QuoridorTile>, the tiles that the pawn could move to
     */
    private Set<QuoridorTile> possiblePawnMoves(QuoridorTile currentTile) {
        Set<QuoridorTile> possibleMoves = new HashSet<>();

        // Get the current positions of the pawn
        int currentTileRow = currentTile.getRow();
        int currentTileCol = currentTile.getColumn();

        // Check each cardinal direction for possible moves, ordering: west, east, south, north
        int[] dirEdges = {Constants.NORTHEDGE, Constants.EASTEDGE, Constants.SOUTHEDGE, Constants.WESTEDGE};

        for (int dirEdge : dirEdges) {
            int[] dir = getCardinalDir(dirEdge);

            int newRow = currentTileRow + dir[0];
            int newCol = currentTileCol + dir[1];

            // Check if the new positions are w/in bounds and there is not
            // a fence in the direction of this newTile
            QuoridorTile newTile = getTile(newRow, newCol);
            if (newTile != null && !currentTile.hasFence(dirEdge)) {
                // Check if the new tile is occupied by another pawn
                if (!newTile.isOccupied()) {
                    possibleMoves.add(newTile);
                } 
                /*
                 * The new tile is occupied by a pawn, we will see if we could jump by 
                 * checking one more unit along this cardinal direction:
                 * 1.) If there is a fence or,
                 * 2.) If there is another pawn
                 */
                else if (areCoordinatesInbound(newRow + dir[0], newCol + dir[1]) && !newTile.hasFence(dirEdge)) {

                    QuoridorTile behindTile = getTile(newRow + dir[0], newCol + dir[1]);
                    if (!behindTile.isOccupied()) {
                        possibleMoves.add(behindTile);

                    } else {
                        // We cannot move along this cardinal direction, and this means we are allowed
                        // to jump to diagonal adjacent tiles
                        
                        if (dirEdge == Constants.EASTEDGE || dirEdge == Constants.WESTEDGE) {
                            // Check in the NORTH direction of the newTile and
                            // attempt to add the upperTile into the possibleMoves
                            QuoridorTile upperTile = getTile(newRow - 1, newCol);
                            if (upperTile != null && !newTile.hasFence(Constants.NORTHEDGE)) {
                                if (!upperTile.isOccupied()) {
                                    possibleMoves.add(upperTile);
                                }
                            }
                            // Check in the SOUTH direction of the newTile and
                            // attempt to add the lowerTile into the possibleMoves
                            QuoridorTile lowerTile = getTile(newRow + 1, newCol);
                            if (lowerTile != null && !newTile.hasFence(Constants.SOUTHEDGE)) {
                                if (!lowerTile.isOccupied()) {
                                    possibleMoves.add(lowerTile);
                                }
                            }
                        } else if (dirEdge == Constants.NORTHEDGE || dirEdge == Constants.SOUTHEDGE) {
                            // Check in the WEST direction of the newTile and
                            // attempt to add the leftTile into the possibleMoves
                            QuoridorTile leftTile = getTile(newRow, newCol - 1);
                            if (leftTile != null && !newTile.hasFence(Constants.WESTEDGE)) {
                                if (!leftTile.isOccupied()) {
                                    possibleMoves.add(leftTile);
                                }
                            }
                            // Check in the EAST direction of the newTile and
                            // attempt to add the rightTile into the possibleMoves
                            QuoridorTile rightTile = getTile(newRow, newCol + 1);
                            if (rightTile != null && !newTile.hasFence(Constants.EASTEDGE)) {
                                if (!rightTile.isOccupied()) {
                                    possibleMoves.add(rightTile);
                                }
                            }
                        } else {
                            throw new IllegalArgumentException("Unexpected condition encountered, please debug with the following info: \n" 
                            + "We are checking in the " + dirEdge + " direction of the currentTile (" 
                            + currentTileRow + "," + currentTileCol + ").\n");
                        }
                    }

                } else {
                    throw new IllegalArgumentException("Unexpected condition encountered, please debug with the following info: \n" 
                    + "We are checking in the " + dirEdge + " direction of the currentTile (" 
                    + currentTileRow + "," + currentTileCol + ").\n");
                }
            }
        }
        return possibleMoves;
    }

    /*
     * This wrapper method checks for all pawnTiles whether there is a valid path
     * from each of them to their corresponding destination row
     * 
     * INPUT: pawnTiles: set of all tiles that currently have a pawn on them
     * 
     * OUTPUT: true, all the pawns have a valid path to their destination
     *         false, at least one of the pawns do not have a valid path to their destination
     */
    private boolean quoridorBoardBFSWrapper(Set<QuoridorTile> pawnTiles) {

        for (QuoridorTile pawnTile : pawnTiles) {
            if (!quoridorBoardBFS(pawnTile)) {
                // Meaning we could not travel from a pawnTile to the destination
                return false;
            }
        }
        // Meaning there is a possible path for all pawns
        return true;
    }

    /*
     * BFS, to search for a path from pawnTile to its destination
     * 
     * INPUT: pawnTile: tile containing a pawn(team)
     * 
     * OUTPUT: true, there is a path from the tile of this pawn to its destination
     *         false, no path
     */
    private boolean quoridorBoardBFS(QuoridorTile pawnTile) {
        // Initialize data structures for BFS
        Queue<QuoridorTile> queue = new LinkedList<>();
        Set<QuoridorTile> visited = new HashSet<>();

        // Initialization
        visited.add(pawnTile);
        queue.offer(pawnTile);

        while (!queue.isEmpty()) {
            QuoridorTile currentTile = queue.poll();

            // Check if currentTile is one of the destination for pawnTile
            // If pawnTile's teamColor == currentTile's color
            if (pawnTile.getTileTeam().equals(currentTile.getTileColor()) && isOnDestinationRow(currentTile) && !currentTile.equals(pawnTile)) {
                return true; // There is a path from pawnTile to its destination
            }

            // Get the moves that are possible from the currentTile
            Set<QuoridorTile> possiblePawnMoves = possiblePawnMoves(currentTile);

            // If we have not visited a tile before, then we add them into the queue to visit them
            for (QuoridorTile possibleMove : possiblePawnMoves) {
                if (!visited.contains(possibleMove)) {
                    visited.add(possibleMove);
                    queue.offer(possibleMove);
                }
            }
        }
        // Having arrived here, we have traversed through all possible tiles 
        // from the starting point of pawnTile
        return false;
        // And this means there is no way to travel from the current location to
        // a goal, which SHOULD NOT be possible in a correctly implemented Quoridor
        // game
    }

    // This method currently assumes that there is only destinations for two teams
    // The destination rows are top and bottom row
    // OUTPUT: true, the tile is in one of the destination rows
    //         false, the tile is not in one of the destination rows
    private boolean isOnDestinationRow(QuoridorTile tile) {
        int tileRow = tile.getRow();

        // If tile is in the top row or the bottom row
        if (tileRow == 0 || tileRow == getHeight() - 1) {
            return true;
        }
        return false;
    }

    /*
     * Assuming each team has only one pawn in this game, and will search
     * exhaustively to find the tile where each team's pawn is on
     * 
     * INPUT: colors, an array containing the color of each team
     * 
     * OUTPUT: Set<QuoridorTile>, a set of tiles that each contain a pawn(team)
     */
    private Set<QuoridorTile> getPawnTiles(String[] colors) {
        Set<QuoridorTile> pawnTiles = new HashSet<>();

        // Under the assumption that besides the destination rows, there is only
        // one tile throughout the board with a given color
        for (String color : colors) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    QuoridorTile currentTile = getTile(row, col);

                    if (currentTile.getTileTeam().equals(color)) {
                        pawnTiles.add(currentTile);
                    }
                }
            }
        }
        return pawnTiles;
    }

    /*
     * Given an edge in our implementation is at most shared between two tiles
     * thus, this method will update the counter piece of this other tile, if such an
     * other tile exists
     * 
     * NOTE: this method only updates the piece of the neighborTile, it does not update
     * the piece of the currentTile (so the update for currentTile should be done beforehand)
     * 
     * INPUT: currentTile
     *        piecePosition, ranging from 0-3, represents the edges in the four cardinal directions
     *        color, the color we would like to change for the neighborTile
     * 
     * NO OUTPUT
     */
    private void updateNeighborTile(QuoridorTile currentTile, int piecePosition, String color) {
        // Check if this neighborTile that we want to update is inbound
        int[] dir = getCardinalDir(piecePosition);
        int newRow = currentTile.getRow() + dir[0];
        int newCol = currentTile.getColumn() + dir[1];

        if (areCoordinatesInbound(newRow, newCol)) {
            QuoridorTile neighborTile = getTile(newRow, newCol);

            int counterPiecePosition = Constants.INVALID_OPT;

            // Switch is a bit more elegant than many ifs
            switch (piecePosition) {
                case Constants.NORTHEDGE:
                    counterPiecePosition = Constants.SOUTHEDGE;
                    break;
                case Constants.EASTEDGE:
                    counterPiecePosition = Constants.WESTEDGE;
                    break;
                case Constants.SOUTHEDGE:
                    counterPiecePosition = Constants.NORTHEDGE;
                    break;
                case Constants.WESTEDGE:
                    counterPiecePosition = Constants.EASTEDGE;
                    break;
                default:
                    break;
            }

            // Not to throw our brain out, a safety check
            if (counterPiecePosition == Constants.INVALID_OPT) {
                throw new IllegalArgumentException("Perhaps a piecePosition that is not valid was entered, piecePosition: " + piecePosition);
            }

            // Update the corresponding piece of the neighborTile to also be the same color
            neighborTile.getPieces().get(counterPiecePosition).setColor(color);
        }
        // Else, this neihgborTile is not w/in bounds and thus no need to update its piece
    }

    /*
     * Given one of the four cardinal direction edges, this method returns the 
     * corresponding coordinates to go in that direction
     * 
     * INPUT: edge, refer to Constants
     * 
     * OUTPUT: int[], where the 0th position holds the change in row, the 1st position
     *         holds the change in col
     */
    private int[] getCardinalDir(int edge) {
        int[] dir = {0, 0};
        switch (edge) {
            case Constants.NORTHEDGE:
                dir = new int[]{-1, 0};
                break;
            case Constants.EASTEDGE:
                dir = new int[]{0, 1};
                break;
            case Constants.SOUTHEDGE:
                dir = new int[]{1, 0};
                break;
            case Constants.WESTEDGE:
                dir = new int[]{0, -1};
                break;
            default:
                throw new IllegalArgumentException("Invalid edge: " + edge);
        }
        return dir;
    }

    // OUTPUT: QuoridorTile, or null
    @Override
    public QuoridorTile getTile(int row, int col) {
        return (QuoridorTile) super.getTile(row, col);
    }

    // OUTPUT: QuoridorTile, or null
    @Override
    public QuoridorTile findTileByValue(int value) {
        return (QuoridorTile) super.findTileByValue(value);
    }
}
