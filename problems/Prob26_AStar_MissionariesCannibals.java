import java.util.*;

public class Prob26_AStar_MissionariesCannibals {
    static int totalM, totalC;

    static class State implements Comparable<State> {
        int m, c, b, g, h, f;
        String path;

        State(int m, int c, int b, int g, String path) {
            this.m = m; this.c = c; this.b = b; this.g = g; this.path = path;
            this.h = m + c; // Distance to goal
            this.f = this.g + this.h;
        }

        public String key() { return m + "," + c + "," + b; }
        public int compareTo(State o) { return this.f - o.f; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Missionaries & Cannibals using A* Search ===");
        System.out.print("Enter total Missionaries: "); totalM = sc.nextInt();
        System.out.print("Enter total Cannibals: "); totalC = sc.nextInt();

        System.out.println("\nSolving...\n");
        astar();
    }

    static boolean isValid(int m, int c) {
        if (m < 0 || c < 0 || m > totalM || c > totalC) return false;
        if (m > 0 && m < c) return false;
        int rm = totalM - m, rc = totalC - c;
        if (rm > 0 && rm < rc) return false;
        return true;
    }

    static void astar() {
        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        String initialPath = "Start: " + totalM + "M, " + totalC + "C on Left Bank [g=0]\n";
        State start = new State(totalM, totalC, 1, 0, initialPath);
        pq.add(start);
        visited.add(start.key());

        int[][] moves = {{1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}};

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if (curr.m == 0 && curr.c == 0 && curr.b == 0) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                System.out.println("Total Cost (Steps): " + curr.g);
                return;
            }

            for (int[] move : moves) {
                int nm = curr.b == 1 ? curr.m - move[0] : curr.m + move[0];
                int nc = curr.b == 1 ? curr.c - move[1] : curr.c + move[1];
                int nb = 1 - curr.b;

                if (isValid(nm, nc)) {
                    State next = new State(nm, nc, nb, curr.g + 1, "");
                    if (!visited.contains(next.key())) {
                        visited.add(next.key());
                        String direction = curr.b == 1 ? "Left -> Right" : "Right -> Left";
                        next.path = curr.path + "Move " + move[0] + "M, " + move[1] + "C (" + direction + ") | Left Bank: " + nm + "M, " + nc + "C [g=" + next.g + "]\n";
                        pq.add(next);
                    }
                }
            }
        }
        System.out.println("No solution exists.");
    }
}
