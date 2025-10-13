//Anson Chen
//CSE 123
// C1: Abstract Strategy Games
import java.util.Scanner;

public class Chomp extends AbstractStrategyGame {
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int TIE = 0;
    public static final int GAME_IS_OVER = -1;
    public static final int GAME_NOT_OVER = -1;

    private char[][] board;
    private boolean isXTurn;
    private int cols;
    private int rows;
    private int winner;


    public Chomp() {
        this.cols = 4;
        this.rows = 8; 
        this.winner = 0; 

        this.board = new char[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (i == 0 && j == 0) {
                    this.board[i][j] = 'X';
                }
                if (i == 0) {
                    if (j == 0) {
                        
                    } else {
                        this.board[i][j] = j;
                    }
                } else if (j == 0) {
                    this.board[i][j] = i;
                } else {
                    this.board[i][j] = 3;
                }
            }
        }
        this.isXTurn = true;
    }

    public String instructions() {
        return "";
    }

    public String toString() {
        String gameBoard = "";
        for (int i )
        for (int i = 0; i < cols; i++) {
            gameBoard += "|"
            for (int j = 0; j < rows; j++)
        }
        return gameBoard;
    }

    public int getWinner() {
        return winner;
    }

    public int getNextPlayer() {
        return 1;
    }

    public String getMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        
        // Read the two integers for column and row
        int col = input.nextInt();
        int row = input.nextInt();
        
        // Format them into a "col,row" string
        return col + " " + row;
    }

    public void makeMove(String coordinates) {
        String[] colAndRow = coordinates.split(" ");
        if (colAndRow.length != 2) {
            throw new IllegalArgumentException("Invalid coordinates format. Expected 'col row'.");
        }

        int col = Integer.parseInt(colAndRow[0]);
        int row = Integer.parseInt(colAndRow[1]);
        if (col < 1 || row < 1 || col >= this.cols || row >= this.rows) {
            throw new IllegalArgumentException("Col and Row inputs must be within board bounds.");
        }
        
        if (col == 1 && row == 1) {

        }


    }
}
