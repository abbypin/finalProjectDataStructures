import java.util.Iterator;
import java.lang.Exception;
import java.util.NoSuchElementException;

public class ListWithIterator<T> implements 
ListWithIteratorInterface<T> {
    Node firstNode;
    int numberOfEntries;

    public ListWithIterator() {
        firstNode = null;
        numberOfEntries = 0;
    }//end constructor

    public boolean isEmpty() {
        return (numberOfEntries == 0);
    }//end isEmpty()

    public int getNumberOfEntries() {
        return numberOfEntries;
    }//end getNumberOfEntries()

    public void add(T newEntry) {
        Node newNode = new Node(newEntry);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            Node lastNode = getNodeAt(numberOfEntries);
            lastNode.setNextNode(newNode);
        }//end if-else

        numberOfEntries++;
    }//end add(newEntry)

    public void add(int givenPosition, T newEntry) {
        // Check if the position is valid
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries + 1)) {
            Node newNode = new Node(newEntry); //The new Node
            if (givenPosition == 1) { // For first position
                newNode.setNextNode(firstNode);
                firstNode = newNode;
                numberOfEntries++;
            } else { // otherwise,..
                // Get the Previous Node for New Node
                Node nodeBefore = getNodeAt(givenPosition - 1);
                // Get The Next Node for New Node
                Node nodeAfter = nodeBefore.getNextNode();
                // Set the Next Node
                newNode.setNextNode(nodeAfter);
                // Set the New Node on its Previous Node
                nodeBefore.setNextNode(newNode);
                numberOfEntries++;
            }//end if-else
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to the list add operation.");
        }//end if-else
    }//end add(givenPositon)

    public T remove(int givenPosition) {
        T result = null;
        // Check if the position is valid
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            if ((givenPosition == 1)) { // For the position
                result = firstNode.getData();
                firstNode = firstNode.getNextNode();
                numberOfEntries--;
            } else { // otherwise,..
                // Get the Node Before the Node to Remove
                Node nodeBefore = getNodeAt(givenPosition - 1);
                // Get the Node to Remove
                Node nodeToRemove = nodeBefore.getNextNode();
                result = nodeToRemove.getData();
                // Get the Node After the Node to Remove
                Node nodeAfter = nodeToRemove.getNextNode();
                // Remove the Node
                nodeBefore.setNextNode(nodeAfter);
                numberOfEntries--;
            }//end if-else
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to the list remove operation.");
        }//end if-else

        return result;
    }//end remove(givenPosition)

    private int getIndex(T anEntry) throws Exception {
        int index = 1;
        boolean found = false;
        Node currentNode = firstNode;
        if (!isEmpty()) {
            while (!found && (currentNode != null)) {
                if (currentNode == anEntry) {
                    found = true;
                    return index;
                } else {
                    index++;
                    currentNode.getNextNode();
                }//end if-else
            }//end while-loop

            if (found == false) {
                throw new Exception("No such neighbor exists in the list.");
            } else {
                return 0;
            }//end if
        } else {
            throw new Exception("There are no neighbors in the list.");
        }//end if-else
    }//end getIndex()

    public void remove() {
        if (!isEmpty()) {
            remove(numberOfEntries);
        }//end if
    }//end remove()

    public boolean remove(T anEntry) {
        try {
            int index = getIndex(anEntry);

            if (index != 0) { // If the index exists
                T removedData = remove(index);
                if (removedData != null) {
                    return true;
                } else {
                    return false;
                }//end if-else
            } else { // if the index does not exist
                return false;
            }//end if-else
        } catch (Exception err) { // if getIndex() fails
            System.out.println(err);
            return false;
        }//end try-catch
    }//end remove()

    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        if (!isEmpty()) {
            while (!found && (currentNode != null)) {
                if (currentNode.getData() == anEntry) {
                    found = true;
                } else {
                    currentNode = currentNode.getNextNode();
                }//end if-else
            }//end while-loop
        }//end if
        return found;
    }//end contains()

    public Iterator<T> iterator() {
        return new IteratorForLinkedList();
    }//end iterator()

    public Iterator<T> getIterator() {
        return iterator();
    }//end getIterator()

    public class IteratorForLinkedList implements Iterator<T> {
        private Node nextNode;

        private IteratorForLinkedList() {
            nextNode = firstNode;
        }//end constructor

        public boolean hasNext() {
            return (nextNode != null);
        }//end hasNext()

        public T next() {
            T result;
            if (hasNext()) {
                result = nextNode.getData();
                nextNode = nextNode.getNextNode();
            } else {
                throw new NoSuchElementException("Illegal call to next(): " + "iterator is after end of list.");
            }//end if-else

            return result;
        }//end next()

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by this iterator");
        }//end remove()
    }//end IteratorForLinkedList

    private Node getNodeAt(int givenPosition) {
        Node currentNode = firstNode;
        
        for (int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.getNextNode();

        return currentNode;
    }//end getNodeAt()

    private class Node {
        private Node next;
        private T data;

        private Node() {
            next = null;
            data = null;
        }//end default cunstructor

        private Node(T dataPortion) {
            next = null;
            data = dataPortion;
        }//end constructor

        private Node(Node newNode, T dataPortion) {
            next = newNode;
            data = dataPortion;
        }//end constructor

        private Node getNextNode() {
            return next;
        }//end getNextNode()

        private void setNextNode(Node newNode) {
            next = newNode;
        }//end setNextNode()

        private T getData() {
            return data;
        }//end getData()
    }//end Node
}//end LinkedListWithIterator
