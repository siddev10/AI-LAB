import java.util.*;

public class Prob13_DLS_8Puzzle {
    static String startState, goalState;
    static int maxDepth;

    static class Node {
        String state;
        int emptyPos, depth;
        String path;

        Node(String state, int emptyPos, int depth, String path) {
            this.state = state;
            this.emptyPos = emptyPos;
            this.depth = depth;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== 8-Puzzle Problem using DLS ===");
        System.out.print("Enter Start State (e.g., 123405786): ");
        startState = sc.next();
        System.out.print("Enter Goal State (e.g., 123456780): ");
        goalState = sc.next();
        System.out.print("Enter Depth Limit (e.g., 3): ");
        maxDepth = sc.nextInt();

        if (startState.length() != 9 || goalState.length() != 9) return;

        System.out.println("\nSolving...\n");
        dls();
    }

    static void dls() {
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] dirNames = {"Up", "Down", "Left", "Right"};

        Stack<Node> stack = new Stack<>();
        
        String initialPath = "Start State:\n" + formatGrid(startState) + "\n";
        stack.push(new Node(startState, startState.indexOf('0'), 0, initialPath));

        int nodesExplored = 0;

        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            nodesExplored++;

            if (curr.state.equals(goalState)) {
                System.out.println("=== Solution Found! ===");
                System.out.println(curr.path);
                System.out.println("Nodes explored: " + nodesExplored + ", Found at Depth: " + curr.depth);
                return;
            }

            if (curr.depth >= maxDepth) continue;

            int r = curr.emptyPos / 3, c = curr.emptyPos % 3;

            for (int i = 0; i < 4; i++) {
                int nr = r + moves[i][0], nc = c + moves[i][1];
                if (nr >= 0 && nr < 3 && nc >= 0 && nc < 3) {
                    int newPos = nr * 3 + nc;
                    char[] chars = curr.state.toCharArray();
                    chars[curr.emptyPos] = chars[newPos];
                    chars[newPos] = '0';
                    String newState = new String(chars);

                    String newPath = curr.path + "Depth " + (curr.depth + 1) + " - Move " + dirNames[i] + ":\n" + formatGrid(newState) + "\n";
                    stack.push(new Node(newState, newPos, curr.depth + 1, newPath));
                }
            }
        }
        System.out.println("No solution exists within Depth Limit: " + maxDepth);
    }

    static String formatGrid(String s) {
        return s.substring(0, 3) + "\n" + s.substring(3, 6) + "\n" + s.substring(6, 9);
    }
}
