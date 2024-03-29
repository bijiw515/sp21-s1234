package deque;

import java.util.Iterator;

public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);

    public Iterator<T> iterator();
    public default boolean isEmpty(){
        return this.size() == 0;
    }
}
