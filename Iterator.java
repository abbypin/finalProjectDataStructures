public interface Iterator<T> extends Iterable<T> {
    public Iterator<T> getNeighborIterator();
    public Iterator<Integer> getWeightIterator();
}//end Iterator
