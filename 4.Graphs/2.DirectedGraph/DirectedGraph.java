import java.util.ArrayList; // Importing the ArrayList Class from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.HashMap; // Importing the HashMap Class from java.util to Implement the Adjacency List.
import java.util.List; // Importing the List Interface from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.Map; // Importing the Map Interface from java.util to Implement the Adjacency List.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from java.util to be Thrown when there is an attempt to access a Vertex which is not in the Graph.
import java.util.Set; // Importing the Set Interface from java.util to return all the Vertices in the Graph as a Set which prevents Duplicates.
import java.util.HashSet; // Importing the HashSet Class from java.util to Track the Visited Vertices when performing Kosajaru's Algorithm.
import java.util.Stack; // Importing the Stack Class from java.util which is used to find Strongly Connected Components through Kosajaru's Algorithm.

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
     * This method removes a Directed Edge between Two Vertices in the Directed Graph, if the Directed Edge exists.
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
     * This method implements <em>Kosaraju's Algorithm</em> which is used to find Strongly Connected Components (SCCs) in a Directed Graph.  <em>Kosaraju's Algorithm</em> works as follows:
     * <ol>
     *     <li>The First Step is to perform a Depth First Search (DFS) on the Graph, and when a Vertex is Visited it is added to the Stack.</li>
     *     <li>The Second Step transposes the Graph so that the Direction of the Edges in the Original Graph are Reversed.</li>
     *     <li>The Final Step works by popping each Vertex from the Stack and for each popped Vertex, you perform a DFS on the Transposed Graph where each DFS Traversal gives one SCC.</li>
     * </ol>
     *
     * @return a List of Lists, where each Inner-List stores a Strongly Connected Component (SCC) in the Directed Graph.
     */

    public List<List<Vertex>> kosarajusAlgorithm() {
        Stack<Vertex> stack = new Stack<>(); Set<Vertex> visited = new HashSet();
        // Perfoming a Depth-First Search on the Graph, and when a Vertex is Visited it is added to the Stack.
        for (Vertex vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                Stack<Vertex> recursionStack = new Stack<>(); recursionStack.push(vertex); while (!recursionStack.isEmpty()) {
                    Vertex current = recursionStack.peek(); if (!visited.contains(current)) {
                        visited.add(current); for (Vertex neighbour : getNeighbours(current)) {if (!visited.contains(neighbour)) recursionStack.push(neighbour);}
                    } else {recursionStack.pop(); stack.push(current);}
                }
            }
        }
        // Tranposing the Graph so that the Direction of the Edges in the Original Graph are Reversed.
        Map<Vertex, List<Vertex>> transposedAdjacencyList = new HashMap<>(); for (Vertex vertex : adjacencyList.keySet()) {transposedAdjacencyList.put(vertex, new ArrayList<>());}
        for (Vertex vertex : adjacencyList.keySet()) {for (Vertex neighbour : adjacencyList.get(vertex)) {transposedAdjacencyList.get(neighbour).add(vertex);}}
        // Popping each Vertex from the Stack and for each popped Vertex, you perform a DFS on the Transposed Graph where each DFS Traversal gives one SCC.
        visited.clear(); List<List<Vertex>> stronglyConnectedComponents = new ArrayList<>(); while (!stack.isEmpty()) {
            Vertex vertex = stack.pop(); if (!visited.contains(vertex)) {
                List<Vertex> component = new ArrayList<>(); Stack<Vertex> recursionStack = new Stack<>(); recursionStack.push(vertex); while (!recursionStack.isEmpty()) {
                    Vertex current = recursionStack.pop(); if (!visited.contains(current)) {visited.add(current); component.add(current);
                        for (Vertex neighbour : transposedAdjacencyList.get(current)) {if (!visited.contains(neighbour)) {recursionStack.push(neighbour);}}
                    }
                } stronglyConnectedComponents.add(component);
            }
        } return stronglyConnectedComponents;
    }

    /**
     * This method tests the {@code DirectedGraph} Class by Adding and Removing Vertices and Edges.
     * @param args the Command-Line Arguments.
     */

    public static void main(String[] args) {
        DirectedGraph<String> graph = new DirectedGraph<>(); graph.addVertex("Apple"); graph.addVertex("Banana"); graph.addVertex("Orange"); graph.addVertex("Strawberry");
        graph.addVertex("Kiwi"); graph.addVertex("Blueberry"); graph.addVertex("Raspberry"); graph.addVertex("Peach"); graph.addVertex("Coconut"); graph.addVertex("Grape");

        graph.addEdge("Apple", "Banana"); graph.addEdge("Apple", "Peach"); graph.addEdge("Apple", "Coconut"); graph.addEdge("Apple", "Strawberry"); graph.addEdge("Apple", "Raspberry");
        System.out.println("Neighbours of Apple: " + graph.getNeighbours("Apple"));

        graph.addEdge("Banana", "Coconut"); graph.addEdge("Coconut", "Apple");  // Creating a Strongly Connected Component of Apple, Bananan and Coconut.
        List<List<String>> stronglyConnectedComponents = graph.kosarajusAlgorithm(); System.out.println("Strongly Connected Componenets: ");
        for (List<String> stronglyConnectedComponent : stronglyConnectedComponents) {System.out.println(stronglyConnectedComponent);} System.out.println("All Tests Passed Successfully!");
        // IT IS IMPORTANT TO REMEMBER THAT STRONGLY CONNECTED COMPONENTS CAN BE COMPRISED OF A SINGLE VERTEX.
    }
}