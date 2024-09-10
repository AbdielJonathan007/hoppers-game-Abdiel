import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

class FrogGraphTest {

    @Test
    void areAdjacent() {
        int[][] testFrogs2 = new int[][]{{1, 0, 0, 0, 1},// starting point
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}};
        int[][] testFrogs2a = new int[][]{{0, 0, 0, 0, 1},// one step from starting
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 0, 0, 0, 1}};
        int[][] testFrogs2b = new int[][]{{1, 0, 0, 0, 1}, // in graph
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}};
        int[][] testFrogs2c = new int[][]{{1, 0, 1, 0, 1}, // not in graph
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0}};
        int[][] testFrogs2d = new int[][]{{1, 0, 0, 0, 1},// much later
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}};
        int[][] testFrogs2e = new int[][]{{1, 0, 0, 0, 0},// also much later
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1}};
        FrogArrangement myFrogs2 = new FrogArrangement(testFrogs2);
        FrogArrangement myFrogs2a = new FrogArrangement(testFrogs2a);
        FrogArrangement myFrogs2b = new FrogArrangement(testFrogs2b);
        FrogArrangement myFrogs2c = new FrogArrangement(testFrogs2c);
        FrogArrangement myFrogs2d = new FrogArrangement(testFrogs2d);
        FrogArrangement myFrogs2e = new FrogArrangement(testFrogs2e);

        FrogGraph myGraph2 = new FrogGraph(myFrogs2);

        Assertions.assertTrue(myGraph2.areAdjacent(myFrogs2, myFrogs2a));
        Assertions.assertTrue(myGraph2.areAdjacent(myFrogs2, myFrogs2b));
        Assertions.assertTrue(myGraph2.areAdjacent(myFrogs2d, myFrogs2e));
        Assertions.assertTrue(!myGraph2.areAdjacent(myFrogs2b, myFrogs2a));
        Assertions.assertTrue(!myGraph2.areAdjacent(myFrogs2, myFrogs2c));
    }

    @Test
    void createGraph() {
        int[][] testFrogs1 = new int[][]{{0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}};
        FrogArrangement myFrogs1 = new FrogArrangement(testFrogs1);
        FrogGraph myGraph1 = new FrogGraph(myFrogs1);
        Assertions.assertEquals(1, myGraph1.winningArrangements.size());

        int[][] testFrogs2 = new int[][]{{1, 0, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0}};
        FrogArrangement myFrogs2 = new FrogArrangement(testFrogs2);
        FrogGraph myGraph2 = new FrogGraph(myFrogs2);
        Assertions.assertEquals(0, myGraph2.winningArrangements.size());

        int[][] testFrogs3 = new int[][]{{0, 0, 0, 0, 1},
                {0, 1, 0, 1, 0},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 0, 1, 0, 0}};
        FrogArrangement myFrogs3 = new FrogArrangement(testFrogs3);
        FrogGraph myGraph3 = new FrogGraph(myFrogs3);
        Assertions.assertEquals(6, myGraph3.winningArrangements.size());

        int[][] testFrogs4 = new int[][]{{0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}};
        FrogArrangement myFrogs4 = new FrogArrangement(testFrogs4);
        FrogGraph myGraph4 = new FrogGraph(myFrogs4);
        Assertions.assertEquals(4, myGraph4.winningArrangements.size());
    }

    @Test
     void loadFrogs() {
        ArrayDeque<Integer> solsArray =  FrogGraph.loadFrogs("Frog_inputs.csv");
        ArrayDeque<Integer> correctArray = new ArrayDeque<Integer>(){{
            add(2);
            add(6);
            add(1);
            add(1);
            add(5);
            add(0);
            add(0);
            add(0);
            add(1);
        }};
        boolean equals = true;
        if (solsArray.size() != correctArray.size()){
            equals = false;
        }
        int true_val;
        int test_val;
        for (int i=0; i< solsArray.size(); i++){
            true_val = correctArray.remove();
            test_val = solsArray.remove();
            if(true_val != test_val){
                equals = false;
            }
        }
        Assertions.assertTrue(equals);
    }
}
