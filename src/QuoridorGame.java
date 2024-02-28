package src;

public class QuoridorGame extends Game{
    public QuoridorGame(int numTeams, int teamSize, IO io) {
        super(numTeams, teamSize, io);
    }
    @Override
    public void start() {
        io.displayMessage("Welcome to the Dots and Boxes Game!");

        // Prompt the user for the initial board size
        int width = io.queryInt("Enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
        int height = io.queryInt("Enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);

        // Create the initial board
        this.board = new QuoridorBoard(width, height, new int[width][height]);
        boolean quitter = false;

        // GIVEN PLAYER AND TEAM CLASS WILL BE CHANGED
        // PLEASE NOTE: BELOW IS STUFF FROM PREVIOUS, NEEDS TO BE CHANGED

        Team currentTeam = teams[0];
        int currentPlayerIndex = 0;
        Player currentPlayer;

        ////////////////////////////////////////////////////////////////

        do {

            // GIVEN PLAYER AND TEAM CLASS WILL BE CHANGED
            // PLEASE NOTE: BELOW IS STUFF FROM PREVIOUS, NEEDS TO BE CHANGED

            io.displayMessage("Lets start with team " + currentTeam.getTeamName() + "!");
            currentPlayer = currentTeam.getPlayers().get(currentPlayerIndex);
            io.displayMessage("Now it's " + currentPlayer.getName() + "'s turn!");
            
            ////////////////////////////////////////////////////////////////
            
            do {
                // Display the current state of the board
                this.board.display();

                boolean choice = io.queryBoolean("Enter your choice of move, team piece or wall: ", "p", "w");
                if (choice) { // User wants wall
                    int moveTile = io.queryInt("Please enter your choice of tile: ", Constants.QUIT_VALUE, board.MAX_VALUE);
                    if (moveTile == Constants.QUIT_VALUE) {
                        quitter = true; // We have a quitter!
                        break; // Return to either Manager or New Game
                    }
                    int moveDirect = io.queryInt("Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: ", Constants.QUIT_VALUE, 4);
                    if (moveDirect == Constants.QUIT_VALUE) {
                        quitter = true; // We have a quitter again!
                        break; // Return to either Manager or New Game
                    }
                } else {
                    int movePiece = io.queryInt("Enter which tile you want to move your team piece: ", Constants.QUIT_VALUE, board.MAX_VALUE);
                    if (movePiece == Constants.QUIT_VALUE) {
                        quitter = true; // Same quitter as always.
                        break; // Return to either Manager or New Game
                    }
                }

                // PLEASE DO: BELOW IS FOR LOGIC-ING IF MOVE IS POSSIBLE

                boolean[] moveBoolean = board.changePiece(move, currentTeam.getTeamColor());

                if (!moveBoolean[0]) {
                    // If the move is invalid
                    io.displayMessage("Invalid move. Please try again.");
                    continue;
                }

                ////////////////////////////////////////////////////////////////


                // GIVEN PLAYER AND TEAM CLASS WILL BE CHANGED
                // PLEASE NOTE: BELOW IS STUFF FROM PREVIOUS, NEEDS TO BE CHANGED


                currentTeam.setMoves(1); // User successfully made a move

                // Moves tile and checks if the move is valid
                if (moveBoolean[1]) {
                    io.displayMessage("A box was completed successfully! Same team gets another turn.");
                    currentTeam.incTeamNumber(1);
                    if (moveBoolean[2]) {
                        currentTeam.incTeamNumber(1); // A really good move just happened
                    }
                    
                    // Check for win condition
                    if (isWin(board)) {
                        break; // Exit the loop if there's a winner
                    }

                    io.displayMessage("As a reward, it's still " + currentPlayer.getName() + "'s turn!");
                } else {
                    // Normal situation, switch teams display player
                    currentTeam = (currentTeam == teams[0]) ? teams[1] : teams[0];
                    io.displayMessage("Now team " + currentTeam.getTeamName() + "!");
                    
                    if (currentTeam == teams[0]) { // Back to the first team, so iter playerIndex
                        currentPlayerIndex = (currentPlayerIndex + 1) % currentTeam.getPlayers().size();
                    }

                    currentPlayer = currentTeam.getPlayers().get(currentPlayerIndex);
                    io.displayMessage("Now it's " + currentPlayer.getName() + "'s turn!");
                }


                ////////////////////////////////////////////////////////////////
            
            } while (true); // Run indefinitely until there's a winner
    
            if (!quitter){
                board.display(); // Display one last time for user satisfaction
    
                // GIVEN PLAYER AND TEAM CLASS WILL BE CHANGED
                // PLEASE NOTE: BELOW IS STUFF FROM PREVIOUS, NEEDS TO BE CHANGED

                teams[0].setNumGames(1);
                teams[1].setNumGames(1);

                // I WILL DO THE SUSSING OUT WINNER THING, BUT TEAM AND PLAYER STATS
                // ARE HERE AS WELL

                if (teams[0].getTeamNumber() >= teams[1].getTeamNumber()) {
                    if (teams[1].getTeamNumber() == teams[0].getTeamNumber()) { // It was tie
                        io.displayMessage("Game Over! It was a tie!");
                    } else { // Team 0 did best
                        io.displayMessage("Game Over! " + teams[0].getTeamName() + " wins!");
                        teams[0].setWins(1); // Increase team's won the game
                    }
                } else { // Team 1 did best
                    io.displayMessage("Game Over! " + teams[1].getTeamName() + " wins!");
                    teams[1].setWins(1); // Increase team's won the game
                }

                ////////////////////////////////////////////////////////////////

            } else {
                // :D
                io.displayMessage("Did one of the teams rage quit? WHOOPS XD");
            }
    
            // Ask the player if they want to quit or restart
            if (io.queryQuitOrRestart()) {
    
                // Ask if the player wants to change the board size
                if (io.queryChangeBoardSize()) {
                    int newWidth = io.getBoardWidth();
                    int newHeight = io.getBoardHeight();
                    this.board = new QuoridorBoard(newWidth, newHeight, new int[newWidth][newHeight]);
                } else {
                    this.board = new QuoridorBoard(width, height, new int[width][height]);
                }

            } else { // Needs to be deleted later
                io.displayMessage("Thanks for playing Quoridor!");
                // Display stats and the game 


                // PLEASE NOTE: BELOW IS STUFF FROM PREVIOUS, NEEDS TO BE CHANGED

                io.displayMessage("Here is for team " + teams[0].getTeamName() + ":");
                io.displayStats(teams[0].getStats(), teams[0].getPlayers());
                io.displayMessage("Here is for team " + teams[1].getTeamName() + ":");
                io.displayStats(teams[1].getStats(), teams[1].getPlayers());

                ////////////////////////////////////////////////////////////////


                break;
            }
    
        } while (true); // Infinite loop for restarting the game  
    }

    @Override
    protected boolean isWin(Board board) {
        Tile[][] tiles = board.getTiles(); // Getter for tiles on board
    
        for (int i=0; i<tiles.length; i++) {
            if (tiles[tiles.length-1][i].isOccupied() && (tiles[tiles.length-1][i].getPieces().get(4).getColor() == teams[1].getTeamColor())){
                return true; // First Team got to bottom of the board
            }
            if (tiles[board.MIN_VALUE][i].isOccupied() && (tiles[board.MIN_VALUE][i].getPieces().get(4).getColor() == teams[0].getTeamColor())){
                return true; // Second Team got to top of the board
            }
        }

        return false;
    }
}
