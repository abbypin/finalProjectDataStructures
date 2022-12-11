public interface PriorityQueueInterface<T> {
    public void enqueue(EntryPQ<T> newEntry);
    public EntryPQ<T> dequeue() throws Exception;
    public EntryPQ<T> peek() throws Exception;
    public boolean isEmpty();
    public int getSize();
    public void clear();
}//end PriorityQueueInterface
