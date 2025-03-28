import java.util.ArrayList; // Importing the ArrayList Class from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.HashMap; // Importing the HashMap Class from java.util to Implement the Adjacency List.
import java.util.List; // Importing the List Interface from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.Map; // Importing the Map Interface from java.util to Implement the Adjacency List.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from java.util to be Thrown when there is an attempt to access a Vertex which is not in the Graph.
import java.util.Set; // Importing the Set Interface from java.util to return all the Vertices in the Graph as a Set which prevents Duplicates.

/**
 * @author LewisThackeray
 * @date 27/03/2025
 *
 * {@code DirectedGraph} implements a <em>Directed Graph</em> which is represented using an Adjacency List Data Structure.  <p>A <b>Directed Graph</b> is a set of vertices and collection of
 * directed edges where each directed edge connects an ordered pair of vertices..</p>
 *
 * @param Vertex is a Generic Type for the Vertices stored in the <em>Directed Graph</em>.
 */

public class DirectedGraph<Vertex> {

    private Map<Vertex, List<Vertex>> adjacencyList; // Creating an Adjacency List which Stores the Directed Graph.

    /**
     * This method constructs an Empty Directed Graph using an Adjacency List representation.
     */

    public DirectedGraph() {adjacencyList = new HashMap<>();}

    /**
     * This method adds a Vertex to the Directed Graph if the Vertex isn't already a member.
     * @param vertex of type Vertex is the Vertex to be added to the Directed Graph.
     */

    public void addVertex(Vertex vertex) {if (!adjacencyList.containsKey(vertex)) adjacencyList.put(vertex, new ArrayList<>());}

    /**
     * This method adds a Directed Edge between Two Vertices in the Directed Graph, and if either Vertex doesn't exist, it will be Added automatically.
     * @param source of type Vertex is the Source Vertex of the Edge.
     * @param destination of type Vertex is the Destination Vertex of the Edge.
     * @throws IllegalArgumentException if either Vertex is NULL.
     */

    public void addEdge(Vertex source, Vertex destination) {
        if (source == null || destination == null) throw new IllegalArgumentException("The Source and Destination Vertices cannot be NULL!"); addVertex(source); addVertex(destination);
        adjacencyList.get(source).add(destination);
    }

    /**
     * This method removes a Directed Edge between Two Vertices in the Directed Graoh, and if the Directed Edge exists.
     * @param source of type Vertex is the Source Vertex of the Edge.
     * @param destination of type Vertex is the Destination Vertex of the Edge.
     */

    public void removeEdge(Vertex source, Vertex destination) {if (adjacencyList.containsKey(source)) adjacencyList.get(source).remove(destination);}

    /**
     * This method removes a Vertex and all the Edges which it is a Source of from the Directed Graph.
     * @param vertex of type Vertex is the Vertex to be removed from the Directed Graph.
     */

    public void removeVertex(Vertex vertex) {
        if (adjacencyList.containsKey(vertex)) {adjacencyList.remove(vertex); for (Vertex aVertex : adjacencyList.keySet()) adjacencyList.get(aVertex).remove(vertex);}
    }

    /**
     * This method returns the List of Neighbouring Vertices (Outgoing Edges) for a Specified Vertex in the Directed Graph.
     * @param vertex of type Vertex is the Vertex whose Neighbours are to be Retrieved.
     * @return a List of Adjacent Vertices to the Vertex passed into the method.
     * @throws NoSuchElementException if the Vertex is not in the Undirected Graph.
     */

    public List<Vertex> getNeighbours(Vertex vertex) {
        List<Vertex> neighbours = adjacencyList.get(vertex); if (neighbours == null) throw new NoSuchElementException("Vertex " + vertex + " is not in the Directed Graph!"); return neighbours;
    }

    /**
     * This method tests the {@code DirectedGraph} Class by Adding and Removing Vertices and Edges.
     * @param args the Command-Line Arguments.
     */

    public static void main(String[] args) {
        DirectedGraph<String> graph = new DirectedGraph<>(); graph.addVertex("Apple"); graph.addVertex("Banana"); graph.addVertex("Orange"); graph.addVertex("Strawberry");
        graph.addVertex("Kiwi"); graph.addVertex("Blueberry"); graph.addVertex("Raspberry"); graph.addVertex("Peach"); graph.addVertex("Coconut"); graph.addVertex("Grape");

        graph.addEdge("Apple", "Banana"); graph.addEdge("Apple", "Peach"); graph.addEdge("Apple", "Coconut"); graph.addEdge("Apple", "Strawberry"); graph.addEdge("Apple", "Raspberry");
        System.out.println("Neighbours of Apple: " + graph.getNeighbours("Apple")); System.out.println("All Tests Passed Successfully!");
    }
}