import java.util.Arrays; // Importing the Arrays Class from java.util to Support Array Manipulation and Create the Auxiliary Array.

/**
 * @author LewisThackeray
 * @date 31/03/2025
 *
 * <p>A <b>String</b> is a Sequence of Characters.  The Sorting Algorithms implemented in <i>Section 2.Sorting</i> can be used to Sort Strings.  However, there are Two Sorting Algorithms specific
 * for Sorting Strings: <b>Least Significant Digit (LSD)</b> and <b>Most Significant Digit (MSD)</b> String Sort.</p>
 *
 * <p><b>Least Significant Digit (LSD)</b> String Sort is a Multi-Pass Algorithm that Sorts Strings by Processing each Character from the Least Significant Digit (LSD) to the Most Significant
 * Digit (MDS).  LSD String Sort is best suited for Fixed-Length Strings.  It is important to note that in a String the Leftmost Character is the MSD and the Rightmost Character is the LSD.  The
 * LSD String Sort iterates through the Input Array of Strings, starting at the Rightmost Character of each String and treats the Character at that position as the Key and uses Key-Indexed
 * Counting to Sort the Strings based on the Current Character.  This process is repeated for each postition in the Input Array of Fixed Length Strings.</p>
 *
 * <p>Key-Indexed Couting is a Linear-Time Sorting Algorithm used to Sort Strings based on a Specific Key or Character.  The First Step of the Algorithm is to create an Array of Size R + 1, where
 * R is the Range of Possible Keys.  This Array will store the Frequency of each Key (each Character in the String).  You then iterate through the Strings and Count the Occurrences of each
 * Character by Incrementing the Corresponding Index in the Count Array for each Occurrence of the Character.  Then we Calculate the Correct Position of each String in the Output Array by
 * Calculating its Cumulative Sum and finally, we use the Count Array to place each Element in its Correct position in the Output Array based on its Key and then we copy the Output Array back to
 * the Original Array.</p>
 */

public class LeastSignificantDigitSort {

    private static final int R = 256; // Creating a Variable to Store the Size of the Character Set, in this Implementation we are using Extended ASCII.

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as this Class is Designed as a Utility Class with only Static Methods for performing
     * Least Significant Digit (LSD) String Sort Operations.  Direct Instantiation is NOT Supported.
     */

    private LeastSignificantDigitSort() {}

    /**
     * This method Sorts an Array of Fixed-Length Strings using the Least Significant Digit (LSD) String Sort.
     * @param array is the Array of Strings to be Sorted.
     * @param len is an Integer which Stores the Fixed Length of Each of the Strings in the Array.
     */

    public static void sort(String[] array, int len) {
        String[] auxiliary_array = new String[array.length]; assert isFixedLength(array, len); // Creating the Auxiliary Array and Asserting that the Strings in the Array are the Same Length.
        // Sorting by Each Character Position from Left to Right.
        for (int position = len - 1; position >= 0; position--) {
            int[] count = new int[R + 1]; for (int i = 0; i < array.length; i++) {count[array[i].charAt(position) + 1]++;} for (int r = 0; r < R; r++) {count[r + 1] += count[r];}
            for (int i = 0; i < array.length; i++) {auxiliary_array[count[array[i].charAt(position)]++] = array[i];} for (int i = 0; i < array.length; i++) {array[i] = auxiliary_array[i];}
            assert(isSortedAtDigit(array, position, len));
        } assert (isSorted(array));
    }

    /**
     * This method Checks that all the Strings in the Array have the Specified Length.
     * @param array is the Array of Strings to Check.
     * @param len is an Integer which Stores the Fixed Length of Each of the Strings in the Array.
     * @return a Boolean Value where true is Returned if all the Strings in the Array have a Length of len, false otherwise.
     */

    private static boolean isFixedLength(String[] array, int len) {for (String string : array) {if (string.length() != len) return false;} return true;}

    /**
     * This method Checks if an Array is Sorted up to the Specified Digit Position in the Array.
     * @param array is the Array of Strings to Check.
     * @param position is an Integer Specifying the Position in the Array which we need to Check up to.
     * @param len is an Integer which Stores the Fixed Length of Each of the Strings in the Array.
     * @return a Boolean Value where true is Returned if all the Strings in the Array have a Length of len, false otherwise.
     */

    private static boolean isSortedAtDigit(String[] array, int position, int len) {
        for (int i = 1; i < array.length; i++) {
            for (int j = position; j < len; j++) {if (array[i - 1].charAt(j) > array[i].charAt(j)) return false; if (array[i - 1].charAt(j) > array[i].charAt(j)) break;}
        } return true;
    }

    /**
     * This method Checks if the Entire Array is Sorted Lexicograghically.
     * @param array is the Array of Strings to Check.
     * @return a Boolean Value where true is Returned if the Array is Sorted, false otherwise.
     */

    private static boolean isSorted(String[] array) {for (int i = 1; i < array.length; i++) {if (array[i - 1].compareTo(array[i]) > 0) return false;} return true;}

    /**
     * This method Displays all the Elements in the Array.
     * @param array is the Array of Strings to Display.
     */

    private static void show(String[] array) {for (String string : array) {System.out.println(string);}}

    /**
     * This method tests {@code LeastSignificantDigitSort} by Performing a Least Significant Digit (LSD) String Sort on a Array of Strings.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String[] array = {"ruff", "babe", "cafe", "turf", "fade", "face", "cape", "cade", "bark"}; System.out.println("Array of Strings before Sorting: "); show(array); sort(array, 4);
        System.out.println("\nArray of Strings after Sorting: "); show(array); System.out.println("\nAll Tests Passed Successfully!");
    }

}
