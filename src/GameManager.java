package src;

/**
 * GameManager.java
 * Michelle L Sun
 * 2/17/2024
 * 
 * Represents the game manager responsible for controlling the flow to different games within the program.
 * Handles game initialization, user interactions, and termination of the program.
 */
public class GameManager {
    public static IO io = new IO();
    private Factory factory = new Factory();

    /**
     * Constructs a GameManager object.
     * Initializes IO and starts the game selection loop.
     */
    public GameManager() {
        io.displayMessage("Welcome!");

        while (true) {
            // Display menu and call to make selected game
            int selectedGame = io.displayMenu();
            Game game = factory.makeGame(selectedGame, io);
            
            if (game != null) {
                // Start selected game
                game.start();

            } else {
                break;  // Complete exit the program
            }
        }

        io.displayMessage("Thanks for playing! Goodbye!");
        io.closeScanner();
    }
}
