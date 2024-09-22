import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int pos;
        public ArrayIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < size;
        }
        public T next() {
            T returnItem = items[Math.floorMod(front + 1 + pos, items.length)];
            pos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ArrayDeque61B otherSet) {
            if (this.size != otherSet.size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                T item1 = items[Math.floorMod(front + 1 + i, items.length)];
                T item2 = (T) otherSet.items[Math.floorMod(otherSet.front + 1 + i, otherSet.items.length)];
                if (!item1.equals(item2)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size; i += 1) {
            int x = Math.floorMod(front + 1 + i, items.length);
            returnSB.append(items[x].toString());
            if (i < size - 1) {
                returnSB.append(", ");
            }
        }
        returnSB.append("}");
        return returnSB.toString();
    }

    public int floorModHelper(int x) {
        return Math.floorMod(x, items.length);
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = items[Math.floorMod(front + 1 + i, items.length)];
        }
        front = capacity - 1;
        back = size;
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
        if (size == 0) {
            return null;
        }
        int removedX = Math.floorMod(front + 1, items.length);
        T removed = items[removedX];
        front = removedX;
        size -= 1;

        if (items.length > 8 * 2) {
            if (size <= items.length / 4) {
                resize(items.length / 2);
            }
        }
        return removed;
    }

    @Override
    public Object removeLast() {
        if (size == 0) {
            return null;
        }
        int removedX = Math.floorMod(back - 1, items.length);
        T removed = items[removedX];
        back = removedX;
        size -= 1;

        if (items.length > 8 * 2) {
            if (size <= items.length / 4) {
                resize(items.length / 2);
            }
        }
        return removed;
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
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
