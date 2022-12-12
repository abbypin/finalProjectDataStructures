//
// Name: Pinkus, A.
// Project: Project 5
// Due: 12/12/2020
// Course: CS2400.02
// Description: The Vertex class.
// Note: I was given exxtended time for the project
//

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {
    private T label; // The label/name of the vertex
    private ListWithIteratorInterface<Edge> edgeList; // The neighbors
    private boolean visited; // Mark as visited
    private VertexInterface<T> previousVertex; // The previous vertex
    private double cost; // The cost of the path

    /** The constructor sets the label of the vertex.
     * @param vertexLabel A generic type, T, vertex label. */
    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new ListWithIterator<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }//end constructor

// Label Method
    /** Gets this vertex's label.
     * @return The object that labels the vertex. */
    public T getLabel() {
        return label;
    }//end getLabel()

// Visited Methods
    /** Marks this vertex as visited. */
    public void visit() {
        visited = true;
    }//end visit()

    /** Marks this vertex as unvisited. */
    public void unvisit() {
        visited = false;
    }//end unvisit()

    /** Sees whether this vertex is marked as visited.
     * @return True if the vertex is visited. */
    public boolean isVisited() {
        return visited;
    }//end isVisited()

// Connect Methods
    /** Connects this vertex and a given vertex with a weighted edge.
     * The two vertices cannot be the same, and must not already have 
     * this edge between them. In a directed graph, the edge points 
     * toward the given index.
     * @param endVertex A vertex in the graph that ends the edge.
     * @param edgeWeight A real-valued edge weight, if any
     * @return True if the edge is added or false otherwise. */
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        if (!this.equals(endVertex)) {
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;
            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    duplicateEdge = true;
                }//end if
            }//end while-loop
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                endVertex.setCost(edgeWeight);
                result = true;
            }//end if
        }//end if

        return result;
    }//end connect(edgeWeight)

    /** Connects this vertex and a given vertex with an unweighted edge.
     * The two vertices cannot be the same, and must not already 
     * have this edge between them. In a directed graph, the edge 
     * points toward the given vertex.
     * @param endVertex A vertex in the graph that ends the edge.
     * @return True if the edge is added, or false otherwise. */
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }//end connect()

// Equals Method
    /** Checks if this vertex is equal to a given vertex.
     * @param secondVertex A vertex to compare with this vertex. */
    @Override
    public boolean equals(VertexInterface<T> secondVertex) {
        boolean result;
        if ((secondVertex == null) || (this.getClass() != secondVertex.getClass()))
            return false;
        else {
            Vertex<T> otherVertex = (Vertex<T>)secondVertex;
            result = label.equals(otherVertex.label);
        }//end if-else
        return result;
    }//end equals()

// Predecessor Methods
    /** Records the previous vertex on a path to this vertex.
     * @param predecessor The vertex previous to this one on the path. */
    public void setPredcessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }//end setPredecessor()

    /** Gets the recorded predecessor of this vertex.
     * @return either this vertex's predecessor or null if no predecessor was recorded. */
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }//end getPredecessor()

    /** Sees whether a predecessor was recorded for this vertex.
     * @return Either this vertex's predecessor or null if no predecessor. */
    public boolean hasPredecessor() {
        return (previousVertex != null);
    }//end hasPredecessor()

// Cost Methods
    /** Records the cost of the path to this vertex.
     * @param newCost The cost of the path. */
    public void setCost(double newCost) {
        cost = newCost;
    }//end setCost()

    /** Retrieves the cost of the path to this vertex.
     * @return The cost of the path. */
    public double getCost() {
        return cost;
    }//end getCost()

// Neighbor Methods
    /** Sees whether this vertex has at least one neighbor.
     * @return True if the vertex has a neighbor. */
    public boolean hasNeighbor() {
        return (!edgeList.isEmpty());
    }//end hasNeighbor()

    /** Gets an unvisited neighbor, if any, of this vertex.
     * @return Either a vertex that is an unvisited neighbor or null 
     * if no such neighbor exists. */
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null)) {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        }//end while-loop
        return result;
    }//end getUnvisitedNeighbor()

    /** Creates an iterator of this vertex's neighbors by following 
     * all edges that begin at this vertex.
     * @return An iterator of the neighboring vertices of this vertex. */
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    }//end getNeighborIterator()

    /** Creates an iterator of the weights of the edges to this vertex's neighbors.
     * @return An iterator of edge weights for edges to neighbors of this vertex. */
    public Iterator<Double> getWeightIterator() {
        return new WeightIterator();
    }//end getWeightIterator()

    public void display() {
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        Iterator<Double> weights = getWeightIterator();
        while (neighbors.hasNext() && weights.hasNext()) {
            System.out.println(neighbors.next().getLabel() + " " + weights.next());
        }//end while-loop
         
    }//end display()

// Neighbor Iterator
    /** Class that is the Neighbor Iterator. Iterates through the neighboring vertex labels. */
    private class NeighborIterator implements Iterator<VertexInterface<T>> {
        private Iterator<Edge> edges;
    
        private NeighborIterator() {
            edges = edgeList.getIterator();
        }//end constructor

        public boolean hasNext() {
            return edges.hasNext();
        }//end hasNext()

        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            } else {
                throw new NoSuchElementException();
            }//end if-else
            return nextNeighbor;
        }//end next()

        public void remove() {
            throw new UnsupportedOperationException();
        }//end remove()
    }//end NeighborIterator

    /** Class that is the Weight Iterator. Iterates through the edge weights. */
    private class WeightIterator implements Iterator<Double> {
        private Iterator<Edge> edges;
        private WeightIterator() {
            edges = edgeList.getIterator();
        }//end constructor

        public boolean hasNext() {
            return edges.hasNext();
        }//end hasNext()

        public Double next() {
            Double nextWeight = null;
            if (edges.hasNext()) {
                Double weightToNextEdge = edges.next().getWeight();
                nextWeight = weightToNextEdge;
            } else {
                throw new NoSuchElementException();
            }//end if-else
            return nextWeight;
        }//end next()

        public void remove() {
            throw new UnsupportedOperationException();
        }//end remove()
    }//end WeightIterator

// Inner Edge Class
    protected class Edge {
        private VertexInterface<T> vertex;
        private double weight;

        protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        }//end constructor
        protected Edge(VertexInterface<T> endVertex) {
            vertex = endVertex;
            weight = 0;
        }//end constructor

        protected VertexInterface<T> getEndVertex() {
            return vertex;
        }//end getEndVertex()

        protected double getWeight() {
            return weight;
        }//end getWeight()

        protected void setEndVertex(VertexInterface<T> endVertex) {
            vertex = endVertex;
        }//end setEndVertex()

        protected void setWeight(double edgeWeight) {
            weight = edgeWeight;
        }//end setWeight()
    }//end Edge
}//end Vertex
