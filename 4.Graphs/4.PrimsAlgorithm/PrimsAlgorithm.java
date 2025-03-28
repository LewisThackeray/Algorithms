package Graphs; // This Package contains the EdgeWeightedDigraph Class which is required to implement PrimsAlgorithm and KruskalsAlgorithm.

import java.util.List; import java.util.ArrayList; // Importing the List Interface and ArrayList Class from java.util to Create a List of Edges in the Minimum Spanning Tree (MST).
import java.util.Set; import java.util.HashSet; // Importing the Set Interface and HashSet Class from java.util to Track the Vertices which have been included in the Minimum Spanning Tree (MST).
import java.util.Map; import java.util.HashMap; // Importing the Map Interface and HashMap Class from java.util to Track the Edges and their corresponding Weights in the Minimum Spanning Tree (MST).
import java.util.PriorityQueue; // Importing the PriorityQueue Class from java.util to Store the Possible Edges to be Added to the Minimum Spanning Tree.
import java.util.Comparator; // Importing the Comparator Inteface to ensure that the Priority Queue returns the Edge with the Smallest Weight first.
import Graphs.EdgeWeightedDigraph; // Importing the EdgeWeightedDigraph Class as Prim's Algorithm creates the Minimum Spanning Tree (MST) from an Edge Weighted Digraph.

/**
 * <p>A <b>Spanning Tree</b> of a Graph is a connected subgraph with no cycles that includes all vertices.  A <b>Minimum Spanning Tree (MST)</b> of an Edge Weighted Digraph is a Spanning Tree
 * whose Weight (the sum of the edge Weights) is no larger than the weight of any other Spanning Tree.</p>
 *
 * <p>There are Two Classic Algorithms for computing Minimum Spanning Trees (MSTs): <b>Prim's Algorithm</b> and <b>Kruskal's Algorithm</b>.  Prim's Algorithm works best for dense graphs.</p>
 *
 * <p><i>Minimum Spanning Trees (MSTs) are much easier to compute when the Edge Weighted Digraph is connected (if there exists at least one path between a Pair of Vertices), otherwise, you
 * get a Minimum Spanning Forest which is comprised of multiple MSTs.</i></p>
 *
 * {@code PrimsAlgorithm} implements <em>Prim's Algorithm</em> to create a Minimum Spanning Tree (MST) from an Edge Weighted Digraph and uses a Priority Queue to track the Vertices and an
 * Adjacency List to store the Edge Weights.  <em>Prim's Algorithm</em> works as follows:
 * <ol>
 *     <li>The first step is to choose an arbitary starting vertex and mark this vertex as visited, a priority queue is used to track the vertices based on their current minimum cost.</li>
 *     <li>You then choose the vertex with the smallest cost from the priority queue and find all the edges to this vertex and check their cost.</li>
 *     <li>If the edge connects to a vertex not yet in the MST and has a lower weight than the vertex's current cost, update the cost of that vertex.</li>
 *     <li>You then add the selected vertex and its assocaited edge to the MST and mark the vertex as visited.</li>
 *     <li>Repeat this process until all the vertices are included in the MST and once all of the vertices are in the MST, the algorithm terminates.</li>
 *     <li></li>
 * </ol>
 *
 * @param Vertex is a Generic Type for Vertices in the Graph.
 */

public class PrimsAlgorithm<Vertex> {

    private Map<Vertex, EdgeWeightedDigraph.Edge<Vertex>> edgeTo; // Creating a Map to Store the Edges in the Minimum Spanning Tree.
    private Map<Vertex, Double> weights; // Creating a Map to Store the Weights of the Edges in the Minimum Spanning Tree, where the Weight at weights[i] corresponds to the Edge at edgeTo[i].
    private Set<Vertex> marked; // Creating a Set to Track the Vertices which have been added to the Minimum Spanning Tree.
    private PriorityQueue<EdgeWeightedDigraph.Edge<Vertex>> priorityQueue; // Creating a Priority Queue to Hold the Possible Edges to be Added to the Minimum Spanning Tree, prioritised by Weight.

