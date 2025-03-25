import java.util.Comparator; // Importing the Comparator Class from the java.util Package to Compare Two Objects and Determine which should come First in an Ordered Sequence.

/**
 * @author LewisThackeray
 * @date 24/03/2025
 *
 * {@code QuickSort} provides Static Methods for Sorting an Array using <em>Quick Sort</em>, using Generics to ensure the Flexibility of my Implementation so that it supports Customised Sorting
 * Orders through the use of Comparator, as well as Generic Data Types through Comparable and Object[].  <em>Quick Sort</em> works as follows:
 *  <ol>
 *      <li>The first step is to choose a <i>pivot</i>, common choices include: the first element, the middle element and the last element.</li>
 *      <li>Then, you rearrange the array so that all the elements less than the pivot are placed to its left, and all elements greater than the pivot are placed to its right.  After this step,
 *      the pivot ends up in its final sorted position.</li>
 *      <li>Apply the same process to the subarray of elements to the left of the pivot (the smaller elements) and the subarray of elements to the right of the pivot (the larger elements).
 *      Continue this recursion until the subarrays contain zero or one elements (the base case of recursion), at which point they are already sorted.</li>
 *      <li>As the recursive routine unwinds, the full sorted array is clear as all the elements have been sorted.</li>
 *  </ol>
 *
 *  <p><i>The partioning process rearranges the array to make the following three conditions hold: the entry a[j] is in its final place in the array, for some j, no entry in a[0] through a[j - 1]
 *  is greater than a[j] and no entry in a[j + 1] through a[a.length - 1] is less than a[j].</i></p>
 *
 *  <p><b>Time Complexity of Quick Sort:</b> The Best and Average Case Time Complexity of Quick Sort is O(nlog(n)) where the Best Case Time Complexity is achieved when each partitioning stage
 *  divides the array exactly in half which results in a balanced recursion tree where the height of the tree is <i>log(n)</i>.  At each level of the recursion tree, <i>n</i> elements are
 *  processed which gives the Time Complexity of O(nlog(n)).  The Worst Case Time Compelxity of Quick Sort is O(n<sup>2</sup>) and occurs when the pivot is always the smallest or largest element
 *  in the array which leads to highly unbalanced partitions: one partition has <i>n - 1</i> elements and the other has <i>0</i> so the height of the recursion tree becomes <i>n</i> and at each
 *  level of the recursion tree, <i>n</i> elements are processed which gives the time complexity of O(n<sup>2</sup>).</p>
 *
 *  <p><b>Space Complexity of Quick Sort:</b> The Space Complexity is O(log(n)) in the Best and Average Cases due to the recursive call stack when the partitions are balanced, requiring a
 *  recursion tree of height <i>log(n)</i>. In the Worst Case, the Space Complexity becomes O(n) when the recursion tree height reaches <i>n</i> due to unbalanced partitions (e.g., when the pivot
 *  is always the smallest or largest element). No additional data structures are used beyond the call stack, making it an in-place sorting algorithm with respect to auxiliary space.</p>
 */

public class QuickSort {

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as the Class is Designed as a Utility Class with only Static Methods for performing
     * Quick Sort Operations.  Direct Instantiation is NOT Supported.
     */

    private QuickSort() {}

    /**
     * This method starts the Recursion when Rearranging the Array in Ascending Order.
     * @param array is the Array to be Sorted.
     */

    public static <T extends Comparable<? super T>> void sort(T[] array) {sort(array, 0, array.length - 1); assert isSorted(array);}

    /**
     * This method recursively rearranges the Array in Ascending Order, using the Natural Order and using Assertion Statments to Verify this Order.
     * @param array is the Array to be Sorted.
     * @param low is the Lower Bound Index of the Segment of the Array to Sort (inclusive).
     * @param high is the Upper Bound Index of the Segment of the Array to Sort (inclusive).
     */

