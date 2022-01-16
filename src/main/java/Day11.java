import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day11 {

    //https://adventofcode.com/2021/day/11
    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        String selection = null;
        while (selection == null) {
            System.out.println("Select 1 for part 1 input:\nSelect 2 for part 2 input:\n");
            selection = input.next();
            switch (selection) {
                case "1":
                    System.out.println("Number of flashes after 100 steps: " + simulateFlashes(100, generateBoard()));
                    break;
                case "2":
                    System.out.println("Step when all flash simultaneously: " + getStepOfSimulFlash(generateBoard()));

                    break;
                default:
                    selection = null;
                    System.out.println("Error: please input 1 or 2 for corresponding solution.");

            }
        }
    }

    private static int simulateFlashes(int steps, int[][] board) {
        if (steps <= 0) {
            return 0;

        } else {
            board = flashBoard(board);

            System.out.println(showBoard(board));
            return getFlashes(board) + simulateFlashes(--steps, board);
        }
    }


    private static int[][] generateBoard() {
        int[][] board = new int[10][10];
        File file = new File("src/main/resources/inputs/day11.txt");
        try {
            Scanner input = new Scanner(file);
            int lineCounter = 0;

            while (input.hasNextLine()) {
                String nextLine = input.nextLine();
                for (int i = 0; i < nextLine.length(); i++) {
                    board[lineCounter][i] = Integer.parseInt("" + nextLine.charAt(i));
                }
                lineCounter++;
            }
            return board;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int[][] flashBoard(int[][] board) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j]++;
            }
        }


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] >= 10) {
                    handleFlash(board, i, j);
                }
            }
        }

        return board;

    }

    private static void handleFlash(int[][] board, int i, int j) {
        board[i][j] = 0;

        if (i > 0 && board[i - 1][j] != 0) {
            if (++board[i - 1][j] >= 10) {
                handleFlash(board, i - 1, j);
            }
        }
        if (i < 9 && board[i + 1][j] != 0) {
            if (++board[i + 1][j] >= 10) {
                handleFlash(board, i + 1, j);
            }
        }
        if (j > 0 && board[i][j - 1] != 0) {
            if (++board[i][j - 1] >= 10) {
                handleFlash(board, i, j - 1);
            }
        }
        if (j < 9 && board[i][j + 1] != 0) {
            if (++board[i][j + 1] >= 10) {
                handleFlash(board, i, j + 1);
            }
        }
        if (i > 0 && j > 0 && board[i - 1][j - 1] != 0) {
            if (++board[i - 1][j - 1] >= 10) {
                handleFlash(board, i - 1, j - 1);
            }
        }
        if (i < 9 && j > 0 && board[i + 1][j - 1] != 0) {
            if (++board[i + 1][j - 1] >= 10) {
                handleFlash(board, i + 1, j - 1);
            }
        }
        if (i > 0 && j < 9 && board[i - 1][j + 1] != 0) {
            if (++board[i - 1][j + 1] >= 10) {
                handleFlash(board, i - 1, j + 1);
            }
        }
        if (i < 9 && j < 9 && board[i + 1][j + 1] != 0) {
            if (++board[i + 1][j + 1] >= 10) {
                handleFlash(board, i + 1, j + 1);
            }
        }

    }

    private static int getFlashes(int[][] board) {
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 0) {
                    counter++;
                }
            }
        }
        return counter;

    }


    private static String showBoard(int[][] board) {
        String boardString = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boardString += board[i][j];
            }
            boardString += "\n";
        }
        return boardString;
    }


    private static int getStepOfSimulFlash(int[][] board) {


        int steps = 0;
        do {
            steps++;
            board = flashBoard(board);
        } while (!isSimulFlashing(board));

        System.out.println(showBoard(board));

        return steps;
    }


    private static boolean isSimulFlashing(int[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}