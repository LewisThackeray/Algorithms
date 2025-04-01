/**
 * <p>A fundamental operation on strings is a substring search: given a text string of length <i>N</i> and a pattern string of length <i>M</i>, find an occurrence of the pattern within the text.
 * There are four main substring search algorithms: <b>Brute Force Substring Search</b>, Knuth-Morris-Pratt (KMP) Substring Search, Boyer-Moore Substring Search and Robin-Karp Substring Search.</p>
 *
 * <p>The <b>Brute Force Substring Search</b> is the simplist approach which beings with the first character in the text and compares it with the first character in the pattern text and continues
 * comparing characters from the pattern and the text, one by one, moving left to right, as long as the characters match.  If there is a mismatch, shift the starting position in the text by one
 * character to the right and restart the comparison.  If all the letters in the pattern and the text match, you've found the pattern.  Repeat this process until you have checked all the possible
 * starting points in the text.</p>
 *
 * {@code BruteForceSubstringSearch} is an implementation of a <em>Brute Force Substring Search</em> which checks if a provided pattern of length M, is a present in text of length N.  This code
 * assumes that the substring search is case sensitive - therefore - if pattern[a] = b and text[a] = B then there is not a match!
 *
 * <p><b>Time Complexity of the Brute Force Substring Search: </b>The Worst Case Time Complexity of the Brute Force Substring Search is O(N * M) where the text length is N and the pattern length
 * is M so the total number of comparisons is N * M.</p>
 */

public class BruteForceSubstringSearch {

    /**
     * This is the Class Constructor which is private to prevent instantiations of the class, as the class is designed as a utility class with only static methods for performing a Brute Force
     * Substring Search.  Direct instantiation is NOT supported.
     */

    private BruteForceSubstringSearch() {}

    /**
     * This method performs a Brute Force Substring Search which determines if a pattern of length M is present in text of length N.
     * @param text is a String of length N which we are determining if the pattern exists in.
     * @param pattern is a String of length M which we are testing to see if it is present in the text.
     * @return a Boolean Value where true is returned if the pattern is present in the text, false otherwise.
     */

    public static boolean search(String text, String pattern) {
        if (text == null || pattern == null) return false; if (pattern.length() == 0) return true; if (pattern.length() > text.length()) return false; int N = text.length(); int M = pattern.length();
        for (int i = 0; i <= N - M; i++) {boolean match = true; for (int j = 0; j < M; j++) {if (text.charAt(i + j) != pattern.charAt(j)) {match = false; break;}} if (match) return true;} return false;
    }

    /**
     * This method tests the {@code BruteForceSubstringSearch} class by creating pattern and text Strings and then performing a Brute Force Substring Search.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String text = "The cat sat on the mat!"; String pattern = "cat"; System.out.println("Does 'cat' exist in 'The cat sat on the mat?' " + search(text, pattern));
        System.out.println("Does 'rat' exist in 'The cat sat on the mat?' " + search(text, "rat")); System.out.println("All Tests Passed Successfully!");
    }
}