    /**
     * This is the Class Constructor which creates a Minimum Spanning Tree (MST) from an Edge Weighted Digraph.
     * @param EdgeWeightedDigraph<Vertex> is the Edge Weighted Digraph which we are going to create the Minimum Spanning Tree (MST) from where the Vertices of the Graph are of type Vertex.
     * @param start of type Vertex is the Vertex which we are going to begin creating the Minimum Spanning Tree (MST) from.
     * @return the Minimum Spanning Tree (MST) created using Prim's Algorithm.
     */

    public PrimsAlgorithm(EdgeWeightedDigraph<Vertex> graph, Vertex start) {
        // Initialising the Data Structures and Setting the Initial Distances to Infinity.
        edgeTo = new HashMap<>(); weights = new HashMap<>(); marked = new HashSet<>(); priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(EdgeWeightedDigraph.Edge::getWeight));
        for (Vertex vertex : graph.getAdjacencyList().keySet()) {weights.put(vertex, Double.POSITIVE_INFINITY);}
        // Starting with the Specified Index which has been Passed into the Method as an Argument.
        weights.put(start, 0.0); priorityQueue.offer(new EdgeWeightedDigraph.Edge<>(start, 0.0));
        // Prim's Algorithm.
        while (!priorityQueue.isEmpty()) {
            EdgeWeightedDigraph.Edge<Vertex> edge = priorityQueue.poll(); Vertex addedVertex = edge.getDestination(); if (marked.contains(addedVertex)) continue; marked.add(addedVertex);
            if (graph.getAdjacencyList().containsKey(addedVertex)) {
                for (EdgeWeightedDigraph.Edge<Vertex> neighbourEdge : graph.getAdjacencyList().get(addedVertex)) {
                    Vertex adjacentVertex = neighbourEdge.getDestination(); if (marked.contains(adjacentVertex)) continue;
                    if (neighbourEdge.getWeight() < weights.getOrDefault(adjacentVertex, Double.POSITIVE_INFINITY)) {
                        weights.put(adjacentVertex, neighbourEdge.getWeight()); edgeTo.put(adjacentVertex, neighbourEdge); priorityQueue.offer(neighbourEdge);
                    }
                }
            }
        }
        // Collect and Return the Edges in the Minimum Spanning Tree (MST).
        List<EdgeWeightedDigraph.Edge<Vertex>> minimumSpanningTree = new ArrayList<>(); for (Vertex vertex : edgeTo.keySet()) {minimumSpanningTree.add(edgeTo.get(vertex));}
    }

    /**
     * This method returns the Minimum Spanning Tree (MST) as the List of Edges in the MST.
     * @return a List of Edges in the Minimum Spanning Tree (MST).
     */

    public List<EdgeWeightedDigraph.Edge<Vertex>> getMinimumSpanningTree() {return new ArrayList<>(edgeTo.values());}

    /**
     * This method tests {@code PrimsAlgorithm} by creating an Edge Weighted Digraph and then performing Prim's Algorithm on the Edge Weighted Digraph to create the Minimum Spanning Tree (MST).
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        EdgeWeightedDigraph<Integer> graph = new EdgeWeightedDigraph<>(); graph.addVertex(1); graph.addVertex(2); graph.addVertex(3); graph.addVertex(4); graph.addVertex(5); graph.addVertex(6);
        graph.addEdge(1, 2, 1.1); graph.addEdge(2, 3, 1.2); graph.addEdge(3, 4, 1.3); graph.addEdge(4, 5, 1.4); graph.addEdge(5, 6, 1.5); graph.addEdge(6, 1, 1.6); graph.addEdge(5, 2, 1.7);
        PrimsAlgorithm<Integer> primsAlgorithm = new PrimsAlgorithm(graph, 1); List<EdgeWeightedDigraph.Edge<Integer>> minimumSpanningTree = primsAlgorithm.getMinimumSpanningTree();
        double weight = 0.0;

        for (EdgeWeightedDigraph.Edge<Integer> edge : minimumSpanningTree) {System.out.println("Edge To " + edge.getDestination() + " with Weight " + edge.getWeight()); weight += edge.getWeight();}
        System.out.println("Weight of the Minimum Spanning Tree (MST): " + weight); System.out.println("All Tests Passed Successfully!");
    }
}