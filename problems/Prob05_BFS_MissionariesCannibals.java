import java.util.*;

public class Prob05_BFS_MissionariesCannibals {
    static int totalM, totalC;

    static class State {
        int m, c, b; // Left bank
        String path;

        State(int m, int c, int b, String path) {
            this.m = m;
            this.c = c;
            this.b = b;
            this.path = path;
        }

        public String key() {
            return m + "," + c + "," + b;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Missionaries & Cannibals using BFS ===");
        System.out.print("Enter total number of Missionaries: ");
        totalM = sc.nextInt();
        System.out.print("Enter total number of Cannibals: ");
        totalC = sc.nextInt();

        System.out.println("\nSolving...\n");
        bfs();
    }

    static boolean isValid(int m, int c) {
        if (m < 0 || c < 0 || m > totalM || c > totalC) return false;
        if (m > 0 && m < c) return false; // Left bank
        int rm = totalM - m;
        int rc = totalC - c;
        if (rm > 0 && rm < rc) return false; // Right bank
        return true;
    }

    static void bfs() {
        Queue<State> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        String initialPath = "Start: " + totalM + "M, " + totalC + "C on Left Bank\n";
        State start = new State(totalM, totalC, 1, initialPath);
        q.add(start);
        visited.add(start.key());

        int[][] moves = {{1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}};

        while (!q.isEmpty()) {
            State curr = q.poll();

            if (curr.m == 0 && curr.c == 0 && curr.b == 0) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                return;
            }

            for (int[] move : moves) {
                int moveM = move[0];
                int moveC = move[1];

                int nm = curr.b == 1 ? curr.m - moveM : curr.m + moveM;
                int nc = curr.b == 1 ? curr.c - moveC : curr.c + moveC;
                int nb = 1 - curr.b;

                if (isValid(nm, nc)) {
                    State next = new State(nm, nc, nb, "");
                    if (!visited.contains(next.key())) {
                        visited.add(next.key());
                        String direction = curr.b == 1 ? "Left -> Right" : "Right -> Left";
                        next.path = curr.path + "Move " + moveM + "M, " + moveC + "C (" + direction + ") | " +
                                "Left Bank: " + nm + "M, " + nc + "C\n";
                        q.add(next);
                    }
                }
            }
        }
        System.out.println("No solution exists.");
    }
}
