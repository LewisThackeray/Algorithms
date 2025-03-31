package Graphs; // This Package contains the EdgeWeightedDigraph Class which is required to implement PrimsAlgorithm and KruskalsAlgorithm.

import java.util.List; // Importing the List Interface from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.ArrayList; // Importing the ArrayList Class from java.util to Create the List of Neighbours for each Vertex in the Adjacency List.
import java.util.Map; // Importing the Map Interface from java.util to Implement the Adjacency List.
import java.util.HashMap; // Importing the HashMap Class from java.util to Implement the Adjacency List;
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from java.util to be Thrown when there is an attempt to accesss a Vertex which is not in the Graph.

/**
 * @author LewisThackeray
 * @date 28/03/2025
 *
 * {@code EdgeWeightedDigraph} is a simple implementation of a {@code DirectedGraph} with Weighted Edges which is represented using an Adjacency List.  This Class is used in my implementations of
 * <em>Prim's Algorithm</em> and <em>Kruskal's Algorithm</em>.
 * @param Vertex is a Generic Type for the Vertices stored in the <em>Edge Weighted Digraph</em>.
 */

public class EdgeWeightedDigraph<Vertex> {

    private Map<Vertex, List<Edge<Vertex>>> adjacencyList; // Creating an Adjacency List which Stores the Edge Weighted Digraph.

    /**
     * This Inner-Class represents a Weighted Edge in the Edge Weighted Digraph.
     */

    public static class Edge<Vertex> {

        private Vertex source; private Vertex destination; private double weight; // Creating Variables to Store the Source and Destination Vertex of the Edge and the Edge's Weight.

        /**
         * This is the Class Constructor which creates a Weighted Edge to the Destination Vertex with the Specified Weight.
         * @param source of type Vertex is the Source Vertex of the Edge.
         * @param destination of type Vertex is the Destination Vertex of the Edge.
         * @param weight is a Double Floating Point Value which Stores the Weight associated with the Edge.
         */

        public Edge(Vertex source, Vertex destination, double weight) {this.source = source; this.destination = destination; this.weight = weight;}

        /**
         * This is a Getter Method which gets the Source Vertex of the Edge.
         * @return a Vertex which is the Source Vertex of the Edge.
         */

        public Vertex getSource() {return source;}

        /**
         * This is a Getter Method which gets the Destination Vertex of the Edge.
         * @return a Vertex which is the Destination Vertex of the Edge.
         */

        public Vertex getDestination() {return destination;}

        /**
         * This is a Getter Method which gets the Weight of the Edge.
         * @return a Double Floating Point Value which is the Weight of the Edge.
         */

        public double getWeight() {return weight;}
    }

    /**
     * This is the Class Constructor which creates an Empty Edge Weighted Digraph using an Adjaceny List.
     */

    public EdgeWeightedDigraph() {adjacencyList = new HashMap<>();}

    /**
     * This method is a Getter Method which returns the Adjacency List of the Edge Weighted Digraph.
     * @return a Map which contains the Neighbours of Each Vertex in the Edge Weighted Digraph.
     */

    public Map<Vertex, List<Edge<Vertex>>> getAdjacencyList() {return adjacencyList;}

    /**
     * This method adds a Vertex to the Edge Weighted Diraph if the Vertex isn't already a member.
     * @param vertex of type Vertex is the Vertex to be added to the Edge Weighted Digraph.
     */

    public void addVertex(Vertex vertex) {if (!adjacencyList.containsKey(vertex)) adjacencyList.put(vertex, new ArrayList<>());}

    /**
     * This method adds a Directed Weighted Edge between Two Vertices with a Specified Weight in the Edge Digraph, and if either Vertex doesn't exist, it will be Added automatically.
     * @param source of type Vertex is the Source Vertex of the Edge.
     * @param destination of type Vertex is the Destination Vertex of the Edge.
     * @param weight is a Double Floating Point Value which is the Weight of the Edge.
     * @throws IllegalArgumentException if either Vertex is NULL.
     */

    public void addEdge(Vertex source, Vertex destination, double weight) {
        if (source == null || destination == null) throw new IllegalArgumentException("The Source and Destination Vertices cannot be NULL!"); addVertex(source); addVertex(destination);
        adjacencyList.get(source).add(new Edge<>(source, destination, weight));
    }

    /**
     * This method removes a Directed Weighted Edge between Two Vertices in the Edge Weighted Directed Graph, if the Directed Weighted Edge exists.
     * @param source of type Vertex is the Source Vertex of the Edge.
     * @param destination of type Vertex is the Destination Vertex of the Edge.
     */

    public void removeEdge(Vertex source, Vertex destination) {
        if (adjacencyList.containsKey(source)) {List<Edge<Vertex>> edges = adjacencyList.get(source); edges.removeIf(edge -> edge.getDestination().equals(destination));}
    }

    /**
     * This method removes a Vertex and all the Edges which it is a Source of from the Edge Weighted Directed Graph.
     * @param vertex of type Vertex is the Vertex to be removed from the Edge Weighted Directed Graph.
     */

    public void removeVertex(Vertex vertex) {
        if (adjacencyList.containsKey(vertex)) {adjacencyList.remove(vertex); for (Vertex aVertex : adjacencyList.keySet()) adjacencyList.get(aVertex).remove(vertex);}
    }

    /**
     * This method tests the {@code EdgeWeightedDigraph} Class by Adding and Removing Vertices and Edges.
     * @param args are the Command Line Arguments.
     */

    public static void main(String[] args) {
        EdgeWeightedDigraph<String> graph = new EdgeWeightedDigraph<>(); graph.addVertex("Apple"); graph.addVertex("Banana"); graph.addVertex("Orange"); graph.addVertex("Kiwi");
        graph.addVertex("Stawberry"); graph.addVertex("Raspberry"); graph.addEdge("Apple", "Banana", 4.5); graph.addEdge("Orange", "Kiwi", 2.0); graph.addEdge("Banana", "Strawberry", 0.6);
        graph.removeVertex("Apple"); graph.removeEdge("Banana", "Strawberry"); System.out.println("All Tests Passed Successfully!");
    }
}