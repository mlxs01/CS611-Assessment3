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
        System.out.println("There is something very wrong with isWin check.");
        return true;
    }
    
    /**
     * Starts the Sliding Window Game.
     * Manages the game loop, prompting user for moves, checking for completion, and handling game restarts.
     */
    @Override
    public void start() {
        io.displayMessage("Welcome to the Sliding Window Game! Let's begin.");

        // Prompt the user for the initial board size
        int width = io.getBoardWidth();
        int height = io.getBoardHeight();

        // Create the initial board
        this.board = new SliderBoard(width, height, generate.generatePuzzle(width, height));
        io.displayMessage("Sorry, no color implementatoin for Slider right now.");
        boolean quitter = false;

        do {
            do {
                // Display the current state of the board
                this.board.display();
    
                // Ask the player for a move
                int move = io.getPieceMove();

                if (move == -1) {
                    quitter = true; // We have a quitter!
                    break; // Return to either Mangaer or New Game
                }
    
                // Moves tile and checks if the move is valid
                if (!board.changePiece(move, "None")[0]) {
                    io.displayMessage("Invalid move. Please try again.");
                } else {
                    teams[0].setMoves(1); // User successfully made a move
                }
    
            } while (!isWin(board));
            
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
            if (io.queryQuitOrRestart()) {
    
                // Ask if the player wants to change the board size
                if (io.queryChangeBoardSize()) {
                    int newWidth = io.getBoardWidth();
                    int newHeight = io.getBoardHeight();
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

}
