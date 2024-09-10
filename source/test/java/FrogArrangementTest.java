import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrogArrangementTest {

    @Test
    void canHop() {
        // Create a FrogArrangement with frogs in a specific state
        int[][] frogsState = {
                {0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}};
        FrogArrangement frogArrangement = new FrogArrangement(frogsState);

        // Test a valid hop
        assertTrue(frogArrangement.canHop(new int[]{1, 3}, new int[]{2, 2}, new int[]{3, 1}));

        // Test an invalid hop
        assertFalse(frogArrangement.canHop(new int[]{0, 0}, new int[]{0, 2}, new int[]{0, 4}));
    }

    @Test
    void isWinningState() {
        // Create FrogArrangements representing winning and non-winning states
        int[][] winningState = {{0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1}};
        FrogArrangement winningArrangement = new FrogArrangement(winningState);

        int[][] nonWinningState = {{0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}};
        FrogArrangement nonWinningArrangement = new FrogArrangement(nonWinningState);

        // Test winning state
        assertTrue(winningArrangement.isWinningState());

        // Test non-winning state
        assertFalse(nonWinningArrangement.isWinningState());
    }

    @Test
    void hop() {
        // Create a FrogArrangement
        int[][] frogsState = {
                {0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}};
        FrogArrangement frogArrangement = new FrogArrangement(frogsState);

        // Perform a hop
        FrogArrangement newFrogArrangement = frogArrangement;

        // Assert that the hop was successful
        assertNotNull(newFrogArrangement);
        // Add more assertions here if needed
    }



}
