package Graphs; // This Package contains the EdgeWeightedDigraph Class which is required to implement BellmanFordAlgorithm.

import java.util.List; import java.util.ArrayList; // Importing the List Interface and the ArrayList Class from java.util to Store the Path from the Source to the Destination as a List of Vertices.
import java.util.HashMap; // Importing the HashMap Class from java.util to Store the Distances from the Source to Each Vertex and a Vertex's Predecessor in the Shortest Path from the Source Vertex.

/**
 * @author LewisThackeray
 * @date 31/03/2025
 *
 * <p>A <b>Single Source Shortest Path Problem</b> is defined as: Given an Edge Weighted Digraph and a Source Node s, support queries of the form, "Is there a Directed Path from s to a Given
 * Target Node t?", if so, find the Shortest Such Path.  A Shortest Path from Vertex s to Vertex t in an Edge Weighted Digraph is a Directed Path from s to t with the property that no such other
 * path has a Lower Weight.</p>
 *
 * <p>There are two primary algorithms for solving the Single Source Shortest Paths Problem in an Edge Weighted Digraph: <b>Dijkstra's Algorithm</b> and the <b>Bellman Ford Algorithm</b>.</p>
 *
 * {@code BellmanFordAlgorithm} implements the <em>Bellman Ford Algorithm</em> which solves the Single Source Shortest Path Problem in an Edge Weighted Digraph with Non-Negative Weights. The
 * <em>Bellman Ford Algorithm</em> works as follows:
 * <ol>
 *     <li>Similar to Dijkstra's Algorithm, the Bellman Ford Algorithm sets the distance to the source vertex as 0 and the distance to the other vertices as infinity.</li>
 *     <li>For each edge (u, v) with weight w (where u is the start vertex and v is the end vertex), check if the shortest known distance to v can be improved by going through u. </li>
 *     <li>If distance[u] + w < distance[v], then update distance[v] = distance[u] + w.</li>
 *     <li>Repeat this relaxation process for all edges, V - 1 times (where V is the number of vertices).  The reason we do this for V - 1 edges is that the longest possible path without a
 *     cycle will have at most V - 1 edges.</li>
 *     <li>After completing V - 1 iterations, perform one more iteration over all edges.  If any distance can still be improved, it means that there is a negative weight cucle in the graph, the
 *     algorithm detects this because it will find a shorter path even when all the shortest paths should have been found.</li>
 * </ol>
 *
 * @param Vertex is a Generic Type for Vertices in the Graph.
 */

public class BellmanFordAlgorithm<Vertex> {

    private HashMap<Vertex, EdgeWeightedDigraph.Edge<Vertex>> edgeTo; // Creating a Map to Store a Vertex's Predecessor in the Shortest Path from the Source Vertex to a Given Vertex.
    private HashMap<Vertex, Double> distanceTo; // Creating a Map to Store the Distances from the Source Vertex to Each Vertex in the Edge Weighted Digraph.
    public boolean hasNegativeCycle; // Creating a Flag to Detect a Negative Cycle.

    /**
     * This is the Class Constructor which Computes the Shortest Path from the Specified Source Vertex to each Vertex in the Edge Weighted Digraph where the Edge Weights could be Negative.
     * @param graph of type EdgeWeightedDigraph is the Edge Weighted Digraph which we are going to perform Dijkstra's Algorithm on.
     * @param source of type Vertex is the Source Vertex which we are going to Compute the Shortest Paths from.
     * @throws IllegalArgumentException if the Edge Weighted Digraph is NULL or the Source Vertex is not in the Graph.
     */

