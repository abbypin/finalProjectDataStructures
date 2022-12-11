public class EntryPQ<T> {
    private VertexInterface<T> vertex;
    private int weight;
    private VertexInterface<T> previousVertex;

    public EntryPQ(VertexInterface<T> newVertex, int edgeWeight, VertexInterface<T> predecessor) {
        vertex = newVertex;
        weight = edgeWeight;
        previousVertex = predecessor;
    }//end constructor

    public VertexInterface<T> getVertex() {
        return vertex;
    }//end getVertex()

    public int getEdgeWeight() {
        return weight;
    }//end getEdgeWeight()

    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }//end getPredecessor()
}//end EntryPQ
