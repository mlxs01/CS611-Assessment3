package src;

import java.util.*;

/**
 * Team.java
 * Michelle L Sun
 * 2/19/2024
 * 
 * Represents a team in the game, including its name, color, players, and statistics.
 * This class manages the team's information such as number of wins, moves, and games played.
 */
public class Team {
    private String teamName;
    private String teamColor;
    private int teamNumber; // Can score number of "wins" or "points" or any other team value
    private List<Player> players;
    private int moves;
    private int wins;
    private int numGames;

    /**
     * Constructs a Team object with the specified team name, color, and inits a player list.
     * 
     * "teamName":  the name of the team
     * "teamColor": the color of the team
     */
    public Team(String teamName, String teamColor) {
        setName(teamName);
        setColor(teamColor);
        this.players = new ArrayList<Player>();
    }

    private void setName(String teamName){
        this.teamName = teamName;
    }

    private void setColor(String teamColor){
        this.teamColor = teamColor;
    }

    public void incTeamNumber(int inc) {
        this.teamNumber += inc;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public int[] getStats() {
        return calcStats(wins, moves, numGames);
    }

    //Setters
    public void setMoves(int inc) {
        this.moves += inc;
    }

    public void setWins(int inc) {
        this.wins += inc;
    }

    public void setNumGames(int inc) {
        this.numGames += inc;
    }

    // Method to calculate the stats of the player
    private int[] calcStats(int wins, int moves, int numGames) {
        // Nothing here for now because previous code
        // doesn't make sense anymore
        return new int[]{wins, moves, numGames};
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColor() {
        return teamColor;
    }
}
