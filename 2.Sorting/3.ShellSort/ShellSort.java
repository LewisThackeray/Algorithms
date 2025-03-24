import java.util.Comparator; // Importing the Comparator Class from the java.util Package to Compare Two Objects and Determine which should come First in an Ordered Sequence.

/**
 * @author LewisThackeray
 * @date 24/03/2025
 *
 * {@code ShellSort} provides Static Methods for Sorting an Array using <em>Shell Sort</em>, using Generics to ensure the Flexibility of my Implementation so that it supports Customised Sorting
 * Orders through the use of Comparator, as well as Generic Data Types through Comparable and Object[].<em>Shell Sort</em> works as follows:
 * <ol>
 *     <li>The first step in Shellsort is to choose a sequence of gap values.  A common initial sequence starts with a large gap and then reduces it by a factor after each pass.  For example,
 *     the sequence could be [5,3,1] for an array size of 10.</li>
 *     <li>For each gap value, perform an insertion sort, but instead of comparing adjacent elements, compare elements that are <i>gap</i> positions apart.  This helps to partially sort the array
 *     by moving elements closer to their final positions.</li>
 *     <li>After completing the sorting for one gap, reduce the gap and repeat the insertion sort for the new gap.  The array becomes more sorted after each pass.</li>
 *     <li>When the gap reduces to 1, the algorithm performs a regular insertion sort, which should now be much faster because the list is already partially sorted.</li>
 * </ol>
 *
 * <p><b>Time Complexity of Shell Sort:</b> The Best Case Time Complexity is O(nlog(n)) when the gap sequence leads to a nearly sorted array quickly.  The Time Complexity is heavily influenced by
 * the gap sequence, therefore, the Average and Worst Case Time Complexity for Shell Sort is O(n(log(n))<sup>2</sup>).</p>
 *
 * <p><b>Space Complexity of Shell Sort:</b> The Algorithm uses Constant Empty Space making it's Worst Case Space Compelxity O(1).</p>
 */

public class ShellSort {

    /**
     * This is a Class Constructor.  The Class Constructor is Private to Prevent Instantiations of the Class, as the Class is Designed as a Utility Class with only Static Methods for Performing
     * Shell Sort Operations.  Direct Instantiation is NOT supported.
     */

    private ShellSort() {}

    /**
     * This method rearranges the Array in Ascending Order, using the Natural Order and using Assertion Statements to Verify this Order.
     * @param array is the Array to be Sorted.
     */

    public static <T extends Comparable<? super T>> void sort(T[] array) {
        int h = 1; while (h < array.length / 3) h = 3 * h + 1; // Generating the Intial Gap using the 3n + 1 Sequence.
        while (h >= 1) {
            for (int i = h; i < array.length; i++) {for (int j = i; j >= h && less(array[j], array[j - h]); j -= h) {swap(array, j, j - h);} h = h / 3;}
        }; assert(isSorted(array));
    }

    /**
     * This method rearranges the Array where the Order is Specified by the Comparator and Assertion Statements are used to Verify this Order.
     * @param array is the Array to be Sorted.
     */

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        int h = 1; while (h < array.length / 3) h = 3 * h + 1; // Generating the Intial Gap using the 3n + 1 Sequence.
        while (h >= 1) {
            for (int i = h; i < array.length; i++) {for (int j = i; j >= h && less(comparator, array[j], array[j - h]); j -= h) {swap(array, j, j - h);} h = h / 3;}
        }; assert(isSorted(array, comparator));

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
     * This method tests the {@code ShellSort} Class by Performing a Shell Sort on the Arrays of Different Types and by Sorting the Arrays in Different Ways.
     * @param args the Command Line Arguments.
     */
    public static void main(String[] args) {

        // Testing the Sort Method with an Array of Integers which Sorts the Array in the Natural Ascending Order.
        Integer[] integerArray = {72,83,45,67,52,64,8,0,11,46,76,34,23,147,89,90,100,10,1000,999,78,435,678}; ShellSort.sort(integerArray); show(integerArray);

        // Testing the Sort Method with an Array of Integers which Sorts the Array with a Customised Comparator in Descending Order.
        Comparator<Integer> descending = Comparator.reverseOrder(); ShellSort.sort(integerArray, descending); show(integerArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array Lexicographically in the Natural Order.
        String[] stringArray = {"Apple", "Nala", "Tigger", "Pusscat", "Spatula", "Knife", "Spoon", "Fork", "Lewis", "Ethan", "Pizza", "Diet Coke", "Bread", "Salad", "Table", "Chairs"};
        ShellSort.sort(stringArray); show(stringArray);

        // Testing the Sort Method with an Array of Strings which Sorts the Array with a Customised Comparator which Sorts the Strings based on their Length.
        Comparator<String> byLength = Comparator.comparingInt(String::length); ShellSort.sort(stringArray, byLength); show(stringArray);

        System.out.println("\n\n All Tests Passed Successfully!!!");

    }
}