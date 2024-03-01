package src;

/*
 * Factory Class
 * Michelle L Sun
 * 2/21/2024
 * 
 * This class provides a factory method to init different games based on user input.
 */
public class Factory {

    /**
     * Instantiates the selected game based on the user's input.
     * 
     * "selectedGame": the index of the selected game
     * "io": the general IO object
     * Returns the instantiated game object
     */
    public Game makeGame(int selectedGame, IO io) {
        int teamSize;
        switch (selectedGame) {
            case 1:
                return new SliderGame(1, 1, io);
            case 2:
                teamSize = io.queryInt("How many team members would you like to have? You can have at most 5.", Constants.TEAM_MIN_SIZE, Constants.TEAM_MAX_SIZE);
                return new BoxGame(2, teamSize, io);
            case 3:
                teamSize = io.queryInt("How many team members would you like to have? You can have at most 5.", Constants.TEAM_MIN_SIZE, Constants.TEAM_MAX_SIZE);
                return new QuoridorGame(2, teamSize, io);
            default:
                return null;
        }
    }
}
