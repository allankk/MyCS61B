public class LinkedListDeque<T> implements Deque<T>{

    private TNode sentinel;
    private int size;
    private TNode p;
    private boolean recursive;
    private T recurx;

    public class TNode {
        public T item;
        public TNode next;
        public TNode prev;
        public TNode(T i, TNode n, TNode p){
            item = i;
            next = n;
            prev = p;
        }
    }

    public LinkedListDeque(){
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
        recursive = false;
        recurx = null;
    }

    public LinkedListDeque(LinkedListDeque other){
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        // @source https://www.youtube.com/watch?v=JNroRiEG7U4

        for (int i = 0; i < other.size(); i += 1){
            addLast((T)other.get(i));
        }
    }

    public T getRecursive(int index){
        if (!recursive()) {
            p = sentinel.next;
        }
            if (index == 0) {
                recurx = p.item;
                p = sentinel.next;
                recursive = false;
            } else {
                p = p.next;
                index -= 1;
                getRecursive(index);
            }
            return recurx;
    }


    // Adds an item of type T to the front of the deque
    @Override
    public void addFirst(T item){
        sentinel.next = new TNode(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    // Adds an item to the back of the deque
    @Override
    public void addLast(T item){
        sentinel.prev.next = new TNode(item, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
        size += 1;

    }

    // Returns true if deque is empty, false otherwise
    @Override
    public boolean isEmpty(){
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Returns the number of items in the deque
    @Override
    public int size() {
        return size;
    }

    // Prints the items in the deque from first to last, separated by a space. Once all
    // the items have been printed, print out a new line.
    @Override
    public void printDeque(){
        TNode x = sentinel.next;
        String s = "";

        for (int i = 0; i < size; i++) {
            s += x.item + " ";
            x = x.next;
        }

        System.out.println(s);
    }

    // Removes and returns the item at the front of the deque. If no such item exists, return null
    @Override
    public T removeFirst() {
        if (sentinel.next == null){
            return null;
        }

        T y = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return y;
    }

    // Removes and returns the item at the back of the deque. If no such item exists, return null
    @Override
    public T removeLast() {
        if (sentinel.prev == null) {
            return null;
        }

        T y = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return y;
    }

    // Gets the item at the given index, where 0 is the front, 1 is the next item and so forth
    // If no such item exists, returns null. Must not alter the deque.
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        TNode x = sentinel.next;

        try {
            int i = 0;
            while (i != index) {
                x = x.next;
                i++;
            }
            return x.item;

        } catch (Exception e){
            return null;
        }
    }

    public boolean recursive() {
        if (recursive == false){
            recursive = true;
            return false;
        } else {
            return true;
        }
    }

}
