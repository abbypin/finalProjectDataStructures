import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {
    private T label; // The label/name of the vertex
    private ListWithIteratorInterface<Edge> edgeList; // The neighbors
    private boolean visited; // Mark as visited
    private VertexInterface<T> previousVertex; // The previous vertex
    private int cost; // The cost of the path

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
    public T getLabel() {
        return label;
    }//end getLabel()

// Visited Methods
    public void visit() {
        visited = true;
    }//end visit()

    public void unvisit() {
        visited = false;
    }//end unvisit()

    public boolean isVisited() {
        return visited;
    }//end isVisited()

// Connect Methods
    public boolean connect(VertexInterface<T> endVertex, int edgeWeight) {
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
                result = true;
            }//end if
        }//end if

        return result;
    }//end connect(edgeWeight)

    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }//end connect()

// Equals Method
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
    public void setPredcessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }//end setPredecessor()

    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }//end getPredecessor()

    public boolean hasPredecessor() {
        return (previousVertex != null);
    }//end hasPredecessor()

// Cost Methods
    public void setCost(int newCost) {
        cost = newCost;
    }//end setCost()

    public int getCost() {
        return cost;
    }//end getCost()

// Neighbor Methods
    public boolean hasNeighbor() {
        return (!edgeList.isEmpty());
    }//end hasNeighbor()

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

    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    }//end getNeighborIterator()

    public Iterator<Integer> getWeightIterator() {
        return new WeightIterator();
    }//end getWeightIterator()

// Neighbor Iterator
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

    private class WeightIterator implements Iterator<Integer> {
        private Iterator<Edge> edges;
        private WeightIterator() {
            edges = edgeList.getIterator();
        }//end constructor

        public boolean hasNext() {
            return edges.hasNext();
        }//end hasNext()

        public Integer next() {
            Integer nextWeight = null;
            if (edges.hasNext()) {
                Integer weightToNextEdge = edges.next().getWeight();
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
        private int weight;

        protected Edge(VertexInterface<T> endVertex, int edgeWeight) {
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

        protected int getWeight() {
            return weight;
        }//end getWeight()

        protected void setEndVertex(VertexInterface<T> endVertex) {
            vertex = endVertex;
        }//end setEndVertex()

        protected void setWeight(int edgeWeight) {
            weight = edgeWeight;
        }//end setWeight()
    }//end Edge
}//end Vertex
