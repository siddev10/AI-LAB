import java.util.*;

public class Prob19_BestFS_Maze {
    static int rows, cols;
    static int[][] maze;
    static int startR, startC, targetR, targetC;

    static class Point implements Comparable<Point> {
        int r, c, h;
        String path;

        Point(int r, int c, String path) {
            this.r = r; this.c = c; this.path = path;
            this.h = Math.abs(r - targetR) + Math.abs(c - targetC);
        }

        public int compareTo(Point o) { return this.h - o.h; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Maze Problem using Best First Search ===");
        System.out.print("Enter number of rows and columns: ");
        rows = sc.nextInt(); cols = sc.nextInt();

        maze = new int[rows][cols];
        System.out.println("Enter the maze (0 for path, 1 for wall):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) maze[i][j] = sc.nextInt();
        }

        System.out.print("Enter start position (row col): ");
        startR = sc.nextInt(); startC = sc.nextInt();

        System.out.print("Enter target position (row col): ");
        targetR = sc.nextInt(); targetC = sc.nextInt();

        System.out.println("\nSolving...\n");
        bestfs();
    }

    static void bestfs() {
        PriorityQueue<Point> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[rows][cols];

        Point initial = new Point(startR, startC, "Start: (" + startR + ", " + startC + ")\n");
        pq.add(initial);
        visited[startR][startC] = true;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] dirNames = {"Up", "Down", "Left", "Right"};

        while (!pq.isEmpty()) {
            Point curr = pq.poll();

            if (curr.r == targetR && curr.c == targetC) {
                System.out.println("=== Path Found ===");
                System.out.println(curr.path);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nr = curr.r + dirs[i][0];
                int nc = curr.c + dirs[i][1];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && maze[nr][nc] == 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    Point next = new Point(nr, nc, "");
                    next.path = curr.path + "Move " + dirNames[i] + " to (" + nr + ", " + nc + ") [h=" + next.h + "]\n";
                    pq.add(next);
                }
            }
        }
        System.out.println("No path found.");
    }
}
