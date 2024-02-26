package src;

public class QuoridorGame extends Game{
    public QuoridorGame(int numTeams, int teamSize, IO io) {
        super(numTeams, teamSize, io);
    }
    @Override
    public void start() {

    }

    @Override
    protected boolean isWin(Board board) {
        return false;
    }
}
