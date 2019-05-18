public class Sort {

    public static void sort(String[] x){

        //Find the smallest item
        // move it to the front
        // selection sort the rest
        sort(x, 0);
    }

    /* sorts x starting at position start/     */

    private static void sort(String[] x, int start){
        if (start == x.length){
            return;
        }
        int smallestIndex = findSmallest(x, start);
        swap(x, start, smallestIndex);
        sort(x, start + 1);
    }

    public static void swap(String[] x, int a, int b){
        String swap = x[a];
        x[a] = x[b];
        x[b] = swap;
    }

    // return the index of the smallest string
    public static int findSmallest(String[] x, int start) {
        int smallestIndex = start;

        for (int i = start; i < x.length; i++){
            int cmp = x[i].compareTo(x[smallestIndex]);
            // if x[i] < x[smallestIndex], cmp will be -1

            if (cmp < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

}
