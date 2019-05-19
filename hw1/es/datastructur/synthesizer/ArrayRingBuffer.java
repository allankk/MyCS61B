package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* Current index point */
    private int current;
    /* capacity of the Array */
    private int capacity;


    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = capacity/2;
        last = capacity/2;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];

    }

    /* Checks if the current index is out of bounds. If so, points back to first index */
    public void checkCurrent() {
        if (last > capacity - 1 && !isFull()) {
            last = 0;
        } else if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
    }

    public void increaseFirst() {
        rb[first] = null;
        first++;
        if (first > capacity - 1) {
            first = 0;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            return;
        }
        checkCurrent();
        if (fillCount == 0) {
            first = last;
            rb[last] = x;
            last += 1;
        } else {
            rb[last] = x;
            last += 1;
        }

        fillCount += 1;
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        T firstitem = rb[first];
        increaseFirst();
        fillCount--;
        return firstitem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }

        return rb[first];
    }


    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterator<T> {
        int currentIndex = first;

        @Override
        public boolean hasNext() {
            if (currentIndex + 1 >= capacity) {
                currentIndex = 0;
            } else if (rb[currentIndex + 1] != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if (currentIndex + 1 >= capacity) {
                currentIndex = 0;
            } else {
                currentIndex++;
            }
            return rb[currentIndex];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayRingBuffer)) {
            return false;
        }

        if (this.fillCount() != ((ArrayRingBuffer) o).fillCount()
            || this.capacity() != ((ArrayRingBuffer) o).capacity()) {
            return false;
        }

        Iterator<T> firstIterator = iterator();
        Iterator<T> secondIterator = ((ArrayRingBuffer) o).iterator();

        while (firstIterator.hasNext()) {
            T first = firstIterator.next();
            T second = secondIterator.next();

            if (first != second) {
                return false;
            }
        }
        return true;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
