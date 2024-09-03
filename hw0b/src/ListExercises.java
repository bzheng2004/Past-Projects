import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int counter = 0;
        for (int i : L) {
            counter = counter + i;
        }
        return counter;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        int counter = 0;
        List<Integer> returnList = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                returnList.add(i);
            } else {
                continue;
            }
        }
        return returnList;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> commonItem = new ArrayList<>();
        int L1Len = L1.size();
        int L2Len = L2.size();
        List<Integer> biggerList = new ArrayList<>();
        List<Integer> smallerList = new ArrayList<>();
        if (L1Len >= L2Len) {
            biggerList = L1;
            smallerList = L2;
        } else {
            biggerList = L2;
            smallerList = L1;
        }
        for (int i = 0; i < biggerList.size(); i++) {
            int current = biggerList.get(i);
            for (int j = 0; j < smallerList.size(); j++) {
                int currentsmall = smallerList.get(j);
                if (current == currentsmall) {
                    commonItem.add(current);
                }
            }
        }
        return commonItem;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int counter = 0;
        for (int i = 0; i < words.size(); i++) {
            String currentWord = words.get(i);
            for (int j = 0; j < currentWord.length(); j++) {
                char currentLetter = currentWord.charAt(j);
                if (currentLetter == c) {
                    counter += 1;
                }
            }
        }
        return counter;
    }
}
