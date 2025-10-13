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
     * - Constructs a new Chomp game, initializing a 4x7 board with 3 layers on each square.
     * - Sets the winner status to NO_WINNER and sets the starting turn to Player 1.
     * Exceptions: N/A
     * Returns: N/A (constructor)
     * Parameters: N/A
     */
    public Chomp() {
        //creates initial chomp bar/game board
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
     * - Provides a detailed set of instructions explaining the rules and objectives of the Chomp game.
     * Exceptions: N/A
     * Returns:
     * - String: A multi-line string containing the game instructions for the player.
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
     * - Generates a string representation of the current state of the Chomp game board, 
     *   including row and column labels for coordinates.
     * Exceptions: N/A
     * Returns:
     * - String: A formatted string representing the chomp bar, with top row showing column index, 
     *           and the left side column showing the row index.
     *   For example: "| X | 0 | 1 | 2 |
     *                 -----------------
     *                 | 0 | 3 | 3 | 3 |
     *                 | 1 | 3 | 3 | 3 |"
     * Parameters: N/A
     */
    public String toString() {
        // add the initial column labels for the chomp bar coordinate plane
        String currentChompBar = "| X |";
        for (int i = 0; i < COLS; i++) {
            currentChompBar += " " + i + " |";
        }
        currentChompBar += "\n";

        // add the divider bar between chomp bar and column labels
        currentChompBar += "-----";
        for (int i = 0; i < COLS; i++) {
            currentChompBar += "----";
        }
        currentChompBar += "\n";

        // add the row labels as well as and rest of the chomp bar (changes dynamically with array)
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
     * - Retrieves the winner of the game.
     * Exceptions: N/A
     * Returns:
     * - int: The player number of the winner (PLAYER_1 or PLAYER_2), or NO_WINNER if the
     * game is not yet over.
     * Parameters: N/A
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Behavior:
     * - Determines which player's turn it is.
     * Exceptions: N/A
     * Returns:
     * - int: The player number of the next player (PLAYER_1 or PLAYER_2), or GAME_IS_OVER
     * if the game has concluded.
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
     * - Prompts the current player to enter their move (row and column) and reads the
     * input from the console.
     * Exceptions:
     * - Throws an IllegalArgumentException if the provided Scanner is null.
     * Returns:
     * - String: A string representing the player's chosen coordinates, formatted as "row col".
     * Parameters:
     * - input: The Scanner object used to read user input from the console.
     */
    public String getMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        
        System.out.print("Chomp Coordinate? ");

        // Read the two integers for row and column
        int row = input.nextInt();
        int col = input.nextInt();
        
        // Format them into a "<row> <col>" string
        return row + " " + col;
    }

    /**
     * Behavior:
     * - Processes a player's move. It chomps one layer from the selected square and all
     * squares to its right and below that are on the same layer.
     * - It then switches the turn to the next player and checks if the game has been won
     * (i.e., if the (0,0) square is now empty).
     * Exceptions:
     * - Throws an IllegalArgumentException if the input string is not in the format "row col".
     * - Throws an IllegalArgumentException if the coordinates are outside the board's bounds.
     * - Throws an IllegalArgumentException if the chosen square has no layers left (is 0).
     * Returns: N/A
     * Parameters: 
     * - input: A string containing the row and column for the move, separated by a space.
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

        // Goes through the selected tile and all tiles to the right and down on the same layer
        // as the selected tile and subtracts one
        int currentChompLayer = chompBar[row][col]; 
        for (int i = row; i < ROWS; i++) {
            for (int j = col; j < COLS; j++) { 
                if (chompBar[i][j] == currentChompLayer) {
                    chompBar[i][j]--;
                }
            }
        }

        // change players
        isFirstTurn = !isFirstTurn;
        // if the the previous player hit the poison square, then the current player winners
        if (chompBar[0][0] == 0) {
            winner = getNextPlayer();
        }
    }
}