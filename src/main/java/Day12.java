import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

//https://adventofcode.com/2021/day/12
public class Day12 {

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        String selection = null;
        while (selection == null) {
            System.out.println("Select 1 for part 1 input:\nSelect 2 for part 2 input:\n");
            selection = input.next();
            switch (selection) {
                case "1":
                    System.out.println("Number of Unique paths: " + getUniquePaths(buildGraphFromInput(), new CaveVertex("start")));
                    break;
                case "2":

                    break;
                default:
                    selection = null;
                    System.out.println("Error: please input 1 or 2 for corresponding solution.");

            }
        }
    }

    private static int getUniquePaths(Graph graph, CaveVertex currentNode) {

        System.out.println("getting paths from vertex: " + currentNode.getName());

        if (currentNode.getName().equals("end")) {
            return 1;
        } else {
            int paths = 0;
            List<CaveVertex> neighbors = graph.getNeighbors(currentNode);
            for (CaveVertex vertex : neighbors) {
                if (vertex.isLargeCave()) {
                    paths += getUniquePaths(graph, vertex);
                } else {
                    if (!vertex.hasBeenVisited()) {
                        vertex.setWasVisited(true);
                        paths += getUniquePaths(graph, vertex);
                    }
                }
            }
            return paths;
        }


    }


    private static Graph buildGraphFromInput() {

        Graph graph = new Graph();

        File file = new File("src/main/resources/inputs/day12.txt");

        try {
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();

                System.out.println("Parsing " + line);

                String[] vertices = line.split("-");

                graph.addNewEdge(vertices[0], vertices[1]);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
