import java.util.Comparator; // Importing the Comparator Class from the java.util Package to Compare Two Objects and Determine which should come First in an Ordered Sequence.

/**
 * @author LewisThackeray
 * @date 25/03/2025
 *
 * {@code HeapSort} provides Static Methods for Sorting an Array using <em>Heap Sort</em>, using Generics to ensure the Flexibility of my Implementation so that is supports Customised Sorting
 * Orders through the use of Comparator, as well as Generic Data Types through Comparable and Object[].  <em>Heap Sort</em> works as follows:
 * <ol>
 *     <li>First, transform the array into a max-heap - a binary tree where the key of each node is greater than or equal to the keys of its children.</li>
 *     <li>Repeatedly extract the maximum element from the heap and place it at the end of the array, reducing the heap size.</li>
 *     <li>Restore the heap property after each extraction until the array is fully sorted.</li>
 * </ol>
 *
 * <p><b>Time Complexity of Heap Sort:</b> The Best, Average and Worst Case Time Complexity is O(nlog(n)), as it performs heapification and extraction in logarithmic time per element.</p>
 *
 * <p><b>Space Complexity of Heap Sort:</b> The Algorithm uses Constant Extra Space, making its Worst-Case Space Complexity O(1).</p>
 */

public class HeapSort {

    /**
     * This is the Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as this Class is Designed as a Utility Class with only Static Methods for performing
     * Heap Sort Operations.  Direct Instantiation is NOT Supported.
     */

    private HeapSort() {}

    /**
     * This method rearranges the Array in Ascending Order, using the Natural Order and using Assertion Statements to Verify this Order.
     * @param array is the Array to be Sorted.
     */

    public static <T extends Comparable<? super T>> void sort(T[] array) {
        for (int k = array.length / 2; k >= 1; k--) {sink(array, k, array.length);} assert isHeap(array, array.length); // Heapify Phase.
        int k = array.length; while (k > 1) {swap(array, 1, k--); sink(array, 1, k); assert isSorted(array, k, array.length - 1);} assert isSorted(array); // Sortdown Phase.
    }

    /**
     * This method rearranges the Array where the Order is Specified by the Comparator and Assertion Statements are used to Verify this Order.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     */

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        for (int k = array.length / 2; k >= 1; k--) {sink(array, comparator, k, array.length);} assert isHeap(array, comparator, array.length); // Heapify Phase.
        int k = array.length; while (k > 1) {swap(array, 1, k--); sink(array, comparator, 1, k); assert isSorted(array, comparator, k, array.length - 1);} assert isSorted(array, comparator); // Sortdown Phase.
    }

    /**
     * This method sinks an Element in the Heap to Restore the Heap Property for the Max-Heap, using Natural Ordering.
     * @param array is the Array to be Sorted.
     * @param index is the Index to Sink from (where the Root of the Heap is at Index 1, therefore, for any node at Index k: the Left Child is at Index 2k, the Right Child is at Index 2k + 1 and the Parent is
     * at Index k/2.
     * @param n is the Size of the Heap.
     */

    private static <T extends Comparable<? super T>> void sink(T[] array, int index, int n) {
        while (2 * index <= n) {int j = 2 * index; if (j < n && less(array[j - 1], array[j])) {j++;} if (!less(array[index - 1], array[j - 1])) {break;} swap(array, index, j); index = j;}
    }

    /**
     * This method sinks an Element in the Heap to Restore the Heap Property for the Max-Heap, where the Order is Specified by the Comparator.
     * @param array is the Array to be Sorted.
     * @param comparator is the Comparator Specifying the Order.
     * @param index is the Index to Sink from (where the Root of the Heap is at Index 1, therefore, for any node at Index k: the Left Child is at Index 2k, the Right Child is at Index 2k + 1 and the Parent is
     * at Index k/2.
     * @param n is the Size of the Heap.
     */

    private static <T> void sink(T[] array, Comparator<? super T> comparator, int index, int n) {
        while (2 * index <= n) {
            int j = 2 * index; if (j < n && less(comparator, array[j - 1], array[j])) {j++;} if (!less(comparator, array[index - 1], array[j - 1])) {break;} swap(array, index, j); index = j;
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
     * This method swaps Two Elements in the Array.
     * @param array is the Array in which the Swap Occurs.
     * @param i is the Index of the First Element that we are Going to Swap.
     * @param j is the Index of the Second Element that we are Going to Swap.
     */

    private static <T> void swap(T[] array, int i, int j) {T swap = array[i - 1]; array[i - 1] = array[j - 1]; array[j - 1] = swap;}

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
     * This method checks if an Array of Size n Satisfies the Max-Heap Property using a Natural Order.
     * @param array is the Array to Check.
     * @param n is the Size of the Heap.
     * @return a boolean value where true is returned if the Max-Heap Property is Satisfied, false otherwise.
     */

    private static <T extends Comparable<? super T>> boolean isHeap(T[] array, int n) {
        for (int i = 1; i <= n / 2; i++) {
            int left = 2 * i; int right = left + 1; if (left <= n && less(array[i - 1], array[left - 1])) {return false;} if (right <= n && less(array[i - 1], array[right - 1])) {return false;}
        } return true;
    }

    /**
     * This method checks if an Array of Size n Satisfies the Max-Heap Property using a Comparator.
     * @param array is the Array to Check.
     * @param comparator is the Comparator specifying the Order.
     * @param n is the Size of the Heap.
     * @return a boolean value where true is returned if the Max-Heap Property is Satisfied, false otherwise.
     */

    private static <T> boolean isHeap(T[] array, Comparator<? super T> comparator, int n) {
        for (int i = 1; i <= n / 2; i++) {
            int left = 2 * i; int right = left + 1;
            if (left <= n && less(comparator, array[i - 1], array[left - 1])) {return false;} if (right <= n && less(comparator, array[i - 1], array[right - 1])) {return false;}
        } return true;
    }

    /**
     * This method tests the {@code HeapSort} Class by performing a Heap Sort on the Arrays of Different Types and by Sorting the Arrays in Different Ways.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {

        // Testing the Sort Method with an Array of Integers which Sorts the Array in the Natural Ascending Order.
        Integer[] integerArray = {72,83,45,67,52,64,8,0,11,46,76,34,23,147,89,90,100,10,1000,999,78,435,678}; HeapSort.sort(integerArray); show(integerArray);

        // Testing the Sort Method with an Array of Integers which Sorts the Array with a Customised Comparator in Descending Order.
        Comparator<Integer> descending = Comparator.reverseOrder(); HeapSort.sort(integerArray, descending); show(integerArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array Lexicographically in the Natural Order.
        String[] stringArray = {"Apple", "Nala", "Tigger", "Pusscat", "Spatula", "Knife", "Spoon", "Fork", "Lewis", "Ethan", "Pizza", "Diet Coke", "Bread", "Salad", "Table", "Chairs"};
        HeapSort.sort(stringArray); show(stringArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array with a Customised Comparator which Sorts the Strings based on their Length.
        Comparator<String> byLength = Comparator.comparingInt(String::length); HeapSort.sort(stringArray, byLength); show(stringArray);

        System.out.println("\n\n All Tests Passed Successfully!!!");

    }

}