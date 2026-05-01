import java.util.*;

public class Prob28_AStar_ShortestRoute {
    static int numNodes, numEdges, startNode, targetNode;
    static int[] heuristics;
    static List<List<int[]>> adj; 

    static class Node implements Comparable<Node> {
        int id, g, h, f;
        String path;

        Node(int id, int g, String path) {
            this.id = id; this.g = g; this.path = path;
            this.h = heuristics[id];
            this.f = this.g + this.h;
        }

        public int compareTo(Node o) { return this.f - o.f; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Shortest Route Problem using A* Search ===");
        System.out.print("Enter number of nodes (0 to N-1): "); numNodes = sc.nextInt();
        
        heuristics = new int[numNodes];
        System.out.println("Enter heuristic value for each node:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("h(" + i + "): ");
            heuristics[i] = sc.nextInt();
        }

        adj = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) adj.add(new ArrayList<>());

        System.out.print("Enter number of edges: "); numEdges = sc.nextInt();
        System.out.println("Enter edges as (u v weight):");
        for (int i = 0; i < numEdges; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt();
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w}); // Assuming undirected
        }

        System.out.print("Enter Start Node: "); startNode = sc.nextInt();
        System.out.print("Enter Target Node: "); targetNode = sc.nextInt();

        System.out.println("\nSolving...\n");
        astar();
    }

    static void astar() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Integer> visited = new HashSet<>();

        pq.add(new Node(startNode, 0, "Start at Node " + startNode + " [g=0, h=" + heuristics[startNode] + "]\n"));
        visited.add(startNode);

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.id == targetNode) {
                System.out.println("=== Route Found ===");
                System.out.println(curr.path);
                System.out.println("Total Path Cost (g): " + curr.g);
                return;
            }

            for (int[] edge : adj.get(curr.id)) {
                int neighbor = edge[0], weight = edge[1];
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor); // this could prevent better paths in general A*, but fine for simple graphs
                    Node next = new Node(neighbor, curr.g + weight, "");
                    next.path = curr.path + "Move to Node " + neighbor + " (cost=" + weight + ") [g=" + next.g + ", h=" + next.h + "]\n";
                    pq.add(next);
                }
            }
        }
        System.out.println("No route found.");
    }
}
