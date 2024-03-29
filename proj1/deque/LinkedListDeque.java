package deque;

import java.util.Iterator;

public class LinkedListDeque <T> implements Deque<T> , Iterable<T>{
    private class node{
        public T value;
        public node next;
        public node prev;
        public node(T val , node next_node , node prev_node){
            this.value = val;
            this.next = next_node;
            this.prev = prev_node;
        }
    }

    private int size;
    private node scout;
    private node last;

    public LinkedListDeque(){
        this.scout = new node(null , null , null);
        this.scout.prev = this.scout;
        this.scout.next = this.scout;
        this.size = 0;
        this.last = this.scout;
    }

    private class LinkedList_iterator implements Iterator<T> {
        private int curr;
        public LinkedList_iterator(){
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
        return new LinkedList_iterator();
    }
    @Override
    public void addFirst(T item){

        if (this.isEmpty()){
            this.addLast(item);
        }else {
            this.scout.next = new node(item , this.scout.next , this.scout);
            this.scout.next.next.prev = this.scout.next;
            this.size += 1;
        }

    }
    @Override
    public void addLast(T item){
        this.last.next = new node(item , this.scout , this.last);

        this.scout.prev = this.last.next;
        this.last = last.next;
        this.size += 1;
    }
    @Override
    public int size(){
        return this.size;
    }

    @Override
    public void printDeque(){
        node p = this.scout;
        while (p != this.scout){
            if(p == this.last)System.out.println(p.value);
            System.out.print(p.value + " ");
            p = p.next;
        }
    }
    @Override
    public T removeFirst(){
        if (! this.isEmpty()) {
            if (this.last == this.scout.next)this.last = this.scout;
            T first_value = this.scout.next.value;
            this.scout.next = this.scout.next.next;
            this.scout.next.prev = this.scout;
            this.size -= 1;
            return first_value;
        }
        return null;
    }
    @Override
    public T removeLast(){
        if(! this.isEmpty()) {
            T last_value = this.last.value;
            this.last = this.last.prev;
            this.last.next = this.scout;
            this.scout.prev = this.last;
            this.size -= 1;
            return last_value;
        }
        return null;
    }

    @Override
    public T get(int index){
        node p = this.scout;
        for(int i = 0 ; i < index ; i += 1){
            p = p.next;
        }
        return p.next.value;
    }

    private T get_helper(node p , int index){
        if (index == 0)return p.next.value;
        else return get_helper(p.next , index - 1);
    }

    public T getRecursive(int index){
        return get_helper(this.scout , index);
    }

    @Override
    public boolean equals(Object o){
        if (o == null)return false;
        if (!(o instanceof LinkedListDeque))return false;
        if (this == o)return true;
        LinkedListDeque<T> other = (LinkedListDeque<T>) o;
        if (other.size != this.size)return false;
        for (int i = 0 ; i < this.size ; i += 1){
            if (!this.get(i).equals(other.get(i)) )return false;
        }
        return true;
    }
}
