package src;

public class QuoridorTile extends Tile {
    public QuoridorTile(int row, int column) {
        super(row, column);
    }

    @Override
    public boolean isOccupied() {
        if (super.getPieces().get(4).getColor() == "None"){
            return false;
        } 
        return true;
    }
}
