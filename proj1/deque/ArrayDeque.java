package deque;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> , Iterable<T> {
    private int size;
    private T[] items;
    private int front;
    private int rear;

    private class ArrayDeque_iterator implements Iterator<T>{
        private int curr;
        public ArrayDeque_iterator(){
            this.curr = 0;
        }
        @Override
        public boolean hasNext() {
            return this.curr < size;
        }
        @Override
        public T next() {
            T next = get(this.curr);
            this.curr += 1;
            return next;
        }
    }

    public Iterator<T> iterator(){
        return new ArrayDeque_iterator();
    }

    private int get_length(){
        return this.items.length;
    }

    private void move_front(){
        if (this.front == 0)this.front = this.items.length - 1;
        else this.front -= 1;
    }

    private void move_rear(){
        if(this.rear == this.items.length - 1)this.rear = 0;
        else this.rear += 1;
    }
    public  ArrayDeque(){
        this.size = 0;
        this.items = (T[]) new Object[8];
        this.front = 0;
        this.rear = 1;
    }

    private void resize_inc(int capacity){
        T [] new_items = (T[]) new Object[capacity];
        if (this.front < this.rear){
            System.arraycopy(this.items , 0 , new_items , 0 , this.rear);
            System.arraycopy(this.items , this.front + 1 , new_items ,this.front + this.size + 1 , this.size - this.rear);
            this.items = new_items;
            this.front += capacity / 2;
        }else if (this.front > this.rear){
            System.arraycopy(this.items , 0  , new_items ,1 , this.size );
            this.items = new_items;
            this.front = 0;
            this.rear = this.size + 1;

        }

    }

    private void resize_dec(int capacity){
        T [] new_items = (T[]) new Object[capacity];
        if (this.front < this.rear) {
            System.arraycopy(this.items, this.front + 1, new_items, 0, this.rear - this.front);
        } else if (this.front > this.rear) {
            System.arraycopy(this.items , 0 , new_items , 0 , this.rear);
            System.arraycopy(this.items , this.front + 1 ,new_items , this.front - capacity + 1 , this.items.length - this.front - 1);
            this.items = new_items;
            this.front -= capacity;
        }
    }

    @Override
    public void addFirst(T item){
        if (this.size == this.items.length)resize_inc(this.size * 2);
        this.items[front] = item;
        this.size += 1;
        move_front();
    }

    @Override
    public void addLast(T item){
        if (this.size == this.items.length)resize_inc(this.size * 2);
        this.items[rear] = item;
        this.size += 1;
        move_rear();
    }

    @Override
    public int size(){
        return this.size;
    }

    private boolean wasting_memory(){
        return this.size < this.items.length / 4 && this.items.length > 16;
    }

    @Override
    public T removeFirst(){
        if (wasting_memory()) resize_dec(this.items.length / 2);
        this.front = (this.front == this.items.length - 1)? 0 : front + 1;
        T first = this.items[front];
        this.items[front] = null;
        if (! this.isEmpty())this.size -= 1;
        return first;
    }
    @Override
    public T removeLast(){
        if (wasting_memory())resize_dec(this.items.length / 2);
        this.rear = (this.rear == 0)? this.items.length - 1 : rear - 1;
        T last = this.items[rear];
        this.items[rear] = null;
        if (! this.isEmpty())this.size -= 1;
        return last;
    }
    @Override
    public T get(int index){
        return this.items[(this.front + index + 1) % this.items.length];
    }

    @Override
    public void printDeque(){
        int i = this.front + 1;
        while (i != this.rear){
            if(i != this.items.length ){
                if (this.items[i] != null) System.out.print(this.items[i] + " ");
            }
            i = (i == this.items.length) ? 0 : i + 1;
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o){
        if (o == null)return false;
        if (!(o instanceof Deque))return false;
        if (this == o)return true;
        Deque<T> other = (Deque<T>) o;
        if (other.size() != this.size)return false;
        for (int i = 0 ; i < this.size ; i += 1){
            if (!this.get(i).equals(other.get(i)) )return false;
        }
        return true;
    }
}