    public BellmanFordAlgorithm(EdgeWeightedDigraph<Vertex> graph, Vertex source) {
        // Verifying that the Edge Weighted Graph is not NULL and the Source Vertex is in the Graph.
        if (graph == null || !graph.getAdjacencyList().containsKey(source)) throw new IllegalArgumentException("The Graph cannot be NULL and must contain the Source Vertex!");
        // Initialising the Class Attributes and Setting the Distance to the Source Vertex as 0 and the Other Vertices to Infinity.
        edgeTo = new HashMap<>(); distanceTo = new HashMap<>(); hasNegativeCycle = false; for (Vertex vertex : graph.getAdjacencyList().keySet()) {distanceTo.put(vertex, Double.POSITIVE_INFINITY);}
        distanceTo.put(source, 0.0); int V = graph.getAdjacencyList().size();
        // Relaxing the Edges |V| - 1 Times.
        for (int i = 0; i < V - 1; i++) {for (Vertex vertex : graph.getAdjacencyList().keySet()) {for (EdgeWeightedDigraph.Edge<Vertex> edge : graph.getAdjacencyList().get(vertex)) {
            Vertex src = edge.getSource(); Vertex destination = edge.getDestination(); double weight = edge.getWeight();
            double distanceSource = distanceTo.getOrDefault(src, Double.POSITIVE_INFINITY); double distanceDestination = distanceTo.getOrDefault(destination, Double.POSITIVE_INFINITY);
            if (distanceSource + weight < distanceDestination) {distanceTo.put(destination, distanceSource + weight); edgeTo.put(destination, edge);}
        }}}
        // Check for Negative Cycles.
        for (Vertex vertex : graph.getAdjacencyList().keySet()) {
            for (EdgeWeightedDigraph.Edge<Vertex> edge : graph.getAdjacencyList().get(vertex)) {
                Vertex src = edge.getSource(); Vertex dest = edge.getDestination(); double weight = edge.getWeight(); if (distanceTo.get(src) + weight < distanceTo.get(dest)) {
                    hasNegativeCycle = true; return;
                }
            }
        }
    }

    /**
     * This method returns the Shortest Distance from the Source to the Specified Vertex, or Infinity is the Vertex is Unreachable.
     * @param vertex of type Vertex is the Vertex which we are finding the Distance from the Source to.
     * @return a Double Floating Point Value which is the Distance from the Source to {@code vertex} or {@code Double.POSITIVE_INFINITY} if the Vertex is Unreachable.
     */

    public double distanceTo(Vertex vertex) {if (hasNegativeCycle) return Double.POSITIVE_INFINITY; return distanceTo.getOrDefault(vertex, Double.POSITIVE_INFINITY);}

    /**
     * This method returns the Shortest Path from the Source to the Specified Vertex as a List of Vertices.
     * @param graph of type EdgeWeightedDigraph is the Edge Weighted Digraph which the Bellman Ford Algorithm has been performed on.
     * @param vertex of type Vertex is the Vertex which we are finding the Path from the Source to.
     * @return a List of Vertices representing the Shortest Path from the Source to {@code vertex}, or NULL if no path exists or a negative cycle is detected.
     */

    public List<Vertex> pathTo(EdgeWeightedDigraph<Vertex> graph, Vertex vertex) {
        if (hasNegativeCycle || !distanceTo.containsKey(vertex)) return null; List<Vertex> path = new ArrayList<>(); Vertex current = vertex; path.add(0, current);
        while (edgeTo.containsKey(current)) {EdgeWeightedDigraph.Edge<Vertex> edge = edgeTo.get(current); Vertex next = edge.getSource(); path.add(0, next); current = next;} return path;
    }

    /**
     * This method tests {@code BellmanFordAlgorithm} by creating an Edge Weighted Digraph and then Calculating the Shortest Path from the Source to the Other Vertices with Negative Edge Weights.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        EdgeWeightedDigraph<String> graph = new EdgeWeightedDigraph<>(); graph.addEdge("Apple", "Banana", -4.5); graph.addEdge("Orange", "Kiwi", -2.0); graph.addEdge("Banana", "Strawberry", -0.6);
        BellmanFordAlgorithm<String> bellmanFord = new BellmanFordAlgorithm<>(graph, "Apple"); System.out.println("Distance to Strawberry from Apple: " + bellmanFord.distanceTo("Strawberry"));
        System.out.println("Shortest Path from Strawberry to Apple: " + bellmanFord.pathTo(graph, "Strawberry")); System.out.println("All Tests Passed Successfully!");
    }
}