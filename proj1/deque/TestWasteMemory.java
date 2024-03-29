package deque;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestWasteMemory {

    @Test
    public void testMemoryProportionality() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        int initialCapacity = 8;
        int numItemsToAdd = 100;
        int numItemsToRemove = 99;

        // Add items to the deque
        for (int i = 0; i < numItemsToAdd; i++) {
            deque.addLast(i);
        }

        // Remove items from the deque
        for (int i = 0; i < numItemsToRemove; i++) {
            deque.removeFirst();
        }
    }
}
