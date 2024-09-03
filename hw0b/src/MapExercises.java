import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        Map<Character, Integer> LtoN = new HashMap<>();
        int numC = 0;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < alphabet.length(); i++) {
            char currentLetter = alphabet.charAt(i);
            numC += 1;
            LtoN.put(currentLetter, numC);
        }
        return LtoN;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> squared = new HashMap<>();
        for (Integer i : nums) {
            int squaredNums = i * i;
            squared.put(i, squaredNums);
        }
        return squared;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Fill in this function.
        Map<String, Integer> wCount = new HashMap<>();
        for (String w : words) {
            if (wCount.containsKey(w)) {
                int currentnum = wCount.get(w);
                wCount.put(w, currentnum + 1);
            } else {
                wCount.put(w, 1);
            }
        }
        return wCount;
    }
}
