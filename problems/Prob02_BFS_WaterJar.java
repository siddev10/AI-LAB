import java.util.*;

public class Prob02_BFS_WaterJar {

    static int jug1Cap, jug2Cap, target;

    // State: (amt in jug1, amt in jug2)
    static class State {
        int j1, j2;
        String path; // tracks steps taken

        State(int j1, int j2, String path) {
            this.j1 = j1;
            this.j2 = j2;
            this.path = path;
        }

        // For visited set — only compare water amounts
        public String key() {
            return j1 + "," + j2;
        }
    }

    static void bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State initial = new State(0, 0, "Start: (0, 0)\n");
        queue.add(initial);
        visited.add(initial.key());

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            int j1 = curr.j1, j2 = curr.j2;

            // ── Goal check ──────────────────────────────────────
            if (j1 == target || j2 == target) {
                System.out.println("Solution Found!\n");
                System.out.println(curr.path);
                System.out.println("Goal State: (" + j1 + ", " + j2 + ")");
                return;
            }

            // ── Generate all possible next states ───────────────
            List<State> nextStates = new ArrayList<>();

            // 1. Fill Jug1
            nextStates.add(new State(jug1Cap, j2,
                    curr.path + "Fill Jug1: (" + jug1Cap + ", " + j2 + ")\n"));

            // 2. Fill Jug2
            nextStates.add(new State(j1, jug2Cap,
                    curr.path + "Fill Jug2: (" + j1 + ", " + jug2Cap + ")\n"));

            // 3. Empty Jug1
            nextStates.add(new State(0, j2,
                    curr.path + "Empty Jug1: (0, " + j2 + ")\n"));

            // 4. Empty Jug2
            nextStates.add(new State(j1, 0,
                    curr.path + "Empty Jug2: (" + j1 + ", 0)\n"));

            // 5. Pour Jug1 → Jug2
            int pour1to2 = Math.min(j1, jug2Cap - j2);
            nextStates.add(new State(j1 - pour1to2, j2 + pour1to2,
                    curr.path + "Pour Jug1->Jug2: ("
                            + (j1 - pour1to2) + ", " + (j2 + pour1to2) + ")\n"));

            // 6. Pour Jug2 → Jug1
            int pour2to1 = Math.min(j2, jug1Cap - j1);
            nextStates.add(new State(j1 + pour2to1, j2 - pour2to1,
                    curr.path + "Pour Jug2->Jug1: ("
                            + (j1 + pour2to1) + ", " + (j2 - pour2to1) + ")\n"));

            // ── Add unvisited states to queue ───────────────────
            for (State s : nextStates) {
                if (!visited.contains(s.key())) {
                    visited.add(s.key());
                    queue.add(s);
                }
            }
        }

        System.out.println("No solution exists.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Jug1 capacity: ");
        jug1Cap = sc.nextInt();
        System.out.print("Enter Jug2 capacity: ");
        jug2Cap = sc.nextInt();
        System.out.print("Enter target amount: ");
        target = sc.nextInt();

        System.out.println("\n=== Water Jar Problem using BFS ===");
        System.out.println("Jug1: " + jug1Cap + "L, Jug2: "
                + jug2Cap + "L, Target: " + target + "L\n");
        bfs();
    }
}