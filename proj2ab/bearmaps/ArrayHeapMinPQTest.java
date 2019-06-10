package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testContains() {
        NaiveMinPQ<Integer> testPQ = new NaiveMinPQ<>();

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
        NaiveMinPQ<Integer> testPQ = new NaiveMinPQ<>();

        for (int i = 50; i < 300; i++) {
            testPQ.add(i, i);
        }


        assertEquals(50, (int) testPQ.getSmallest());
        }

    @Test
    public void testRemoveSmallest() {
        NaiveMinPQ<Integer> testPQ = new NaiveMinPQ<>();

        for (int i = 50; i < 300; i++) {
            testPQ.add(i, i);
        }

        assertEquals(50, (int) testPQ.removeSmallest());
        assertEquals(51, (int) testPQ.removeSmallest());
    }

    @Test
    public void testPriority() {
        NaiveMinPQ<Integer> testPQ = new NaiveMinPQ<>();

        for (int i = 50; i < 300; i++) {
            testPQ.add(i, i);
        }

        testPQ.changePriority(60, 1);
        testPQ.changePriority(61, 2);

        assertEquals(60, (int) testPQ.removeSmallest());
        assertEquals(61, (int) testPQ.removeSmallest());
    }




}
