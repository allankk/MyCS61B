public class LinkedListtest {

    public static void main(String[] args){
        LinkedListDeque<Integer> llist = new LinkedListDeque();

        llist.addFirst(22);
        llist.addFirst(11);
        llist.addLast(33);

        int i = llist.get(2);
        System.out.println(i);

        System.out.println("get first" + llist.get(1));
        System.out.println("get first " + llist.getRecursive(1));

        llist.printDeque();

        llist.removeLast();

    }
 }
