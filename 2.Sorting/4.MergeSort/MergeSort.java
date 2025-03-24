import java.util.Comparator; // Importing the Comparator Class from the java.util Package to Compare Two Objects and Determine which should come First in an Ordered Sequence.

/**
 * @author LewisThackeray
 * @date 24/03/2025
 *
 * {@code MergeSort} provides Static Methods for Sorting an Array using a Top-Down, Recursive Version of <em>Merge Sort</em>, using Generics to ensure the Flexibility of my Implementation so
 * that it supports Customised Sorting Orders through the use of Comparator, as well as Generic Data Types through Comparable and Object[].  A Top-Down <em>Merge Sort</em> works as follows:
 * <ol>
 *     <li>The first step is to recursively split the array into halves until each sub-array contains exactly one element.</li>
 *     <li>Then the algorithm merges the sorted sub-arrays until all the elements in the original array are in the sorted array.</li>
 * </ol>
 *
 * <p><b>Time Complexity of Top-Down Merge Sort:</b> The Best, Average and Worst Case Time Complexity is O(nlog(n)) as their are log(n) levels in the Recursion Tree and at each level in the
 * Recusion Tree, the <i>merge</i> function processes all n elements across all subarrays, leading to a Total Time Complexity of O(nlog(n)).</p>
 *
 * <p><b>Space Complexity of Top-Down Merge Sort:</b> This implementation uses an Auxiliary Array of size n, thus the Space Complexity is O(n).</p>
 */

public class MergeSort {

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as the Class is Designed as a Utility Class with only Static Methods for performing
     * Merge Sort Operations.  Direct Instantion is NOT Supported.
     */

    private MergeSort() {}

    /**
     * This method creates the Auxiliary Array and Starts the Recursion when Rearranging the Array in Ascending Order.
     * @param array is the Array to be Sorted.
     */

    public static <T extends Comparable<? super T>> void sort(T[] array) {
        T[] auxiliary_array = (T[]) new Comparable[array.length]; sort(array, auxiliary_array, 0, array.length - 1); assert isSorted(array);
    }

    /**
     * This method recursively rearranges the Array in Ascending Order, using the Natural Order and using Assertion Statements to Verify this Order.
     * @param array is the Array to be Sorted.
     * @param auxiliary_array is the Auxiliary Array.
     * @param low is the Lower Bound Index of the Segment of the Array to Sort (inclusive).
     * @param high is the Upper Bound Index of the Segment of the Array to Sort (inclusive).
     */

    private static <T extends Comparable<? super T>> void sort(T[] array, T[] auxiliary_array, int low, int high) {
        if (high <= low) return; int mid = low + (high - low) / 2; sort(array, auxiliary_array, low, mid); sort(array, auxiliary_array, mid + 1, high); merge(array, auxiliary_array, low, mid, high);
    }

    /**
     * This method creates the Auxiliary Array and Starts the Recursion when Rearranging the Array where the Order is Specified by the Comparator.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     */

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        T[] auxiliary_array = (T[]) new Comparable[array.length]; sort(array, auxiliary_array, comparator, 0, array.length - 1); assert isSorted(array, comparator, 0, array.length - 1);
    }

    /**
     * This method recursively rearranges the Array where the Order is Specified by the Comparator and Assertion Statements are used to Verify this Order.
     * @param aray is the Array to be Sorted.
     * @param auxiliary_array is the Auxiliary Array.
     * @param comparator is the Comparator Specifying the Order.
     * @param low is the Lower Bound Index of the Segment of the Array to Sort (inclusive).
     * @param high is the Upper Bound Index of the Segment of the Array to Sort (inclusive).
     */

    private static <T> void sort(T[] array, T[] auxiliary_array, Comparator<? super T> comparator, int low, int high) {
        if (high <= low) return; int mid  = low + (high - low) / 2; sort(array, auxiliary_array, comparator,low, mid); sort(array, auxiliary_array, comparator, mid + 1, high);
        merge(array, auxiliary_array, comparator, low, mid, high);
    }

    /**
     * This method merges Two Subarrays where the Array is in Ascending Order, using Natural Ordering.
     * @param array is the Array containing the Subarrays.
     * @param auxiliary_array is the Auxiliary Array used to merge the Two Subarrays.
     * @param low is the Start Index of the First Subarray.
     * @param mid is the End Index of the First Subarray.
     * @param high is the End Index of the Second Subarray.
     */

    private static <T extends Comparable<? super T>> void merge(T[] array, T[] auxiliary_array, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {auxiliary_array[k] = array[k];} int i = low; int j = mid + 1; for (int k = low; k <= high; k++) {
            if (i > mid) {array[k] = auxiliary_array[j++];} else if(j > high) {array[k] = auxiliary_array[i++];}
            else if (less(auxiliary_array[j], auxiliary_array[i])) {array[k] = auxiliary_array[j++];} else {array[k] = auxiliary_array[i++];}
        }
    }

    /**
     * This method merges the Two Subarrays where the Order of the Array is Specified by the Comparator.
     * @param array is the Array containing the Subarrays.
     * @param auxiliary_array is the Auxiliary Array used to merge the Two Subarrays.
     * @param comparator is the Comparator Specifying the Order.
     * @param low is the Start Index of the First Subarray.
     * @param mid is the End Index of the First Subarray.
     * @param high is the End Index of the Second Subarray.
     */

    private static <T> void merge(T[] array, T[] auxiliary_array, Comparator<? super T> comparator, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {auxiliary_array[k] = array[k];} int i = low; int j = mid + 1; for (int k = low; k <= high; k++) {
            if (i > mid) {array[k] = auxiliary_array[j++];} else if(j > high) {array[k] = auxiliary_array[i++];}
            else if (less(comparator, auxiliary_array[j], auxiliary_array[i])) {array[k] = auxiliary_array[j++];} else {array[k] = auxiliary_array[i++];}
        }
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
     * This method tests the {@code MergeSort} Class by Performing a Merge Sort on the Arrays of Different Types and by Sorting the Arrays in Different Ways.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {

        // Testing the Sort Method with an Array of Integers which Sorts the Array in the Natural Ascending Order.
        Integer[] integerArray = {72,83,45,67,52,64,8,0,11,46,76,34,23,147,89,90,100,10,1000,999,78,435,678}; MergeSort.sort(integerArray); show(integerArray);

        // Testing the Sort Method with an Array of Integers which Sorts the Array with a Customised Comparator in Descending Order.
        Comparator<Integer> descending = Comparator.reverseOrder(); MergeSort.sort(integerArray, descending); show(integerArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array Lexicographically in the Natural Order.
        String[] stringArray = {"Apple", "Nala", "Tigger", "Pusscat", "Spatula", "Knife", "Spoon", "Fork", "Lewis", "Ethan", "Pizza", "Diet Coke", "Bread", "Salad", "Table", "Chairs"};
        MergeSort.sort(stringArray); show(stringArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array with a Customised Comparator which Sorts the Strings based on their Length.
        Comparator<String> byLength = Comparator.comparingInt(String::length); MergeSort.sort(stringArray, byLength); show(stringArray);

        System.out.println("\n\n All Tests Passed Successfully!!!");

    }
}