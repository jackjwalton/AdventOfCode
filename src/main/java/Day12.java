import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//https://adventofcode.com/2021/day/12
public class Day12 {

    public static void main(String[] args) {
        Graph graph = buildGraphFromInput();
        CaveVertex start = graph.getVertexByName("start");
        List<CaveVertex> visitedList = new ArrayList<>();
        visitedList.add(start);


        Scanner input = new Scanner(System.in);
        String selection = null;
        while (selection == null) {
            System.out.println("Select 1 for part 1 input:\nSelect 2 for part 2 input:\n");
            selection = input.next();
            switch (selection) {
                case "1":
                    System.out.println("Number of Unique paths: " + getUniquePaths(graph, start, visitedList));
                    break;
                case "2":
                    System.out.println("Number of Unique paths: " + getUniquePathsV2(graph, start, visitedList, false));
                    break;
                default:
                    selection = null;
                    System.out.println("Error: please input 1 or 2 for corresponding solution.");

            }
        }
    }

    private static int getUniquePaths(Graph graph, CaveVertex currentNode, List<CaveVertex> visitedNodes) {

        System.out.println("getting paths from vertex: " + currentNode.getName());

        if (currentNode.getName().equals("end")) {
            return 1;
        } else {
            int paths = 0;
            List<CaveVertex> neighbors = graph.getNeighbors(currentNode);
            System.out.print("visited Path: ");
            printNodeList(visitedNodes);

            System.out.print("Before: ");
            printNodeList(neighbors);

            List<CaveVertex> filteredNeighbors = new ArrayList<>();
            filteredNeighbors.addAll(neighbors);


            filteredNeighbors.removeAll(visitedNodes);


            System.out.print("After: ");
            printNodeList(filteredNeighbors);

            for (CaveVertex vertex : filteredNeighbors) {
                if (vertex.isLargeCave()) {
                    paths += getUniquePaths(graph, vertex, visitedNodes);
                } else { //A small cave
                    List<CaveVertex> copyOfVisitedNodes = new ArrayList<>();
                    copyOfVisitedNodes.addAll(visitedNodes);

                    copyOfVisitedNodes.add(vertex);

                    paths += getUniquePaths(graph, vertex, copyOfVisitedNodes);
                }
            }
            System.out.println("Exhausted All nodes for this path");
            System.out.println("Number of paths to end reached: " + paths);
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


            return graph;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void printNodeList(List<CaveVertex> nodeList) {
        for (CaveVertex vertex : nodeList) {
            System.out.print(vertex.getName() + ",");
        }
        System.out.println();
    }

    private static int getUniquePathsV2(Graph graph, CaveVertex currentNode, List<CaveVertex> visitedNodes, boolean hasVisitedCaveTwice) {

        if (currentNode.getName().equals("end")) {
            return 1;
        } else {
            int paths = 0;
            List<CaveVertex> neighbors = graph.getNeighbors(currentNode);

            List<CaveVertex> filteredNeighbors = new ArrayList<>();
            filteredNeighbors.addAll(neighbors);

            if (hasVisitedCaveTwice) {
                filteredNeighbors.removeAll(visitedNodes);
            } else {
                filteredNeighbors.remove(graph.getVertexByName("start"));
            }



            for (CaveVertex vertex : filteredNeighbors) {
                if (vertex.isLargeCave()) {
                    paths += getUniquePathsV2(graph, vertex, visitedNodes, hasVisitedCaveTwice);
                } else { //A small cave
                    List<CaveVertex> copyOfVisitedNodes = new ArrayList<>();
                    copyOfVisitedNodes.addAll(visitedNodes);

                    if (!hasVisitedCaveTwice && visitedNodes.contains(vertex)) {
                        paths += getUniquePathsV2(graph, vertex, copyOfVisitedNodes, true);
                    } else {
                        copyOfVisitedNodes.add(vertex);
                        paths += getUniquePathsV2(graph, vertex, copyOfVisitedNodes, hasVisitedCaveTwice);

                    }
                }
            }

            return paths;
        }
    }


}
