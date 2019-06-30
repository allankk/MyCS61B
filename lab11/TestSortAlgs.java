import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> testQ = new Queue<Integer>();
        testQ.enqueue(9);
        testQ.enqueue(29);
        testQ.enqueue(-2);
        testQ.enqueue(3553);
        testQ.enqueue(22);
        testQ.enqueue(0);
        testQ.enqueue(-22);
        testQ.enqueue(213);

        testQ = QuickSort.quickSort(testQ);

        Iterator<Integer> iter = testQ.iterator();

        int previous = Integer.MIN_VALUE;
        for (Integer each : testQ) {
            assertTrue(previous < each);
            previous = each;
        }

    }

    @Test
    public void testMergeSort() {
        Queue<Integer> testQ = new Queue<Integer>();
        testQ.enqueue(9);
        testQ.enqueue(29);
        testQ.enqueue(-2);
        testQ.enqueue(3553);
        testQ.enqueue(22);
        testQ.enqueue(0);
        testQ.enqueue(-22);
        testQ.enqueue(213);

        testQ = MergeSort.mergeSort(testQ);

        Iterator<Integer> iter = testQ.iterator();

        int previous = Integer.MIN_VALUE;
        for (Integer each : testQ) {
            assertTrue(previous < each);
            previous = each;
        }

    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
