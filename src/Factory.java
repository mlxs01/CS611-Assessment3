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
        switch (selectedGame) {
            case 1:
                return new SliderGame(1, 1, io);
            case 2:
                IO boxIO = new BoxIO(); 
                // For Dots and Boxes the IO needs to be modified slightly
                int teamSize = io.getTeamSize();
                return new BoxGame(2, teamSize, boxIO);
            default:
                return null;
        }
    }
}
