package src;
import java.util.*;

/**
 * IO.java
 * Michelle L Sun
 * 2/7/24
 * 
 * Handles input/output operations for all parts of program.
 * This class provides methods for prompting the user for input, displaying messages,
 * and retrieving integer values within specified ranges.
 */
public class IO {
    protected static final Scanner scanner = new Scanner(System.in); // So there no multiple instances of scanners
    public static final int BOARD_MAX_SIZE = 9;
    public static final int BOARD_MIN_SIZE = 2;

    /**
     * Retrieves team information including team name, color, and member names.
     * 
     * "teamSize":   the size of the team
     * "colors":     a list of possible team colors
     * "teamNames":  a list of possible team names
     * Returns an array containing team information
     */
    public String[] getTeamInfo(int teamSize, List<String> colors, List<String> teamNames){
        String [] team = new String[teamSize+2];

        team[0] = getTeamName(teamNames);

        boolean validColor = false;

        // Check if the color is within possible colors
        while(!validColor) {
            String color = getColor();
            if (colors.contains(color)){
                validColor = true;
                team[1] = color;
            } else {
                displayMessage("Invalid color. Please try again.");
                displayMessage("Here are the valid colors: "+ colors);
            }
        }
        displayMessage("Let's get some team member names!");
        for (int i = 1; i <= teamSize; i++) {
            displayMessage("Come forward team player "+ i + ".");
            team[i+1] = getUserName();
        }

        return team;
    }

    private String getTeamName(List<String> teamNames){
        boolean isGoodTeamName; 
        String name;
        do {
            isGoodTeamName = true; // Optomistic check
            displayMessage("Enter your team name: ");
            name = scanner.nextLine();
            if (teamNames.contains(name)) {
                isGoodTeamName = false;
                System.out.println(teamNames.indexOf(name));
                displayMessage("Team Name is already taken. Try again!");
            }
        } while (!isGoodTeamName);
        return name;
    }

    public int getTeamSize() {
        boolean isGoodTeamSize = false;
        int teamSize;
        do {
            isGoodTeamSize = true; // Optomistic check
            displayMessage("How many team members would you like to have? You can have at most 5.");
            teamSize = queryInt("Enter the number of team members: ", 1, 5);
            if (teamSize < 1) {
                isGoodTeamSize = false;
                displayMessage("Please enter a number greater than 0 or less than 6.");
            }
        } while (!isGoodTeamSize);
        return teamSize;
    }

    private String getColor(){
        displayMessage("Enter your team color: ");
        String color = scanner.nextLine().toUpperCase();
        return color;
    }

    public String getUserName() { // Why did I change the method name? Because. :D
        // Get player name from user
        displayMessage("Enter your name: ");
        return scanner.nextLine(); 
        // Not sanitized, since it might be a gamer-tag
    }
    
    public int getBoardWidth() {
        // Get board width from user
        return queryInt("Enter the board width (max 9, min 2): ", BOARD_MIN_SIZE, BOARD_MAX_SIZE);
    }
    
    public int getBoardHeight() {
        // Get board height from user
        return queryInt("Enter the board height (max 9, min 2): ", BOARD_MIN_SIZE, BOARD_MAX_SIZE);
    }
    
    public int getPieceMove() {
        int quitValue = -1; // Value to signify quitting
        int value = queryInt("Enter the piece would you like to move or enter " + quitValue + " to quit: ", -1, Integer.MAX_VALUE); // Allow positive integers only

        if (value == quitValue) {
            displayMessage("Quitting game...");
            return quitValue; // Return the quitValue to indicate quitting
        }

        return value;
    }   

    /**
     * Prompts the user for an integer input within the specified range.
     * 
     * 'prompt' : The message to display as a prompt.
     * 'min' : The minimum value allowed.
     * 'max' : The maximum value allowed.
     * Returns the integer input by the user.
     */
    protected int queryInt(String prompt, int min, int max) {
        int value = -1;
        boolean valid = false;
    
        do {
            displayMessage(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value < min || value > max) {
                    throw new NumberFormatException();
                }
                valid = true;
            } catch (NumberFormatException e) {
                displayMessage("Invalid input. ");
            }
        } while (!valid);
    
        return value;
    }

    public boolean queryQuitOrRestart() { // Ask user if they want to quit or restart
        String input;
        boolean valid = false;
    
        do {
            displayMessage("Do you want to quit or restart? (q/r): ");
            input = scanner.nextLine().toLowerCase();
    
            if (input.equals("q") || input.equals("r")) {
                valid = true;
            } else {
                displayMessage("Invalid input. Please enter 'q' to quit or 'r' to restart: ");
            }
    
        } while (!valid);
    
        return input.equals("r"); // returns true if restart, false if quit
    }

    public boolean queryChangeBoardSize() { // Ask user if they want to change board size
        String input;
        boolean valid = false;
    
        do {
            displayMessage("Do you want change board size? (y/n): ");
            input = scanner.nextLine().toLowerCase();
    
            if (input.equals("y") || input.equals("n")) {
                valid = true;
            } else {
                displayMessage("Invalid input. Please enter 'y' to quit or 'n' to restart: ");
            }
    
        } while (!valid);
    
        return input.equals("y"); // Returns true if yes, false if no
    }
    
    // Used to close the scanner before leaving the program
    public void closeScanner() {
        scanner.close();
    }    

    public void displayMessage(String message) {
        // Display messages to the user
        System.out.println("[>] " + message);
    }

    public int displayMenu(){

        displayMessage("Select a game:");
        displayMessage("1. Slider Game");
        displayMessage("2. Box Game");
        displayMessage("3. Quit");
        int choice = queryInt("Enter your choice: ", 1, 3);

        return choice;
    }
    

    public void displayStats(int[] stats, List<Player> players) {
        String[] statLabels = {"Wins", "Total Moves", "Total Games", "Players"};
        for (int i = 0; i < statLabels.length; i++) {
            if (i < 3){
                displayMessage(statLabels[i] + ": " + stats[i]);
            } else {
                displayMessage(statLabels[i] + ": ");
                for (int j = 0; j < players.size(); j++) {
                    String player = players.get(j).getName();
                    System.out.print(player + "  ");
                }
                System.out.println(""); // For New Line
            }
        }
    }
    
}