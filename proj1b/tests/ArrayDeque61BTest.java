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

        aList1.addFirst(9);
        aList1.addFirst(8);
        aList1.addFirst(7);
        aList1.addFirst(6);
        aList1.addFirst(5);
        aList1.addFirst(4);
        aList1.addFirst(3);
        aList1.addFirst(2);
        aList1.addFirst(1);
        aList1.addFirst(0);
        assertThat(aList1.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    public void addLastResize() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();

        aList1.addLast(9);
        aList1.addLast(8);
        aList1.addLast(7);
        aList1.addLast(6);
        aList1.addLast(5);
        aList1.addLast(4);
        aList1.addLast(3);
        aList1.addLast(2);
        aList1.addLast(1);
        aList1.addLast(0);
        assertThat(aList1.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).inOrder();
    }

    @Test
    public void removeLastResize() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        for (int i = 0; i < 24; i++) {
            aList1.addLast(i);
        }

        for (int i = 0; i < 20; i++) {
            aList1.removeLast();
        }
        assertThat(aList1.toList()).containsExactly(0, 1 ,2 ,3).inOrder();
        assertThat(aList1.size()).isEqualTo(4);
    }

    @Test
    public void removeFirstResize() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        for (int i = 0; i < 24; i++) {
            aList1.addLast(i);
        }

        for (int i = 0; i < 20; i++) {
            aList1.removeFirst();
        }
        assertThat(aList1.toList()).containsExactly(20, 21, 22, 23).inOrder();
        assertThat(aList1.size()).isEqualTo(4);
    }

    @Test
    public void isEmptyTrue() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        assertThat(aList1.isEmpty()).isTrue();
    }

    @Test
    public void isEmptyFalse() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addFirst(1);
        assertThat(aList1.isEmpty()).isFalse();
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

    @Test
    public void getOobLarge() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        assertThat(aList1.get(28723)).isNull();
    }

    @Test
    public void getOobNeg() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        assertThat(aList1.get(-28723)).isNull();
    }

    @Test
    public void sizeAfterRemoveToEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeFirst();
        aList1.removeFirst();
        aList1.removeFirst();
        assertThat(aList1.size()).isEqualTo(0);
    }

    @Test
    public void sizeAfterRemoveFromEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.removeFirst();
        aList1.removeLast();
        aList1.removeFirst();
        assertThat(aList1.isEmpty()).isTrue();
    }

    @Test
    public void toListEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        assertThat(aList1.toList()).isEmpty();
    }

    @Test
    public void addFirstAfterRemove() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.removeFirst();
        aList1.removeLast();
        aList1.addFirst(1);
        aList1.addFirst(2);
        aList1.addFirst(3);
        assertThat(aList1.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    public void addLastAfterRemove() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.removeFirst();
        aList1.removeLast();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        assertThat(aList1.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeFirst();
        assertThat(aList1.toList()).containsExactly(2, 3).inOrder();
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeLast();
        assertThat(aList1.toList()).containsExactly(1, 2).inOrder();
    }

    @Test
    public void firstToEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeFirst();
        aList1.removeFirst();
        aList1.removeFirst();
        assertThat(aList1.isEmpty()).isTrue();
    }

    @Test
    public void lastToEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeLast();
        aList1.removeLast();
        aList1.removeLast();
        assertThat(aList1.isEmpty()).isTrue();
    }

    @Test
    public void removeFirstToOneTest() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeFirst();
        aList1.removeFirst();
        assertThat(aList1.toList()).containsExactly(3).inOrder();
    }

    @Test
    public void removeLastToOneTest() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeLast();
        aList1.removeLast();
        assertThat(aList1.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void addFirstRemoveToEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addFirst(1);
        aList1.addFirst(2);
        aList1.addFirst(3);
        aList1.removeFirst();
        aList1.removeFirst();
        aList1.removeFirst();
        aList1.addFirst(1);
        aList1.addFirst(2);
        aList1.addFirst(3);
        assertThat(aList1.toList()).containsExactly(3, 2, 1);
    }

    @Test
    public void addLastRemoveToEmpty() {
        Deque61B<Integer> aList1 = new ArrayDeque61B<>();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        aList1.removeLast();
        aList1.removeLast();
        aList1.removeLast();
        aList1.addLast(1);
        aList1.addLast(2);
        aList1.addLast(3);
        assertThat(aList1.toList()).containsExactly(1, 2, 3);
    }

    @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void arrayAddLastTestBasicWithoutToList() {
        Deque61B<String> aList1 = new ArrayDeque61B<>();

        aList1.addLast("front");
        aList1.addLast("middle");
        aList1.addLast("back");
        assertThat(aList1).containsExactly("front", "middle", "back");
    }

    @Test
    public void arrayTestEqualDeques61B() {
        Deque61B<String> aList1 = new ArrayDeque61B<>();
        Deque61B<String> aList2 = new ArrayDeque61B<>();

        aList1.addLast("front");
        aList1.addLast("middle");
        aList1.addLast("back");

        aList2.addLast("front");
        aList2.addLast("middle");
        aList2.addLast("back");

        assertThat(aList1).isEqualTo(aList2);
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }


}

