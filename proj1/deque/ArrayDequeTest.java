package deque;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;
public class ArrayDequeTest {
    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> deque =new  ArrayDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(2, (int)deque.get(0));
        assertEquals(1, (int)deque.get(1));
    }

    @Test
    public void testAddLast() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("first");
        deque.addLast("second");
        assertEquals("first", deque.get(0));
        assertEquals("second", deque.get(1));
    }

    @Test
    public void testIsEmpty() {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        deque.addFirst('a');
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testSize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertEquals(0, deque.size());
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(2, deque.size());
    }

    @Test
    public void testPrintDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("first");
        deque.addLast("second");
        deque.printDeque(); // Output: first second
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(1, (int)deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals(2, (int)deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("first");
        deque.addLast("second");
        assertEquals("second", deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals("first", deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testGet() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(1, (int)deque.get(0));
        assertEquals(2, (int)deque.get(1));
        assertNull(deque.get(2));
    }

    @Test
   public void test_specialcase(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(0);
        for (int i = 0 ; i < 9 ; i++){
            deque.addLast(i);
        }
    }
}