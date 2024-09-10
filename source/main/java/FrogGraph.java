import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Represents a graph of FrogArrangements, where nodes are different frog configurations and edges represent valid hops.
 */
public class FrogGraph {
    public static void main(String[] args) {

        // Example frog configurations for testing
        int[][] testFrogs = new int[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}
        };

        int[][] testFrogs_a = new int[][]{
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0}
        };

        int[][] testFrogs_b = new int[][]{
            {1, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };

        int[][] testFrogs3 = new int[][]{
            {0, 0, 0, 0, 1},
            {0, 1, 0, 1, 0},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 0, 1, 0, 0}
        };

        // Create FrogArrangement instances for testing
        FrogArrangement testArrangement3 = new FrogArrangement(testFrogs3);
        FrogArrangement myFrogs = new FrogArrangement(testFrogs);
        FrogArrangement myFrogs_a = new FrogArrangement(testFrogs_a);
        FrogArrangement myFrogs_b = new FrogArrangement(testFrogs_b);

        // Create a FrogGraph instance using the test arrangement
        FrogGraph myGraph4 = new FrogGraph(myFrogs);

        // Print solutions and adjacency check
        myGraph4.printSolutions();
        System.out.println("This should say true: " + myGraph4.areAdjacent(myFrogs_a, myFrogs_b));

        // Load frog arrangements from a CSV file and print them
        ArrayDeque<Integer> solsArray = loadFrogs("Frog_inputs.csv");
        System.out.println("The following line should be 2 6 1 1 5 0 0 0 1:");
        while(!solsArray.isEmpty()){
            int curInt = solsArray.remove();
            System.out.print(curInt + " ");
        }
    }

    // The starting arrangement used to build the graph
    FrogArrangement startingArrangement;

    /**
     * Maps each FrogArrangement to its predecessor FrogArrangement in the graph.
     * This helps in reconstructing the sequence of moves leading to any arrangement.
     */
    HashMap<FrogArrangement, FrogArrangement> predecessorMap;

    /**
     * Maps each FrogArrangement to its adjacent FrogArrangements.
     * Represents the adjacency list of the graph.
     */
    HashMap<FrogArrangement, Queue<FrogArrangement>> frogNeighbors;

    /**
     * Maps each FrogArrangement to the distance from the starting arrangement.
     * Distance is measured as the number of edges traversed.
     */
    HashMap<FrogArrangement, Integer> distFromStarting;

    /**
     * Contains all winning arrangements (with exactly one frog) reachable from the starting arrangement.
     */
    Queue<FrogArrangement> winningArrangements = new ArrayDeque<>();

    /**
     * Constructs a FrogGraph from a starting FrogArrangement and initializes the graph.
     * @param frog the starting FrogArrangement
     */
    FrogGraph(FrogArrangement frog) {
        startingArrangement = frog;
        predecessorMap = new HashMap<>();
        frogNeighbors = new HashMap<>();
        distFromStarting = new HashMap<>();
        winningArrangements = new ArrayDeque<>();
        createGraph(frog);
    }

    /**
     * Creates the graph of FrogArrangements using breadth-first search (BFS).
     * Each FrogArrangement is connected to other arrangements that can be reached by valid hops.
     * @param frog the starting FrogArrangement
     */
    protected void createGraph(FrogArrangement frog) {
        Queue<FrogArrangement> bfsQueue = new ArrayDeque<>();
        bfsQueue.add(frog);
        frogNeighbors.put(frog, new ArrayDeque<>());
        distFromStarting.put(frog, 0);

        if (frog.isWinningState()) {
            winningArrangements.add(frog);
        }

        while (!bfsQueue.isEmpty()) {
            FrogArrangement curFrogs = bfsQueue.remove();

            for (int[] from : FrogArrangement.hopOptions.keySet()) {
                for (int[] over : FrogArrangement.hopOptions.get(from).keySet()) {
                    int[] to = FrogArrangement.hopOptions.get(from).get(over);

                    if (curFrogs.canHop(from, over, to)) {
                        FrogArrangement newFrogs = curFrogs.hop(from, over, to);

                        if (newFrogs != null) {
                            if (!predecessorMap.containsKey(newFrogs)) {
                                predecessorMap.put(newFrogs, curFrogs);
                                distFromStarting.put(newFrogs, distFromStarting.get(curFrogs) + 1);
                                if (newFrogs.isWinningState()) {
                                    winningArrangements.add(newFrogs);
                                } else {
                                    bfsQueue.add(newFrogs);
                                }
                                frogNeighbors.put(newFrogs, new ArrayDeque<>());
                            }
                            frogNeighbors.get(curFrogs).add(newFrogs);
                            frogNeighbors.get(newFrogs).add(curFrogs);
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints all winning arrangements and their distance from the starting arrangement.
     * Also prints the sequence of arrangements leading to each winning state.
     */
    public void printSolutions() {
        if (winningArrangements.isEmpty()) {
            System.out.println("No winning arrangements");
            return;
        }

        System.out.println("Number of " + winningArrangements.size() + " different ending states");

        for (FrogArrangement winning : winningArrangements) {
            System.out.println("This arrangement: ");
            winning.printFrogs();
            System.out.println("Has distance " + distFromStarting.get(winning) + " from the starting position");

            FrogArrangement curArrangement = winning;
            while (!curArrangement.equals(startingArrangement)) {
                FrogArrangement predecessor = predecessorMap.get(curArrangement);
                predecessor.printFrogs();
                System.out.println("_____");
                curArrangement = predecessor;
            }
        }
    }

    /**
     * Checks if two FrogArrangement objects are adjacent in the graph.
     * @param frogs1 the first FrogArrangement
     * @param frogs2 the second FrogArrangement
     * @return true if frogs1 is adjacent to frogs2, false otherwise
     */
    public boolean areAdjacent(FrogArrangement frogs1, FrogArrangement frogs2) {
        Queue<FrogArrangement> adjFrogs = frogNeighbors.get(frogs1);
        return adjFrogs != null && adjFrogs.contains(frogs2);
    }

    /**
     * Loads frog arrangements from a CSV file and returns the number of winning arrangements for each arrangement.
     * @param frogFilename the name of the CSV file containing frog arrangements
     * @return an ArrayDeque containing the number of winning arrangements for each input arrangement
     */
    public static ArrayDeque<Integer> loadFrogs(String frogFilename) {
        ArrayDeque<Integer> numOfSolutionsArray = new ArrayDeque<>();
        try (FileInputStream inStream = new FileInputStream(frogFilename);
             Scanner scanner = new Scanner(inStream)) {
            
            while (scanner.hasNextLine()) {
                int count = 0;
                int[][] curFrogs = new int[5][];
                while (count < 5 && scanner.hasNextLine()) {
                    String curLine = scanner.nextLine();
                    String[] clean = curLine.trim().split(",");
                    if (clean.length != 5) {
                        System.out.println("Something is wrong with the input file");
                        return numOfSolutionsArray;
                    } else {
                        curFrogs[count] = new int[]{
                            intValue(clean[0]),
                            intValue(clean[1]),
                            intValue(clean[2]),
                            intValue(clean[3]),
                            intValue(clean[4])
                        };
                    }
                    count += 1;
                }
                if (count != 5) {
                    System.out.println("Somehow we didn't get 5 lines");
                    return numOfSolutionsArray;
                } else {
                    FrogArrangement curArrangement = new FrogArrangement(curFrogs);
                    FrogGraph curGraph = new FrogGraph(curArrangement);
                    numOfSolutionsArray.add(curGraph.winningArrangements.size());
                }
            }
            return numOfSolutionsArray;
        } catch (IOException e){

        }
    }
}
