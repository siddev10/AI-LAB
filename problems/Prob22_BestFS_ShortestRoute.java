import java.util.*;

public class Prob22_BestFS_ShortestRoute {
    static int numNodes, numEdges, startNode, targetNode;
    static int[] heuristics;
    static List<List<int[]>> adj; // adj list (neighbor, weight)

    static class Node implements Comparable<Node> {
        int id, h;
        String path;

        Node(int id, String path) {
            this.id = id; this.path = path;
            this.h = heuristics[id];
        }

        public int compareTo(Node o) { return this.h - o.h; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Shortest Route Problem using Best First Search ===");
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
        bestfs();
    }

    static void bestfs() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Integer> visited = new HashSet<>();

        pq.add(new Node(startNode, "Start at Node " + startNode + " [h=" + heuristics[startNode] + "]\n"));
        visited.add(startNode);

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.id == targetNode) {
                System.out.println("=== Route Found ===");
                System.out.println(curr.path);
                return;
            }

            for (int[] edge : adj.get(curr.id)) {
                int neighbor = edge[0];
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    Node next = new Node(neighbor, "");
                    next.path = curr.path + "Move to Node " + neighbor + " [h=" + next.h + "]\n";
                    pq.add(next);
                }
            }
        }
        System.out.println("No route found.");
    }
}
