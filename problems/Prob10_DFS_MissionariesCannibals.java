import java.util.*;

public class Prob10_DFS_MissionariesCannibals {
    static int totalM, totalC;

    static class State {
        int m, c, b;
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
        System.out.println("=== Missionaries & Cannibals using DFS ===");
        System.out.print("Enter total number of Missionaries: ");
        totalM = sc.nextInt();
        System.out.print("Enter total number of Cannibals: ");
        totalC = sc.nextInt();

        System.out.println("\nSolving...\n");
        dfs();
    }

    static boolean isValid(int m, int c) {
        if (m < 0 || c < 0 || m > totalM || c > totalC) return false;
        if (m > 0 && m < c) return false;
        int rm = totalM - m, rc = totalC - c;
        if (rm > 0 && rm < rc) return false;
        return true;
    }

    static void dfs() {
        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        String initialPath = "Start: " + totalM + "M, " + totalC + "C on Left Bank\n";
        State start = new State(totalM, totalC, 1, initialPath);
        stack.push(start);
        visited.add(start.key());

        int[][] moves = {{1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}};

        while (!stack.isEmpty()) {
            State curr = stack.pop();

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
                        next.path = curr.path + "Move " + move[0] + "M, " + move[1] + "C (" + direction + ") | " +
                                "Left Bank: " + nm + "M, " + nc + "C\n";
                        stack.push(next);
                    }
                }
            }
        }
        System.out.println("No solution exists.");
    }
}
