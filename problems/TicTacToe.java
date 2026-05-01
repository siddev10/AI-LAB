import java.util.Scanner;

public class TicTacToe {

    static char[] board = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    static Scanner sc = new Scanner(System.in);

    // ─── Display Board ───────────────────────────────────────────
    static void printBoard() {
        System.out.println("\n " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println();
    }

    static char checkWinner() {
        int[][] wins = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // cols
                { 0, 4, 8 }, { 2, 4, 6 } // diagonals
        };
        for (int[] w : wins) {
            if (board[w[0]] == board[w[1]] && board[w[1]] == board[w[2]])
                return board[w[0]];
        }
        return ' ';
    }

    // ─── Check Board Full ─────────────────────────────────────────
    static boolean isFull() {
        for (char c : board)
            if (c != 'X' && c != 'O')
                return false;
        return true;
    }

    // ─── Reset Board ──────────────────────────────────────────────
    static void resetBoard() {
        for (int i = 0; i < 9; i++)
            board[i] = (char) ('1' + i);
    }

    // ══════════════════════════════════════════════════════════════
    // NON-AI MODE: Human vs Human
    // ══════════════════════════════════════════════════════════════
    static void humanVsHuman() {
        resetBoard();
        char currentPlayer = 'X';
        System.out.println("\n=== Human vs Human ===");

        while (true) {
            printBoard();
            System.out.print("Player " + currentPlayer + ", enter position (1-9): ");
            int pos = sc.nextInt() - 1;

            if (pos < 0 || pos > 8 || board[pos] == 'X' || board[pos] == 'O') {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            board[pos] = currentPlayer;
            char winner = checkWinner();

            if (winner != ' ') {
                printBoard();
                System.out.println("Player " + winner + " wins!");
                return;
            }
            if (isFull()) {
                printBoard();
                System.out.println("It's a Draw!");
                return;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    // ══════════════════════════════════════════════════════════════
    // AI MODE: Minimax Algorithm
    // ══════════════════════════════════════════════════════════════

    // Minimax: AI = 'O' (maximizer), Human = 'X' (minimizer)
    static int minimax(boolean isMaximizing) {
        char winner = checkWinner();
        if (winner == 'O')
            return 10; // AI wins
        if (winner == 'X')
            return -10; // Human wins
        if (isFull())
            return 0; // Draw

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] != 'X' && board[i] != 'O') {
                    char temp = board[i];
                    board[i] = 'O';
                    best = Math.max(best, minimax(false));
                    board[i] = temp;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] != 'X' && board[i] != 'O') {
                    char temp = board[i];
                    board[i] = 'X';
                    best = Math.min(best, minimax(true));
                    board[i] = temp;
                }
            }
            return best;
        }
    }

    // Find best move for AI
    static int bestMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestPos = -1;

        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                char temp = board[i];
                board[i] = 'O';
                int moveVal = minimax(false);
                board[i] = temp;

                if (moveVal > bestVal) {
                    bestVal = moveVal;
                    bestPos = i;
                }
            }
        }
        return bestPos;
    }

    static void humanVsAI() {
        resetBoard();
        System.out.println("\n=== Human (X) vs AI (O) ===");

        while (true) {
            printBoard();

            // Human turn
            System.out.print("Your turn (1-9): ");
            int pos = sc.nextInt() - 1;
            if (pos < 0 || pos > 8 || board[pos] == 'X' || board[pos] == 'O') {
                System.out.println("Invalid move! Try again.");
                continue;
            }
            board[pos] = 'X';

            char winner = checkWinner();
            if (winner != ' ') {
                printBoard();
                System.out.println("You win!");
                return;
            }
            if (isFull()) {
                printBoard();
                System.out.println("Draw!");
                return;
            }

            // AI turn
            System.out.println("AI is thinking...");
            int aiPos = bestMove();
            board[aiPos] = 'O';
            System.out.println("AI chose position " + (aiPos + 1));

            winner = checkWinner();
            if (winner != ' ') {
                printBoard();
                System.out.println("AI wins!");
                return;
            }
            if (isFull()) {
                printBoard();
                System.out.println("Draw!");
                return;
            }
        }
    }

    // ─── Main ─────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=============================");
        System.out.println("   TIC-TAC-TOE");
        System.out.println("=============================");
        System.out.println("1. Human vs Human (Non-AI)");
        System.out.println("2. Human vs AI (Minimax)");
        System.out.print("Choose mode: ");

        int choice = sc.nextInt();
        if (choice == 1)
            humanVsHuman();
        else
            humanVsAI();
    }
}