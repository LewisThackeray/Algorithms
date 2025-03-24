import java.util.Comparator; // Importing the Comparator Class from the java.util Package to Compare Two Objects and Determine which should come First in an Ordered Sequence.

/**
 * @author LewisThackeray
 * @date 24/05/2025
 *
 * {@code SelectionSort} provides Static Methods for Sorting an Array using <em>Selection Sort</em>, using Generics to ensure the flexibility of my implementation so that it supports customised
 * sorting orders through the use of Comparator, as well as generic data types through Comparable and Object[].  <em>Selection Sort</em> works as follows:
 * <ol>
 *     <li>First, find the Smallest Item in the Array and Exchange it with the First Entry (itself if the First Entry is the Smallest).</li>
 *     <li>Then, find the next Smallest Item and Exchange it with the Second Entry.</li>
 *     <li>Continue in this way until the entire array is sorted.</li>
 * </ol>
 *
 * <p><b>Time Complexity of Selection Sort:</b> The Best, Average and Worst Case Time Complexity is 0(n<sup>2</sup>) because the Algorithm always performs the same Number of Comparisons and
 * Swaps, irrespective of the Input Array.</p>
 *
 * <p><b>Space Complexity of Selection Sort:</b> The Algorithm uses Constant Extra Space, making its Worst-Case Space Complexity 0(1).</p>
 */

public class SelectionSort {

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as the Class is Designed as a Utility Class with only Static
     * Methods for performing Selection Sort Operations.  Direct Instantiation is NOT Supported.
     */

    private SelectionSort() {}

    /**
     * This method rearranges the Array in Ascending Order, using the Natural Order and using Assertion Statements to Verify this Order.
     * @param array is the Array to be Sorted.
     */

    public static <T extends Comparable<? super T>> void sort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i; for (int j = i + 1; j < array.length; j++) {
                if (less(array[j], array[min])) min = j;
            }
            swap(array, i, min); assert isSorted(array, 0, i);
        } assert isSorted(array);
    }

    /**
     * This method rearranges the Array in Ascending Order, where the Order is Specified by the Comparator and Assertion Statements are used to Verify this Order.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     */

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        for (int i = 0; i < array.length; i++) {
            int min = i; for (int j = i + 1; j < array.length; j++) {
                if (less(comparator, array[j], array[min])) {min = j;}
            } swap(array, i, min); assert isSorted(array, comparator, 0, i);
        }
        assert isSorted(array, comparator);
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
     * This method checks that an Entire Array is Sorted Accoding to the Comparator.
     * @param array is the Array to Check.
     * @param comparator is the Comparator Specifying the Order.
     * @return true if the Array is Sorted, false Otherwise.
     */

    private static <T> boolean isSorted(T[] array, Comparator<? super T> comparator) {return isSorted(array, comparator, 0, array.length - 1);}

    /**
     * This method checks if a Segment of the Array (from low to high - inclusive) is Sorted According to the Comparator.
     * @param array is the Array to Check.
     * @comparator is the Comparator Specifiying the Order.
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
     * This method tests the {@code SelectionSort} Class by Performing a Selection Sort on an Array of Integers.
     * @param args the Command Line Arguments.
     */
    public static void main(String[] args) {

        // Testing the Sort Method with an Array of Integers which Sorts the Array in the Natural Ascending Order.
        Integer[] integerArray = {72,83,45,67,52,64,8,0,11,46,76,34,23,147,89,90,100,10,1000,999,78,435,678}; SelectionSort.sort(integerArray); show(integerArray);

        // Testing the Sort Method with an Array of Integers which Sorts the Array with a Customised Comparator in Descending Order.
        Comparator<Integer> descending = Comparator.reverseOrder(); SelectionSort.sort(integerArray, descending); show(integerArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array Lexigraphically in the Natural Order.
        String[] stringArray = {"Apple", "Nala", "Tigger", "Pusscat", "Spatula", "Knife", "Spoon", "Fork", "Lewis", "Ethan", "Pizza", "Diet Coke", "Bread", "Salad", "Table", "Chairs"};
        SelectionSort.sort(stringArray); show(stringArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array with a Customised Comparator which Sorts the Strings based on their Length.
        Comparator<String> byLength = Comparator.comparingInt(String::length); SelectionSort.sort(stringArray, byLength); show(stringArray);

        System.out.println("\n\n All Tests Passed Successfully!!!");

    }
}