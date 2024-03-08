package src;

/**
 * Piece.java
 * Michelle L Sun 
 * 2/16/24
 * 
 * Represents a piece in a Game.
 * Each piece has a value and color associated with it.
 */
public class Piece {
    private int value;
    private String color;

    /**
     * Constructs a Piece object with the specified value and color.
     * 
     * 'value' : Value of the piece.
     * 'color' : Color of the piece.
     */

    public Piece(int value, String color) {
        setValue(value);
        setColor(color);
    }

    // Getters and Setters
    public int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return getColor() + "";
    }
}
