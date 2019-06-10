package bearmaps;

import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private PriorityNode[] items;
    public int size;


    public ArrayHeapMinPQ(int initCapacity) {
        items = (PriorityNode[]) new Object[initCapacity + 1];
        size = 0;
    }

    public ArrayHeapMinPQ() {
        this(1);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(T item, double priority) {
        PriorityNode newItem = new PriorityNode(item, priority);

    }

    private void resize() {
        if (items.length / size > 0.75) {
            PriorityNode[] temp = (PriorityNode[]) new Object[items.length * 2];
            for (int i = 1; i <= size; i++) {
                temp[i] = items[i];
            }

            items = temp;

        } else if (items.length / size < 0.4) {
            PriorityNode[] temp = (PriorityNode[]) new Object[items.length / 2];
            for (int i = 1; i <= size; i++) {
                temp[i] = items[i];
            }

            items = temp;
        }
    }

    // Helpers to restore heap by priority
    private void swim(int k) {
        int parentNodeIndex = k/2;
        while (k > 1 && items[k].compareTo(items[parentNodeIndex]) > 0) {
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
        items[i] = items[j];
        items[j] = temp;
    }


    @Override
    public boolean contains(T item) {
        return false;
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[1].getItem();
    }

    @Override
    public T removeSmallest() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void changePriority(T item, double priority) {

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
