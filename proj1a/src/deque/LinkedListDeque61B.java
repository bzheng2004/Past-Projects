package deque;

import java.util.ArrayList;
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
