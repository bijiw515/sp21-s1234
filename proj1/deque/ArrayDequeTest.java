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
    public void test_randomly(){
        AListNoResizing<Integer> L_noresize = new AListNoResizing<>();
        ArrayDeque<Integer> L_buggy = new ArrayDeque<>();

        int N = 100000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L_noresize.addLast(randVal);
                L_buggy.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size_noresize = L_noresize.size();
                int size_buggy = L_buggy.size();
                assertEquals(size_noresize , size_buggy);
                System.out.println("size_noresize: " + size_noresize);
                System.out.println("size_buggy: " + size_buggy);
            }else if (operationNumber == 2){
                if(L_noresize.size() != 0){
                    int last_noresize = L_noresize.get(0);
                    int last_buggy = L_buggy.get(0);
                    assertEquals(last_noresize , last_buggy);
                    System.out.println("getLast_noresize(" + last_noresize + ")");
                    System.out.println("getLast_buggy(" + last_buggy + ")");
                }
            } else if (operationNumber == 3) {
                if(L_noresize.size() != 0){
                    int last_noresize = L_noresize.removeLast();
                    int last_buggy = L_buggy.removeLast();
                    assertEquals(last_noresize , last_buggy);
                    System.out.println("removeLast_noresize(" + last_noresize + ")");
                    System.out.println("removeLast_buggy(" + last_buggy + ")");
                }
            }
        }
    }
}