package Graphs; // This Package contains the EdgeWeightedDigraph Class which is required to implement KruskalsAlgorithm.

import java.util.List; import java.util.ArrayList; // Importing the List Interface and ArrayList Class from java.util to Create a List of Edges in the Minimum Spanning Tree (MST).
import java.util.Map; import java.util.HashMap; // Importing the Map Interface and HashMap Class from java.util to Support the Union-Find Algorithm and Track Connected Componenets in the Graph.
import java.util.PriorityQueue; // Importing the PriorityQueue Class from java.util to Store the Possible Edges to be Added to the Minimum Spanning Tree.
import java.util.Comparator; // Importing the Comparator Inteface to ensure that the Priority Queue returns the Edge with the Smallest Weight first.
import Graphs.EdgeWeightedDigraph; // Importing the EdgeWeightedDigraph Class as Prim's Algorithm creates the Minimum Spanning Tree (MST) from an Edge Weighted Digraph.

/**
 * <p>A <b>Spanning Tree</b> of a Graph is a connected subgraph with no cycles that includes all vertices.  A <b>Minimum Spanning Tree (MST)</b> of an Edge Weighted Digraph is a Spanning Tree
 * whose Weight (the sum of edge Weights) is no larger than the weight of any other Spanning Tree.</p>
 *
 * <p>There are Two Classic Algorithms for computing Minimum Spanning Trees (MSTs): <b>Prim's Algorithm</b> and <b>Kruskal's Algorithm</b>.  Kruskal's Algorithm works best for sparse graphs.</p>
 *
 * <p><i>Minimum Spanning Tree (MSTs) are much easier to compute when the Edge Weighted Digraph is connected (if there exists at least one path between a Pair of Vertices), otherwise, you get a
 * Minimum Spanning Forest which is comprised of multiple MSTs.</i></p>
 *
 * {@code KruskalsAlgorithm} implements <em>Kruskal's Algorithm</em> to create a Minimum Spanning Tree (MST) from an Edge Weighted Digraph and uses a Priority Queue to track the Vertices and a
 * List to Store the Edge Weights.  <em>Kruskal's Algorithm</em> works as follows:
 * <ol>
 *     <li>The algorithm begins by sorting all of the edges in the graph by weight and intialising an empty Minimum Spanning Tree (MST).</li>
 *     <li>For each edge in the sorted list of edges: if adding the edge doesn't form a cycle, include it in the MST, however, if it down form a cycle, you skip the edge.</li>
 *     <li>This process is repeated until the MST contains V - 1 edges, where V is the Number of Vertices.</li>
 * </ol>
 *
 * <p><b>As previously mentioned, Prim's Algorithm and Kruskal's Algorithm are both methods for computing Minimum Spanning Trees (MSTs), however, they both accomplish this task in different ways,
 * Kruskal's Algorithm uses a glboal method where it considers all of the edges in the graph at once and builds the MST by selecting edges in order of increasing weight, regardless of their
 * position in the graph.  Whereas, Prim's Algorithm uses a local method where it grows the MST from a single starting vertex, expanding it step-by-step by adding the cheapest edge connecting
 * the current tree to a vertex outside it.  To summarise, Prim's Algorithm starts with one edge and grows the MST from there, whereas Kruskal's Algorithm starts with all edges.</b></p>
 *
 * @param Vertex is a Generic Type for Vertices in the Graph.
 */

public class KruskalsAlgorithm<Vertex> {

    private List<EdgeWeightedDigraph.Edge<Vertex>> minimumSpanningTree; // Creating a List to Store the Edges in the Minimum Spanning Tree.
    private Map<Vertex, Vertex> parent; // Creating a Map to Support the Union-Find Algorithm to Track the Parent of Each Vertex in the Minimum Spanning Tree.
    private Map<Vertex, Integer> rank; // Creating a Map to Support the Union-Find Algorithm to Track the Rank of Each Vertex in the Minimum Spanning Tree, to Optimise Tree Height.

    /**
     * This is the Class Constructor which creates a Minimum Spanning Tree (MST) from an Edge Weighted Digraph.
     * @param graph of type EdgeWeightedDigraph is the Edge Weighted Digraph which we are going to create the Minimum Spanning Tree (MST) from where the Vertices of the Graph are of type Vertex.
     */

