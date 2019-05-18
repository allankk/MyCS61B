public class ArrayDequeTest {

    public static void main(String[] args){
        ArrayDeque<Integer> testarray = new ArrayDeque<>();

        testarray.addFirst(10);
        testarray.addFirst(5);
        testarray.addFirst(2);
        testarray.addLast(15);
        testarray.addLast(20);
        testarray.addFirst(-3);
        testarray.addFirst(-5);
        testarray.addFirst(-8);
        testarray.addFirst(-10);
        testarray.addFirst(-20);
        testarray.addFirst(-35);
        testarray.addLast(30);
        testarray.addLast(50);
        testarray.addLast(60);
        testarray.addLast(90);
        testarray.addLast(120);
        testarray.addLast(130);
        testarray.addLast(180);
        testarray.addFirst(-45);

        ArrayDeque<Integer> copyarray = new ArrayDeque<>(testarray);
    }
}

