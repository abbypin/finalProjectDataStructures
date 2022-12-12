import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

/** The Directed Graph implementation class. */
public class DirectedGraph<T> implements GraphInterface<T> {
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices = new LinkedDictionary<>();
        edgeCount = 0;
    }//end constructor

// Add Methods
    /** Adds a vertex to the graph.
     * @param vertexLabel An object that labels the new vertex and is distinct from the labels of the current vertices.
     * @return True if the vertex is added, or false otherwise. */
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcomes = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return (addOutcomes == null);
    }//end addVertex()

    /** Adds a weighted edge to the graph.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object that labels the end vertex of the edge.
     * @param edgeWeight A double that is the weight of the edge. 
     * @return True if the edge is added, false otherwise. */
    public boolean addEdge(T begin, T end, double edgeWeight) {
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

    /** Adds an unweighted edge to the graph.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object that labels the end vertex of the edge.
     * @return True if the edge is added, false otherwise. */
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }//end addEdge()

// Has Edge Method
    /** Checks if an Edge exists between two vertices.
     * @param begin An object that labels the origin vertex of the edge to test.
     * @param end An object that labels the origin vertex of the edge to test.
     * @return True if an egde exists between the vertices given, false otherwise. */
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

// Basic Methods
    /** Checks wether the graph is empty.
     * @return True if the graph is empty, false otherwise. */
    public boolean isEmpty() {
        return vertices.isEmpty();
    }//end isEmpty()

    /** Gets the number of vertices in the graph.
     * @return An int that is the number of vertices. */
    public int getNumberOfVertices() {
        return vertices.getSize();
    }//end getNumberOfVertices()

    /** Get the number of edges in the graph.
     * @return An int that is the number of edges. */
    public int getNumberOfEdges() {
        return edgeCount;
    }//end getNumberOfEdges()

    /** Protected method that resets the vertices to unvisited, 0 cost, and sets the predecessor to null. */
    protected void resetVertices() {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredcessor(null);
        }//end while-loop
    }//end resetVertices()

// Algorithm Methods
    /** Finds the least-cost path between two given vertices in this graph.
     * @param begin An object that labels the path's origin vertex.
     * @param end An object that labels the path's end vertex.
     * @param path A stack of labels that is empty initially; at the completion of the method, this stack 
     * contains the labels of the vertcies of the cheapest path the label of the origin vertex is at the bottom.
     * @return The cost of the cheapest path. */
    public double getCheapestPath(T origin, T end, Stack<T> path) {
        resetVertices();
        // Variables
        boolean done = false; // Checks if the endVertex was found
        PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<EntryPQ>(); // The Priority Queue
        VertexInterface<T> originVertex = vertices.getValue(origin); // Origin Vertex
        VertexInterface<T> endVertex = vertices.getValue(end); // End Vertex

        // Add/Enqueue the Origin Vertex Entry to the Priority Queue
        priorityQueue.add(new EntryPQ(originVertex, 0, null));
        // Loop through the Graph
        while (!done && !priorityQueue.isEmpty()) {
            // Remove/Dequeue the Front Entry from the Priority Queue
            EntryPQ frontEntry = priorityQueue.remove(); // EntryPQ Type
            // Get the Vertex Created by the Front Entry in the Priority Queue
            VertexInterface<T> frontVertex = frontEntry.getVertex();
            // If the Vertex is Not Visited
            if (!frontVertex.isVisited()) {
                // Mark the Vertex as Visited
                frontVertex.visit();

                // Set the Front Vertex Cost to the Cost in Front Entry
                frontVertex.setCost(frontEntry.getCost());

                // Set the Predcessor of Front Vertex to the Front Entry Cost
                frontVertex.setPredcessor(frontEntry.getPredecessor());
                if (frontVertex.equals(endVertex)) {
                    done = true;
                    endVertex.setCost(frontVertex.getCost());
                } else {
                    // Get the frontVertex Neighbor and Weight Iterators
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
                    while (neighbors.hasNext()) {
                        // Get Next Neighbor and Edge Weight
                        VertexInterface<T> nextNeighbor = neighbors.next(); // Vertex
                        double weightOfEdgeToNeighbor = (double)edgeWeights.next(); // Cost

                        // Unvisited Neighbors
                        if (!nextNeighbor.isVisited()) {
                            // Set the Cost of the Next Neighbor
                            double nextCost = (double)weightOfEdgeToNeighbor + frontEntry.getCost();
                            // Add/Enqueue the Next Neighbor to the Priority Queue
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        }//end if
                    }//end while-loop
                }//end if-else
            }//end if
        }//end while-loop

        double pathCost = 0;
        try {
            // Get the Cost of the End Vertex
            pathCost = endVertex.getCost();
        
            // Push the End Vertex onto the Path Stack
            path.push(end);
            // Set the Vertex Variable for the While Loop
            VertexInterface<T> vertex = endVertex;

            // Loop through to Get the Path Vertexes
            while (vertex.hasPredecessor()) {
                vertex = vertex.getPredecessor();
                path.push(vertex.getLabel());
                System.out.print(path.peek() + " - ");
            }//end while-loop
            System.out.println(" : pathCost = " + pathCost);
        } catch (Exception err) {
            System.out.println(err);
        }//end try-catch
    
        return pathCost;
    }//end getCheapestPath()

    /** Private class for the Priority Queue that holds the vertex, its cost, and the predceessor in the path. */
    private class EntryPQ implements Comparable<EntryPQ>, java.io.Serializable {
        private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost;

        private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        }//end constructor

        /** Get this vertex for this entry.
         * @return The current vertex. */
        public VertexInterface<T> getVertex() {
            return vertex;
        }//end getVertex()

        /** Get the previous vertex in the path.
         * @return The previous vertex. */
        public VertexInterface<T> getPredecessor() {
            return previousVertex;
        }//end getPredecessor()

        /** Get the cost.
         * @return The cost of type double. */
        public double getCost() {
            return cost;
        }//end getCost()

        /** Compare an entry with another entry.
         * @param otherEntry EntryPQ object that is to be compared.
         * @return An int 0 if the too entries are equal or greater than or less than 0 for greater and less respectively. */
        public int compareTo(EntryPQ otherEntry) {
            return (int)Math.signum(otherEntry.cost - cost);
        }//end compareTo()

        /** Create a String version of this object.
         * @return The resulting String. */
        public String toString() {
            String result = vertex.toString() + " " + cost;
            return result;
        }//end toString()
    }//end EntryPQ
}//end GraphADT
