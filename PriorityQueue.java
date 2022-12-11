import java.lang.Exception;
public class PriorityQueue<T> implements PriorityQueueInterface<T> {
    private Node firstNode;
    private Node lastNode;
    private int numberOfEntries;

    public PriorityQueue() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }//end constructor

    public void enqueue(EntryPQ<T> newEntry) {
        Node newNode = new Node(newEntry, null);
        if (isEmpty()) {
            firstNode = newNode;
            numberOfEntries++;
        } else {
            if (firstNode.getData().getEdgeWeight() < newNode.getData().getEdgeWeight()) {
                Node frontNode = getNodeAt(1);
                firstNode = newNode;
                firstNode.setNextNode(frontNode);
            } else {
                lastNode.setNextNode(newNode);
                lastNode = newNode;
            }//end if-else
            numberOfEntries++;
        }//end if-else
    }//end enqueue()

    public EntryPQ<T> dequeue() throws Exception {
        EntryPQ<T> result = null;
        if (!isEmpty()) {
            result = firstNode.getData();
            firstNode = firstNode.getNextNode();
            numberOfEntries--;
        } else {
            throw new Exception("Empty Queue Exception. Cannot remove an entry.");
        }
        return result;
    }//end dequeue()

    public EntryPQ<T> peek() throws Exception {
        if (isEmpty())
            throw new Exception("Empty Queue Exception. No Data to peek at.");
        return firstNode.getData();
    }//end peek()

    public boolean isEmpty() {
        return (numberOfEntries == 0);
    }//end isEmpty()

    public int getSize() {
        return numberOfEntries;
    }//end getSize()

    public void clear() {
        throw new UnsupportedOperationException();
    }//end clear()

    private Node getNodeAt(int givenPosition) {
        Node currentNode = firstNode;
        
        for (int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.getNextNode();

        return currentNode;
    }//end getNodeAt()

    private class Node {
        private Node next;
        private EntryPQ<T> data;

        private Node() {
            next = null;
            data = null;
        }//end default constructor
        private Node(EntryPQ<T> dataPortion) {
            data = dataPortion;
            next = null;
        }//end constructor
        private Node(EntryPQ<T> dataPortion, Node newNode) {
            data = dataPortion;
            next = newNode;
        }//end constructor

        private Node getNextNode() {
            return next;
        }//end getNextNode()

        private void setNextNode(Node newNode) {
            next = newNode;
        }//end setNextNode()

        private EntryPQ<T> getData() {
            return data;
        }//end getData()
    }//end Node
}//end PriorityQueue
