import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node {
        T item;
        Node next;
        Node prev;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

    }

    private Node sentinel;
    private int size;

    @Override
    public Iterator<T> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<T> {
        private int pos;
        public LinkedIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            Node current = sentinel.next;
            for (int i = 0; i < pos; i++) {
                current = current.next;
            }

            T returnItem = current.item;
            pos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkedListDeque61B otherSet) {
            if (this.size != otherSet.size) {
                return false;
            }
            Node node1 = sentinel.next;
            Node node2 = otherSet.sentinel.next;
            for (int i = 0; i < size; i++) {
                if (!node1.item.equals(node2.item)) {
                    return false;
                }
                node1 = node1.next;
                node2 = node2.next;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("{");
        Node current = sentinel.next;
        for (int i = 0; i < size; i += 1) {
            returnSB.append(current.item.toString());
            if (i < size - 1) {
                returnSB.append(", ");
            }
            current = current.next;
        }
        returnSB.append("}");
        return returnSB.toString();
    }

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        Node nextNode = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = nextNode;
        sentinel.next = nextNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node nextNode = new Node(x, sentinel, sentinel.prev);
        sentinel.prev.next = nextNode;
        sentinel.prev = nextNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> outcome = new ArrayList<>();
        Node now = sentinel.next;
        while (now != sentinel) {
            outcome.add(now.item);
            now = now.next;
        }
        return outcome;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size--;
        return first.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        size--;
        return last.item;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        if (index > size) {
            return null;
        }
        int i = 0;
        Node now = sentinel.next;
        while (i < index) {
            now = now.next;
            i++;
        }
        return now.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        if (index > size) {
            return null;
        }
        if (index == 0) {
            return sentinel.next.item;
        }
        sentinel = sentinel.next;
        T result = getRecursive(index - 1);
        sentinel = sentinel.prev;
        return result;
    }
}
