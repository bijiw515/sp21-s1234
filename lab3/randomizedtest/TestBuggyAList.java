package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing alist_no_resizing = new AListNoResizing<>();
        BuggyAList buggyAList = new BuggyAList();
        for (int i = 0 ; i < 3 ; i += 1){
            alist_no_resizing.addLast(i);
            buggyAList.addLast(i);
            assertEquals(alist_no_resizing.size() , buggyAList.size());
            assertEquals(alist_no_resizing , buggyAList);
        }
        for (int j = 0 ; j < 3 ; j += 1){
            alist_no_resizing.removeLast();
            buggyAList.removeLast();
            assertEquals(alist_no_resizing.size() , buggyAList.size());
            assertEquals(alist_no_resizing , buggyAList);
        }
    }
    @Test
    public void test_randomly(){
        AListNoResizing<Integer> L_noresize = new AListNoResizing<>();
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
                assertEquals(size_noresize , size_buggy);
                System.out.println("size_noresize: " + size_noresize);
                System.out.println("size_buggy: " + size_buggy);
            }else if (operationNumber == 2){
                if(L_noresize.size() != 0){
                    int last_noresize = L_noresize.getLast();
                    int last_buggy = L_buggy.getLast();
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
