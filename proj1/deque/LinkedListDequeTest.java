package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void testIsEmpty() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        assertTrue("Deque should be empty", deque.isEmpty());
    }

    @Test
    public void testAddFirst() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        assertFalse("Deque should not be empty after addFirst", deque.isEmpty());
    }

    @Test
    public void testAddLast() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addLast(1);
        assertFalse("Deque should not be empty after addLast", deque.isEmpty());
    }

    @Test
    public void testSize() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals("Deque size should be 2", 2, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        int removedItem = deque.removeFirst();
        assertEquals("Removed item should be 1", 1, removedItem);
        assertEquals("Deque size should be 1 after removeFirst", 1, deque.size());
    }

    @Test
    public void testRemoveLast() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        int removedItem = deque.removeLast();
        assertEquals("Removed item should be 2", 2, removedItem);
        assertEquals("Deque size should be 1 after removeLast", 1, deque.size());
    }

    @Test
    public void testGet() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals("Get(0) should return first item", Integer.valueOf(1), deque.get(0));
        assertEquals("Get(1) should return last item", Integer.valueOf(2), deque.get(1));
    }


    /*@Test
    public void test_randomly() {
        LinkedListDeque<Integer> L_noresize = new LinkedListDeque<>();
        BuggyAList<Integer> L_buggy = new BuggyAList<>();

        int N = 5000;
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
                assertEquals(size_noresize, size_buggy);
                System.out.println("size_noresize: " + size_noresize);
                System.out.println("size_buggy: " + size_buggy);
            } else if (operationNumber == 2) {
                if (L_noresize.size() != 0) {
                    int last_noresize = L_noresize.getLast();
                    int last_buggy = L_buggy.getLast();
                    assertEquals(last_noresize, last_buggy);
                    System.out.println("getLast_noresize(" + last_noresize + ")");
                    System.out.println("getLast_buggy(" + last_buggy + ")");
                }
            } else if (operationNumber == 3) {
                if (L_noresize.size() != 0) {
                    int last_noresize = L_noresize.removeLast();
                    int last_buggy = L_buggy.removeLast();
                    assertEquals(last_noresize, last_buggy);
                    System.out.println("removeLast_noresize(" + last_noresize + ")");
                    System.out.println("removeLast_buggy(" + last_buggy + ")");
                }
            }
        }
    }*/
}