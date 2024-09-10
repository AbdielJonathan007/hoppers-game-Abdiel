import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FrogArrangement {
    // Immutable map defining valid hop options.
    // The map maps a starting position to a map of possible hops where:
    //   - the key is the position being jumped over,
    //   - and the value is the landing position.
    static Map<int[], Map<int[], int[]>> hopOptions = Collections.unmodifiableMap(new HashMap<int[], Map<int[], int[]>>() {{
        put(new int[]{0, 0}, new HashMap<int[], int[]>() {
            {
                put(new int[]{0, 2}, new int[]{0, 4});
                put(new int[]{2, 0}, new int[]{4, 0});
                put(new int[]{1, 1}, new int[]{2, 2});
            }
        });
        put(new int[]{0, 2}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{4, 2});
                put(new int[]{1, 3}, new int[]{2, 4});
                put(new int[]{1, 1}, new int[]{2, 0});
            }
        });
        put(new int[]{0, 4}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 4}, new int[]{4, 4});
                put(new int[]{1, 3}, new int[]{2, 2});
                put(new int[]{0, 2}, new int[]{0, 0});
            }
        });
        put(new int[]{1, 1}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{3, 3});
            }
        });
        put(new int[]{1, 3}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{3, 1});
            }
        });
        put(new int[]{2, 0}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{2, 4});
                put(new int[]{1, 1}, new int[]{0, 2});
                put(new int[]{3, 1}, new int[]{4, 2});
            }
        });
        put(new int[]{2, 2}, new HashMap<int[], int[]>() {
            {
                put(new int[]{1, 1}, new int[]{0, 0});
                put(new int[]{1, 3}, new int[]{0, 4});
                put(new int[]{3, 3}, new int[]{4, 4});
                put(new int[]{3, 1}, new int[]{4, 0});
            }
        });
        put(new int[]{2, 4}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{2, 0});
                put(new int[]{1, 3}, new int[]{0, 2});
                put(new int[]{3, 3}, new int[]{4, 2});
            }
        });
        put(new int[]{3, 1}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{1, 3});
            }
        });
        put(new int[]{3, 3}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{1, 1});
            }
        });
        put(new int[]{4, 0}, new HashMap<int[], int[]>() {
            {
                put(new int[]{3, 1}, new int[]{2, 2});
                put(new int[]{4, 2}, new int[]{4, 4});
                put(new int[]{2, 0}, new int[]{0, 0});
            }
        });
        put(new int[]{4, 2}, new HashMap<int[], int[]>() {
            {
                put(new int[]{2, 2}, new int[]{0, 2});
                put(new int[]{3, 1}, new int[]{2, 0});
                put(new int[]{3, 3}, new int[]{2, 4});
            }
        });
        put(new int[]{4, 4}, new HashMap<int[], int[]>() {
            {
                put(new int[]{3, 3}, new int[]{2, 2});
                put(new int[]{4, 2}, new int[]{4, 0});
                put(new int[]{2, 4}, new int[]{0, 4});
            }
        });
    }});

    final int[][] frogs;

    /**
     * Constructs a FrogArrangement object with the given initial positions of frogs.
     * @param inputFrogs A 2D array representing the initial positions of frogs, where 1 indicates a frog's presence and 0 indicates an empty space.
     */
    FrogArrangement(int[][] inputFrogs) {
        frogs = new int[inputFrogs.length][];
        for (int i = 0; i < inputFrogs.length; i++) {
            frogs[i] = Arrays.copyOf(inputFrogs[i], inputFrogs[i].length);
        }
    }

    /**
     * Determines if a hop from a starting position to an ending position, over a given position, is valid.
     * @param from The starting position of the frog.
     * @param over The position of the frog being jumped over.
     * @param to The target landing position of the frog.
     * @return True if the hop is valid, otherwise false.
     */
    public boolean canHop(int[] from, int[] over, int[] to) {
        return frogs[from[0]][from[1]] == 1 &&
               frogs[over[0]][over[1]] == 1 &&
               frogs[to[0]][to[1]] == 0;
    }

    /**
     * Checks if the current frog arrangement is a winning state.
     * A winning state is defined as having exactly one frog remaining on the board.
     * @return True if there is exactly one frog left, otherwise false.
     */
    public boolean isWinningState() {
        int frogCount = 0;
        for (int[] row : frogs) {
            for (int col : row) {
                frogCount += col; // Increment frogCount if cell contains a frog (1)
            }
        }
        return frogCount == 1;
    }

    /**
     * Performs a hop operation if valid and returns a new FrogArrangement reflecting the result.
     * @param start The starting position of the frog.
     * @param mid The position of the frog being jumped over.
     * @param end The target landing position of the frog.
     * @return A new FrogArrangement object with the updated positions, or null if the hop is invalid.
     */
    public FrogArrangement hop(int[] start, int[] mid, int[] end) {
        if (!canHop(start, mid, end)) {
            return null;
        }

        int[][] newFrogs = new int[frogs.length][frogs[0].length];
        for (int i = 0; i < frogs.length; i++) {
            System.arraycopy(frogs[i], 0, newFrogs[i], 0, frogs[i].length);
        }

        newFrogs[start[0]][start[1]] = 0; // Remove frog from start
        newFrogs[mid[0]][mid[1]] = 0;     // Remove frog from mid (jumped over)
        newFrogs[end[0]][end[1]] = 1;     // Place frog at end

        return new FrogArrangement(newFrogs); // Return new arrangement after hop
    }

    /**
     * Prints the current arrangement of frogs in a grid format.
     */
    public void printFrogs() {
        for (int[] row : frogs) {
            System.out.print("[");
            for (int j = 0; j < row.length; j++) {
                if (j > 0) {
                    System.out.print(", ");
                }
                System.out.print(row[j]);
            }
            System.out.println("]");
        }
    }

    /**
     * Calculates a hash code for the current frog arrangement based on its 2D array representation.
     * @param f The 2D array representing frog positions.
     * @return A hash code for the frog arrangement.
     */
    private int calculateHash(int[][] f) {
        int hash = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                hash += f[i][j] * Math.pow(2, (i * 5 + j));
            }
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrogArrangement that = (FrogArrangement) o;
        return calculateHash(this.frogs) == calculateHash(that.frogs);
    }

    @Override
    public int hashCode() {
        return calculateHash(this.frogs);
    }
}