    public KruskalsAlgorithm(EdgeWeightedDigraph<Vertex> graph) {
        // Initialising all the Data Structures and Adding all the Edges in the Edge Weighted Digraph to the Priority Queue.
        PriorityQueue<EdgeWeightedDigraph.Edge<Vertex>> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(EdgeWeightedDigraph.Edge::getWeight)); parent = new HashMap<>();
        minimumSpanningTree = new ArrayList<>(); rank = new HashMap<>(); for (Vertex vertex : graph.getAdjacencyList().keySet()) {parent.put(vertex, vertex); rank.put(vertex, 0);}
        for (Vertex vertex : graph.getAdjacencyList().keySet()) {for (EdgeWeightedDigraph.Edge<Vertex> edge : graph.getAdjacencyList().get(vertex)) {priorityQueue.offer(edge);}}
        // Kruskal's Algorithm.
        while (!priorityQueue.isEmpty() && minimumSpanningTree.size() < graph.getAdjacencyList().size() - 1) {
            EdgeWeightedDigraph.Edge<Vertex> edge = priorityQueue.poll(); Vertex destination = edge.getDestination(); Vertex source = null;
            for (Vertex vertex : graph.getAdjacencyList().keySet()) {if (graph.getAdjacencyList().get(vertex).contains(edge)) {source = vertex; break;}} if (source == null) continue;
            if (!find(source).equals(find(destination))) {minimumSpanningTree.add(edge); union(source, destination);} // Checking if Adding this Edge Creates a Cycle using Union-Find.
        }
    }

    /**
     * The Union Find Data Structure is used to Efficiently Detect Cycles and Manage Connected Components as Edges are added to the Minimum Spanning Tree (MST).  This method implements the
     * find operation which finds the Root of a Specified Vertex with Path Compression to improve the Efficiency of this find implementation.
     * @param vertex of type Vertex is to find the Root of.
     * @return of type Vertex is the Root of the Component containing the vertex.
     */

    private Vertex find(Vertex vertex) {if (!parent.get(vertex).equals(vertex)) parent.put(vertex, find(parent.get(vertex))); return parent.get(vertex);}

    /**
     * This method implements the union operation of the Union Find Data Structure which merges two separate connected components (sets) into a single component when an Edge is added to the
     * Minimum Spanning Tree (MST) which ensures that the algorithm can track which vertices are connected as it builds the MST, enabling efficient cycle detection.
     * @param vertex1 of type Vertex is the Startpoint of the Edge being added to the MST.
     * @param vertex2 of type Vertex is the Endpoint of the Edge being added to the MST.
     */

    private void union(Vertex vertex1, Vertex vertex2) {
        Vertex rootVertex1 = find(vertex1); Vertex rootVertex2 = find(vertex2); if (rootVertex1.equals(rootVertex2)) return; int rankVertex1 = rank.get(rootVertex1);
        int rankVertex2 = rank.get(rootVertex2); if (rankVertex1 < rankVertex2) {parent.put(rootVertex1, rootVertex2);} else if (rankVertex1 > rankVertex2) {parent.put(rootVertex2, rootVertex1);}
        else {parent.put(rootVertex2, rootVertex1); rank.put(rootVertex1, rankVertex1 + 1);}
    }

    /**
     * This method returns the Minimum Spanning Tree (MST) as the List of Edges in the MST.
     * @return a List of Edges in the Minimum Spanning Tree (MST).
     */

    public List<EdgeWeightedDigraph.Edge<Vertex>> getMinimumSpanningTree() {return new ArrayList<>(minimumSpanningTree);}

    /**
     * This method tests {@code KruskalsAlgorithm} by creating an Edge Weighted Digraph and then performing Kruskal's Algorithm on the Edge Weighted Digraph to create the Minimum Spanning Tree (MST).
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        EdgeWeightedDigraph<Integer> graph = new EdgeWeightedDigraph<>(); graph.addVertex(1); graph.addVertex(2); graph.addVertex(3); graph.addVertex(4); graph.addVertex(5); graph.addVertex(6);
        graph.addEdge(1, 2, 1.1); graph.addEdge(2, 3, 1.2); graph.addEdge(3, 4, 1.3); graph.addEdge(4, 5, 1.4); graph.addEdge(5, 6, 1.5); graph.addEdge(6, 1, 1.6); graph.addEdge(5, 2, 1.7);
        KruskalsAlgorithm<Integer> kruskalsAlgorithm = new KruskalsAlgorithm(graph); List<EdgeWeightedDigraph.Edge<Integer>> minimumSpanningTree = kruskalsAlgorithm.getMinimumSpanningTree();
        double weight = 0.0;

        for (EdgeWeightedDigraph.Edge<Integer> edge : minimumSpanningTree) {System.out.println("Edge To " + edge.getDestination() + " with Weight " + edge.getWeight()); weight += edge.getWeight();}
        System.out.println("Weight of the Minimum Spanning Tree (MST): " + weight); System.out.println("All Tests Passed Successfully!");
    }
}