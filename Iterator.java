public interface Iterator<T> extends Iterable<T> {
    public Iterator<T> getNeighborIterator();
    public Iterator<Double> getWeightIterator();
}//end Iterator
