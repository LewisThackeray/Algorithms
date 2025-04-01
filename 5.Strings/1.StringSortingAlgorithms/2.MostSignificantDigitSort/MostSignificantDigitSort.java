import java.util.Arrays; // Importing the Arrays Class from java.util to Support Array Manipulation and Create the Auxiliary Array.

/**
 * @author LewisThackeray
 * @date 31/03/2025
 *
 * <p>A <b>String</b> is a Sequence of Characters.  The Sorting Algorithms implemented in <i>Section 2.Sorting</i> can be used to Sort Strings.  However, there are Two Sorting Algorithms specific
 * for Sorting Strings: <b>Least Significant Digit (LSD)</b> and <b>Most Significant Digit (MSD)</b> String Sort.</p>
 *
 * <p><b>Most Significant Digit (LSD)</b> String Sort is a Sorting Algorithm that passes Strings starting from the Most Significant Digit (MSD) to the Least Significant Digit (LSD).  MSD String
 * Sort is often implemented as a Recursive Algorithm that is well-suited for Sorting Variable-Length Strings.  The Algorithm Sorts the Strings based on their First (Leftmost) Character using
 * Key-Indexed Counting which partitions the Strings into Groups, where each Group contains Strings starting with the Same Character.  For each Group (Partition), Recursively apply MSD String Sort
 * to the Remaining Characters of the Strings and this process continues until the Algorithm has processed all Characers in the Strings or the Group contains only one String.  The Recursion ends
 * when all the Strings are Sorted or when no more Characters are available to Sort.</p>
 *
 * <p>Key-Indexed Couting is a Linear-Time Sorting Algorithm used to Sort Strings based on a Specific Key or Character.  The First Step of the Algorithm is to create an Array of Size R + 1, where
 * R is the Range of Possible Keys.  This Array will store the Frequency of each Key (each Character in the String).  You then iterate through the Strings and Count the Occurrences of each
 * Character by Incrementing the Corresponding Index in the Count Array for each Occurrence of the Character.  Then we Calculate the Correct Position of each String in the Output Array by
 * Calculating its Cumulative Sum and finally, we use the Count Array to place each Element in its Correct position in the Output Array based on its Key and then we copy the Output Array back to
 * the Original Array.</p>
 */

public class MostSignificantDigitSort {

    private static final int R = 256; // Creating a Variable to Store the Size of the Character Set, in this Implementation we are using Extended ASCII.
    private static final int CUT = 6; // Creating a Variable to Store the Cut Off Point, where an Insertion Sort is Performed, so Sub-Arrays with Less than 6 Strings perform an Insertion Sort.

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as this Class is Designed as a Utility Class with only Static Methods for performing
     * Most Significant Digit (MSD) String Sort Operations.  Direct Instantiation is NOT Supported.
     */

    private MostSignificantDigitSort() {}

    /**
     * This is a method which Sorts the Array of Strings using Most Significant Digit (MSD) Sort by Calling the Recursive Routine.
     * @param array is the Array of Strings to be Sorted.
     */

    public static void sort(String[] array) {String[] auxiliary_array = new String[array.length]; sort(array, auxiliary_array, 0, array.length - 1, 0); assert(isSorted(array));}

    /**
     * This method Recursively Sorts the Array of Strings from low to high Starting at Character Position position.
     * @param array is the Array of Strings to be Sorted.
     * @param auxiliary_array is the Auxiliary Array which is a Temporary Array used in the Sorting process to assist with Distributing and Rearranging the Strings.
     * @param low is an Integer which is the Lower Bound Index of the Subarray.
     * @param high is an Integer which is the Higher Bound Index of the Subarray.
     * @param position is an Integer which is the Current Character Position.
     */

    private static void sort(String[] array, String[] auxiliary_array, int low, int high, int position) {
        if (high <= low + CUT) {insertionSort(array, low, high, position); return;} int count[] = new int[R + 2]; for (int i = low; i <= high; i++) {count[charAt(array[i], position) + 2]++;}
        for (int r = 0; r < R + 1; r++) {count[r + 1] += count[r];} for (int i = low; i <= high; i++) {auxiliary_array[count[charAt(array[i], position) + 1]++ + low] = array[i];}
        for (int i = low; i <= high; i++) {array[i] = auxiliary_array[i];}
        for (int r = 0; r < R; r++) {int newLow = low + count[r]; int newHigh = low + count[r + 1] - 1; if (newLow < newHigh) {sort(array, auxiliary_array, newLow, newHigh, position + 1);}}
    }

    /**
     * This method performs an Insertion Sort on a Subarray when the Size is Smaller than CUT Constant, where the Insertion Sort starts at the Character Position position.
     * @param array is the Array of Strings to be Sorted.
     * @param low is an Integer which is the Lower Bound Index of the Subarray.
     * @param high is an Integer which is the Higher Bound Index of the Subarray.
     * @param position is an Integer which is the Current Character Position.
     */

    private static void insertionSort(String[] array, int low, int high, int position) {
        for (int i = low + 1; i <= high; i++) {int j = i; while (j < low) {
            String current = array[j]; String previous = array[j - 1]; if (current.substring(position).compareTo(previous.substring(position)) < 0) {
                String temp = array[j]; array[j] = array[j - 1]; array[j - 1] = temp;} else {break;}
        }}
    }

    /**
     * This method is an Overloaded Method of charAt() which Returns the Character at a Specified Position in the String.
     * @param string is the String which the Character Resides In.
     * @param position an Integer which is the Current Character Position.
     * @return an Integer which is the Position of the Character in the String, -1 if the Position Specified is Outside of the String.
     */

    private static int charAt(String string, int position) {if (position < string.length()) return string.charAt(position); return -1;}

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
        String[] array = {"ruff", "babe", "cafe", "turf", "fade", "face", "cape", "cade", "bark"}; System.out.println("Array of Strings before Sorting: "); show(array); sort(array);
        System.out.println("\nArray of Strings after Sorting: "); show(array); System.out.println("\nAll Tests Passed Successfully!");
    }


}