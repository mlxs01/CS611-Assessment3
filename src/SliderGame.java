package src;

/**
 * Game.java
 * Michelle L Sun 
 * 2/19/24
 * 
 * Represents the Sliding Window Game.
 * Manages the game flow including setup, gameplay, and termination.
 * This class coordinates interactions between the board, player, input/output, and puzzle generation.
 */
public class SliderGame extends Game{
    private Generate generate;

    /**
     * Constructs a Game object, initializing necessary components (IO, Generate, Player).
     * Prompts the user for their name and initializes a new player.
     * Displays a welcome message.
     */
    public SliderGame(int numTeams, int teamSize, IO io) {
        super(numTeams, teamSize, io);
        this.generate = new Generate();
        //this.player = new Player(io.getPlayerName());
    }
    
    /**
     * Starts the Sliding Window Game.
     * Manages the game loop, prompting user for moves, checking for completion, and handling game restarts.
     */
    @Override
    public void start() {
        io.displayMessage("Welcome to the Sliding Window Game! Let's begin.");

        // Prompt the user for the initial board size
        int width = io.queryInt("Please enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
        int height = io.queryInt("Please enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);

        // Create the initial board
        this.board = new SliderBoard(width, height, generate.generatePuzzle(width, height));
        io.displayMessage("Sorry, no color implementatoin for Slider right now.");

        do {

            boolean quitter = gameLoop(teams[0], 0);
            if (!quitter){
            board.display(); // Display one last time for user satisfaction

            teams[0].setNumGames(1);
            
            io.displayMessage("Congratulations, " + teams[0].getTeamName() + "! You solved the puzzle!");
            teams[0].setWins(1); // User won the game
            } else {
                // :D
                io.displayMessage("We have a quitter :D");
            }

            // Ask the player if they want to quit or restart
            if (io.queryBoolean("Do you want to restart or quit?", "r", "q")) {
    
                // Ask if the player wants to change the board size
                if (io.queryBoolean("Do you want change board size?", "y", "n")) {
                    int newWidth = io.queryInt("Please enter the board width (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);
                    int newHeight = io.queryInt("Please enter the board height (max 9, min 2): ", Constants.BOARD_MIN_SIZE, Constants.BOARD_MAX_SIZE);            
                    board = new SliderBoard(newWidth, newHeight, generate.generatePuzzle(newWidth, newHeight));
                } else {
                    // Generate board with puzzle of same dimensions
                    this.board = new SliderBoard(width, height, generate.generatePuzzle(width, height));
                }
            } else { 
                io.displayMessage("Thanks for playing Sliding Window Game! Here were the stats for this game session: ");
                // Display stats and the game
                io.displayStats(teams[0].getStats(), teams[0].getPlayers());
                // Going back to the Menu
                break;
            }
    
        } while (true); // Infinite loop for restarting the game    
    }

    @Override
    protected boolean gameLoop(Team currentTeam, int currentPlayerIndex){ // True if quitter, false otherwise
        boolean quitter = false;
        do {
            // Display the current state of the board
            this.board.display();

            // Ask the player for a move
            int move = io.queryInt("Please enter the tile you want to move: ", Constants.QUIT_VALUE, board.MAX_VALUE);
            if (move == Constants.QUIT_VALUE) {
                io.displayMessage("Quitting Game... ");
                quitter = true; // We have a quitter!
                break; // Return to either Manager or New Game
            }

            // Moves tile and checks if the move is valid
            if (!board.changePiece(move, "None")[0]) {
                io.displayMessage("Invalid move. Please try again.");
            } else {
                currentTeam.setMoves(1); // User successfully made a move
            }

        } while (!isWin(board));

        return quitter;
    }

    @Override
    protected boolean isWin(Board board) {
        Tile[][] tiles = board.getTiles(); // Getter for tiles on board
    
        int correctValue = 1;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                if (correctValue == (tiles.length * tiles[0].length)) { // Got to the end with no error, hence true.
                    return true;
                }

                Piece piece = tiles[row][col].getPieces().get(0);
                if (piece.getValue() != correctValue) {
                    // Piece value doesn't match the expected value
                    return false;
                }
                
                correctValue++;
            }
        }
        return true;
    }

}
