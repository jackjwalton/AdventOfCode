import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 {


    //https://adventofcode.com/2021/day/11
    public static void main(String[] args) {


        boolean[][] initialDotMap = generateInitialDotMap();
        System.out.println("initial number of dots: " + countDots(initialDotMap));
        List<FoldInstruction> foldInstructionList = generateFoldInstructions();
        boolean[][] foldedMap;

        Scanner input = new Scanner(System.in);
        String selection = null;
        while (selection == null) {
            System.out.println("Select 1 for part 1 input:\nSelect 2 for part 2 input:\n");
            selection = input.next();
            switch (selection) {
                case "1":
                    foldedMap = executeFold(initialDotMap, foldInstructionList.get(0));
                    System.out.println("Number of Dots after 1 Fold: " + countDots(foldedMap));

                    break;
                case "2":
                    foldedMap = initialDotMap;
                    while (foldInstructionList.size() > 0) {
                        foldedMap = executeFold(foldedMap, foldInstructionList.remove(0));
                    }
                    displayFoldedMap(foldedMap);
                    break;
                default:
                    selection = null;
                    System.out.println("Error: please input 1 or 2 for corresponding solution.");

            }
        }
    }


    private static boolean[][] executeFold(boolean[][] dotMap, FoldInstruction foldInstruction) {
        boolean[][] newDotMap;
        int foldLine = foldInstruction.getFoldline();
        char axis = foldInstruction.getAxis();

        System.out.println("Folding on axis: " + foldInstruction.getAxis());
        System.out.println("Folding on line : " + foldLine);
        System.out.println("initial x length: " + dotMap.length);
        System.out.println("initial y length: " + dotMap[0].length);

        switch (axis) {
            case 'x':
                newDotMap = new boolean[foldLine][dotMap[0].length];
                for (int x = 0; x < newDotMap.length; x++) {
                    for (int y = 0; y < newDotMap[0].length; y++) {
                        newDotMap[x][y] = dotMap[x][y] || dotMap[(foldLine + foldLine - x)][y];
                    }
                }
                break;

            case 'y':
                newDotMap = new boolean[dotMap.length][foldLine];
                for (int x = 0; x < newDotMap.length; x++) {
                    for (int y = 0; y < newDotMap[0].length; y++) {
                        newDotMap[x][y] = dotMap[x][y] || dotMap[x][foldLine + foldLine - y];
                    }
                }
                break;
            default:
                System.out.println("you messed up");
                System.out.println("axis: " + axis);
                return null;
        }

        System.out.println("new x length: " + newDotMap.length);
        System.out.println("new y length: " + newDotMap[0].length);
        return newDotMap;
    }

    private static boolean[][] generateInitialDotMap() {
        File file = new File("src/main/resources/inputs/day13.txt");

        boolean[][] initialDotMap = new boolean[1311][895];

        try {
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();

                if (line.length() == 0) {
                    return initialDotMap;
                } else {
                    String[] coordinates = line.split(",");
                    int xPos = Integer.parseInt(coordinates[0]);
                    int yPos = Integer.parseInt(coordinates[1]);

                    initialDotMap[xPos][yPos] = true;
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return initialDotMap;
    }

    private static List<FoldInstruction> generateFoldInstructions() {
        File file = new File("src/main/resources/inputs/day13.txt");
        List<FoldInstruction> foldInstructionList = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                if (input.nextLine().length() == 0) {
                    while (input.hasNextLine()) {
                        String line = input.nextLine();

                        String[] words = line.split(" ");

                        String[] instructions = words[2].split("=");

                        char axis = instructions[0].charAt(0);
                        int element = Integer.parseInt(instructions[1]);

                        foldInstructionList.add(new FoldInstruction(axis, element));
                    }
                }
            }

            return foldInstructionList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static int countDots(boolean[][] dotMap) {
        int counter = 0;
        for (int x = 0; x < dotMap.length; x++) {
            for (int y = 0; y < dotMap[0].length; y++) {
                if (dotMap[x][y]) {
                    counter++;
                }
            }
        }
        return counter;
    }


    private static void displayFoldedMap(boolean[][] dotMap) {
        for (int y = 0; y < dotMap[0].length; y++) {
            for (int x = 0; x < dotMap.length; x++) {
                System.out.print(dotMap[x][y] ? "#" : ".");
            }
            System.out.println();
        }
    }
}
