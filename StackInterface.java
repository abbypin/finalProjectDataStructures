public interface StackInterface<T> {
    public void push(VertexInterface<T> newEntry);
    public VertexInterface<T> pop();
    public VertexInterface<T> peek();
    public boolean isEmpty();
}//end StackInteface
