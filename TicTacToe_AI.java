import java.util.Scanner;

public class TicTacToe_AI {

    static char[][] board = new char[3][3];
    static void initBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }
    static void printBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print(" | ");
            }
            System.out.println();
            if (i < 2) System.out.println("--+---+--");
        }
        System.out.println();
    }

    static boolean isWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    static boolean isDraw() {

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') return false;
        return true;
    }


    static int minimax(boolean isMaximizing) {
        if (isWinner('O')) return 10;
        if (isWinner('X')) return -10;
        if (isDraw()) return 0;
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(false);
                        board[i][j] = ' ';
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(true);
                        board[i][j] = ' ';
                        bestScore = Math.min(bestScore, score);
                    }

                }
            }
            return bestScore;
        }
    }

    static void bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1, moveCol = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        moveRow = i;
                        moveCol = j;
                    }
                }
            }
        }
        board[moveRow][moveCol] = 'O';
    }
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    initBoard();
    System.out.println("Tic-Tac-Toe AI using Minimax Algorithm");
    System.out.println("You are X, AI is O");
    System.out.println("Enter row and column (0-2)");

    while (true) {
        printBoard();
        int r, c;
        System.out.print("Your move: ");
        r = sc.nextInt();
        c = sc.nextInt();
        if (board[r][c] != ' ') {
            System.out.println("Invalid move! Try again.");
            continue;
        }
        board[r][c] = 'X';
        if (isWinner('X')) {
            printBoard();
            System.out.println("You win!");
            break;
        }
        if (isDraw()) {
            printBoard();
            System.out.println("It's a draw!");
            break;
        }
        bestMove();
        if (isWinner('O')) {
            printBoard();
            System.out.println("AI wins!");
            break;
        }
        if (isDraw()) {
            printBoard();
            System.out.println("It's a draw!");
            break;
        }
    }

    sc.close();
    }
}