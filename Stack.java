import java.util.EmptyStackException;
import java.util.Vector;
public class Stack<T> implements StackInterface<T> {
    private Vector<VertexInterface<T>> stack;
    private static final int DEFAULT_CAPACITY = 3;

    public Stack() {
        stack = new Vector<>(DEFAULT_CAPACITY);
    }//end default constructor

    public Stack(int initialCapacity) {
        stack = new Vector<>(initialCapacity);
    }//end constructor

    public void push(VertexInterface<T> newEntry) {
        stack.add(newEntry);
    }//end push()

    public VertexInterface<T> pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack.remove(stack.size() - 1);
        }//end if-else
    }//end pop()

    public VertexInterface<T> peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack.lastElement();
        }//end if-else
    }//end peek()

    public boolean isEmpty() {
        return stack.isEmpty();
    }//end isEmpty()
}//end Stack
