import java.util.*;

public class Prob04_BFS_Maze {
    static int rows, cols;
    static int[][] maze;
    static int startR, startC, targetR, targetC;

    static class Point {
        int r, c;
        String path;

        Point(int r, int c, String path) {
            this.r = r;
            this.c = c;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Maze Problem using BFS ===");
        System.out.print("Enter number of rows and columns (e.g., 5 5): ");
        rows = sc.nextInt();
        cols = sc.nextInt();

        maze = new int[rows][cols];
        System.out.println("Enter the maze row by row (0 for path, 1 for wall):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = sc.nextInt();
            }
        }

        System.out.print("Enter start position (row col, 0-indexed): ");
        startR = sc.nextInt();
        startC = sc.nextInt();

        System.out.print("Enter target position (row col, 0-indexed): ");
        targetR = sc.nextInt();
        targetC = sc.nextInt();

        System.out.println("\nSolving...\n");
        bfs();
    }

    static void bfs() {
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        q.add(new Point(startR, startC, "Start: (" + startR + ", " + startC + ")\n"));
        visited[startR][startC] = true;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] dirNames = {"Up", "Down", "Left", "Right"};

        while (!q.isEmpty()) {
            Point curr = q.poll();

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
                    q.add(new Point(nr, nc, curr.path + "Move " + dirNames[i] + " to (" + nr + ", " + nc + ")\n"));
                }
            }
        }
        System.out.println("No path found to the target.");
    }
}
