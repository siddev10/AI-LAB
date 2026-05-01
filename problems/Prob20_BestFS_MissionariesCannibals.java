import java.util.*;

public class Prob20_BestFS_MissionariesCannibals {
    static int totalM, totalC;

    static class State implements Comparable<State> {
        int m, c, b, h;
        String path;

        State(int m, int c, int b, String path) {
            this.m = m; this.c = c; this.b = b; this.path = path;
            this.h = m + c; // Distance to goal (0M, 0C on left bank)
        }

        public String key() { return m + "," + c + "," + b; }
        public int compareTo(State o) { return this.h - o.h; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Missionaries & Cannibals using Best First Search ===");
        System.out.print("Enter total Missionaries: "); totalM = sc.nextInt();
        System.out.print("Enter total Cannibals: "); totalC = sc.nextInt();

        System.out.println("\nSolving...\n");
        bestfs();
    }

    static boolean isValid(int m, int c) {
        if (m < 0 || c < 0 || m > totalM || c > totalC) return false;
        if (m > 0 && m < c) return false;
        int rm = totalM - m, rc = totalC - c;
        if (rm > 0 && rm < rc) return false;
        return true;
    }

    static void bestfs() {
        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        String initialPath = "Start: " + totalM + "M, " + totalC + "C on Left Bank\n";
        State start = new State(totalM, totalC, 1, initialPath);
        pq.add(start);
        visited.add(start.key());

        int[][] moves = {{1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}};

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if (curr.m == 0 && curr.c == 0 && curr.b == 0) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                return;
            }

            for (int[] move : moves) {
                int nm = curr.b == 1 ? curr.m - move[0] : curr.m + move[0];
                int nc = curr.b == 1 ? curr.c - move[1] : curr.c + move[1];
                int nb = 1 - curr.b;

                if (isValid(nm, nc)) {
                    State next = new State(nm, nc, nb, "");
                    if (!visited.contains(next.key())) {
                        visited.add(next.key());
                        String direction = curr.b == 1 ? "Left -> Right" : "Right -> Left";
                        next.path = curr.path + "Move " + move[0] + "M, " + move[1] + "C (" + direction + ") | Left Bank: " + nm + "M, " + nc + "C [h=" + next.h + "]\n";
                        pq.add(next);
                    }
                }
            }
        }
        System.out.println("No solution exists.");
    }
}
