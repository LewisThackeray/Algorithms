import java.util.ArrayList; // Importing the ArrayList Class from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.HashMap; // Importing the HashMap Class from java.util to Implement the Adjacency List.
import java.util.List; // Importing the List Interface from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.Map; // Importing the Map Interface from java.util to Implement the Adjacency List.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from java.util to be Thrown when there is an attempt to access a Vertex which is not in the Graph.
import java.util.Set; // Importing the Set Interface from java.util to return all the Vertices in the Graph as a Set which prevents Duplicates.
import java.util.HashSet; // Importing the HashSet Class from java.util to Track the Vertices that have been Visited during a Depth First or Breadth First Search.
import java.util.Queue; // Importing the Queue Interface from java.util to Store the Vertices in a Graph when Performing a Breadth First Search.
import java.util.LinkedList; // Importing the LinkedList Class form java.util to Implement the Queue Interface as a Linked List.

/**
 * @author LewisThackeray
 * @date 27/03/2025
 *
 * {@code UndirectedGraph} implements an <em>Undirected Graph</em> which is represented using an Adjacency List Data Structure.  <p>An <b>Undirected Graph</b> is a set of vertices and collection
 * of edges that each connect a pair of vertices.</p>
 *
 * @param Vertex is a Generic Type for the Vertices stored in the <em>Undirected Graph</em>.
 */

public class UndirectedGraph<Vertex> {

    private Map<Vertex, List<Vertex>> adjacencyList; // Creating an Adjacency List which Stores the Undirected Graph.

    /**
     * This method constructs an Empty Undirected Graph using an Adjacency List representation.
     */

    public UndirectedGraph() {adjacencyList = new HashMap<>();}

    /**
     * This method adds a Vertex to the Undirected Graph if the Vertex isn't already a member.
     * @param vertex of type Vertex is the Vertex to be added to the Undirected Graph.
     */

    public void addVertex(Vertex vertex) {if (!adjacencyList.containsKey(vertex)) adjacencyList.put(vertex, new ArrayList<>());}

    /**
     * This method adds an Undirected Edge between Two Vertices in the Undirected Graph, and if either Vertex doesn't exist, it will be Added automatically.
     * @param vertex1 of type Vertex is the First Vertex of the Edge.
     * @param vertex2 of type Vertex is the Second Vertex of the Edge.
     * @throws IllegalArgumentException if either Vertex is NULL.
     */

    public void addEdge(Vertex vertex1, Vertex vertex2) {
        if (vertex1 == null || vertex2 == null) throw new IllegalArgumentException("The Vertex/Vertices cannot be NULL!"); addVertex(vertex1); addVertex(vertex2);
        adjacencyList.get(vertex1).add(vertex2); adjacencyList.get(vertex2).add(vertex1);
    }

    /**
     * This method removes an Undirected Edge between Two Vertices in the Undirected Graph, if the Undirected Edge exists.
     * @param vertex1 of type Vertex is the First Vertex of the Edge.
     * @param vertex2 of type Vertex is the Second Vertex of the Edge.
     */

    public void removeEdge(Vertex vertex1, Vertex vertex2) {
        if (adjacencyList.containsKey(vertex1) && adjacencyList.containsKey(vertex2)) {adjacencyList.get(vertex1).remove(vertex2); adjacencyList.get(vertex2).remove(vertex1);}
    }

    /**
     * This method removes a Vertex and all its associated Edges from the Undirected Graph.
     * @param vertex of type Vertex is the Vertex to be removed from the Undirected Graph.
     */

    public void removeVertex(Vertex vertex) {
        if (adjacencyList.containsKey(vertex)) {
            List<Vertex> neighbours = new ArrayList<>(adjacencyList.get(vertex)); for (Vertex neighbour : neighbours) {adjacencyList.get(neighbour).remove(vertex);} adjacencyList.remove(vertex);
        }
    }

    /**
     * This method returns the List of Neighbouring Vertices for a Specified Vertex in the Undirected Graph.
     * @param vertex of type Vertex is the Vertex whose Neighbours are to be Retrieved.
     * @return a List of Adjacent Vertices to the Vertex passed into the method.
     * @throws NoSuchElementException if the Vertex is not in the Undirected Graph.
     */

