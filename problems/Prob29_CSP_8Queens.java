import java.util.*;

public class Prob29_CSP_8Queens {
    static final int N = 8;
    static int[] board;

    public static void main(String[] args) {
        System.out.println("=== 8-Queens Constraint Satisfaction Problem ===");
        
        board = new int[N];
        Arrays.fill(board, -1);

        System.out.println("\nSolving using Backtracking...\n");
        if (solve(0)) {
            System.out.println("=== Solution Found! ===");
            printBoard();
        } else {
            System.out.println("No solution exists for N=" + N);
        }
    }

    static boolean solve(int col) {
        if (col == N) return true;

        for (int i = 0; i < N; i++) {
            if (isSafe(i, col)) {
                board[col] = i;
                System.out.println("Placed Queen at Row " + i + ", Col " + col);
                
                if (solve(col + 1)) return true;
                
                // Backtrack
                board[col] = -1;
                System.out.println("Backtracking from Row " + i + ", Col " + col);
            }
        }
        return false;
    }

    static boolean isSafe(int row, int col) {
        for (int i = 0; i < col; i++) {
            if (board[i] == row || Math.abs(board[i] - row) == Math.abs(i - col)) {
                return false;
            }
        }
        return true;
    }

    static void printBoard() {
        System.out.println("\nFinal Board Layout:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) System.out.print("Q ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }
}
