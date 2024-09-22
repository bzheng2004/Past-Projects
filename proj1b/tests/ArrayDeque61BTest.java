import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(0);   // [0]
        aList1.addLast(1);   // [0, 1]
        aList1.addFirst(-1); // [-1, 0, 1]
        aList1.addLast(2);   // [-1, 0, 1, 2]
        aList1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(aList1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    @Test
    public void addLastTestBasic() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();

        aList1.addLast(0);
        aList1.addLast(1);
        aList1.addLast(2);
        assertThat(aList1.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    public void addFirstTestBasic() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();

        aList1.addFirst(0);
        aList1.addFirst(1);
        aList1.addFirst(2);
        assertThat(aList1.toList()).containsExactly(2, 1, 0).inOrder();
    }

    @Test
    public void addFirstResize() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();

        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        aList1.addFirst(0);
        assertThat(aList1.toList()).containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0).inOrder();
    }

    @Test
    public void toListEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        assertThat(aList1.toList()).isEmpty();
    }

    @Test
    public void sizeZero() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        assertThat(aList1.size()).isEqualTo(0);
    }

    @Test
    public void sizeAdded() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        assertThat(aList1.size()).isEqualTo(3);
    }

    @Test
    public void getValid() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        assertThat(aList1.get(0)).isEqualTo(1);
        assertThat(aList1.get(1)).isEqualTo(2);
        assertThat(aList1.get(2)).isEqualTo(3);
    }

}
