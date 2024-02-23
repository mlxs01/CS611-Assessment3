package src;

import java.util.*;
/**
 * Color.java
 * Michelle L Sun 
 * 2/19/24
 * 
 * Colors class provides ANSI escape codes for text colors and methods to retrieve color codes.
 */
public class Colors {
    // ANSI escape codes for text colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    
    public List<String> getPossibleColors() {
        return Arrays.asList("RED", "GREEN", "BLUE", "PURPLE");
    }

    public String getColor(String colorName) {
        switch (colorName) {
            case "RED":
                return ANSI_RED;
            case "GREEN":
                return ANSI_GREEN;
            case "BLUE":
                return ANSI_BLUE;
            case "PURPLE":
                return ANSI_PURPLE;
            default:
                return ANSI_RESET; // Default color
        }
    }
    
}
