package src;

/**
 * BoxGame.java
 * Michelle L Sun
 * 2/21/2024
 * 
 * Represents the game logic and flow for the Dots and Boxes game.
 * Inherits from the abstract class Game and implements the specific behavior for starting, playing,
 * and determining the outcome of the Dots and Boxes game.
 */
public class BoxGame extends Game {

    public BoxGame(int numTeams, int teamSize, IO io) {
        super(numTeams, teamSize, io);
    }

    @Override
    public void start() {
        io.displayMessage("Welcome to the Dots and Boxes Game!");

        // Prompt the user for the initial board size
        int width = io.queryInt("Please enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
        int height = io.queryInt("Please enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);

        // Create the initial board
        this.board = new BoxBoard(width, height, new int[width][height]);
        boolean quitter = false;
        Team currentTeam = teams[0];
        int currentPlayerIndex = 0;
        Player currentPlayer;


        do {
            io.displayMessage("Lets start with team " + currentTeam.getTeamName() + "!");
            currentPlayer = currentTeam.getPlayers().get(currentPlayerIndex);
            io.displayMessage("Now it's " + currentPlayer.getName() + "'s turn!");
            do {
                // Display the current state of the board
                this.board.display();

                // Ask the player for a move
                int moveTile = io.queryInt("Please enter your choice of tile: ", Constants.QUIT_VALUE, board.MAX_VALUE);
                if (moveTile == Constants.QUIT_VALUE) {
                    io.displayMessage("Quitting Game... ");
                    quitter = true; // We have a quitter!
                    break; // Return to either Manager or New Game
                }
                int moveDirect = io.queryInt("Please enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: ", Constants.QUIT_VALUE, 4);
                if (moveDirect == Constants.QUIT_VALUE) {
                    io.displayMessage("Quitting Game... ");
                    quitter = true; // We have a quitter again!
                    break; // Return to either Manager or New Game
                }

                int move = ((moveTile * 4) + moveDirect);

                // Check if the move is valid and completes a box
                boolean[] moveBoolean = board.changePiece(move, currentTeam.getTeamColor());

                if (!moveBoolean[0]) {
                    // If the move is invalid
                    io.displayMessage("Invalid move. Please try again.");
                    continue;
                }

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
            
            } while (true); // Run indefinitely until there's a winner
    
            if (!quitter){
                board.display(); // Display one last time for user satisfaction
    
                teams[0].setNumGames(1);
                teams[1].setNumGames(1);

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

            } else {
                // :D
                io.displayMessage("Did one of the teams rage quit?");
            }
    
            // Ask the player if they want to quit or restart
            if (io.queryBoolean("Do you want to restart or quit?", "r'", "q")) {
    
                // Ask if the player wants to change the board size
                if (io.queryBoolean("Do you want change board size?", "y", "n")) {
                    int newWidth = io.queryInt("Enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
                    int newHeight = io.queryInt("Enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);            
                    this.board = new BoxBoard(newWidth, newHeight, new int[newWidth][newHeight]);
                } else {
                    this.board = new BoxBoard(width, height, new int[width][height]);
                }

            } else { // Needs to be deleted later
                io.displayMessage("Thanks for playing Dots and Boxes!");
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
    protected boolean isWin(Board board){
        Tile[][] tiles = board.getTiles(); // Getter for tiles on board
    
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if(!tiles[row][col].isOccupied()){
                    return false;
                }
            }
        }
        
        return true;
    }
}
