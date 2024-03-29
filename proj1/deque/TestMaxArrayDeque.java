package deque;
import org.junit.Test;
import org.junit.Assert;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class TestMaxArrayDeque {

    public class int_comparator implements Comparator<Integer> {
        @Override
        public int compare(Integer val1 , Integer val2){
            return val1 - val2;
        }
    }
    private Comparator<Integer> ascendingComparator = Comparator.naturalOrder();
    private Comparator<Integer> descendingComparator = Comparator.reverseOrder();
    @Test
    public void test_int_max_array(){
        Comparator<Integer> c = new int_comparator();
        MaxArrayDeque<Integer> max_deque = new MaxArrayDeque<>(c);
        max_deque.addFirst(2);
        max_deque.addFirst(1);
        max_deque.addFirst(0);
        int actual1 = max_deque.max();
        int actual2 = max_deque.max(c);
        assertEquals(2 , actual1);
        assertEquals(2 , actual2);
    }



    @Test
    public void testMaxWithAscendingComparator() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(ascendingComparator);
        deque.addLast(5);
        deque.addLast(10);
        deque.addLast(8);
        assertEquals(Integer.valueOf(10), deque.max());
    }

    @Test
    public void testMaxWithDescendingComparator() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(descendingComparator);
        deque.addLast(5);
        deque.addLast(10);
        deque.addLast(8);
        assertEquals(Integer.valueOf(5), deque.max());
    }

    @Test
    public void testMaxWithCustomComparator() {
        Comparator<Integer> customComparator = (a, b) -> Integer.compare(Math.abs(a), Math.abs(b));
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(customComparator);
        deque.addLast(-5);
        deque.addLast(-10);
        deque.addLast(8);
        assertEquals(Integer.valueOf(-10), deque.max());
    }

    @Test
    public void testMaxWithEmptyDeque() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(ascendingComparator);
        assertEquals(null,deque.max());
    }
}

