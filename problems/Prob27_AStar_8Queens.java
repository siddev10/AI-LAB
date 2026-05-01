import java.util.*;

public class Prob27_AStar_8Queens {
    static int N;

    static class State implements Comparable<State> {
        List<Integer> board;
        int g, h, f;
        String path;

        State(List<Integer> board, String path) {
            this.board = new ArrayList<>(board);
            this.path = path;
            this.g = board.size(); // depth
            this.h = calcConflicts();
            this.f = this.g + this.h * 10; // weight conflicts heavily
        }

        int calcConflicts() {
            int c = 0;
            for (int i = 0; i < board.size(); i++) {
                for (int j = i + 1; j < board.size(); j++) {
                    if (board.get(i).equals(board.get(j)) || Math.abs(board.get(i) - board.get(j)) == Math.abs(i - j)) c++;
                }
            }
            return c;
        }

        public int compareTo(State o) {
            if (this.f != o.f) return this.f - o.f;
            return o.g - this.g; // favor deeper paths
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== N-Queens Problem using A* Search ===");
        System.out.print("Enter N: "); N = sc.nextInt();

        System.out.println("\nSolving...\n");
        astar();
    }

    static void astar() {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(new ArrayList<>(), "Start with empty board\n"));

        int statesExplored = 0;

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            statesExplored++;
            int row = curr.board.size();

            if (row == N && curr.h == 0) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                printBoard(curr.board);
                System.out.println("States explored: " + statesExplored);
                return;
            }

            if (row == N) continue;

            for (int col = 0; col < N; col++) {
                State next = new State(curr.board, "");
                next.board.add(col);
                next.g = next.board.size();
                next.h = next.calcConflicts();
                next.f = next.g + next.h * 10;
                
                if (next.h == 0) { // Pruning
                    next.path = curr.path + "Place Queen at Row " + row + ", Col " + col + " [g=" + next.g + "]\n";
                    pq.add(next);
                }
            }
        }
        System.out.println("No solution exists.");
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
