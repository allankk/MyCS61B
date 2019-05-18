public class ArrayDeque<T> {
    T[] items;
    int size;
    int startpos;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        startpos = 4;
    }

    // creates a deep copy of other
    public ArrayDeque(ArrayDeque other){
        items = (T[]) new Object[other.size()];
        startpos = other.startpos;

        for (int i = 0; i < other.size; i++){
            addLast((T)other.get(i));
        }
    }

    public void resize(){
        if (size + 1 == items.length){
            T[] tempitems = (T[]) new Object[items.length * 2];
            for (int i = 0; i < this.size; i++){
                tempitems[i] = this.get(i);
            }
            items = tempitems;
            startpos = 0;
        } else if (size < 0.3 * items.length && items.length > 8){
            T[] tempitems = (T[]) new Object[items.length / 2];
            for (int i = 0; i < this.size; i++) {
                tempitems[i] = this.get(i);
            }
            items = tempitems;
            startpos = 0;
        }
    }

    // adds element to beginning
    public void addFirst(T item){
        startpos -= 1;
        if (startpos < 0) {
            startpos = items.length - 1;
        }
        items[startpos] = item;
        size += 1;
        resize();
    }

    // adds element to end
    public void addLast(T item){
        int calcindex = startpos+size();
        if (calcindex > items.length - 1) {
            calcindex -= items.length;
        }
        items[calcindex] = item;
        size += 1;
        resize();
    }

    // sees if deque is empty
    public boolean isEmpty() {
        if (size == 0){
            return true;
        } else {
            return false;
        }
    }

    // returns number of items in the deque
    public int size() {
        return size;
    }

    // prints the items from first to last, separated by a space
    public void printDeque() {
        String s = "";
        int temppos = startpos;
        for (int i = 0; i < size; i++){
            s += " " + items[temppos];
            temppos += 1;
            if (temppos > items.length - 1) {
                temppos -= items.length;
            }
        }
    }

    // returns and removes the first item in the deque
    public T removeFirst() {
        T x = items[startpos];
        items[startpos] = null;
        startpos += 1;
        if (startpos >= items.length) {
            startpos -= items.length;
        }
        resize();
        size -= 1;
        return x;
    }

    // removes and returns the item at the back of the deque
    public T removeLast(){
        int calcindex = startpos+size()-1;
        if (calcindex > items.length - 1) {
            calcindex -= items.length;
        }

        T x = items[calcindex];
        items[calcindex] = null;
        size -= 1;
        resize();
        return x;
    }

    // gets the item at the given index. if doesn't exist, returns null
    public T get(int index) {

        int calcindex = startpos + index;
        if (calcindex > items.length - 1) {
            calcindex -= items.length;
        }
        return items[calcindex];
    }

}