    private static <T extends Comparable<? super T>> void sort(T[] array, int low, int high) {
        if (high <= low) return; int pivotIndex = partition(array, low, high); sort(array, low, pivotIndex - 1); sort(array, pivotIndex + 1, high); assert isSorted(array, low, high);
    }

    /**
     * This method starts the Recursion when Rearranging the Array where the Order is Specified by the Comparator.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     */

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {sort(array, comparator, 0, array.length - 1); assert isSorted(array, comparator);}

    /**
     * This method recursively rearranges the Array where the Order is Specified by the Comparator and Assertion Statements are used to Verify this Order.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     * @param low is the Lower Bound Index of the Segment of the Array to Sort (inclusive).
     * @param high is the Upper Bound Index of the Segment of the Array to Sort (inclusive).
     */

    private static <T> void sort(T[] array, Comparator<? super T> comparator, int low, int high) {
        if (high <= low) return; int pivotIndex = partition(array, comparator, low, high); sort(array, comparator, low, pivotIndex - 1); sort(array, comparator, pivotIndex + 1, high);
        assert isSorted(array, comparator, low, high);
    }

    /**
     * This method partitions the Array into Two Subarrays where the Objective is to Sort the Array into Ascending Order.
     * @param array is the Array to be Partitioned.
     * @param low is the Lower Bound Index of the Segment of the Array to Partition.
     * @param high is the Upper Bound Index of the Segment of the Array to Partition.
     * @return the Index of the Pivot Element after Partitioning, representing its Final Sorted Position.
     */

    private static <T extends Comparable<? super T>> int partition(T[] array, int low, int high) {
        T pivot = array[low]; int i = low; int j = high + 1; while (true) {
            while (less(array[++i], pivot)) {if (i == high) break;} while (less(pivot, array[--j])) {if (j == low) break;} if (i >= j) break; swap(array, i, j);
        } swap(array, low, j); return j;
    }

    /**
     * This method partitions the Array into Two Subarrays where the Objective is to Sort the Array based on the Order Specified by the Comparator.
     * @param array is the Array to be Partitioned.
     * @param comparator is the Comparator Specifying the Order.
     * @param low is the Lower Bound Index of the Segment of the Array to Partition.
     * @param high is the Upper Bound Index of the Segment of the Array to Partition.
     */

    private static <T> int partition(T[] array, Comparator<? super T> comparator, int low, int high) {
        T pivot = array[low]; int i = low; int j = high + 1; while (true) {
            while (less(comparator, array[++i], pivot)) {if (i == high) break;} while (less(comparator, pivot, array[--j])) {if (j == low) break;} if (i >= j) break; swap(array, i, j);
        } swap(array, low, j); return j;
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
     * This method tests the {@code QuickSort} Class by performing a Quick Sort on the Arrays of Different Types and by Sorting the Arrays in Different Ways.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {

        // Testing the Sort Method with an Array of Integers which Sorts the Array in the Natural Ascending Order.
        Integer[] integerArray = {72,83,45,67,52,64,8,0,11,46,76,34,23,147,89,90,100,10,1000,999,78,435,678}; QuickSort.sort(integerArray); show(integerArray);

        // Testing the Sort Method with an Array of Integers which Sorts the Array with a Customised Comparator in Descending Order.
        Comparator<Integer> descending = Comparator.reverseOrder(); QuickSort.sort(integerArray, descending); show(integerArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array Lexicographically in the Natural Order.
        String[] stringArray = {"Apple", "Nala", "Tigger", "Pusscat", "Spatula", "Knife", "Spoon", "Fork", "Lewis", "Ethan", "Pizza", "Diet Coke", "Bread", "Salad", "Table", "Chairs"};
        QuickSort.sort(stringArray); show(stringArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array with a Customised Comparator which Sorts the Strings based on their Length.
        Comparator<String> byLength = Comparator.comparingInt(String::length); QuickSort.sort(stringArray, byLength); show(stringArray);

        System.out.println("\n\n All Tests Passed Successfully!!!");

    }
}