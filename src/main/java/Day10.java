import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//https://adventofcode.com/2021/day/10
public class Day10 {

    private static final List<Character> OPEN_CHAR_LIST = Arrays.asList('{', '(', '<', '[');
    private static final List<Character> CLOSED_CHAR_LIST = Arrays.asList('}', ')', '>', ']');

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        String selection = null;
        while (selection == null) {
            System.out.println("Select 1 for part 1 input:\nSelect 2 for part 2 input:\n");
            selection = input.next();
            switch (selection) {
                case "1":
                    System.out.println("Illegal Char identifier score: " + getIllegalCharScore());
                    break;
                case "2":
                    System.out.println("Middle score of autocomplete Strings: " + getMiddleScoreOfAutoCompleteStrings());
                    break;
                default:
                    selection = null;
                    System.out.println("Error: please input 1 or 2 for corresponding solution.");

            }
        }


    }

    private static int getIllegalCharScore() {
        File file = new File("src/main/resources/inputs/day10.txt");
        int illegalCharScore = 0;


        try {
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {

                illegalCharScore += getCorruptedCharScoreForLine(input.nextLine());

            }

            return illegalCharScore;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return illegalCharScore;
    }


    private static int getCorruptedCharScoreForLine(String line) {
        Stack<Character> charStack = new Stack<>();


        for (char c : line.toCharArray()) {

            if (OPEN_CHAR_LIST.contains(c)) {
                charStack.push(c);

            } else if (CLOSED_CHAR_LIST.contains(c)) {
                int indexChar = CLOSED_CHAR_LIST.indexOf(c);

                if (!(charStack.pop().equals(OPEN_CHAR_LIST.get(indexChar)))) {
                    switch (c) {
                        case ')':
                            return 3;
                        case ']':
                            return 57;
                        case '}':
                            return 1197;
                        case '>':
                            return 25137;
                        default:
                            System.out.println("0% chance of getting here");
                    }
                }


            } else {
                System.out.println("You messed up buddy.");
                return 0;
            }


        }

        System.out.println("Line is non-corrupted.");
        return 0;
    }


    private static double getMiddleScoreOfAutoCompleteStrings() {
        File file = new File("src/main/resources/inputs/day10.txt");
        List<Double> autoCompleteScores = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                autoCompleteScores.add(getAutoCompleteScoreForLine(input.nextLine()));

            }

            autoCompleteScores.removeAll(Collections.singletonList(0.0));


            Collections.sort(autoCompleteScores);


            int middleIndex = autoCompleteScores.size()/2;


            return autoCompleteScores.get(middleIndex);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }


    private static double getAutoCompleteScoreForLine(String line) {
        Stack<Character> charStack = new Stack<>();

        double autoCompleteScore = 0;

        for (char c : line.toCharArray()) {

            if (OPEN_CHAR_LIST.contains(c)) {
                charStack.push(c);
            } else if (CLOSED_CHAR_LIST.contains(c)) {
                int indexChar = CLOSED_CHAR_LIST.indexOf(c);

                if (!(charStack.pop().equals(OPEN_CHAR_LIST.get(indexChar)))) {
                    return 0;
                }

            } else {
                System.out.println("You messed up buddy.");
                return 0;
            }
        }

        while (!charStack.isEmpty()) {
            autoCompleteScore *= 5;

            switch (charStack.pop()) {
                case '(':
                    autoCompleteScore += 1;
                    break;
                case '[':
                    autoCompleteScore += 2;
                    break;
                case '{':
                    autoCompleteScore += 3;
                    break;
                case '<':
                    autoCompleteScore += 4;
                    break;
                default:
                    System.out.println("seriously bro...");
                    break;
            }

        }



        return autoCompleteScore;
    }
}
