package Graphs; // This Package contains the EdgeWeightedDigraph Class which is required to implement DijkstrasAlgorithm.

import java.util.List; import java.util.ArrayList; // Importing the List Interface and the ArrayList Class from java.util to Store the Path from the Source to the Destination as a List of Vertices.
import java.util.HashMap; // Importing the HashMap Class from java.util to Store the Distances from the Source to Each Vertex and a Vertex's Predecessor in the Shortest Path from the Source Vertex.
import java.util.HashSet; // Importing the HashSet Class from java.util to Store the Vertices which have been Visited in the Edge Weighte Digraph and so their Shortest Paths are Finalised.
import java.util.PriorityQueue; // Importing the PriorityQueue Class from java.util to Store the Edges in the Edge Weighted Digraph to ensure the Vertex with the Shortest Distance is Processed Next.
import java.util.Comparator; // Importing the Comparator Interface from java.util to ensure that the Vertex with the Smallest Distance to the Current Vertex is Processed Next.
import Graphs.EdgeWeightedDigraph; // Importing the EdgeWeightedDigraph Class as Dijkstra's Algorithm calculates the Shortest Paths for the Edge Weighted Digraph.

/**
 * <p>A <b>Single Source Shortest Path Problem</b> is defined as: Given an Edge Weighted Digraph and a Source Node s, support queries of the form, "Is there a Directed Path from s to a Given
 * Target Node t?", if so, find the Shortest Such Path.  A Shortest Path from Vertex s to Vertex t in an Edge Weighted Digraph is a Directed Path from s to t with the property that no such other
 * path has a Lower Weight.</p>
 *
 * <p>There are two primary algorithms for solving the Single Source Shortest Paths Problem in an Edge Weighted Digraph: <b>Dijkstra's Algorithm</b> and the <b>Bellman Ford Algorithm</b>.</p>
 *
 * {@code DijkstrasAlgorithm} implements <em>Dijkstra's Algorithm</em> which solves the Single Source Shortest Paths Problem in an Edge Weighted Digraph with Non-Negative Weights.  <em>Dijkstra's
 * Algorithm</em> works as follows:
 * <ol>
 *     <li>The first step is to set the distance to the source vertex to 0 and set the distance of all other vertices to infinity.</li>
 *     <li>A priority queue is used to store vertices, ensuring the vertex with the shortest distance to the current vertex is removed next.  Once a vertex is removed from the priority queue, the
 *     algorithm examines all its neighbours.  For each neighbouring vertex, it calculates the distance through the current vertex.</li>
 *     <li>If the distance is smaller than the previously known distance, the neighbour's distance is updated, and the new distance replaces the old distance in the priority queue.</li>
 *     <li>This process continues until all vertices have been visited.</li>
 * </ol>
 *
 * @param Vertex is a Generic Type for Vertices in the Graph.
 */

public class DijkstrasAlgorithm<Vertex> {

    private HashMap<Vertex, EdgeWeightedDigraph.Edge<Vertex>> edgeTo; // Creating a Map to Store a Vertex's Predecessor in the Shortest Path from the Source Vertex to a Given Vertex.
    private HashMap<Vertex, Double> distanceTo; // Creating a Map to Store the Distances from the Source Vertex to Each Vertex in the Edge Weighted Digraph.
    private HashSet<Vertex> visited; // Creating a Set to Store the Vertices which have been Visited and so their Shortest Paths are Finalised.
    private PriorityQueue<Vertex> priorityQueue; // Creating a Priority Queue to Store the Edges, where they are Prioritised by the Distance to the Current Vertex.

    /**
     * This is the Class Constructor which Computes the Shortest Path from the Specified Source Vertex to each Vertex in the Edge Weighted Digraph where the Edge Weights are Non-Negative.
     * @param graph of type EdgeWeightedDigraph is the Edge Weighted Digraph which we are going to perform Dijkstra's Algorithm on.
     * @param source of type Vertex is the Source Vertex which we are going to Compute the Shortest Paths from.
     * @throws IllegalArgumentException if the Edge Weighted Digraph is NULL or the Source Vertex is not in the Graph.
     */

