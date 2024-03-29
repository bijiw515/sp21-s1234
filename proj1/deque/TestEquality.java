package deque;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Comparator;

public class TestEquality {

    @Test
    public void testLinkedListDequeEquals() {
        // Create two LinkedListDeque instances with the same content
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        // Test whether they are equal
        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testArrayDequeEquals() {
        // Create two ArrayDeque instances with the same content
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        // Test whether they are equal
        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testLinkedListDequeNotEquals() {
        // Create two LinkedListDeque instances with different content
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();
        deque2.addLast(3);
        deque2.addLast(2);
        deque2.addLast(1);

        // Test whether they are not equal
        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testArrayDequeNotEquals() {
        // Create two ArrayDeque instances with different content
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addLast(3);
        deque2.addLast(2);
        deque2.addLast(1);

        // Test whether they are not equal
        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testLinkedListDequeEqualsWithDifferentOrder() {
        // Create two LinkedListDeque instances with the same content but in different orders
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();
        deque2.addLast(3);
        deque2.addLast(1);
        deque2.addLast(2);

        // Test whether they are not equal
        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testArrayDequeEqualsWithDifferentTypes() {
        // Create two ArrayDeque instances with the same content but different types of elements
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<String> deque2 = new ArrayDeque<>();
        deque2.addLast("1");
        deque2.addLast("2");
        deque2.addLast("3");

        // Test whether they are not equal
        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testLinkedListDequeEqualsWithEmptyDeque() {
        // Create two empty LinkedListDeque instances
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();

        // Test whether they are equal
        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testArrayDequeEqualsWithEmptyDeque() {
        // Create two empty ArrayDeque instances
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        ArrayDeque<Integer> deque2 = new ArrayDeque<>();

        // Test whether they are equal
        assertTrue(deque1.equals(deque2));
    }

    @Test
    public void testLinkedListDequeEqualsWithDifferentSizes() {
        // Create two LinkedListDeque instances with different sizes
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);

        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        // Test whether they are not equal
        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testArrayDequeEqualsWithDifferentSizes() {
        // Create two ArrayDeque instances with different sizes
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        // Test whether they are not equal
        assertFalse(deque1.equals(deque2));
    }
}