    public List<Vertex> getNeighbours(Vertex vertex) {
        List<Vertex> neighbours = adjacencyList.get(vertex); if (neighbours == null) throw new NoSuchElementException("Vertex " + vertex + " is not in the Undirected Graph!"); return neighbours;
    }

    /**
     * This method performs a Breadth-First Search (BFS) starting at the Specified Vertex.  Breadth First Search is a graph traversal algorithm that explores all the vertices of a graph level by
     * level.  With a BFS, we visit all the neighbours of a node, and then all the neighbours of the first node visited, and all the neighbours of the second node and so on, before moving further
     * away from the start node.
     * @param start of type Vertex is the Vertex which the Breadth-First Search (BFS) begins at.
     * @return a List of Vertices in the Order in which they were Visited.
     * @throws NoSuchElementException if the Vertex is not in the Undirected Graph.
     */

    public List<Vertex> breadthFirstSearch(Vertex start) {
        if (!adjacencyList.containsKey(start)) throw new NoSuchElementException("Vertex " + start + " is not in the Undirected Graph!");
        List<Vertex> result = new ArrayList<>(); Set<Vertex> visited = new HashSet<>(); Queue<Vertex> queue = new LinkedList<>(); visited.add(start); queue.add(start);
        while (!queue.isEmpty()) {
            Vertex current = queue.poll(); result.add(current);
            for (Vertex neighbour : adjacencyList.get(current)) {if (!visited.contains(neighbour)) {visited.add(neighbour); queue.add(neighbour);}}
        } return result;
    }

    /**
     * This method performs a Depth-First Search (DFS) starting at the Specified Vertex.  Depth First Search is a graph traversal algorithm that explores as far down one branch as possible before
     * backtracking to explore other branches.  This method begins the recursive routine to perform a DFS.
     * @param start of type Vertex is the Vertex which the Depth-First Search (DFS) begins at.
     * @return a List of Vertices in the Order in which they were Visited.
     * @throws NoSuchElementException if the Vertex is not in the Undirected Graph.
     */

    public List<Vertex> depthFirstSearch(Vertex start) {
        if (!adjacencyList.containsKey(start)) throw new NoSuchElementException("Vertex " + start + " is not in the Undirected Graph!");
        List<Vertex> result = new ArrayList<>(); Set<Vertex> visited = new HashSet<>(); depthFirstSearch(start, visited, result); return result;
    }

    /**
     * This method is an overloaded version of {@code depthFirstSearch} which recursively performs a Depth First Search on the Undirected Graph, starting at the Specified Vertex.
     * @param vertex of type Vertex is the Vertex which the Depth-First Search (DFS) begins at.
     * @param visited is a Set which stores the Vertices in the Undirected Graph which have been visited.
     * @param result is a List of Vertices in the Order in which they were Visited.
     */

    public void depthFirstSearch(Vertex vertex, Set<Vertex> visited, List<Vertex> result) {
        visited.add(vertex); result.add(vertex); for (Vertex neighbour : adjacencyList.get(vertex)) {if (!visited.contains(neighbour)) depthFirstSearch(neighbour, visited, result);}
    }

    /**
     * This method tests the {@code UndirectedGraph} Class by Adding and Removing Vertices and Edges.
     * @param args the Command-Line Arguments.
     */

    public static void main(String[] args) {
        UndirectedGraph<String> graph = new UndirectedGraph<>(); graph.addVertex("Apple"); graph.addVertex("Banana"); graph.addVertex("Orange"); graph.addVertex("Strawberry");
        graph.addVertex("Kiwi"); graph.addVertex("Blueberry"); graph.addVertex("Raspberry"); graph.addVertex("Peach"); graph.addVertex("Coconut"); graph.addVertex("Grape");

        graph.addEdge("Apple", "Banana"); graph.addEdge("Apple", "Peach"); graph.addEdge("Apple", "Coconut"); graph.addEdge("Apple", "Strawberry"); graph.addEdge("Apple", "Raspberry");
        System.out.println("Neighbours of Apple: " + graph.getNeighbours("Apple"));

        System.out.println("Depth-First Search starting at Apple: " + graph.depthFirstSearch("Apple"));
        System.out.println("Breadth-First Search stating at Apple: " + graph.breadthFirstSearch("Apple")); System.out.println("All Tests Passed Successfully!");
    }
}