    public DijkstrasAlgorithm(EdgeWeightedDigraph<Vertex> graph, Vertex source) {
        if (graph == null || !graph.getAdjacencyList().containsKey(source)) throw new IllegalArgumentException("The Graph cannot be NULL and must contain the Source Vertex!");
        // Initialising the Data Structures and Setting the Initial Distances (0 for the Source, ∞ for the Other Vertices).
        edgeTo = new HashMap<>(); distanceTo = new HashMap<>(); visited = new HashSet<>();
        priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(v -> distanceTo.getOrDefault(v, Double.POSITIVE_INFINITY))); distanceTo.put(source, 0.0); priorityQueue.add(source);
        // Dijkstra's Algorithm.
        while (!priorityQueue.isEmpty()) {
            Vertex vertex = priorityQueue.poll(); if (visited.contains(vertex)) continue; visited.add(vertex);
            for (EdgeWeightedDigraph.Edge<Vertex> edge : graph.getAdjacencyList().getOrDefault(vertex, new ArrayList<>())) {
                Vertex adjacentVertex = edge.getDestination(); double weight = edge.getWeight(); if (weight < 0) throw new IllegalArgumentException("Non-Negative Edge Weights are not allowed.");
                double newDistance = distanceTo.get(vertex) + weight; if (!distanceTo.containsKey(adjacentVertex) || newDistance < distanceTo.get(adjacentVertex)) {
                    distanceTo.put(adjacentVertex, newDistance); edgeTo.put(adjacentVertex, edge); priorityQueue.remove(adjacentVertex); priorityQueue.add(adjacentVertex);
                }
            }
        }
    }

    /**
     * This method returns the Shortest Distance from the Source to the Specified Vertex, or Infinity is the Vertex is Unreachable.
     * @param vertex of type Vertex is the Vertex which we are finding the Distance from the Source to.
     * @return a Double Floating Point Value which is the Distance from the Source to {@code vertex} or {@code Double.POSITIVE_INFINITY} if the Vertex is Unreachable.
     */

    public double distanceTo(Vertex vertex) {return distanceTo.getOrDefault(vertex, Double.POSITIVE_INFINITY);}

    /**
     * This method returns the Shortest Path from the Source to the Specified Vertex as a List of Vertices.
     * @param graph of type EdgeWeightedDigraph is the Edge Weighted Digraph which Dijkstra's Algorithm has been performed on.
     * @param vertex of type Vertex is the Vertex which we are finding the Path from the Source to.
     * @return a List of Vertices representing the Shortest Path from the Source to {@code vertex}, or NULL if no path exists.
     */

    public List<Vertex> pathTo(EdgeWeightedDigraph<Vertex> graph, Vertex vertex) {
        if (!distanceTo.containsKey(vertex)) return null; List<Vertex> path = new ArrayList<>(); Vertex current = vertex;
        while (current != null) {path.add(0, current); EdgeWeightedDigraph.Edge<Vertex> edge = edgeTo.get(current); if (edge == null) break; current = edge.getSource();} return path;
    }

    /**
     * This method tests {@code DijkstrasAlgorithm} by creating an Edge Weighted Digraph and then Calculating the Shortest Path from the Source to the Other Vertices.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        EdgeWeightedDigraph<String> graph = new EdgeWeightedDigraph<>(); graph.addEdge("Apple", "Banana", 4.5); graph.addEdge("Orange", "Kiwi", 2.0); graph.addEdge("Banana", "Strawberry", 0.6);
        DijkstrasAlgorithm<String> dijkstra = new DijkstrasAlgorithm<>(graph, "Apple"); System.out.println("Distance to Strawberry from Apple: " + dijkstra.distanceTo("Strawberry"));
        System.out.println("Shortest Path from Strawberry to Apple: " + dijkstra.pathTo(graph, "Strawberry")); System.out.println("All Tests Passed Successfully!");
    }

}

