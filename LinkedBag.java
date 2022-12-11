import java.util.NoSuchElementException;

public class LinkedBag<T> implements BagInterface<T> {
    Node firstNode;
    int numberOfEntries;

    public LinkedBag() {
        firstNode = null;
        numberOfEntries = 0;
    }//end constructor

    public boolean isEmpty() {
        return (numberOfEntries == 0);
    }//end isEmpty()

    public int getSize() {
        return numberOfEntries;
    }//end getSize()

    // Add a New Entry
    public void add(T dataOne, T dataTwo, T dataThree, T dataFour, T dataFive) {
        @SuppressWarnings("unchecked")
        T[] newEntry = (T[])new Object[5];
        newEntry[0] = dataOne;
        newEntry[1] = dataTwo;
        newEntry[2] = dataThree;
        newEntry[3] = dataFour;
        newEntry[4] = dataFive;
        Node newNode = new Node(newEntry);

        if (firstNode == null) {
            firstNode = newNode;
            numberOfEntries++;
        } else {
            newNode.setNextNode(firstNode);
            firstNode = newNode;
            numberOfEntries++;
        }//end if-else
    }//end add()

    // Replace the Node with a new Node
    public void replace(int index, T dataFive) {
        // Veriables
        Node getNode = null;
        Node nodeBefore = null;
        Node nodeAfter = null;

        // Try to get the Node
        try {
            getNode = getNodeAt(index);
        } catch (Exception err) {
            System.out.println(err + "Failed to get one of the index Node for replace");
        }//end try-catch
        // Try to get the Node Before the Node getting Replaced
        try {
            nodeBefore = getNodeAt(index - 1);
        } catch (Exception err) {
            System.out.println(err + "Failed to get one of the index NodeBefore for replace");
        }//end try-catch
        // Try to get the Node After the Node getting Replaced
        try {
            nodeAfter = nodeBefore.getNextNode();
        } catch (Exception err) {
            if (getIndexOf(nodeAfter.getData()[0]) == getSize() - 1 || nodeBefore.getNextNode() == null) {
                nodeAfter = null;
            } else {
                System.out.println(err + "Failed to get one of the index NodeAfter for replace");
            }//end if-else
        }//end try-catch
        
        // Get the Current Airport Data
        T dataOne = getNode.getData()[0];
        T dataTwo = getNode.getData()[1];
        T dataThree = getNode.getData()[2];
        T dataFour = getNode.getData()[3];

        // Create a New Array Containing the Old Data and the new Edge Data
        @SuppressWarnings("unchecked")
        T[] data = (T[])new Object[5];
        data[0] = dataOne;
        data[1] = dataTwo;
        data[2] = dataThree;
        data[3] = dataFour;
        data[4] = dataFive;

        // Create a New Node Containing Data
        Node newNode = new Node(data);
        // Set the Next Node
        newNode.setNextNode(nodeAfter);
        // Set the New Node on its Previous Node
        nodeBefore.setNextNode(newNode);
    }//end replace()

    // Remove the firstNode
    public T[] remove() throws Exception {
        T[] result = null;
        if (!isEmpty()) {
            result = firstNode.getData();
            firstNode = firstNode.getNextNode();
        } else {
            throw new Exception("No elements saved from the airport data.");
        }//end if-else

        return result;
    }//end remove()

    // Get the Data at the Index
    public T[] get(int index) {
        @SuppressWarnings("unchecked")
        T[] array = (T[])new Object[5];
        Node currentNode = firstNode;
        boolean found = false;
        int i = 0;
        if ((index > (getSize() - 1)) && (i < 0)) {
            throw new NoSuchElementException();
        } else {
            while (!found && (i <= index)) {
                if (i == index) {
                    found = true;
                    array[0] = currentNode.getData()[0];
                    array[1] = currentNode.getData()[1];
                    array[2] = currentNode.getData()[2];
                    array[3] = currentNode.getData()[3];
                    array[4] = currentNode.getData()[4];
                } else {
                    i++;
                    currentNode = currentNode.getNextNode();
                }//end if-else
            }//end while-loop

            if (!found) {
                throw new NoSuchElementException();
            }//end if()
        }//end if-else

        return array;
    }//end get()

    // Get the Index of the airportCode
    public int getIndexOf(T dataOne) {
        boolean found = false;
        Node currentNode = firstNode;
        int index = 0, i = 0;

        while (!found && (firstNode != null)) {
            if (firstNode.getData()[0].equals(dataOne)) {
                found = true;
                index = i;
                return index;
            } else {
                i++;
                currentNode = currentNode.getNextNode();
            }//end if-else
        }//end while-loop

        if (!found) {
            index = -1;
        }//end if-else

        return index;
    }//end getIndexOf()

    // Get the Node at the Index
    public Node getNodeAt(int index) throws Exception {
        Node currentNode = firstNode;
        boolean found = false;
        int i = 0;
        if (i < 0 || index > getSize()) {
            throw new Exception("NoSuchIndexException");
        } else {
            while (!found && (currentNode != null)) {
                if (i == index) {
                    found = true;
                    return currentNode;
                } else {
                    currentNode = currentNode.getNextNode();
                    i++;
                }//end if-else
            }//end while-loop
        }//end if-else

        return currentNode;
    }//end getNodeAt()

    private class Node {
        private Node next;
        private T[] data;

        private Node() {
            next = null;
            data = null;
        }//end constructor
        private Node(T[] dataPortion) {
            data = dataPortion;
            next = null;
        }//end construnctor

        private Node getNextNode() {
            return next;
        }//end getNextNode()

        private T[] getData() {
            return data;
        }//end getData()

        private void setNextNode(Node newNode) {
            next = newNode;
        }//end setNextNode()
    }//end Node
}
