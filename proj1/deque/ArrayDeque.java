package deque;

public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int front;
    private int rear;

    public int get_length(){
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
        System.arraycopy(this.items , 0 , new_items , 0 , this.rear);
        System.arraycopy(this.items , this.front + 1 , new_items ,this.front + this.size + 1 , this.size - this.rear);
        this.items = new_items;
        this.front += this.size;
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

    public void addFirst(T item){
        if (this.size == this.items.length)resize_inc(this.size * 2);
        this.items[front] = item;
        this.size += 1;
        move_front();
    }

    public void addLast(T item){
        if (this.size == this.items.length)resize_inc(this.size * 2);
        this.items[rear] = item;
        this.size += 1;
        move_rear();
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public boolean wasting_memory(){
        return this.size < this.items.length / 4 && this.items.length >= 16;
    }

    public T removeFirst(){
        if (wasting_memory()) resize_dec(this.items.length / 2);
        this.front = (this.front == this.items.length - 1)? 0 : front + 1;
        T first = this.items[front];
        this.items[front] = null;
        if (! this.isEmpty())this.size -= 1;
        return first;
    }

    public T removeLast(){
        if (wasting_memory())resize_dec(this.items.length / 2);
        this.rear = (this.rear == 0)? this.items.length - 1 : rear - 1;
        T last = this.items[rear];
        this.items[rear] = null;
        if (! this.isEmpty())this.size -= 1;
        return last;
    }

    public T get(int index){
        return this.items[(this.front + index + 1) % this.items.length];
    }

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
}
