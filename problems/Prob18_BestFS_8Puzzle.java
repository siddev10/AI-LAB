import java.util.*;

public class Prob18_BestFS_8Puzzle {
    static String startState, goalState;

    static class Node implements Comparable<Node> {
        String state;
        int emptyPos, h;
        String path;

        Node(String state, int emptyPos, String path) {
            this.state = state;
            this.emptyPos = emptyPos;
            this.path = path;
            this.h = calcH(state);
        }

        int calcH(String s) {
            int dist = 0;
            for (int i = 0; i < 9; i++) {
                char c = s.charAt(i);
                if (c != '0') {
                    int goalIdx = goalState.indexOf(c);
                    dist += Math.abs(i / 3 - goalIdx / 3) + Math.abs(i % 3 - goalIdx % 3);
                }
            }
            return dist;
        }

        public int compareTo(Node o) { return this.h - o.h; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== 8-Puzzle Problem using Best First Search ===");
        System.out.print("Enter Start State (e.g., 123405786): "); startState = sc.next();
        System.out.print("Enter Goal State (e.g., 123456780): "); goalState = sc.next();

        if (startState.length() != 9 || goalState.length() != 9) return;

        System.out.println("\nSolving...\n");
        bestfs();
    }

    static void bestfs() {
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] dirNames = {"Up", "Down", "Left", "Right"};

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        String initialPath = "Start State (h=" + new Node(startState, 0, "").h + "):\n" + formatGrid(startState) + "\n";
        pq.add(new Node(startState, startState.indexOf('0'), initialPath));
        visited.add(startState);

        int nodesExplored = 0;

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            nodesExplored++;

            if (curr.state.equals(goalState)) {
                System.out.println("=== Solution Found! ===");
                System.out.println(curr.path);
                System.out.println("Nodes explored: " + nodesExplored);
                return;
            }

            int r = curr.emptyPos / 3, c = curr.emptyPos % 3;

            for (int i = 0; i < 4; i++) {
                int nr = r + moves[i][0], nc = c + moves[i][1];
                if (nr >= 0 && nr < 3 && nc >= 0 && nc < 3) {
                    int newPos = nr * 3 + nc;
                    char[] chars = curr.state.toCharArray();
                    chars[curr.emptyPos] = chars[newPos];
                    chars[newPos] = '0';
                    String newState = new String(chars);

                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        Node nextNode = new Node(newState, newPos, "");
                        nextNode.path = curr.path + "Move " + dirNames[i] + " (h=" + nextNode.h + "):\n" + formatGrid(newState) + "\n";
                        pq.add(nextNode);
                    }
                }
            }
        }
        System.out.println("No solution exists.");
    }

    static String formatGrid(String s) {
        return s.substring(0, 3) + "\n" + s.substring(3, 6) + "\n" + s.substring(6, 9);
    }
}
