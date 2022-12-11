import java.util.Iterator;

// TODO: Find out if the toArray() is Needed
public class DirectedGraph<T> implements GraphInterface<T> {
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices = new LinkedDictionary<>();
        edgeCount = 0;
    }//end constructor

// Add Methods
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcomes = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return (addOutcomes == null);
    }//end addVertex()

    public boolean addEdge(T begin, T end, int edgeWeight) {
        boolean result = false;

        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex, edgeWeight);
        }//end if
        if (result)
            edgeCount++;
        return result;
    }//end addEgde()

    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }//end addEdge()

// Has Edge Method
    public boolean hasEdge(T begin, T end) {
        boolean found = false;

        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            }//end while-loop
        }//end if

        return found;
    }//end hasEdge()

// BAsic Methods
    public boolean isEmpty() {
        return vertices.isEmpty();
    }//end isEmpty()

    public int getNumberOfVertices() {
        return vertices.getSize();
    }//end getNumberOfVertices()

    public int getNumberOfEdges() {
        return edgeCount;
    }//end getNumberOfEdges()

// Algorithm Methods
    // TODO: Finish this Algorithm // You should get the longest path
    /* TODO: Traverse the graph
     * 1. start with the origin vertex <>
     *    - (A, 0, null) is placed in the Priority Queue <>
     * - In the loop,
     * 2. begin the loop by dequeueing the front entry of the Queue <>
     * 3. use the contents of this entry to change the state of the vertex <>
     *      1. set path length to 0 <>
     *      2. set null predicessor <>
     * 4. origin vertex set to visited <>
     * 5. get unvisited neighbors and their costs <>
     * 6. use ^ for to create the objects in the Priority Queue <>
     * 7. remove the front entry from the Queue and visit that vertex <>
     * 8. increase the path cost and set the predecessor <>
     * 9. the get the current vertex's neighbors <>
     * 10. repeate until you reach the end vertex <>
     * 11. push the end vertex to the path stack
     * 12. loop to get the predecessors in the path
     */
    public int getCheapestPath(VertexInterface<T> originVertex, VertexInterface<T> endVertex, StackInterface<T> path) {
        // Variables
        int pathCost = 0; // The pathCost
        boolean done = false; // Checks if the endVertex was found
        PriorityQueue<T> priorityQueue = new PriorityQueue<>(); // The Priority Queue
        // Add/Enqueue the Origin Vertex Entry to the Priority Queue
        priorityQueue.enqueue(new EntryPQ<T>(originVertex, 0, null));
        try { // If the Dequeue fails
            // Loop through the Graph
            while (!done && !priorityQueue.isEmpty()) {
                    // Remove/Dequeue the Front Entry from the Priority Queue
                    EntryPQ<T> frontEntry = priorityQueue.dequeue(); // EntryPQ Type
                    // Get the Vertex Created by the Front Entry in the Priority Queue
                    VertexInterface<T> frontVertex = frontEntry.getVertex();
                    // If the Vertex is Not Visited
                    if (!frontVertex.isVisited()) {
                        // Mark the Vertex as Visited
                        frontVertex.visit();

                        // Set the Front Vertex Cost to the Cost in Front Entry
                        frontVertex.setCost(frontEntry.getEdgeWeight());
                        // Set the Path Cost to the Froth Entry Cost
                        pathCost = frontVertex.getCost();

                        // Set the Predcessor of Front Vertex to the Front Entry Cost
                        frontVertex.setPredcessor(frontEntry.getPredecessor());
                        if (frontVertex == endVertex) {
                            done = true;
                        } else {
                            // Get Unvisited Neighbors of Front Vertex
                            while (frontVertex.hasNeighbor()) {
                                VertexInterface<T> nextNeighbor = frontVertex.getUnvisitedNeighbor(); // Vertex
                                int weightOfEdgeToNeighbor = nextNeighbor.getCost(); // Cost
                                if (!nextNeighbor.isVisited()) {
                                    // Set the Cost of the Next Neighbor
                                    int nextCost = weightOfEdgeToNeighbor + pathCost;
                                    // Add/Enqueue the Next Neighbor to the Priority Queue
                                    priorityQueue.enqueue(new EntryPQ<T>(nextNeighbor, nextCost, frontVertex));
                                }//end if
                            }//end while-loop
                        }//end if-else
                    }//end if
            }//end while-loop
            // Get the Cost of the End Vertex
            pathCost = endVertex.getCost();
            // Push the End Vertex onto the Path Stack
            path.push(endVertex);
            // Set the Vertex Variable for the While Loop
            VertexInterface<T> vertex = endVertex;

            // Loop through to Get the Path Vertexes
            while (vertex.hasPredecessor()) {
                vertex = vertex.getPredecessor();
                path.push(vertex);
            }//end while-loop
        } catch (Exception err) { // If dequeue does not work,..
            System.out.println("NoVertexException: Created error:" + err);
        }//end try-catch

        return pathCost;
    }//end getCheapestPath()
}//end GraphADT
