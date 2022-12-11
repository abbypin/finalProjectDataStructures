public interface BagInterface<T> {
    public boolean isEmpty();
    public int getSize();
    public void add(T dataOne, T dataTwo, T dataThree, T dataFour, T dataFive);
    public T[] get(int index);
    public int getIndexOf(T dataOne);
    public T[] remove() throws Exception;
    public void replace(int index, T dataFive);
}//end BagInterface
