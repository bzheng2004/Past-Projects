import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B {
    private T[] items;
    private int size;
    private int front;
    private int back;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 1;
    }

    public int floorModHelper(int x) {
        return Math.floorMod(x, items.length);
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }
    @Override
    public void addFirst(Object x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[front] = (T) x;
        front = floorModHelper(front - 1);
        size = size + 1;
    }

    @Override
    public void addLast(Object x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[back] = (T) x;
        back = floorModHelper(back + 1);
        size = size + 1;
    }

    @Override
    public List toList() {
        List<T> returnList = new ArrayList<>();
        int x = Math.floorMod(front + 1, items.length);
        for (int i = 0; i < size; i++) {
            returnList.add(items[x]);
            x = Math.floorMod(x + 1, items.length);
        }
        return returnList;
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
    public Object removeFirst() {
        return null;
    }

    @Override
    public Object removeLast() {
        return null;
    }

    @Override
    public Object get(int index) {
        if (index < 0) {
            return null;
        }
        if (index > size) {
            return null;
        }
        return items[floorModHelper(front + 1 + index)];
    }

    @Override
    public Object getRecursive(int index) {
        return null;
    }
}
