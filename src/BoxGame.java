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
        int width = io.getBoardWidth();
        int height = io.getBoardHeight();

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
                int move = io.getPieceMove(); // Assuming getMove() returns the position of a line

                if (move == -1) {
                    quitter = true; // We have a quitter!
                    break; // Return to either Mangaer or New Game
                }

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
                io.displayMessage("Did one of the teams rage quit? WHOOPS XD");
            }
    
            // Ask the player if they want to quit or restart
            if (io.queryQuitOrRestart()) {
    
                // Ask if the player wants to change the board size
                if (io.queryChangeBoardSize()) {
                    int newWidth = io.getBoardWidth();
                    int newHeight = io.getBoardHeight();
                    this.board = new BoxBoard(newWidth, newHeight, new int[newWidth][newHeight]);
                } else {
                    // Generate board with puzzle of same dimensions
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
