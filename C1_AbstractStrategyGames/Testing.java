import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    /**
     * Tests a game scenario where a player is forced to make the losing move on 
     * the poison square (0, 0), resulting in a win for the other player, accounting for 3 layers
     */
    @Test
    @DisplayName("STUDENT TEST CASE - Win Condition")
    public void firstCaseTest() {
        AbstractStrategyGame g = new Chomp();

        // Simulate Player 1 chomping the top layer of (0, 0)
        // chompBar[0][0] is now 2 layer
        g.makeMove("0 0");
        assertFalse(g.isGameOver(), "Game shouldn't be over after one chomp on (0,0)");
        assertEquals(Chomp.PLAYER_2, g.getNextPlayer(), "It should be Player 2's turn");

        // Simulate Player 2 chomping the next layer of (0, 0)
        // chompBar[0][0] is now 1 layer
        g.makeMove("0 0");
        assertFalse(g.isGameOver(), "Game shouldn't be over after two chomps on (0,0)");
        assertEquals(Chomp.PLAYER_1, g.getNextPlayer(), "It should be Player 1's turn");
        
        // Player 1 is now forced to chomp the final layer of (0, 0) and loses
        // chompBar[0][0] is now 0 layers
        g.makeMove("0 0");

        // The game should be over and Player 2 should be the winner.
        assertTrue(g.isGameOver(), "Game should be over after (0, 0) becomes 0");
        assertEquals(Chomp.PLAYER_2, g.getWinner(), "Player 2 should be the winner");
    }

    /**
     * Tests that the game correctly throws an IllegalArgumentException for various
     * types of illegal moves.
     */
    @Test
    @DisplayName("STUDENT TEST CASE - Illegal Move")
    public void secondCaseTest() {
        AbstractStrategyGame g = new Chomp();

        // 1. Test for coordinates out of bounds (too high)
        assertThrows(IllegalArgumentException.class, () -> {
            g.makeMove("4 7"); // Board is 4x7, so indices are 0 -> 3 and 0 -> 6
        }, "Exception not thrown for out-of-bounds move (too high).");

        // 2. Test for coordinates out of bounds (negative)
        assertThrows(IllegalArgumentException.class, () -> {
            g.makeMove("-1 5");
        }, "Exception not thrown for out-of-bounds move (negative).");

        // 3. Test for chomping an already empty square
        g.makeMove("3 6");
        g.makeMove("3 6"); 
        g.makeMove("3 6"); 
        
        assertThrows(IllegalArgumentException.class, () -> {
            g.makeMove("3 6"); // Now try to chomp the empty square
        }, "Exception not thrown for chomping an empty square.");
    }
}