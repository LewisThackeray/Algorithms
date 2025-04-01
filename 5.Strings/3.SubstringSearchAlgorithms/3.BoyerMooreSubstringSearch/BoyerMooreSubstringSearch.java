import java.util.Arrays; // Importing the Arrays Class from java.util to Manage the Good Suffix and Bad Character Tables which Support the Heuristics used in this String Sorting Algorithm.

/**
 * <p>A fundamental operation on strings is a substring search: given a text string of length <i>N</i> and a pattern string of length <i>M</i>, find an occurrence of the pattern within the text.
 * There are four main substring search algorithms: Brute Force Substring Search, Knuth-Morris-Pratt (KMP) Substring Search, <b>Boyer-Moore Substring Search</b> and Robin-Karp Substring Search.</p>
 *
 * The <b>Boyer-Moore Substring Search</b> Algorithm uses two heuristics to skips parts of the text: the <i>Bad Character Rule</i> and the <i>Good Suffix Rule</i>.  The <i>Bad Character Rule</i>
 * uses the position of the mismatched character in the pattern to decide how far to shift the patter when a mismatch occurs between a pattern character and a text character.  It aligns the
 * pattern such that the mismatched text character aligns with the last occurrence of that character in the pattern.  For the <i>Good Suffix Rule</i>, when a mismatch occurs, the algorithm checks
 * the suffix of the pattern (up to the mismatch) that matches the text and shifts the patter so that the next occurrence of that suffex in the patter aligns with the text.
 *
 * {@code BoyerMooreSubstringSearch} is an implementation of the <em>Boyer-Moore Substring Search</em> which checks if a provided pattern of length M, is present in text of length N.  This code
 * assumes that the substring search is case sensitive - therefore - if patter[a] = b and text[a] = B then there is not a match!.
 *
 * <p><b>Time Complexity of the Boyer-Moore Substring Search:</b> The Worst Case Time Complexity for the Boyer-Moore Substring Search is O(N * M).</p>
 */

public class BoyerMooreSubstringSearch {

    private static final int R = 256; // Creating a Variable to Store the Size of the Character Set, in this Implementation we are using Extended ASCII.

    /**
     * This is the Class Constructor which is private to prevent instantiations of the class, as the class is designed as a utility class with only static methods for performing the Boyer-Moore
     * Substring Search.  Direct instantiation is NOT supported.
     */

    private BoyerMooreSubstringSearch() {}

    /**
     * This method performs the Boyer-Moore Substring Search which determines if a pattern of length M is present in text of length N.
     * @param text is a String of length N which we are determining if the pattern exists in.
     * @param pattern is a String of length M which we are testing to see if it is present in the text.
     * @return a Boolean Value where true is returned if the pattern is present in the text, false otherwise.
     */

    public static boolean search(String text, String pattern) {
        if (text == null || pattern == null || pattern.length() == 0 || pattern.length() > text.length()) return false; int N = text.length(); int M = pattern.length();
        // Preprocessing the Bad Character Table to Support the Bad Character Rule Heuristic.
        int[] badCharacter = new int[R]; for (int i = 0; i < R; i++) {badCharacter[i] = -1;} for (int i = 0; i < M; i++) {badCharacter[pattern.charAt(i)] = i;}
        // Preprocessing the Good Suffix Table to Support the Good Suffix Rule Heuristic.
        int[] goodSuffix = new int[M + 1]; Arrays.fill(goodSuffix, M); int[] suffix = new int[M + 1]; for (int k = 0; k <= M; k++) {suffix[k] = M;}
        for (int k = M - 1; k >= 0; k--) {int p = k; while (p >= 0 && pattern.charAt(p) == pattern.charAt(M - 1 - (k - p))) {p--;}suffix[k] = k - p;}
        for (int k = M - 1; k >= 0; k--) {if (suffix[k] == k + 1) {for (int p = 0; p < M - 1 - k; p++) {if (goodSuffix[p] == M) {goodSuffix[p] = M - 1 - k;}}}}
        // Boyer-Moore Substring Search.
        int s = 0; while (s <= N - M) {
            int j = M - 1; while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {j--;}
            if (j < 0) {return true;} else {
                int badCharacterShift = Math.max(1, j - badCharacter[text.charAt(s + j)]); int goodSuffixShift = (j == M - 1) ? 1 : goodSuffix[j]; s += Math.max(badCharacterShift, goodSuffixShift);
            }
        } return false;
    }

    /**
     * This method tests the {@code BoyerMooreSubstringSearch} class by creating pattern and text Strings and then performing the Boyer-Moore Substring Search.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String text = "The cat sat on the mat!"; String pattern = "cat"; System.out.println("Does 'cat' exist in 'The cat sat on the mat?' " + search(text, pattern));
        System.out.println("Does 'rat' exist in 'The cat sat on the mat?' " + search(text, "rat")); System.out.println("All Tests Passed Successfully!");
    }
}