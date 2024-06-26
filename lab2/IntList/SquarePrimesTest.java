package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void test_square_prime_a_bit_harder(){
        IntList lst = IntList.of(3, 5, 7, 17, 29);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("9 -> 25 -> 49 -> 289 -> 841" , lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testaddadjucent(){
        IntList L = IntList.of(1 , 1 , 2 , 3);
        L.addadjucent();
        assertEquals("4 -> 3" , L.toString());
    }
}

