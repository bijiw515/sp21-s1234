package deque;

public class LinkedListDeque <T>{
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

    public void addFirst(T item){
        this.scout.next = new node(item , this.scout.next , this.scout);
        if (this.scout.next.next == this.scout){
            this.last = this.scout.next;
            this.scout.prev = this.last;
        }
        this.size += 1;
    }

    public void addLast(T item){
        this.last.next = new node(item , this.scout , this.last);
        this.last = last.next;
        this.scout.prev = this.last;
        this.size += 1;
    }

    public boolean isEmpty(){
        return this.scout.prev == this.scout && this.scout.next == this.scout;
    }

    public int size(){
        return this.size;
    }

    public void printDeque(){
        node p = this.scout;
        while (p != this.scout){
            if(p == this.last)System.out.println(p.value);
            System.out.print(p.value + " ");
            p = p.next;
        }
    }

    public T removeFirst(){
        if (! this.isEmpty()) {
            T first_value = this.scout.next.value;
            this.scout.next = this.scout.next.next;
            this.scout.next.prev = this.scout;
            this.size -= 1;
            return first_value;
        }
        return null;
    }

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

    public node getlast(){
        return this.last;
    }
    public T get(int index){
        node p = this.scout;
        for(int i = 0 ; i < index ; i += 1){
            p = p.next;
        }
        return p.next.value;
    }
}
