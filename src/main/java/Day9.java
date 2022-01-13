import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**Notes:
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
                    int product = getProductOfLargestBasins();
                    System.out.println("Product of Largest Basins: " + product);

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
                    System.out.println("Lowpoint found: " + target);
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


    public static int getProductOfLargestBasins() {

        List<Integer> listOfBasinSizes = getListOfBasinsBySize();
        if (listOfBasinSizes == null) {
            return 0;
        }

        return 0;
    }


    private static List<Integer> getListOfBasinsBySize() {

        int[][] inputAsIntArray = getInputAsIntArray();
        if (inputAsIntArray == null) {
            return null;
        }

        List<Lowpoint> lowpointList = getListOfLowpoints();

        for(Lowpoint lowpoint : lowpointList){
            int basinSize = findBasinSize(lowpoint, inputAsIntArray);
        }




        return null;
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


    private static int findBasinSize(int target, int yPos, int xPos, int[][] board){
        int sum = 0;
        if(yPos >= 0){
            if(target > board[yPos-1][xPos]){
                return 1 + findBasinSize(board[yPos-1][xPos], yPos -1, xPos, board);
            }
        }
    }

    private boolean isHighpoint(Lowpoint lowpoint, int[][] board){
        int target = lowpoint.lowpointValue;
        boolean couldBeLowpoint;


        if(lowpoint.yPos >= 0){
            couldBeLowpoint = target > board[lowpoint.yPos-1][lowpoint.xPos];
        }
        if(lowpoint.xPos >= 0){
            couldBeLowpoint = target > board[lowpoint.xPos-1][lowpoint.xPos];
        }
    }

}
