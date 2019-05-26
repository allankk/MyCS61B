import org.w3c.dom.Node;

import java.security.Key;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private int size;
    private Node root;
    private String printOut;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }


    public BSTMap() {
        size = 0;
        printOut = "";

    }

    @Override
    public void clear() {
        this.size = 0;
        root = null;
    }


    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("key is not a valid argument");
        if (root == null) return null;
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (key == null) throw new IllegalArgumentException("value is null");
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("key is null");

        root = put(root, key, value);
    }

    public Node put(Node x, K key, V value) {
        if (x == null) {
            this.size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.val = value;
        return x;
    }


    // Prints out BSTMap in order of increasing Key.
    public void printInOrder() {
        if (root == null) System.out.println("BSTMap empty");
        else printInOrder(root);

    }

    public void printInOrder(Node x) {
        if (x.left == null && x.right == null) {
            System.out.println(x.key.toString());
        } else if (x.right == null && x.left != null) {
            printInOrder (x.left);
            System.out.println(x.key.toString());
        } else if (x.left == null && x.right != null) {
            System.out.println(x.key.toString());
            printInOrder(x.right);
        } else {
            printInOrder(x.left);
            System.out.println(x.key.toString());
            printInOrder(x.right);
        }


    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
