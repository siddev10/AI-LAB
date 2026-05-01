import java.util.*;

public class Prob17_BestFS_WaterJar {
    static int jug1Cap, jug2Cap, target;

    static class State implements Comparable<State> {
        int j1, j2, h;
        String path;

        State(int j1, int j2, String path) {
            this.j1 = j1;
            this.j2 = j2;
            this.path = path;
            this.h = Math.abs(target - j1) + Math.abs(target - j2);
        }

        public String key() { return j1 + "," + j2; }
        public int compareTo(State o) { return this.h - o.h; }
    }

    static void bestfs() {
        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        State initial = new State(0, 0, "Start: (0, 0)\n");
        pq.add(initial);
        visited.add(initial.key());

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            int j1 = curr.j1, j2 = curr.j2;

            if (j1 == target || j2 == target) {
                System.out.println("=== Solution Found! ===");
                System.out.println(curr.path);
                System.out.println("Goal State: (" + j1 + ", " + j2 + ")");
                return;
            }

            List<State> nextStates = new ArrayList<>();
            nextStates.add(new State(jug1Cap, j2, curr.path + "Fill Jug1: (" + jug1Cap + ", " + j2 + ")\n"));
            nextStates.add(new State(j1, jug2Cap, curr.path + "Fill Jug2: (" + j1 + ", " + jug2Cap + ")\n"));
            nextStates.add(new State(0, j2, curr.path + "Empty Jug1: (0, " + j2 + ")\n"));
            nextStates.add(new State(j1, 0, curr.path + "Empty Jug2: (" + j1 + ", 0)\n"));
            
            int pour1to2 = Math.min(j1, jug2Cap - j2);
            nextStates.add(new State(j1 - pour1to2, j2 + pour1to2, curr.path + "Pour Jug1->Jug2: (" + (j1 - pour1to2) + ", " + (j2 + pour1to2) + ")\n"));
            
            int pour2to1 = Math.min(j2, jug1Cap - j1);
            nextStates.add(new State(j1 + pour2to1, j2 - pour2to1, curr.path + "Pour Jug2->Jug1: (" + (j1 + pour2to1) + ", " + (j2 - pour2to1) + ")\n"));

            for (State s : nextStates) {
                if (!visited.contains(s.key())) {
                    visited.add(s.key());
                    pq.add(s);
                }
            }
        }
        System.out.println("No solution exists.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Water Jar Problem using Best First Search ===");
        System.out.print("Enter Jug1 capacity: "); jug1Cap = sc.nextInt();
        System.out.print("Enter Jug2 capacity: "); jug2Cap = sc.nextInt();
        System.out.print("Enter target amount: "); target = sc.nextInt();

        System.out.println("\nSolving...\n");
        bestfs();
    }
}
