public class Queue<T> {
    private int numOfEntries;
    private int i = 0;
    @SuppressWarnings("unchecked")
    private Object[] array = (T[])new Object[4];

    public Queue() {
        numOfEntries = 0;
    }//end

    public void enqueue(Object data) {
        array[i] = data;
        numOfEntries++;
        i++;
    }//end enquque()

    public T peek() {
        @SuppressWarnings("unchecked")
        T result = (T) array[numOfEntries - 1];

        return result;
    }//end peek()

    public Object dequeue() {
        Object result = array[numOfEntries - 1];
        array[numOfEntries - 1] = null;
        numOfEntries--;

        return result;
    }//end dequeue()

    public boolean isEmpty() {
        return (numOfEntries == 0);
    }//end isEmpty()

    public int getNumOfEntries() {
        return numOfEntries;
    }//end getNumOfEntries()
}//end Queue
