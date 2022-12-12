import java.util.Stack;
public interface GraphInterface<T> {
    /** Adds a vertex to the graph.
     * @param vertexLabel An object that labels the new vertex and is distinct from the labels of the current vertices.
     * @return True if the vertex is added, or false otherwise. */
    public boolean addVertex(T vertexLabel);
    /** Adds a weighted edge to the graph.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object that labels the end vertex of the edge.
     * @param edgeWeight A double that is the weight of the edge. 
     * @return True if the edge is added, false otherwise. */
    public boolean addEdge(T begin, T end, double edgeWeight);
    /** Adds an unweighted edge to the graph.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object that labels the end vertex of the edge.
     * @return True if the edge is added, false otherwise. */
    public boolean addEdge(T begin, T end);
    /** Checks if an Edge exists between two vertices.
     * @param begin An object that labels the origin vertex of the edge to test.
     * @param end An object that labels the origin vertex of the edge to test.
     * @return True if an egde exists between the vertices given, false otherwise. */
    public boolean hasEdge(T begin, T end);
    /** Checks wether the graph is empty.
     * @return True if the graph is empty, false otherwise. */
    public boolean isEmpty();
    /** Gets the number of vertices in the graph.
     * @return An int that is the number of vertices. */
    public int getNumberOfVertices();
    /** Get the number of edges in the graph.
     * @return An int that is the number of edges. */
    public int getNumberOfEdges();
    /** Finds the least-cost path between two given vertices in this graph.
     * @param begin An object that labels the path's origin vertex.
     * @param end An object that labels the path's end vertex.
     * @param path A stack of labels that is empty initially; at the completion of the method, this stack 
     * contains the labels of the vertcies of the cheapest path the label of the origin vertex is at the bottom.
     * @return The cost of the cheapest path. */
    public double getCheapestPath(T origin, T end, Stack<T> path);
}//end GraphInterface