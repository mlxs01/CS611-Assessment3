package src;

/**
 * Player.java
 * Michelle L Sun 
 * 2/19/24
 * 
 * Represents a player in a given team.
 * Manages player information, right now being only name.
 */
public class Player {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    //Getters
    public String getName() {
        return name;
    }

}
