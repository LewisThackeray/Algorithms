import java.util.Comparator; // Importing the Comparator Class from the java.util Package to Compare Two Objects and Determine which should come First in an Ordered Sequence.

/**
 * @author LewisThackeray
 * @date 24/03/2025
 *
 * {@code InsertionSort} provides Static Methods for Sorting an Array using <em>Insertion Sort</em>, using Generics to ensure the Flexibility of my Implementation so that it supports Customised
 * Sorting Orders through the use of Comparator, as well as Generic Data Types through Comparable and Object[].  <em>Insertion Sort</em> works as follows:
 * <ol>
 *     <li>At each iteration, Insertion Sort removes one Element from the Input Data, finds the Location it belongs within the Sorted List, and Inserts it there.</li>
 *     <li>This process is repeated until no Input Elements remain.</li>
 * </ol>
 *
 * <p><i>An Insertion Sort is more efficient than Selection Sort for sorting partially sorted arrays where a partially sorted array is an array in which the number of inversions is significantly
 * smaller than the maximum possible inversions for its size.</i></p>
 *
 * <p><b>Time Complexity of Insertion Sort:</b> The Best Case Time Complexity is O(n) if the Array is already Sorted and No Shifts are needed.  If the array is partially sorted and only has
 * <i>k</i> inversions, then the time complexity is approximately O(n + k), however, for more disordered arrays, the time complexity tends towards the Average and Worst Case Time Complexity of
 * O(n<sup>2</sup>.</p>
 *
 * <p><b>Space Complexity of Insertion Sort:</b> The Algorithm uses Constant Empty Space, making its Worst-Case Space Complexity O(1).</p>
 */

public class InsertionSort {

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as the Class is Designed as a Utility Class with only Static Methods for performing
     * Insertion Sort Operations.  Direct Instantiation is NOT Supported.
     */

    private InsertionSort() {}

    /**
     * This method rearranges the Array in Ascending Order, using the Natural Order and using Assertion Statements to Verify this Order.
     * @param array is the Array to be Sorted.
     */

    public static <T extends Comparable<? super T>> void sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 && less(array[j], array[j - 1]); j--) {swap(array, j, j - 1);} assert isSorted(array, 0, i);
        } assert isSorted(array);
    }

    /**
     * This method rearranges the Array where the Order is Specified by the Comparator and Assertion Statements are used to Verify this Order.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     */

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 && less(comparator, array[j], array[j - 1]); j--) {swap(array, j, j - 1);} assert isSorted(array, comparator, 0, i);
        } assert isSorted(array, comparator);
    }

    /**
     * This method compares Two Objects to Determine if the First Object is Less than the Second Object.
     * @param v is the First Comparable Object.
     * @param w is the Second Comparable Object.
     * @return true if v < w, false otherwise.
     */

    private static <T extends Comparable<? super T>> boolean less(T v, T w) {return v.compareTo(w) < 0;}

    /**
     * This method compares Two Object using a Comparator to Determine if the First Object is Less than the Second Object.
     * @param comparator is the Comparator used to Make the Comparison.
     * @param v is the First Object.
     * @param w is the Second Object.
     * @return true if v < w According to the Comparator, false otherwise.
     */

    private static <T> boolean less(Comparator<? super T> comparator, T v, T w) {return comparator.compare(v, w) < 0;}

    /**
     * This method swaps Two Elements in the Array.
     * @param array is the Array in which the Swap Occurs.
     * @param i is the Index of the First Element that we are Going to Swap.
     * @param j is the Index of the Second Element that we are Going to Swap.
     */

    private static <T> void swap(T[] array, int i, int j) {T swap = array[i]; array[i] = array[j]; array[j] = swap;}

    /**
     * This method checks that an Entire Array is Sorted in Ascending Order.
     * @param array is the Array to Check.
     * @return true if the Array is Sorted, false Otherwise.
     */

    private static <T extends Comparable<? super T>> boolean isSorted(T[] array) {return isSorted(array, 0, array.length - 1);}

    /**
     * This method checks if a Segment of the Array (from low to high - inclusive) is Sorted in Ascending Order.
     * @param array is the Array to Check.
     * @param low is the Lower Bound Index (inclusive).
     * @param high is the Upper Bound Index (inclusive).
     * @return true if the Specified Portion of the Array is Sorted, false Otherwise.
     */

    private static <T extends Comparable<? super T>> boolean isSorted(T[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {if (less(array[i], array[i - 1])) {return false;}} return true;
    }

    /**
     * This method checks that an Entire Array is Sorted According to the Comparator.
     * @param array is the Array to Check.
     * @param comparator is the Comparator Specifying the Order.
     * @return true if the Array is Sorted, false Otherwise.
     */

    private static <T> boolean isSorted(T[] array, Comparator<? super T> comparator) {return isSorted(array, comparator, 0, array.length - 1);}

    /**
     * This method checks if a Segment of the Array (from low to high - inclusive) is Sorted According to the Comparator.
     * @param array is the Array to Check.
     * @param comparator is the Comparator Specifiying the Order.
     * @param low is the Lower Bound Index (inclusive).
     * @param high is the Upper Bound Index (inclusive).
     * @return true if the Specified Portion of the Array is Sorted, false Otherwise.
     */

    private static <T> boolean isSorted(T[] array, Comparator<? super T> comparator, int low, int high) {
        for (int i = low + 1; i <= high; i++) {if (less(comparator, array[i], array[i - 1])) {return false;}} return true;
    }

    /**
     * This method displays all the Elements in the Array to the User in a Readable and Understandable Format.
     * @param array is the Array to Print.
     */

    private static <T> void show(T[] array) {for (int i = 0; i < array.length; i++) {System.out.println(array[i]);}}

    /**
     * This method tests the {@code InsertionSort} Class by Performing an Insertion Sort on the Arrays of Different Types and by Sorting the Arrays in Different Ways.
     * @param args the Command Line Arguments.
     */
    public static void main(String[] args) {

        // Testing the Sort Method with an Array of Integers which Sorts the Array in the Natural Ascending Order.
        Integer[] integerArray = {72,83,45,67,52,64,8,0,11,46,76,34,23,147,89,90,100,10,1000,999,78,435,678}; InsertionSort.sort(integerArray); show(integerArray);

        // Testing the Sort Method with an Array of Integers which Sorts the Array with a Customised Comparator in Descending Order.
        Comparator<Integer> descending = Comparator.reverseOrder(); InsertionSort.sort(integerArray, descending); show(integerArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array Lexicographically in the Natural Order.
        String[] stringArray = {"Apple", "Nala", "Tigger", "Pusscat", "Spatula", "Knife", "Spoon", "Fork", "Lewis", "Ethan", "Pizza", "Diet Coke", "Bread", "Salad", "Table", "Chairs"};
        InsertionSort.sort(stringArray); show(stringArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array with a Customised Comparator which Sorts the Strings based on their Length.
        Comparator<String> byLength = Comparator.comparingInt(String::length); InsertionSort.sort(stringArray, byLength); show(stringArray);

        System.out.println("\n\n All Tests Passed Successfully!!!");

    }
}