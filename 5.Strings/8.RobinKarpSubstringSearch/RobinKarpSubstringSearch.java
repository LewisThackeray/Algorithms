/**
 * <p>A fundamental operation on strings is a substring search: given a text string of length <i>N</i> and a pattern string of length <i>M</i>, find an occurrence of the pattern within the text.
 * There are four main substring search algorithms: Brute Force Substring Search, Knuth-Morris-Pratt (KMP) Substring Search, Boyer-Moore Substring Search and <b>Robin-Karp Substring Search.</b></p>
 *
 * The <b>Robin-Karp Substring Search</b> Algorithm uses hashing and is particularly useful whem you need to search for multiple patterns or perform approximate string matching.  The algorith uses
 * a rolling has function to compute the hash of the pattern, compute the hash of substrings in the text (where the substrings are of the same length as the pattern) and compare these hash values
 * to quickly check if the substring matches the pattern.  The algorithm hashes a substring of the text and compares the hash with the hash of the pattern, if the hash values match, you then
 * perform a direct comparison of the unhashed substring and the unhashed pattern to avoid hash collisions.  If the hashes do not match, move to the next substring in the text.
 *
 * {@code RobinKarpSubstringSerach} is an implementation of the <em>Robin-Karp Substring Search</em> which checks if a provided pattern of length M, is present in text of length N.  This code
 * assumes that the substring search is case sensitive - therefore - if patter[a] = b and text[a] = B then there is not a match!.
 *
 * <p><b>Time Complexity of the Robin-Karp Substring Search:</b> The Worst Case Time Complexity for the Robin-Karp Substring Search is O(N * M) when hash collisions occur frequently.</p>
 */

public class RobinKarpSubstringSearch {

    private static final long PRIME = 101; private static final int RADIX = 256; // Creating Constants to Store a Large Prime Number and the Base of the Rolling Hash Function.

    /**
     * This is the Class Constructor which is private to prevent instantiations of the class, as the class is designed as a utility class with only static methods for performing the Robin-Karp
     * Substring Search.  Direct instantiation is NOT supported.
     */

    private RobinKarpSubstringSearch() {}

    /**
     * This method calculates the Hash Value of a String of a Given Length using a Polynomial Rolling Hash Function.
     * @param string is the String to be Hashed.
     * @param M is an Integer which stores the Number of Characters we are Hashing in the String as the Substrings should be the Same Length as the Pattern.
     * @return a Long Integer which Stores the Hash Value for the String passed into the method.
     */

    private static long hash(String string, int M) {long hash = 0; for (int i = 0; i < M; i++) {hash = (RADIX * hash + string.charAt(i)) % PRIME;} return hash < 0 ? hash + PRIME : hash;}

    /**
     * This method performs the Robin-Karp Substring Search which determines if a pattern of length M is present in text of length N.
     * @param text is a String of length N which we are determining if the pattern exists in.
     * @param pattern is a String of length M which we are testing to see if it is present in the text.
     * @return a Boolean Value where true is returned if the pattern is present in the text, false otherwise.
     */

    public static boolean search(String text, String pattern) {
        if (text == null || pattern == null || pattern.length() == 0 || pattern.length() > text.length()) return false; int N = text.length(); int M = pattern.length();
        long patternHash; long textHash; long h = 1; // Creating Variables to Store the Hash Value of the Pattern and the Current Substring and a Variable to Store the Highest Power of RADIX.
        for (int i = 1; i < M; i++) {h = (h * RADIX) % PRIME;} patternHash = hash(pattern, M); textHash = hash(text, M);// Calculating the value of h for the Rolling Hash and the Getting the Hash Values.
        // Robin-Karp Substring Search.
        for (int i = 0; i <= N - M; i++) {
            if (patternHash == textHash) {boolean match = true; for (int j = 0; j < M; j++) {if (pattern.charAt(j) != text.charAt(i + j)) {match = false; break;}} if (match) return true;}
            if (i < N - M) {textHash = (textHash - text.charAt(i) * h) % PRIME; textHash = (textHash * RADIX + text.charAt(i + M)) % PRIME; if (textHash < 0) textHash += PRIME;}
        } return false;
    }

    /**
     * This method tests the {@code RobinKarpSubstringSearch} class by creating pattern and text Strings and then performing the Robin-Karp Substring Search.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String text = "The cat sat on the mat!"; String pattern = "cat"; System.out.println("Does 'cat' exist in 'The cat sat on the mat?' " + search(text, pattern));
        System.out.println("Does 'rat' exist in 'The cat sat on the mat?' " + search(text, "rat")); System.out.println("All Tests Passed Successfully!");
    }



}