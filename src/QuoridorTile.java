package src;

import java.util.List;

public class QuoridorTile extends Tile {
    public QuoridorTile(int row, int column) {
        super(row, column);
    }

    // If a pawn is on this tile
    // Where the TEAMPIECE is pieces[5]
    @Override
    public boolean isOccupied() {
        if (super.getPieces().get(Constants.TEAMPIECE).getColor() == "None"){
            return false;
        } 
        return true;
    }

    // There could only be a fence on indexes 0, 1, 2, 3
    // Edges by default is "None"
    public boolean hasFence(int pieceIndex) {
        return !getPieces().get(pieceIndex).getColor().equals("None");
    }

    // Pieces[4] represents COLOR of the tile
    public String getTileColor() {
        return getPieces().get(Constants.COLORPIECE).getColor();
    }

    // Pieces[5] represents TEAM(color) of the tile
    public String getTileTeam() {
        return getPieces().get(Constants.TEAMPIECE).getColor();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        QuoridorTile other = (QuoridorTile) obj;
        // Will use the above if there are other properties that belong only to 
        // QuoridorTile that we will like to compare

        return super.equals(obj);
    }

    @Override
    public String toString() {
        List<Piece> pieces = getPieces();
        return "This tile is at (" + getRow() + ", " + getColumn() + ")" +
                ", northEdge: " + pieces.get(Constants.NORTHEDGE) +
                ", eastEdge: " + pieces.get(Constants.EASTEDGE) +
                ", southEdge: " + pieces.get(Constants.SOUTHEDGE) +
                ", westEdge: " + pieces.get(Constants.WESTEDGE) +
                ", colorPiece: " + pieces.get(Constants.COLORPIECE) +
                ", teamPiece: " + pieces.get(Constants.TEAMPIECE);
    }
}
