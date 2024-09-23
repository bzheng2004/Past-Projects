import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> maxComparator;

    public MaxArrayDeque61B(Comparator<T> comp) {
        maxComparator = comp;
    }

    public T max(Comparator<T> comp) {
        if (isEmpty()) {
            return null;
        }
        T largest = (T) get(0);
        for (int i = 0; i < size(); i++) {
            T current = (T) get(i);
            if (comp.compare(current, largest) > 0) {
                largest = current;
            }
        }
        return largest;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T largest = (T) get(0);
        for (int i = 0; i < size(); i++) {
            T current = (T) get(i);
            if (maxComparator.compare(current, largest) > 0) {
                largest = current;
            }
        }
        return largest;
    }
}
