import java.util.*;

public class Prob16_DLS_8Queens {
    static final int N = 8;
    static final int maxDepth = 3;

    static class State {
        List<Integer> board;
        String path;

        State(List<Integer> board, String path) {
            this.board = new ArrayList<>(board);
            this.path = path;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 8-Queens Problem using DLS ===");
        System.out.println("Using Depth Limit = " + maxDepth);
        System.out.println("\nSolving...\n");
        dls();
    }

    static void dls() {
        Stack<State> stack = new Stack<>();
        stack.push(new State(new ArrayList<>(), "Start with empty board\n"));

        while (!stack.isEmpty()) {
            State curr = stack.pop();
            int row = curr.board.size();

            if (row == N) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                printBoard(curr.board);
                return;
            }

            if (row >= maxDepth) continue;

            for (int col = N - 1; col >= 0; col--) {
                if (isValid(curr.board, row, col)) {
                    State next = new State(curr.board, curr.path + "Depth " + (row + 1) + " - Place Queen at Row " + row + ", Col " + col + "\n");
                    next.board.add(col);
                    stack.push(next);
                }
            }
        }
        System.out.println("No solution exists within depth " + maxDepth + ". For N-Queens, depth must be at least N.");
    }

    static boolean isValid(List<Integer> board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board.get(i) == col || Math.abs(board.get(i) - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    static void printBoard(List<Integer> board) {
        System.out.println("Final Board Layout:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board.get(i) == j) System.out.print("Q ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }
}
