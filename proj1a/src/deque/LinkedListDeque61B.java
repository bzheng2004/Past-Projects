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

    public LinkedListDeque61B(T x) {
        sentinel = new Node(null, null, null);
        Node nextNode = new Node(x, sentinel, sentinel);
        sentinel.next = nextNode;
        sentinel.prev = nextNode;
        size = 1;
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
        while(now != sentinel) {
            outcome.add(now.item);
            now = now.next;
        }
        return outcome;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
