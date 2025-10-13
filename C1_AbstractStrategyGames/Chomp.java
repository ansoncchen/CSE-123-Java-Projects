// Anson Chen
// 10/12/2025
// CSE 123
// C1: Abstract Stategy Game
// TA: Ishita
// This class represents a 3-layered Chomp Game that extends the AbstractStrategyGame class
import java.util.*;

public class Chomp extends AbstractStrategyGame {
    private int[][] chompBar;
    private static final int ROWS = 4;
    private static final int COLS = 7; 
    

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    private static final int STARTING_LAYERS = 3;
    private boolean isFirstTurn;

    private int winner;
    public static final int GAME_IS_OVER = -1;
    public static final int NO_WINNER = -1;

    /**
     * Behavior: 
     * Exceptions: N/A
     * Returns: N/A (constructor)
     * Parameters: N/A
     */
    public Chomp() {
        this.chompBar = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                chompBar[i][j] = STARTING_LAYERS;
            }
        }

        this.winner = NO_WINNER;
        this.isFirstTurn = true;
    }

    /**
     * Behavior:
     * Exceptions: N/A
     * Returns:
     * Parameters: N/A
     */
    public String instructions() {
        String result = "";
        result += "Welcome to Chomp, a two-player game where your goal is to force your\n";
        result += "opponent to take the 'poison' square at coordinate (0, 0).\n";
        result += "The board is a 'chocolate bar' with 3 layers. The numbers on the board show\n";
        result += "how many layers are left. A '0' means the square is completely gone.\n";
        result += "On your turn, enter the coordinates for the square you want to chomp,\n";
        result += "with the row first, then the column (e.g., '2 3').\n";
        result += "Chomping removes one layer from your chosen square and every square to the\n";
        result += "right of and below it, but only if they are on the same layer as your pick.\n";
        result += "The player who is forced to chomp the last layer of the (0, 0) square loses.\n";
        return result;
    }

    /**
     * Behavior: 
     * Exceptions: N/A
     * Returns:
     *  - String: Representation, with top row representing the column numbers and the 
     *  - Ex: "| X | 0 | 1 | 2 |
               -----------------
               | 0 | 3 | 3 | 3 |
               | 1 | 3 | 3 | 3 |""
     * Parameters: N/A
     */
    public String toString() {
        //add the initial row labels for the chomp bar coordinate plane
        String currentChompBar = "| X |";
        for (int i = 0; i < COLS; i++) {
            currentChompBar += " " + i + " |";
        }
        currentChompBar += "\n";

        currentChompBar += "-----";
        for (int i = 0; i < COLS; i++) {
            currentChompBar += "----";
        }
        currentChompBar += "\n";

        //add the column labels as well as 
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (j == 0) {
                    currentChompBar += "| " + i + " |";
                }
                currentChompBar += " " + chompBar[i][j] + " |";
            }
            currentChompBar += "\n";
        }
        return currentChompBar;
    }

    /**
     * Behavior:
     * Exceptions:
     * Returns:
     * Parameters:
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Behavior:
     * Exceptions:
     * Returns:
     * Parameters: N/A
     */
    public int getNextPlayer() {
        if (isGameOver()) {
            return GAME_IS_OVER;
        }
        
        if (isFirstTurn) {
            return PLAYER_1;
        } else {
            return PLAYER_2;
        }
    }

    /**
     * Behavior:
     * Exceptions:
     * Returns:
     * Parameters:
     */
    public String getMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        
        System.out.print("Chomp Coordinate? ");

        // Read the two integers for rol and column
        int row = input.nextInt();
        int col = input.nextInt();
        
        // Format them into a "<row> <col>" string
        return row + " " + col;
    }

    /**
     * Behavior:
     * Exceptions:
     * Returns:
     * Parameters: 
     */
    public void makeMove(String input) {
        String[] coordinates = input.split(" ");
        if (coordinates.length != 2) {
            throw new IllegalArgumentException("Invalid coordinates format. Expected 'row col'");
        }

        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);
        if (col < 0 || row < 0 || col >= COLS || row >= ROWS) {
            throw new IllegalArgumentException("Row and Col inputs must be within board bounds");
        }
        if (chompBar[row][col] == 0) {
            throw new IllegalArgumentException("That position is already empty, choose another");
        }

        int currentChompLayer = chompBar[row][col]; 
        for (int i = row; i < ROWS; i++) {
            for (int j = col; j < COLS; j++) { 
                if (chompBar[i][j] == currentChompLayer) {
                    chompBar[i][j]--;
                }
            }
        }

        isFirstTurn = !isFirstTurn;
        if (chompBar[0][0] == 0) {
            winner = getNextPlayer();
        }
    }
}