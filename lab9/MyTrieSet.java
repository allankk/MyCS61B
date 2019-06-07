import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {

    private static final int R = 256;

    private Node root;
    private int n;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
        private boolean isKey;
        private Map<Character, Node> map = new HashMap<>();

        public Node(char key, boolean isKey) {
            val = key;
            this.isKey = isKey;
        }

        public Node(){
            isKey = false;
        }
    }

    public MyTrieSet() {
        root = new Node();
    }


    @Override
    public void clear() {
        root = new Node();
    }

    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            } else {
                curr = curr.map.get(c);
            }
        }
        return true;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> keysFound = new ArrayList<>();

        Node x = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!x.map.containsKey(c)) {
                return keysFound;
            } else {
                x = x.map.get(c);
            }
        }

        colHelp(prefix, keysFound, x);

        return keysFound;
    }

    private void colHelp (String s, List<String> keysFound, Node x) {
        if (x.isKey) keysFound.add(s);

        for (Character each : x.map.keySet()) {
            colHelp(s + each, keysFound, x.map.get(each));
        }
    }


    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
