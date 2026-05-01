import java.util.*;

public class Prob08_DFS_8Puzzle {
    static String startState, goalState;

    static class Node {
        String state;
        int emptyPos;
        String path;

        Node(String state, int emptyPos, String path) {
            this.state = state;
            this.emptyPos = emptyPos;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== 8-Puzzle Problem using DFS ===");
        System.out.println("Enter state as 9 contiguous digits (0 for empty space).");
        System.out.print("Enter Start State (e.g., 123405786): ");
        startState = sc.next();
        System.out.print("Enter Goal State (e.g., 123456780): ");
        goalState = sc.next();

        if (startState.length() != 9 || goalState.length() != 9) {
            System.out.println("Invalid input length.");
            return;
        }

        System.out.println("\nSolving...\n");
        dfs();
    }

    static void dfs() {
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
        String[] dirNames = {"Up", "Down", "Left", "Right"};

        Stack<Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        String initialPath = "Start State:\n" + formatGrid(startState) + "\n";
        stack.push(new Node(startState, startState.indexOf('0'), initialPath));
        visited.add(startState);

        int nodesExplored = 0;

        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            nodesExplored++;

            if (curr.state.equals(goalState)) {
                System.out.println("=== Solution Found! ===");
                System.out.println(curr.path);
                System.out.println("Nodes explored: " + nodesExplored);
                return;
            }

            int r = curr.emptyPos / 3;
            int c = curr.emptyPos % 3;

            for (int i = 0; i < 4; i++) {
                int nr = r + moves[i][0];
                int nc = c + moves[i][1];

                if (nr >= 0 && nr < 3 && nc >= 0 && nc < 3) {
                    int newPos = nr * 3 + nc;
                    char[] chars = curr.state.toCharArray();
                    chars[curr.emptyPos] = chars[newPos];
                    chars[newPos] = '0';
                    String newState = new String(chars);

                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        String newPath = curr.path + "Move " + dirNames[i] + ":\n" + formatGrid(newState) + "\n";
                        stack.push(new Node(newState, newPos, newPath));
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
