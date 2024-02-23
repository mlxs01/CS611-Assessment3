package src;
import java.util.*;

/**
 * Generate.java
 * Michelle L Sun
 * 2/7/24
 * 
 * Represents a puzzle generator for the Sliding Window Game.
 * Generates solvable puzzles of specified dimensions.
 */
public class Generate {

    /**
     * Generates a puzzle of the specified width and height.
     *
     * 'width' : The width of the puzzle.
     * "height" : The height of the puzzle.
     * Returns a 2D array representing the generated puzzle.
     */
    public int[][] generatePuzzle(int width, int height) {
        int[][] puzzle;
        List<Integer> flatPuzzle;
        int size = width * height;
    
        do {
            flatPuzzle = getShuffledValues(size);
            puzzle = new int[height][width];
    
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    puzzle[i][j] = flatPuzzle.get(i * width + j);
                }
            }
        } while (!isSolvable(flatPuzzle));
        
        return puzzle;
    }    

    private List<Integer> getShuffledValues(int size) {
        List<Integer> values;
    
        do {
            values = new ArrayList<>();
            for (int i = 1; i < size; i++) {
                values.add(i);
            }
            Collections.shuffle(values); // Shuffle the values via Collections class
    
            // Makes sure 0 is at the end
            values.add(0);
        } while (isConsecutiveAscending(values));
    
        return values;
    }
    
    private boolean isConsecutiveAscending(List<Integer> values) {
        // In low size cases, there is much higher chance that the puzzle is solvable by default
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isSolvable(List<Integer> puzzle) {
        int inversionCount = 0;
        for (int i = 0; i < puzzle.size() - 1; i++) {
            for (int j = i + 1; j < puzzle.size(); j++) {
                int valI = puzzle.get(i);
                int valJ = puzzle.get(j);
    
                // Skip the blank space (0)
                if (valI == 0 || valJ == 0) {
                    continue;
                }
    
                if (valI > valJ) {
                    inversionCount++;
                }
            }
        }
        return inversionCount % 2 == 0;
    }
    
}
