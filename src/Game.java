package src;

import java.util.*;

/**
 * Game.java
 * Michelle L Sun 
 * 2/21/24
 * 
 * Represents the Abstract class Game.
 * Manages the game flow including setup, gameplay, and termination.
 * This class coordinates interactions between the board, player, input/output, and puzzle generation.
 */
public abstract class Game {
    protected IO io;
    protected Board board;
    protected Team[] teams;

    /**
     * Constructs a Game object, initializing necessary components (IO, Generate, Player).
     * Prompts the user for their name and initializes a new player.
     * Displays a welcome message.
     */
    public Game(int numTeams, int teamSize, IO io) {
        this.io = io;
        initalizeTeams(numTeams, teamSize);
    }

    // Abstract method for starting the game
    public abstract void start();

    protected abstract boolean gameLoop(Team currentTeam, int currentPlayerIndex);

    // Abstract method to check for win condition
    protected abstract boolean isWin(Board board);

    /**
     * Initializes teams for the game.
     * 
     * "numTeams": the number of teams
     * "teamSize": the size of each team
     */
    protected void initalizeTeams(int numTeams, int teamSize){
        io.displayMessage("Time to make some team(s)!");

        teams = new Team[numTeams];

        // Lists to enforce unique teams
        List<String> teamNames = new ArrayList<>();
        Colors color = new Colors();
        List<String> possibleColors = new ArrayList<>(color.getPossibleColors());

        for (int i = 1; i <= numTeams; i++){ // Each loop of this is one team initiated
            io.displayMessage("Come up team "+ i + "!");
            String[] teamInfo = io.getTeamInfo(teamSize, possibleColors, teamNames); 

            teamNames.add(teamInfo[0]);
            // Should return team name, color and player names
            // 0 = name, 1 = color, rest is player names
            teams[i-1] = new Team(teamInfo[0], teamInfo[1]);
            possibleColors.remove(teamInfo[1]);
            
            for (int j = 2; j < teamInfo.length; j++){
                Player player = new Player(teamInfo[j]); // Makes all players
                teams[i-1].getPlayers().add(player);
            }
        }

    }

}

