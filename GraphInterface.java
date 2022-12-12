import java.util.Stack;
public interface GraphInterface<T> {
    public boolean addVertex(T vertexLabel);
    public boolean addEdge(T begin, T end, double edgeWeight);
    public boolean addEdge(T begin, T end);
    public boolean hasEdge(T begin, T end);
    public boolean isEmpty();
    public int getNumberOfVertices();
    public int getNumberOfEdges();
    public void display();
    // Going to take in Vertex Type
    public double getCheapestPath(T origin, T end, Stack<T> path);
}//end GraphInterface