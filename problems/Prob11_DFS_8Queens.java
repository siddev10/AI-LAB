import java.util.*;

public class Prob11_DFS_8Queens {
    static int N;

    static class State {
        List<Integer> board;
        String path;

        State(List<Integer> board, String path) {
            this.board = new ArrayList<>(board);
            this.path = path;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== N-Queens Problem using DFS ===");
        System.out.print("Enter N: ");
        N = sc.nextInt();

        System.out.println("\nSolving...\n");
        dfs();
    }

    static void dfs() {
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

            for (int col = N - 1; col >= 0; col--) { // Reverse order so left-most branch processed first in DFS
                if (isValid(curr.board, row, col)) {
                    State next = new State(curr.board, curr.path + "Place Queen at Row " + row + ", Col " + col + "\n");
                    next.board.add(col);
                    stack.push(next);
                }
            }
        }
        System.out.println("No solution exists for N=" + N);
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
