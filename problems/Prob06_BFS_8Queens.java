import java.util.*;

public class Prob06_BFS_8Queens {
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
        System.out.println("=== N-Queens Problem using BFS ===");
        System.out.print("Enter N (e.g., 8 for 8-Queens): ");
        N = sc.nextInt();

        System.out.println("\nSolving...\n");
        bfs();
    }

    static void bfs() {
        Queue<State> q = new LinkedList<>();
        q.add(new State(new ArrayList<>(), "Start with empty board\n"));

        while (!q.isEmpty()) {
            State curr = q.poll();
            int row = curr.board.size();

            if (row == N) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                printBoard(curr.board);
                return;
            }

            for (int col = 0; col < N; col++) {
                if (isValid(curr.board, row, col)) {
                    State next = new State(curr.board, curr.path + "Place Queen at Row " + row + ", Col " + col + "\n");
                    next.board.add(col);
                    q.add(next);
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
                if (board.get(i) == j)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
}
