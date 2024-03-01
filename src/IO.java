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

        team[0] = queryTeamName(teamNames);   
        team[1] = queryTeamColor(colors);

        displayMessage("Let's get some team member names!");
        for (int i = 1; i <= teamSize; i++) {
            displayMessage("Come forward team player "+ i + ".");
            team[i+1] = queryString("What is your name? ");
        }

        return team;
    }

    private String queryTeamName(List<String> teamNames){
        boolean isGoodTeamName; 
        String name;
        do {
            isGoodTeamName = true; // Optomistic check
            name = queryString("Enter your team name: ");
            if (teamNames.contains(name)) {
                isGoodTeamName = false;
                displayMessage("Team Name is already taken. Try again!");
            }
        } while (!isGoodTeamName);
        return name;
    } 

    // These two are currently seperated since one is checking for contains and one not contains

    private String queryTeamColor(List<String> colors){
        boolean validColor = false;
        String color;
        // Check if the color is within possible colors
        do {
            color = queryString("What is your team color? ").toUpperCase();
            if (colors.contains(color)){
                validColor = true;
            } else {
                displayMessage("Invalid color. Please try again.");
                displayMessage("Here are the valid colors: "+ colors);
            }
        }while(!validColor);

        return color;
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

    protected String queryString(String prompt){
        displayMessage(prompt);
        String input = scanner.nextLine();
        return input;
    }

    protected boolean queryBoolean(String prompt, String opt1, String opt2){
        String input;
        boolean valid = false;
    
        do {
            displayMessage(prompt + "(" + opt1 + "/" + opt2 + "): ");
            input = scanner.nextLine().toLowerCase();
    
            if (input.equals(opt1) || input.equals(opt2)) {
                valid = true;
            } else {
                displayMessage("Invalid input. Please enter " + opt1 + ", " + opt2 + ": ");
            }
    
        } while (!valid);
    
        return input.equals(opt1); // Returns true for first option
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
        displayMessage("3. Quoridor Game");
        displayMessage("4. Quit");
        int choice = queryInt("Enter your choice: ", 1, 4);

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