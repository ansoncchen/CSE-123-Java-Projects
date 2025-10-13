import java.util.*;

public class Chomp extends AbstractStrategyGame {
    private int[][] chompBar;
    private final int totalCols; 
    private final int totalRows;

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
        this.totalRows = 7;
        this.totalCols = 4;

        this.chompBar = new int[this.totalCols][this.totalRows];
        for (int i = 0; i < this.totalCols; i++) {
            for (int j = 0; j < this.totalRows; j++) {
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
     * Parameters:
     */
    public String toString() {
        //add the initial row labels for the chomp bar coordinate plane
        String currentChompBar = "| X |";
        for (int i = 0; i < totalRows; i++) {
            currentChompBar += " " + i + " |";
        }
        currentChompBar += "\n";

        currentChompBar += "-----";
        for (int i = 0; i < totalRows; i++) {
            currentChompBar += "----";
        }
        currentChompBar += "\n";

        //add the column labels as well as 
        for (int i = 0; i < totalCols; i++) {
            for (int j = 0; j < totalRows; j++) {
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

        // Read the two integers for column and row
        int col = input.nextInt();
        int row = input.nextInt();
        
        // Format them into a "<row> <col>" string
        return col + " " + row;
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
            throw new IllegalArgumentException("Col and Row inputs must be within board bounds");
        }
        if (chompBar[row][col] == 0) {
            throw new IllegalArgumentException("That position is already empty, choose another");
        }

        for (int i = col; i < totalCols; i++) {
            for (int j = row; j < totalRows; j++) { 
                if (chompBar[i][j] > 0) {
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