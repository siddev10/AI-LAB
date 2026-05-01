import java.util.*;

public class Prob01_TwoPlayerGame {
    static char[] board = new char[9];
    static char humanPlayer, aiPlayer;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Tic-Tac-Toe: Human vs AI (Minimax) ===");
        System.out.print("Choose your player (X or O, X goes first): ");
        String choice = sc.next().toUpperCase();
        if (choice.equals("O")) {
            humanPlayer = 'O'; aiPlayer = 'X';
        } else {
            humanPlayer = 'X'; aiPlayer = 'O';
        }

        Arrays.fill(board, ' ');
        printBoard();
        
        boolean isHumanTurn = (humanPlayer == 'X');
        while (true) {
            if (isHumanTurn) humanMove();
            else aiMove();

            printBoard();
            
            if (checkWinner(humanPlayer)) { System.out.println("You win!"); break; }
            if (checkWinner(aiPlayer)) { System.out.println("AI wins!"); break; }
            if (isDraw()) { System.out.println("It's a draw!"); break; }

            isHumanTurn = !isHumanTurn;
        }
    }

    static void printBoard() {
        System.out.println("\nBoard:");
        for (int i = 0; i < 9; i += 3) {
            System.out.println(" " + board[i] + " | " + board[i+1] + " | " + board[i+2]);
            if (i < 6) System.out.println("---+---+---");
        }
        System.out.println();
    }

    static void humanMove() {
        System.out.print("Enter your move (1-9): ");
        while (true) {
            int move = sc.nextInt() - 1;
            if (move >= 0 && move < 9 && board[move] == ' ') {
                board[move] = humanPlayer;
                System.out.println("Human placed " + humanPlayer + " at position " + (move + 1));
                break;
            } else {
                System.out.print("Invalid move. Try again: ");
            }
        }
    }

    static void aiMove() {
        System.out.println("AI is thinking...");
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = aiPlayer;
                int score = minimax(board, 0, false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        board[move] = aiPlayer;
        System.out.println("AI placed " + aiPlayer + " at position " + (move + 1));
    }

    static int minimax(char[] b, int depth, boolean isMax) {
        if (checkWinner(aiPlayer)) return 10 - depth;
        if (checkWinner(humanPlayer)) return depth - 10;
        if (isDraw()) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (b[i] == ' ') {
                    b[i] = aiPlayer;
                    best = Math.max(best, minimax(b, depth + 1, false));
                    b[i] = ' ';
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (b[i] == ' ') {
                    b[i] = humanPlayer;
                    best = Math.min(best, minimax(b, depth + 1, true));
                    b[i] = ' ';
                }
            }
            return best;
        }
    }

    static boolean checkWinner(char p) {
        int[][] wins = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int[] w : wins) {
            if (board[w[0]] == p && board[w[1]] == p && board[w[2]] == p) return true;
        }
        return false;
    }

    static boolean isDraw() {
        for (char c : board) if (c == ' ') return false;
        return true;
    }
}
