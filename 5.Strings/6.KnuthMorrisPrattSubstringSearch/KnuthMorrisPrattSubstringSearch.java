/**
 * <p>A fundamental operation on strings is a substring search: given a text string of length <i>N</i> and a pattern string of length <i>M</i>, find an occurrence of the pattern within the text.
 * There are four main substring search algorithms: Brute Force Substring Search, <b>Knuth-Morris-Pratt (KMP) Substring Search</b>, Boyer-Moore Substring Search and Robin-Karp Substring Search.</p>
 *
 * The <b>Knuth-Morris-Pratt Substring Search</b> Algorithm is an efficient method for search for a substring (a pattern) with a string (the text) which creates a partial match table during the
 * preprocessing phase that provides information on how to skip unnecessary  comparisons, allowing the search phase to shift the pattern efficiently whenever a mismatch occurs, rather than
 * re-checking previously matched characters.
 *
 * {@code KnuthMorrisPrattSubstringSearch} is an implementation of the <em>Knuth-Morris-Pratt Substring Search</em> which checks if a provided pattern of length M, is present in text of length N.
 * This code assumes that the substring search is case sensitive - therefore - if pattern[a] = b and text[a] = B then there is not a match!
 *
 * <p><b>Time Complexity of the Knuth-Morris-Pratt Substring Search:</b> The Worst Case Time Complexity of the Knuth-Morris-Pratt Substring Search is O(N + M) where the text length is N and the
 * pattern length is M.  The prefix table is built in O(M) and the search phase runs in O(N) so the number of comparisons is bounded by N + M.</p>
 */

public class KnuthMorrisPrattSubstringSearch {

    /**
     * This is the Class Constructor which is private to prevent instantiations of the class, as the class is designed as a utility class with only static methods for performing the Knuth-Morris-
     * Pratt Substring Search.  Direct instantiation is NOT supported.
     */

    private KnuthMorrisPrattSubstringSearch() {}

    /**
     * This method builds the Prefix Table for the Knuth-Morris-Pratt Substring Search.  The table stores the values representing the lengths of the longest possible prefix which is also a suffix
     * for each substring of the pattern.
     * @param pattern is a String which stores the pattern we are testing to see if it exists in the text.
     * @return an Array of Integers containing the Prefix Table values.
     */

    private static int[] preProcess(String pattern) {
        int M = pattern.length(); int[] prefixTable = new int[M]; prefixTable[0] = 0; // Creating the Prefix Table and Setting prefixTable[0] = 0 as the First Character has no Proper Prefix.
        int len = 0; int i = 1; // Creating Variables to Store the Length of the Longest Possible Prefix and the Current Position in the Pattern.
        while (i < M) {if (pattern.charAt(i) == pattern.charAt(len)) {len++; prefixTable[i] = len; i++;} else {if (len > 0) {len = prefixTable[len - 1];} else {prefixTable[i] = 0; i++;}}}
        return prefixTable;
    }

    /**
     * This method searches for the pattern in the text using the Knuth-Morris-Pratt Substring Search which determines if a pattern of length M is present in text of length N.
     * @param text is a String of length N which we are determining if the pattern exists in.
     * @param pattern is a String of length M which we are testing to see if it is present in the text.
     * @return a Boolean Value where true is returned if the pattern is present in the text, false otherwise.
     */

    public static boolean search(String text, String pattern) {
        if (text == null || pattern == null) return false; if (pattern.length() == 0) return true; if (pattern.length() > text.length()) return false;
        int N = text.length(); int M = pattern.length(); int[] prefixTable = preProcess(pattern);
        int i = 0; int j = 0; while (i < N) {if (text.charAt(i) == pattern.charAt(j)) {i++; j++; if (j == M) return true;} else {if (j > 0) {j = prefixTable[j - 1];} else {i++;}}} return false;
    }

    /**
     * This method tests the {@code KnuthMorrisPrattSubstringSearch} class by creating pattern and text Strings and then performing the Knuth-Morris-Pratt Substring Search.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String text = "The cat sat on the mat!"; String pattern = "cat"; System.out.println("Does 'cat' exist in 'The cat sat on the mat?' " + search(text, pattern));
        System.out.println("Does 'rat' exist in 'The cat sat on the mat?' " + search(text, "rat")); System.out.println("All Tests Passed Successfully!");
    }


}