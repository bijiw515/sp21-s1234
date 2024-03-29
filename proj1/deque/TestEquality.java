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

    @Test
    public void testDeepEquals() {
        // Create two MaxArrayDeque instances with the same content and comparator
        Comparator<Integer> comparator = Comparator.naturalOrder();
        MaxArrayDeque<Integer> deque1 = new MaxArrayDeque<>(comparator);
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        MaxArrayDeque<Integer> deque2 = new MaxArrayDeque<>(comparator);
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        // Test whether they are deeply equal
        assertTrue(deepEquals(deque1, deque2));
    }

    @Test
    public void testDeepEqualsWithDifferentComparator() {
        // Create two MaxArrayDeque instances with the same content but different comparators
        Comparator<Integer> comparator1 = Comparator.naturalOrder();
        MaxArrayDeque<Integer> deque1 = new MaxArrayDeque<>(comparator1);
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        Comparator<Integer> comparator2 = Comparator.reverseOrder();
        MaxArrayDeque<Integer> deque2 = new MaxArrayDeque<>(comparator2);
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        // Test whether they are not deeply equal
        assertFalse(deepEquals(deque1, deque2));
    }

    // Method to test deep equality
    private boolean deepEquals(MaxArrayDeque<?> deque1, MaxArrayDeque<?> deque2) {
        // Check if comparators are equal
        if (!deque1.comparator.equals(deque2.comparator)) {
            return false;
        }

        // Check if sizes are equal
        if (deque1.size() != deque2.size()) {
            return false;
        }

        // Check if elements are equal
        for (int i = 0; i < deque1.size(); i++) {
            if (!deque1.get(i).equals(deque2.get(i))) {
                return false;
            }
        }

        return true;
    }
}
