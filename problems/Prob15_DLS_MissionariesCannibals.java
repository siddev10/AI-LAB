import java.util.*;

public class Prob15_DLS_MissionariesCannibals {
    static int totalM, totalC, maxDepth;

    static class State {
        int m, c, b, depth;
        String path;

        State(int m, int c, int b, int depth, String path) {
            this.m = m; this.c = c; this.b = b; this.depth = depth;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Missionaries & Cannibals using DLS ===");
        System.out.print("Enter total Missionaries: "); totalM = sc.nextInt();
        System.out.print("Enter total Cannibals: "); totalC = sc.nextInt();
        System.out.print("Enter Depth Limit (e.g., 3): "); maxDepth = sc.nextInt();

        System.out.println("\nSolving...\n");
        dls();
    }

    static boolean isValid(int m, int c) {
        if (m < 0 || c < 0 || m > totalM || c > totalC) return false;
        if (m > 0 && m < c) return false;
        int rm = totalM - m, rc = totalC - c;
        if (rm > 0 && rm < rc) return false;
        return true;
    }

    static void dls() {
        Stack<State> stack = new Stack<>();
        String initialPath = "Start: " + totalM + "M, " + totalC + "C on Left Bank\n";
        stack.push(new State(totalM, totalC, 1, 0, initialPath));

        int[][] moves = {{1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}};

        while (!stack.isEmpty()) {
            State curr = stack.pop();

            if (curr.m == 0 && curr.c == 0 && curr.b == 0) {
                System.out.println("=== Solution Found ===");
                System.out.println(curr.path);
                return;
            }

            if (curr.depth >= maxDepth) continue;

            for (int[] move : moves) {
                int nm = curr.b == 1 ? curr.m - move[0] : curr.m + move[0];
                int nc = curr.b == 1 ? curr.c - move[1] : curr.c + move[1];
                int nb = 1 - curr.b;

                if (isValid(nm, nc)) {
                    String direction = curr.b == 1 ? "Left -> Right" : "Right -> Left";
                    String nextPath = curr.path + "Depth " + (curr.depth + 1) + " - Move " + move[0] + "M, " + move[1] + "C (" + direction + ") | Left Bank: " + nm + "M, " + nc + "C\n";
                    stack.push(new State(nm, nc, nb, curr.depth + 1, nextPath));
                }
            }
        }
        System.out.println("No solution exists within Depth Limit: " + maxDepth);
    }
}
