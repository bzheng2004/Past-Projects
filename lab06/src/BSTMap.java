import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size;
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value, Node left, Node right){
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
    private Node root;

    public BSTMap() {
        root = null;
        size = 0;
    }


    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V value) {
        if (n == null) {
            size++;
            return new Node(key, value, null, null);
        }
        int comp = key.compareTo(n.key);
        if (comp < 0) {
            n.left = put(n.left, key, value);
        } else if (comp > 0) {
            n.right = put(n.right, key, value);
        } else {
            n.value = value;
        }
        return n;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (n == null) {
            return null;
        }
        int comp = key.compareTo(n.key);
        if (comp < 0) {
            return get(n.left, key);
        } else if (comp > 0) {
            return get(n.right, key);
        } else {
            return n.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(Node n, K key) {
        if (n == null) {
            return false;
        }
        int comp = key.compareTo(n.key);
        if (comp < 0) {
            return containsKey(n.left, key);
        }
        if (comp > 0) {
            return containsKey(n.right, key);
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
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
