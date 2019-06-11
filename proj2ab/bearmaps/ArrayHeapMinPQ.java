package bearmaps;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private PriorityNode[] items;
    public int size;
    private Map<T, Integer> keys;


    public ArrayHeapMinPQ(int initCapacity) {
        items = new ArrayHeapMinPQ.PriorityNode[initCapacity + 1];
        size = 0;
        keys = new HashMap<>();
    }

    public ArrayHeapMinPQ() {
        this(5);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(T item, double priority) {

        if (keys.containsKey(item)) throw new IllegalArgumentException();

        PriorityNode newItem = new PriorityNode(item, priority);

        items[++size] = newItem;
        keys.put(item, size);
        swim(size);
        resize();
    }

    private void resize() {
        double division = (double) size / items.length;

        if (division > 0.75) {
            PriorityNode[] temp = new ArrayHeapMinPQ.PriorityNode[items.length * 2];
            for (int i = 1; i <= size; i++) {
                temp[i] = items[i];
            }

            items = temp;

        } else if (division < 0.3) {
            if (items.length < 8) return;

            PriorityNode[] temp = new ArrayHeapMinPQ.PriorityNode[(int) (items.length / 1.5)];
            for (int i = 1; i <= size; i++) {
                temp[i] = items[i];
            }

            items = temp;
        }
    }

    // Helpers to restore heap by priority
    private void swim(int k) {
        int parentNodeIndex = k/2;
        while (k > 1 && items[k].compareTo(items[parentNodeIndex]) < 0) {
            swap(k, parentNodeIndex);
            swim(parentNodeIndex);
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && items[j].compareTo(items[j+1]) > 0) j++;
            if (items[k].compareTo(items[j]) < 0) break;
            swap(k, j);
            k = j;
        }
    }

    //

    private void swap(int i, int j) {
        PriorityNode temp = items[i];
        keys.put(items[i].getItem(), j);
        keys.put(items[j].getItem(), i);
        items[i] = items[j];
        items[j] = temp;
    }


    @Override
    public boolean contains(T item) {
        return keys.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[1].getItem();
    }

    @Override
    public T removeSmallest() {
        if (isEmpty()) throw new NoSuchElementException();
        PriorityNode itemToReturn = items[1];
        keys.remove(itemToReturn.getItem());
        swap(1, size--);
        sink(1);
        items[size + 1] = null;
        return itemToReturn.getItem();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!keys.containsKey(item)) throw new NoSuchElementException();

        int itemIndex = keys.get(item);
        items[itemIndex].setPriority(priority);
        swim(itemIndex);
        sink(itemIndex);
    }


    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
