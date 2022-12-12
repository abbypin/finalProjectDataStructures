import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

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

    public void display() {
		System.out.println("Graph has " + getNumberOfVertices() + " vertices and " + getNumberOfEdges() + " edges.");
		
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			((Vertex<T>)(vertexIterator.next())).display();
		}//end while-loop
	}//end display 

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
    public double getCheapestPath(T origin, T end, Stack<T> path) {
        resetVertices();
        // Variables
        double pathCost = 0; // The pathCost
        boolean done = false; // Checks if the endVertex was found
        PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<EntryPQ>(); // The Priority Queue
        VertexInterface<T> originVertex = vertices.getValue(origin); // Origin Vertex
        VertexInterface<T> endVertex = vertices.getValue(end); // End Vertex

        // Add/Enqueue the Origin Vertex Entry to the Priority Queue
        priorityQueue.add(new EntryPQ(originVertex, 0, null));
        try { // If the Dequeue fails
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
                        // Set the Path Cost to the Froth Entry Cost
                        pathCost = frontVertex.getCost();

                        // Set the Predcessor of Front Vertex to the Front Entry Cost
                        frontVertex.setPredcessor(frontEntry.getPredecessor());
                        if (frontVertex.equals(endVertex)) {
                            done = true;
                            endVertex.setCost(pathCost);
                        } else {
                            // Get Unvisited Neighbors of Front Vertex
                            
                           
                            // Get the frontVertex Neighbor and Weight Iterators
                            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                            Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
                            while (neighbors.hasNext()) {
                                // Get Next Neighbor and Edge Weight
                                VertexInterface<T> nextNeighbor = neighbors.next(); // Vertex
                                double weightOfEdgeToNeighbor = edgeWeights.next(); // Cost

                                // Unvisited Neighbors
                                if (!nextNeighbor.isVisited()) {
                                    // Set the Cost of the Next Neighbor
                                    double nextCost = weightOfEdgeToNeighbor + pathCost;
                                    // Add/Enqueue the Next Neighbor to the Priority Queue
                                    priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                                }//end if
                            }//end while-loop
                        }//end if-else
                    }//end if
            }//end while-loop
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
        } catch (Exception err) { // If dequeue does not work,..
            System.out.println("NoVertexException: Created error:" + err);
        }//end try-catch

        return pathCost;
    }//end getCheapestPath()

    private class EntryPQ implements Comparable<EntryPQ> {
        private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost;

        private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        }//end constructor

        public VertexInterface<T> getVertex() {
            return vertex;
        }//end getVertex()

        public VertexInterface<T> getPredecessor() {
            return previousVertex;
        }//end getPredecessor()

        public double getCost() {
            return cost;
        }//end getCost()

        public int compareTo(EntryPQ otherEntry) {
            return (int)Math.signum(otherEntry.cost - cost);
        }//end compareTo()

        public String toString() {
            String result = vertex.toString() + " " + cost;
            return result;
        }//end toString()
    }//end EntryPQ
}//end GraphADT
