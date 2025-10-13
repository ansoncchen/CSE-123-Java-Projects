import java.util.*;

public class Chomp extends AbstractStrategyGame {
    private int[][] chompBar;
    private final int totalRows;
    private final int totalCols; 

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    private boolean isXTurn;

    private int winner;

    /**
     * Behavior: 
     * Exceptions: N/A
     * Returns: N/A (constructor)
     * Parameters: N/A
     */
    public Chomp() {
        this.totalRows = 4;
        this.totalCols = 7;

        this.chompBar = new int[this.totalRows][this.totalCols];
        for (int i = 0; i < this.totalRows; i++) {
            for (int j = 0; j < this.totalCols; j++) {
                chompBar[i][j] = 3;
            }
        }

        this.winner = -1;
        this.isXTurn = true;
    }

    /**
     * Behavior:
     * Exceptions: 
     * Returns:
     * Parameters:
     */
    public String instructions() {
        String insturction = "";
        return insturction;
    }

    /**
     * Behavior: 
     * Exceptions:
     * Returns:
     *  - String: Representation, with top row representing 
     *  - Ex: "| X | 0 | 1 | 2 |
               -----------------
               | 0 | 3 | 3 | 3 |
               | 1 | 3 | 3 | 3 |""
     * Parameters: N/A
     */
    public String toString() {
        //add the initial row labels for the chomp bar coordinate plane
        String currentChompBar = "| X |";
        for (int i = 0; i < totalCols; i++) {
            currentChompBar += " " + i + " |";
        }
        currentChompBar += "\n";

        currentChompBar += "-----";
        for (int i = 0; i < totalCols; i++) {
            currentChompBar += "----";
        }
        currentChompBar += "\n";

        //add the column labels as well as 
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
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
     * Parameters:
     */
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        
        if(isXTurn) {
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
            throw new IllegalArgumentException("Invalid coordinates format. Expected 'col row'");
        }

        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);
        if (col < 0 || row < 0 || col >= totalCols || row >= totalRows) {
            throw new IllegalArgumentException("Row and Col inputs must be within board bounds");
        }
        if (chompBar[row][col] == 0) {
            throw new IllegalArgumentException("That position is already empty, choose another");
        }

        int currentChompLayer = chompBar[row][col]; 

        for (int i = row; i < totalRows; i++) {
            for (int j = col; j < totalCols; j++) { 
                if (chompBar[i][j] == currentChompLayer) {
                    chompBar[i][j]--;
                }
            }
        }

        isXTurn = !isXTurn;

        if (chompBar[0][0] == 0) {
            winner = getNextPlayer();
        }
    }

}