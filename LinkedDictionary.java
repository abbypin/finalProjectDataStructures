import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedDictionary<K, V> implements DictionaryInterface<K, V> {
    private Node firstNode;
    private int size;

    public LinkedDictionary() {
        firstNode = null;
        size = 0;
    }//end constructor

// Add Method
    public V add(K key, V value) {
        V result = null;
        if ((key == null || value == null))
            throw new IllegalArgumentException("Cannot add null to a dictionary.");
        else {
            Node currentNode = firstNode;

            if ((currentNode != null) && (key.equals(currentNode.getKey()))) {
                result = currentNode.getValue();
                currentNode.setValue(value);
            } else {
                Node newNode = new Node(key, value);
                if (currentNode == null) {
                    newNode.setNextNode(firstNode);
                    firstNode = newNode;
                } else {
                    newNode.setNextNode(currentNode);
                    firstNode = newNode;
                }//end if-else
                size++;
            }//end if-else
        }//end if-else
        return result;
    }//end add(key, value)

// Remove Method
    public V remove(K key) {
        V result = null;
        boolean found = false;
        Node currentNode = firstNode;
        Node nodeBefore = null;
        Node nodeAfter = null;
        int count = 0;

        while (!found && (currentNode != null)) {
            if (key.equals(currentNode.getKey())) {
                // Found is True
                found = true;
                // Set the Result
                result = currentNode.getValue();
                // Get Node Before and After
                nodeBefore = getNodeAt(count - 1);
                nodeAfter = currentNode.getNextNode();
                // Remove Node
                nodeBefore.setNextNode(nodeAfter);
                // Decrament the Size
                size--;
            } else {
                currentNode = currentNode.getNextNode();
            }//end if-else
            count++; // Count the llop for the index
        }//end while-loop

        return result;
    }//end remove(key)

// Traversing Methods
    public boolean contains(K key) {
        boolean found = false;
        Node currentNode = firstNode;
        
        while (!found && (currentNode != null)) {
            if (key.equals(currentNode.getKey())) {
                found = true;
            } else {
                currentNode = currentNode.getNextNode();
            }//end if-else
        }//end while-loop

        return found;
    }//end contains()

    private int getIndex(K key) {
        int index = 0;
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (key.equals(currentNode.getKey())) {
                found = true;
                return index;
            } else {
                index++;
                currentNode = currentNode.getNextNode();
            }//end if-else
        }//end while-loop

        return -1;
    }//end getIndex()

// Get Value Method
    public V getValue(K key) {
        V result = null;
        int index = getIndex(key);

        if (index != -1) {
            Node node = getNodeAt(index);
            result = node.getValue();
        }//end if
        
        return result;
    }//end getValue()

// Iterator
    public Iterator<K> getKeyIterator() {
        return new KeyIterator();
    }//end getKeyIterator()

// Basic Methods
    public boolean isEmpty() {
        return (size == 0);
    }//end isEmpty()

    public int getSize() {
        return size;
    }//end getSize()

    public void clear() {
        throw new UnsupportedOperationException();
    }//end clear()

// Get Node Method
    private Node getNodeAt(int givenPosition) {
        Node currentNode = firstNode;
        
        for (int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.getNextNode();

        return currentNode;
    }//end getNodeAt()

// Key Iterator
    private class KeyIterator implements Iterator<K> {
        private Node nextNode;

        public KeyIterator() {
            nextNode = firstNode;
        }//end constructor

        public boolean hasNext() {
            return (nextNode != null);
        }//end hasNext()

        public K next() {
            K result;

            if (hasNext()) {
                result = nextNode.getKey();
                nextNode = nextNode.getNextNode();
            } else {
                throw new NoSuchElementException();
            }//end if-else

            return result;
        }//end next()

        public void remove() {
            throw new UnsupportedOperationException();
        }//end remove()
    }//end KeyIterator

// Node Class
    private class Node {
        private Node next;
        private K key;
        private V value;

        private Node() {
            next = null;
            key = null;
            value = null;
        }//end default constructor

        private Node(K newKey, V newValue) {
            key = newKey;
            value = newValue;
            next = null;
        }//end constructor

        public void setNextNode(Node newNode) {
            next = newNode;
        }//end setNextNode()

        public Node getNextNode() {
            return next;
        }//end getNextNode()

        public K getKey() {
            return key;
        }//end getKey()

        public void setValue(V newValue) {
            value = newValue;
        }//end setValue()

        public V getValue() {
            return value;
        }//end getValue()
    }//end Node
}//end LinkedDictionary
