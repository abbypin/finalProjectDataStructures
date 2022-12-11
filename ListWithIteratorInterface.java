import java.util.Iterator;
public interface ListWithIteratorInterface<T> {
    public int getNumberOfEntries();
    public boolean isEmpty();
    public void add(T newEntry);
    public void add(int givenPosition, T newEntry);
    public T remove(int givenPosition);
    public void remove();
    public boolean remove(T anEntry);
    public boolean contains(T anEntry);
    // TODO: Find out if the toArray is Needed
    //public T[] toArray();
    //public T[][] toMatrix();
    public Iterator<T> getIterator();
}//end ListWithIteratorInterface
