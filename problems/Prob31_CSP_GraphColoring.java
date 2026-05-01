import java.util.*;

public class Prob31_CSP_GraphColoring {
    static int V, numColors;
    static int[][] graph;
    static int[] color;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Graph Coloring CSP ===");
        System.out.print("Enter number of vertices: "); V = sc.nextInt();
        
        graph = new int[V][V];
        System.out.println("Enter adjacency matrix (row by row, 0 or 1):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) graph[i][j] = sc.nextInt();
        }

        System.out.print("Enter number of available colors: "); numColors = sc.nextInt();
        
        color = new int[V];
        Arrays.fill(color, 0);

        System.out.println("\nSolving...\n");
        if (graphColoringUtil(0)) {
            System.out.println("=== Solution Found! ===");
            for (int i = 0; i < V; i++) {
                System.out.println("Vertex " + i + " ---> Color " + color[i]);
            }
        } else {
            System.out.println("Solution does not exist for " + numColors + " colors.");
        }
    }

    static boolean isSafe(int v, int c) {
        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && color[i] == c) return false;
        }
        return true;
    }

    static boolean graphColoringUtil(int v) {
        if (v == V) return true; // All vertices colored

        for (int c = 1; c <= numColors; c++) {
            if (isSafe(v, c)) {
                color[v] = c;
                System.out.println("Assigned Color " + c + " to Vertex " + v);

                if (graphColoringUtil(v + 1)) return true;

                // Backtrack
                System.out.println("Backtracking from Vertex " + v + " (Color " + c + " didn't lead to solution)");
                color[v] = 0;
            }
        }
        return false; // No valid color found for this vertex
    }
}
