public interface GraphInterface<T> {
    public boolean addVertex(T vertexLabel);
    public boolean addEdge(T begin, T end, int edgeWeight);
    public boolean addEdge(T begin, T end);
    public boolean hasEdge(T begin, T end);
    public boolean isEmpty();
    public int getNumberOfVertices();
    public int getNumberOfEdges();
    // Going to take in Vertex Type
    public int getCheapestPath(VertexInterface<T> originVertex, VertexInterface<T> endVertex, StackInterface<T> path);
}//end GraphInterface