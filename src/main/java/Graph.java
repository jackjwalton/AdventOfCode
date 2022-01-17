import java.net.CacheRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {

    //creating an object of the Map class that stores the edges of the graph
    private Map<CaveVertex, List<CaveVertex>> edgeMap = new HashMap<>();


    public void addVertex(CaveVertex vertex) {
        edgeMap.put(vertex, new LinkedList<>());
    }


    public void addNewEdge(String source, String destination) {

        if (!containsVertex(source)) {
            addVertex(new CaveVertex(source));
        }
        if (!containsVertex(destination)) {
            addVertex(new CaveVertex(destination));
        }

        edgeMap.get(getVertexByName(source)).add(getVertexByName(destination));
        edgeMap.get(getVertexByName(destination)).add(getVertexByName(source));
    }


    public List<CaveVertex> getNeighbors(CaveVertex target) {
        return edgeMap.get(target);
    }


    public boolean containsVertex(String target) {
        for (CaveVertex vertex : edgeMap.keySet()) {
            if (vertex.getName().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public CaveVertex getVertexByName(String target) {
        for (CaveVertex vertex : edgeMap.keySet()) {
            if (vertex.getName().equals(target)) {
                return vertex;
            }
        }
        System.out.println("vertex does not exist :(");
        return null;
    }

}
