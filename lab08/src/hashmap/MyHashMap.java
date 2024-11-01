package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }


    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final double loadFactorMax;
    private int capacity;
    private int size;

    private static final int initialCapacity = 16;
    private static final double loadFactor = 0.75;
    // You should probably define some more!

    /**
     * Constructors
     */
    public MyHashMap() {
        this(initialCapacity, loadFactor);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, loadFactor);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor      maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactorMax = loadFactor;
        this.size = 0;
        this.buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }

    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * Note that that this is referring to the hash table bucket itself,
     * not the hash map itself.
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */

    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new HashSet<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!


    @Override
    public void put(K key, V value) {
        int x = Math.floorMod(key.hashCode(), capacity);
        Collection<Node> bucket = buckets[x];
        for (Node n : bucket) {
            if (n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        bucket.add(new Node(key, value));
        size++;

        if (size > capacity * loadFactorMax) {
            resize(2 *capacity);
        }
    }

    private void resize(int newCapacity) {
        Collection<Node>[] newB = new Collection[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newB[i] = createBucket();
        }
        for(Collection<Node> b : buckets) {
            for (Node n : b) {
                int bPlace = Math.floorMod(n.key.hashCode(), newCapacity);
                newB[bPlace].add(n);
            }
        }
        buckets = newB;
        capacity = newCapacity;
    }

    @Override
    public V get(K key) {
        int x = Math.floorMod(key.hashCode(), capacity);
        Collection<Node> bucket = buckets[x];
        for (Node n : bucket) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int x = Math.floorMod(key.hashCode(), capacity);
        Collection<Node> bucket = buckets[x];
        for (Node n : bucket) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
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
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}



