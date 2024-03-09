package src;

public class QuoridorGame extends Game{
    public QuoridorGame(int numTeams, int teamSize, IO io) {
        super(numTeams, teamSize, io);
    }

    @Override
    public void start() {
        io.displayMessage("Welcome to the Quoridor Game!");

        // Prompt the user for the initial board size
        int width = io.queryInt("Enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
        int height = io.queryInt("Enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);

        // Create the initial board
        String teamsColors[] = getTeamColors();
        this.board = new QuoridorBoard(width, height, new int[width][height], teamsColors);

        Team currentTeam = teams[0];
        int currentPlayerIndex = 0;

        do {
            // Before anything, ask each team to pick a position on the board for their corresponding player pieces to start at
            for (int i = 0; i < teams.length; i++) {
                int startPoint = io.queryInt("Enter on which column your player piece starts: (" +
                Constants.MIN_X + ", " + (width-1) + ")", Constants.MIN_X, width-1);
                // Say pieceValue is the tile number
                System.out.println("this should be tile row: " + (height-1)*i + " and tile col: " + startPoint);
                initDestinationRowColor((height-1)*(teams.length - 1 - i), teams[i].getTeamColor());
                board.getTile((height-1)*i, startPoint).getPieces().get(Constants.TEAMPIECE).setColor(teams[i].getTeamColor());
            }

            boolean quitter = gameLoop(currentTeam, currentPlayerIndex);

            if (quitter){
                // :D
                io.displayMessage("Did one of the teams rage quit? WHOOPS XD");
            }
    
            // Ask the player if they want to quit or restart
            if (io.queryBoolean("Do you want to restart or quit?", "r", "q")) {
    
                // Ask if the player wants to change the board size
                if (io.queryBoolean("Do you want change board size?", "y", "n")) {
                    int newWidth = io.queryInt("Enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
                    int newHeight = io.queryInt("Enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);            
                    this.board = new QuoridorBoard(newWidth, newHeight, new int[newWidth][newHeight], teamsColors);
                } else {
                    this.board = new QuoridorBoard(width, height, new int[width][height], teamsColors);
                }

            } else { // Needs to be deleted later
                io.displayMessage("Thanks for playing Quoridor!");
                // Display stats and the game 
                io.displayMessage("Here is for team " + teams[0].getTeamName() + ":");
                io.displayStats(teams[0].getStats(), teams[0].getPlayers());
                io.displayMessage("Here is for team " + teams[1].getTeamName() + ":");
                io.displayStats(teams[1].getStats(), teams[1].getPlayers());

                break;
            }
    
        } while (true); // Infinite loop for restarting the game  
    }

    @Override
    protected boolean gameLoop(Team currentTeam, int currentPlayerIndex){ // True if quitter, false otherwise
        boolean quitter = false;
        Player currentPlayer;

        io.displayMessage("Lets start with team " + currentTeam.getTeamName() + "!");
        currentPlayer = currentTeam.getPlayers().get(currentPlayerIndex);
        io.displayMessage("Now it's "+ currentTeam.getTeamColor() +  " Team Player " 
        + currentPlayer.getName() + "'s turn!");

        do {

            // Display the current state of the board
            ((QuoridorBoard) this.board).display(currentTeam.getTeamColor());
            // this.board.display(currentTeam.getTeamColor());

            boolean[] moveBoolean;
            boolean choice = io.queryBoolean("Enter your choice of move, team piece or wall: ", "p", "w");
            if (!choice) { // User wants wall
                int moveTile = io.queryInt("Please enter your choice of tile: ", Constants.QUIT_VALUE, board.MAX_VALUE);
                if (moveTile == Constants.QUIT_VALUE) {
                    quitter = true; // We have a quitter!
                    break; // Return to either Manager or New Game
                }
                int wallDir = io.queryInt("Enter the direction you want your wall to face (north=0, east=1, south=2, west=3) or enter -1 to quit: ", Constants.QUIT_VALUE, 4);
                if (wallDir == Constants.QUIT_VALUE) {
                    quitter = true; // We have a quitter again!
                    break; // Return to either Manager or New Game
                }
                int wallExtendDir = io.queryInt("Enter the direction you want your wall to extend (north=0, east=1, south=2, west=3) or enter -1 to quit: ", Constants.QUIT_VALUE, 4);
                if (wallExtendDir == Constants.QUIT_VALUE) {
                    quitter = true; // We have a quitter again!
                    break; // Return to either Manager or New Game
                }


                moveBoolean = ((QuoridorBoard) board).changePiece(moveTile, wallDir, wallExtendDir);
                // moveBoolean = board.changePiece(move, currentTeam.getTeamColor());

                if (!moveBoolean[0]) {
                    // If the move is invalid
                    io.displayMessage("Invalid move. Please try again.");
                    continue;
                }

            } else { // User wants team piece
                int moveTile = io.queryInt("Enter which tile you want to move your team piece: ", Constants.QUIT_VALUE, board.MAX_VALUE);
                if (moveTile == Constants.QUIT_VALUE) {
                    quitter = true; // Same quitter as always.
                    break; // Return to either Manager or New Game
                }

                moveBoolean = board.changePiece(moveTile, currentTeam.getTeamColor());

                if (!moveBoolean[0]) {
                    // If the move is invalid
                    io.displayMessage("Invalid move. Please try again.");
                    continue;
                }

            }

            currentTeam.setMoves(1); // User successfully made a move
                
            // Check for win condition
            if (isWin(board)) {
                this.board.display(); // Display one last time for user satisfaction
                // currentTeam reached the end
                io.displayMessage("Game Over! " + currentTeam.getTeamName() + " wins!");
                currentTeam.setWins(1); // Increase team's wins
                break; // Exit the loop if there's a winner
            }

            // Switch teams display player
            currentTeam = (currentTeam == teams[0]) ? teams[1] : teams[0];
            io.displayMessage("Now team " + currentTeam.getTeamName() + "!");
            
            if (currentTeam == teams[0]) { // Back to the first team, so iter playerIndex
                currentPlayerIndex = (currentPlayerIndex + 1) % currentTeam.getPlayers().size();
            }

            currentPlayer = currentTeam.getPlayers().get(currentPlayerIndex);
            io.displayMessage("Now it's " + currentPlayer.getName() + "'s turn!");
    
        } while (true); // Run indefinitely until there's a winner
        return quitter;
    }

    private void initDestinationRowColor(int row, String teamColor) {
        for (int i = 0; i < board.getWidth(); i++) {
            board.getTile(row, i).getPieces().get(Constants.COLORPIECE).setColor(teamColor);
        }
    }

    private String[] getTeamColors(){
        String teamColors[] = new String[Constants.TEAM_MAX_SIZE]; // Max amount of team colors
        for (int i = 0; i < teams.length; i++){
            teamColors[i] = teams[i].getTeamColor();
        }
        return teamColors;
    }

    @Override
    protected boolean isWin(Board board) {
        Tile[][] tiles = board.getTiles(); // Getter for tiles on board
    
        // teams[0].getTeamColor()
        for (int i=0; i<tiles.length; i++) {
            if (tiles[tiles.length-1][i].isOccupied() &&
             (tiles[tiles.length-1][i].getPieces().get(Constants.COLORPIECE).getColor()
             .equals(tiles[tiles.length-1][i].getPieces().get(Constants.TEAMPIECE).getColor()))){
                return true; // First Team got to bottom of the board
            }
            if (tiles[board.MIN_VALUE][i].isOccupied() && 
             (tiles[board.MIN_VALUE][i].getPieces().get(Constants.COLORPIECE).getColor()
             .equals(tiles[board.MIN_VALUE][i].getPieces().get(Constants.TEAMPIECE).getColor()))){
                return true; // Second Team got to top of the board
            }
        }

        return false;
    }
}
