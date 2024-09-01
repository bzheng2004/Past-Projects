import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        return new int[]{1, 2, 3, 4, 5, 6};
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        String[] ergunOrder = new String[] {"beyti", "pizza", "hamburger", "tea"};
        String[] erikOrder = new String[] {"sushi", "pasta", "avocado", "coffee"};
        if (customer.equals("Ergun")) {
            return ergunOrder;
        }
        if (customer.equals("Erik")) {
            return erikOrder;
        }
        return new String[3];
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int mx = array[0];
        int nx = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < mx) {
                mx = array[i];
            }
        }
        for (int j = 0; j < array.length; j++) {
            if (array[j] > nx) {
                nx = array[j];
            }
        }
        return Math.abs(mx - nx);
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function
        int counter = 0;
        while (x != 1) {
            if (x % 2 == 0) {
                x = x/2;
                counter = x;
                list.add(counter);
            }
            if (x % 2 != 0) {
                x = x * 3 + 1;
                counter = x;
                list.add(counter);
            }
        if (x == 1) {
            list.add(1);
            return list;
        }
        }

        return null;
    }

}
