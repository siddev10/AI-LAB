import java.util.*;

public class Prob12_DLS_WaterJar {
    static int jug1Cap, jug2Cap, target, maxDepth;

    static class State {
        int j1, j2, depth;
        String path;

        State(int j1, int j2, int depth, String path) {
            this.j1 = j1;
            this.j2 = j2;
            this.depth = depth;
            this.path = path;
        }

        public String key() {
            return j1 + "," + j2 + "," + depth;
        }
    }

    static void dls() {
        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        State initial = new State(0, 0, 0, "Start: (0, 0)\n");
        stack.push(initial);
        visited.add(initial.key());

        while (!stack.isEmpty()) {
            State curr = stack.pop();
            int j1 = curr.j1, j2 = curr.j2, d = curr.depth;

            if (j1 == target || j2 == target) {
                System.out.println("=== Solution Found! ===");
                System.out.println(curr.path);
                System.out.println("Goal State: (" + j1 + ", " + j2 + ") at Depth: " + d);
                return;
            }

            if (d >= maxDepth) continue;

            List<State> nextStates = new ArrayList<>();
            nextStates.add(new State(jug1Cap, j2, d + 1, curr.path + "Depth " + (d+1) + " - Fill Jug1: (" + jug1Cap + ", " + j2 + ")\n"));
            nextStates.add(new State(j1, jug2Cap, d + 1, curr.path + "Depth " + (d+1) + " - Fill Jug2: (" + j1 + ", " + jug2Cap + ")\n"));
            nextStates.add(new State(0, j2, d + 1, curr.path + "Depth " + (d+1) + " - Empty Jug1: (0, " + j2 + ")\n"));
            nextStates.add(new State(j1, 0, d + 1, curr.path + "Depth " + (d+1) + " - Empty Jug2: (" + j1 + ", 0)\n"));
            
            int pour1to2 = Math.min(j1, jug2Cap - j2);
            nextStates.add(new State(j1 - pour1to2, j2 + pour1to2, d + 1, curr.path + "Depth " + (d+1) + " - Pour Jug1->Jug2: (" + (j1 - pour1to2) + ", " + (j2 + pour1to2) + ")\n"));
            
            int pour2to1 = Math.min(j2, jug1Cap - j1);
            nextStates.add(new State(j1 + pour2to1, j2 - pour2to1, d + 1, curr.path + "Depth " + (d+1) + " - Pour Jug2->Jug1: (" + (j1 + pour2to1) + ", " + (j2 - pour2to1) + ")\n"));

            for (State s : nextStates) {
                if (!visited.contains(s.key())) {
                    visited.add(s.key());
                    stack.push(s);
                }
            }
        }
        System.out.println("No solution exists within Depth Limit: " + maxDepth);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Water Jar Problem using DLS ===");
        System.out.print("Enter Jug1 capacity: ");
        jug1Cap = sc.nextInt();
        System.out.print("Enter Jug2 capacity: ");
        jug2Cap = sc.nextInt();
        System.out.print("Enter target amount: ");
        target = sc.nextInt();
        System.out.print("Enter Depth Limit (e.g., 3): ");
        maxDepth = sc.nextInt();

        System.out.println("\nSolving...\n");
        dls();
    }
}
