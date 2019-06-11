package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> testPQ = new ArrayHeapMinPQ<>();

        for (int i = 0; i < 300; i++) {
            testPQ.add(i, i);
        }

        assertTrue(testPQ.contains(44));
        assertTrue(testPQ.contains(122));
        assertFalse(testPQ.contains(333));
        assertFalse(testPQ.contains(555));

    }


    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> testPQ = new ArrayHeapMinPQ<>();

        for (int i = 50; i < 300; i++) {
            testPQ.add(i, i);
        }


        assertEquals(50, (int) testPQ.getSmallest());
        }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> testPQ = new ArrayHeapMinPQ<>();

        for (int i = 50; i < 300; i++) {
            testPQ.add(i, i);
        }

        assertEquals(50, (int) testPQ.removeSmallest());
        assertEquals(51, (int) testPQ.removeSmallest());
    }

    @Test
    public void testPriority() {
        ArrayHeapMinPQ<Integer> testPQ = new ArrayHeapMinPQ<>();

        for (int i = 50; i < 300; i++) {
            testPQ.add(i, i);
        }

        testPQ.changePriority(60, 1);
        testPQ.changePriority(61, 2);

        assertEquals(60, (int) testPQ.removeSmallest());
        assertEquals(61, (int) testPQ.removeSmallest());
    }

    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> testArrayPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> testMinPQ = new NaiveMinPQ<>();

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            testArrayPQ.add(i, i);
        }
        System.out.println("ARRAYHEAP Total time add(): " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            testMinPQ.add(i, i);
        }
        System.out.println("MINHEAP Total time add(): " + sw.elapsedTime() +  " seconds.");

        System.out.println();

        sw = new Stopwatch();
        for (int i = 7500; i > 0; i--) {
            testArrayPQ.contains(i);
        }
        System.out.println("ARRAYHEAP Total time contains(): " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 7500; i > 0; i--) {
            testMinPQ.contains(i);
        }
        System.out.println("MINHEAP Total time contains(): " + sw.elapsedTime() +  " seconds.");

        System.out.println();

        // GETSMALLEST

        sw = new Stopwatch();
        for (int i = 0; i < 8000; i++) {
            testArrayPQ.getSmallest();
        }
        System.out.println("ARRAYHEAP Total time getSmallest(): " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 8000; i++) {
            testMinPQ.getSmallest();
        }
        System.out.println("MINHEAP Total time getSmallest(): " + sw.elapsedTime() +  " seconds.");

        System.out.println();


        // CHANGEPRIORITY

        sw = new Stopwatch();
        for (int i = 1000; i < 4000; i++) {
            testArrayPQ.changePriority(i, i - 100);
        }
        System.out.println("ARRAYHEAP Total time changePriority(): " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 1000; i < 4000; i++) {
            testMinPQ.changePriority(i, i-100);
        }
        System.out.println("MINHEAP Total time changePriority(): " + sw.elapsedTime() +  " seconds.");

        System.out.println();

        // REMOVESMALLEST

        sw = new Stopwatch();
        for (int i = 0; i < 8000; i++) {
            testArrayPQ.removeSmallest();
        }
        System.out.println("ARRAYHEAP Total time removeSmallest(): " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 8000; i++) {
            testMinPQ.removeSmallest();
        }
        System.out.println("MINHEAP Total time removeSmallest(): " + sw.elapsedTime() +  " seconds.");

        System.out.println();


        }
    }

