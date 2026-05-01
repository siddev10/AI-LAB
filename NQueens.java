import java.util.Scanner;

public class NQueens {

    static final int MAX = 10;

    // Function to print the board configuration
    static void printSolution(int[][] board, int N) {
        System.out.println("\nSolution for " + N + "-Queens:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1)
                    System.out.print(" Q ");
                else
                    System.out.print(" . ");
            }
            System.out.println();
        }
    }

    // Check if a queen can be placed on board[row][col]
    // Constraints: No queen in same row, upper diagonal, or lower diagonal
    static boolean isSafe(int[][] board, int row, int col, int N) {
        int i, j;

        // Check this row on left side
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // Check upper diagonal on left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check lower diagonal on left side
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // Recursive Backtracking function to solve the CSP
    static boolean solveNQUtil(int[][] board, int col, int N) {
        // Base case: If all queens are placed, return true
        if (col >= N)
            return true;

        // Try placing queen in all rows for this column
        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col, N)) {
                // Place the queen
                board[i][col] = 1;

                // Recur to place rest of the queens
                if (solveNQUtil(board, col + 1, N))
                    return true;

                // Backtrack: remove queen from board[i][col]
                board[i][col] = 0;
            }
        }

        // Queen cannot be placed in any row in this column
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] board = new int[MAX][MAX];

        System.out.print("Enter the number of Queens (N): ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer value for N.");
            sc.close();
            return;
        }

        int N = sc.nextInt();

        if (N <= 0) {
            System.out.println("N must be a positive integer.");
            sc.close();
            return;
        }

        if (N > MAX) {
            System.out.println("N is too large. Max supported is " + MAX + ".");
            sc.close();
            return;
        }

        if (!solveNQUtil(board, 0, N)) {
            System.out.println("Solution does not exist");
        } else {
            printSolution(board, N);
        }

        sc.close();
    }
}