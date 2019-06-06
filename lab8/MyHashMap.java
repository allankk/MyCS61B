import java.util.*;

public class MyHashMap<K, V> implements Map61B{

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry (K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() { return this.key; }
        public V getValue() { return this.value; }
        public V setValue(java.lang.Object x) {
            V old = this.value;
            this.value = (V) x;
            return old;
            }
    }

    private Set<K> keySet;
    private int initialSize;
    private int size;
    private double loadFactor;
    private ArrayList<Entry<K, V>> bins;

    public MyHashMap() {
        this.initialSize = 16;
        this.loadFactor = 0.75;
        bins = new ArrayList<>();
        bins.addAll(Collections.nCopies(initialSize, null));
        this.size = 0;
        keySet = new HashSet<>();
    }

    public MyHashMap(int initialSize){
        if (initialSize < 1) {
            throw new IllegalArgumentException();
        }
        this.initialSize = initialSize;
        this.loadFactor = 0.75;
        bins = new ArrayList<>();
        bins.addAll(Collections.nCopies(initialSize, null));
        this.size = 0;
        keySet = new HashSet<>();
    }

    public MyHashMap(int initialSize, double loadFactor){
        if (initialSize < 1 || loadFactor <= 0.0) {
            throw new IllegalArgumentException();
        }
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        bins = new ArrayList<>();
        bins.addAll(Collections.nCopies(initialSize, null));
        this.size = 0;
        keySet = new HashSet<>();
    }

    private int hash(Object key) {
        return (key == null) ? 0 : (0x7fffffff & key.hashCode()) % this.initialSize;
    }


    @java.lang.Override
    public void clear() {
        bins = new ArrayList<>();
        bins.addAll(Collections.nCopies(initialSize, null));
        keySet = new HashSet<>();
        this.size = 0;
    }

    @java.lang.Override
    public boolean containsKey(java.lang.Object key) {
        return keySet.contains(key);
    }

    @java.lang.Override
    public java.lang.Object get(java.lang.Object key) {
        Entry e = find(key, bins.get(hash(key)));
        return (e == null) ? null : e.value;
    }

    @java.lang.Override
    public int size() {
        return this.size;
    }

    @java.lang.Override
    public void put(java.lang.Object key, java.lang.Object value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int i = hash(key);
        Entry<K, V> e = find(key, (Entry<K, V>)this.get(i));
        if (e == null) {
            bins.set(i, new Entry(key, value, bins.get(i)));
            keySet.add((K) key);
            size++;
            if (this.size > initialSize * loadFactor) grow();
        } else {
            e.setValue(value);
        }
    }

    private Entry<K, V> find(Object key, Entry<K, V> bin) {
        for (Entry<K, V> e = bin; e != null; e = e.next) {
            if ( key == null && e.key == null || key.equals(e.key)) {
                return e;
            }
            return null;
        }
        return null;
    }

    private void grow() {
        MyHashMap<K, V> newMap = new MyHashMap<>(bins.size() * 2, loadFactor);
        for (Entry<K, V> each : bins) {
            if (each != null) {
                newMap.put(each.getKey(), each.getValue());
            }
        }
    }


    @java.lang.Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Iterator iterator() {
        Iterator<K> iterator = this.keySet().iterator();
        while (iterator.hasNext()) {
            K key = iterator.next();
        }

        return null;
    }

    @java.lang.Override
    public java.lang.Object remove(java.lang.Object key) {
        return new UnsupportedOperationException();
    }

    @java.lang.Override
    public java.lang.Object remove(java.lang.Object key, java.lang.Object value) {
        return new UnsupportedOperationException();
    }
}
