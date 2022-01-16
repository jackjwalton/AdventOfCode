import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Notes:
 * Reads input way too many times. A consequence of speedy 2-part problems. Refactoring part 1 would solve this.
 */
public class Day9 {

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        String selection = null;
        while (selection == null) {
            System.out.println("Select 1 for part 1 input:\nSelect 2 for part 2 input:\n");
            selection = input.next();
            switch (selection) {
                case "1":
                    int sum = getSumOfLowpoints();
                    System.out.println("Sum of lowpoints: " + sum);
                    break;
                case "2":
                    int product = getProductOfLargestThreeBasins();
                    System.out.println("Product of Largest Basins: " + product);
                    break;
                default:
                    selection = null;
                    System.out.println("Error: please input 1 or 2 for corresponding solution.");

            }
        }


    }

    public static int getSumOfLowpoints() {
        int totalSum = 0;
        List<Lowpoint> lowpointList = getListOfLowpoints();
        for (int i = 0; i < lowpointList.size(); i++) {
            totalSum += 1 + lowpointList.get(i).lowpointValue;
        }

        return totalSum;


    }

    private static List<Lowpoint> getListOfLowpoints() {
        File file = new File("src/main/resources/inputs/day9.txt");

        List<Lowpoint> lowpointList = new ArrayList<>();
        int yPos = 0;

        try {
            Scanner input = new Scanner(file);

            String previousLine = "";
            String currentLine = input.nextLine();


            do {
                String nextLine = input.nextLine();
                int previous = Integer.MAX_VALUE;


                for (int xPos = 0; xPos < currentLine.length(); xPos++) {
                    int target = Integer.parseInt("" + currentLine.charAt(xPos));
                    boolean couldBeLowpoint = target < previous;

                    if (couldBeLowpoint) {
                        int below = Integer.parseInt("" + nextLine.charAt(xPos));
                        couldBeLowpoint = target < below;
                    }

                    if (couldBeLowpoint && xPos != currentLine.length() - 1) { //last index
                        int next = Integer.parseInt("" + currentLine.charAt(xPos + 1));
                        couldBeLowpoint = target < next;

                    }

                    if (couldBeLowpoint && previousLine != "") { //last index
                        int above = Integer.parseInt("" + previousLine.charAt(xPos));
                        couldBeLowpoint = target < above;

                    }


                    if (couldBeLowpoint) {
                        lowpointList.add(new Lowpoint(target, yPos, xPos));
                    }
                    previous = target;
                }

                previousLine = currentLine;
                currentLine = nextLine;
                yPos++;
            } while (input.hasNextLine());

            //At Last Line
            int previous = Integer.MAX_VALUE;

            for (int xPos = 0; xPos < currentLine.length(); xPos++) {
                int target = Integer.parseInt("" + currentLine.charAt(xPos));
                boolean couldBeLowpoint = target < previous;

                if (couldBeLowpoint && xPos != currentLine.length() - 1) { //last index
                    int next = Integer.parseInt("" + currentLine.charAt(xPos + 1));
                    couldBeLowpoint = target < next;

                }

                if (couldBeLowpoint && previousLine != "") { //last index
                    int above = Integer.parseInt("" + previousLine.charAt(xPos));
                    couldBeLowpoint = target < above;

                }


                if (couldBeLowpoint) {
//                    System.out.println("Lowpoint found: " + target);
                    lowpointList.add(new Lowpoint(target, yPos, xPos));
                }
                previous = target;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lowpointList;
    }


    ///Part 2 Code


    private static int getProductOfLargestThreeBasins() {

        int firstLargest;
        int secondLargest;
        int thirdLargest;
        firstLargest = secondLargest = thirdLargest = 0; //if < 3 basins, return 0


        int[][] inputAsIntArray = getInputAsIntArray();
        if (inputAsIntArray == null) {
            return 0;
        }

        List<Lowpoint> lowpointList = getListOfLowpoints();

        for (Lowpoint lowpoint : lowpointList) {
            System.out.println("Finding basin for lowpoint: " + lowpoint.xPos + "," + lowpoint.yPos + "," + lowpoint.lowpointValue);

            boolean[][] defaultHitBoard = new boolean[inputAsIntArray.length][inputAsIntArray[0].length];

            List<Lowpoint> basin = generateBasin(lowpoint, inputAsIntArray, defaultHitBoard, new ArrayList<>());
            int target = basin.size();

            System.out.println("Basin size: " + target);


            if (target > firstLargest) {
                thirdLargest = secondLargest;
                secondLargest = firstLargest;
                firstLargest = target;
            } else if (target > secondLargest) {
                thirdLargest = secondLargest;
                secondLargest = target;
            } else if (target > thirdLargest) {
                thirdLargest = target;
            }


            System.out.println("Largest Basins: {" + firstLargest + "," + secondLargest + "," + thirdLargest + "}");
        }


        return firstLargest * secondLargest * thirdLargest;
    }


    private static int[][] getInputAsIntArray() {
        int[][] inputArray = null;
        File file = new File("src/main/resources/inputs/day9.txt");
        try {
            Scanner input = new Scanner(file);

            int totalLinesInInput = 0;
            while (input.hasNextLine()) {
                totalLinesInInput++;
                input.nextLine();
            }


            input = new Scanner(file);
            if (totalLinesInInput > 0) {
                String line = input.nextLine();
                int lineCounter = 0;
                inputArray = new int[totalLinesInInput][line.length()];

                for (int i = 0; i < line.length(); i++) {
                    inputArray[lineCounter][i] = Integer.parseInt("" + line.charAt(i));
                }

                while (input.hasNextLine()) {
                    String nextLine = input.nextLine();
                    lineCounter++;
                    for (int i = 0; i < nextLine.length(); i++) {
                        inputArray[lineCounter][i] = Integer.parseInt("" + nextLine.charAt(i));
                    }
                }

                return inputArray;

            } else {
                return null;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputArray;
    }


    private static List<Lowpoint> generateBasin(Lowpoint lowpoint, int[][] board, boolean[][] hitBoard, List<Lowpoint> basin) {
        int yPos = lowpoint.yPos;
        int xPos = lowpoint.xPos;
        int target = lowpoint.lowpointValue;

        System.out.println("New target Lowpoint: {" + yPos + "," + xPos + "," + target + "}");

        if(target == 9){
            return basin;
        }


        basin.add(new Lowpoint(target, yPos,xPos));
        hitBoard[yPos][xPos] = true;


        System.out.println("Basin size: " + basin.size());


        if (yPos > 0) {
            if (target < board[yPos - 1][xPos] && !hitBoard[yPos - 1][xPos]) {
                generateBasin(new Lowpoint(board[yPos - 1][xPos], yPos - 1, xPos), board, hitBoard, basin);
            }
        }
        if (yPos < board.length - 1) {

            if (target < board[yPos + 1][xPos] && !hitBoard[yPos + 1][xPos]) {
                generateBasin(new Lowpoint(board[yPos + 1][xPos], yPos + 1, xPos), board, hitBoard, basin);
            }
        }

        if (xPos > 0) {

            if (target < board[yPos][xPos - 1] && !hitBoard[yPos][xPos-1]) {
                generateBasin(new Lowpoint(board[yPos][xPos - 1], yPos, xPos - 1), board, hitBoard, basin);
            }
        }
        if (xPos < board[0].length - 1) {

            if (target < board[yPos][xPos + 1] && !hitBoard[yPos][xPos+1]) {
                generateBasin(new Lowpoint(board[yPos][xPos + 1], yPos, xPos + 1), board, hitBoard, basin);
            }
        }

        return basin;
    }


}
