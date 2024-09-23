import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }
    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void maxNonEmptyTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("3");
        mad.addFirst("sad road");
        assertThat(mad.max()).isEqualTo("sad road");
    }

    @Test
    public void maxEmpty() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        assertThat(mad.max()).isNull();
    }

    @Test
    public void maxDefaultComparator() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        m.addFirst(1);
        m.addFirst(3);
        m.addFirst(5);
        assertThat(m.max()).isEqualTo(5);
    }

    @Test
    public void maxDifferentComparator() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        m.addFirst(1);
        m.addFirst(3);
        m.addFirst(5);
        assertThat(m.max(Comparator.naturalOrder())).isEqualTo(5);
    }

    @Test
    public void DifferentComp() {
        MaxArrayDeque61B<String> m = new MaxArrayDeque61B<>(new StringLengthComparator());
        m.addFirst("");
        m.addFirst("2");
        m.addFirst("fury road");
        assertThat(m.max(Comparator.naturalOrder())).isEqualTo("fury road");
    }

}
