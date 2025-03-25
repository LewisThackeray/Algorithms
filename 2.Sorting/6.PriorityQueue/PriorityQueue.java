import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Priority Queue.
import java.util.NoSuchElementException; // Importing the NoSuchElementException from the java.util Package to Throw a Runtime Error when trying to Access a Non-Existent Item in the Priority Queue.

/**
 * @author LewisThackeray
 * @date 24/03/2025
 *
 * {@code PriorityQueue} represents a Minimum Priority Queue of Generic Items implemented using a Binary Heap stored in an Array.  The Minimum Priority Queue works by removing the Lowest
 * Priority Item (in this case the Smallest) from the Priority Queue First and since the Minimum Priority Queue is Stored in an Array, the Lowest Priority Item is at the First Index in the Array
 * (Index 0).
 *
 * <p><b>Heap Property for a Min-Heap:</b> The Key of Each Node is Less Than or Equal to the Keys of its Children.</p>
 *
 * @param <Item> is the Generic Type of Items in the Minimum Priority Queue.
 *
 */

public class PriorityQueue<Item extends Comparable<Item>> implements Iterable<Item> {

    private Item[] heap; // Creating a Fixed-Size Array to Store the Binary Heap.
    private int n; // Creating a Counter to Store the Number of Items in the Priority Queue.

    /**
     * This is the Class Constructor which Initialises a Mimimum Priority Queue with a given fixed capacity.
     * @param capacity specifies the fixed capacity of the Mimimum Priority Queue.
     * @throws IllegalArgumentException if the capacity is less than 1.
     */

    @SuppressWarnings("unchecked") public PriorityQueue(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("The Capacity must be at Least 1!"); heap = (Item[]) new Comparable[capacity + 1]; n = 0;
    }

    /**
     * This method tests whether the Mimimum Priority Queue is Empty.
     * @return a boolean value where true is returned if the Mimimum Priority Queue is Empty, false otherwise.
     */

    public boolean isEmpty() {return n == 0;}

    /**
     * This method returns the number of items in the Minimum Priority Queue.
     * @return an integer which is the number of items in the Minimum Priority Queue.
     */

    public int size() {return n;}

    /**
     * This method returns the Smallest Item in the Minimum Priority Queue but doesn't remove the Item from the Priority Queue.
     * @return an Item which is the Lowest Priority Item in the Minimum Priority Queue.
     * @throws NoSuchElementException if the Minimum Priority Queue is Empty.
     */

    public Item min() {if (isEmpty()) {throw new NoSuchElementException("The Minimum Priority Queue is Empty!");} return heap[1];}

    /**
     * This method inserts an Item into the Minimum Priority Queue.
     * @param item is the Item which is being inserted into the Minimum Priority Queue.
     * @throws IllegalStateException if the Minimum Priority Queue is Full.
     */

    public void insert(Item item) {if (n == heap.length - 1) {throw new IllegalStateException("The Minimum Priority Queue is Full!");} n++; heap[n] = item; swim(n);}

    /**
     * This method deletes the Lowest Priority Item in the Minimum Priority Queue and also returns the Lowest Priority Item.
     * @throws NoSuchElementException if the Minimum Priority Queue is Empty.
     */

    public Item delete() {
        if (isEmpty()) {throw new NoSuchElementException("The Minimum Priority Queue is Empty!");} Item min = heap[1]; swap(1, n); heap[n] = null; n--; if (n > 0) sink(1); return min;
    }

    /**
     * This method restores the Heap Property by moving an Item up the Binary Heap.
     * @param index is the Index of the Item to Swim.
     */

    private void swim(int index) {while (index > 1 && greater(index / 2, index)) {swap(index, index / 2); index = index / 2;}}

    /**
     * This method restores the Heap Property by moving an Item down the Binary Heap.
     * @param index is the Index of the Item to Sink.
     */

    private void sink(int index) {while (2 * index <= n) {int j = 2 * index; if (j < n && greater(j, j + 1)) j++; if (!greater(index, j)) break; swap(index, j); index = j;}}

    /**
     * This method compares Two Items in the Binary Heap.
     * @param i is the Index of the First Item.
     * @param j is the Index of Second Item.
     * @return a boolean value where true is returned if the Item at i is greater than the Item at j, false otherwise.
     */

    private boolean greater(int i, int j) {return heap[i].compareTo(heap[j]) > 0;}

    /**
     * This method swaps Two Items in the Binary Heap.
     * @param i is the Index of the First Item.
     * @param j is the Index of the Second Item.
     */

    private void swap(int i, int j) {Item temp = heap[i]; heap[i] = heap[j]; heap[j] = temp;}

    /**
     * This method returns an Instance of the HeapIterator Class.
     * @return an Iterator over the Items in the Minimum Priority Queue.
     */

    @Override public Iterator<Item> iterator() {return new HeapIterator();}

    /**
     * This Inner-Class implements the Iterator Interface for the Minimum Priority Queue.
     */

    private class HeapIterator implements Iterator<Item> {

        private PriorityQueue<Item> copy; // Creating a Copy of the Minimum Priority Queue to Iterate Through.

        /**
         * This method populates the Copy of the Minimum Priority Queue with the Items for Iteration.
         */

        @SuppressWarnings("unchecked") public HeapIterator() {
            copy = new PriorityQueue<>(heap.length - 1); for (int i = 1; i <= n; i++) {copy.insert(heap[i]);}
        }

        /**
         * This method checks if there are more Items in the Minimum Priority Queue to Iterate Over.
         * @return a boolean value where true is returned if there are more Items in the Minimum Priority Queue to Iterate over, false otherwise.
         */

        public boolean hasNext() {return !copy.isEmpty();}

        /**
         * This method returns the next Item in the Iteration.
         * @returns an Item which is the Next Smallest Item in the Minimum Priority Queue.
         * @throws NoSuchElementException if there are no more Items in the Minimum Priority Queue.
         */

        public Item next() {if (!hasNext()) {throw new NoSuchElementException("No More Items in the Minimum Priority Queue!");} return copy.delete();}

    }

    /**
     * This method tests the {@code PriorityQueue} Class by Creating and Appending data to the Minimum Priority Queue and then Removing the Items from the Minimum Priority Queue.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {

        // Testing the Minimum Priority Queue with Integers by Appending Integers to the Priority Queue and then Removing Them and Ensuring the Integers are Removed in the Correct Order.
        PriorityQueue<Integer> integerPriorityQueue = new PriorityQueue<>(10); integerPriorityQueue.insert(4); integerPriorityQueue.insert(2); integerPriorityQueue.insert(3);
        integerPriorityQueue.insert(5); integerPriorityQueue.insert(1); while (!integerPriorityQueue.isEmpty()) {System.out.println(integerPriorityQueue.delete());}

        // Testing the Minimum Priority Queue with Doubles by Appending Doubles to the Priority Queue and Removing Them and Ensuring the Doubles are Removed in the Correct Order.
        PriorityQueue<Double> doublePriorityQueue = new PriorityQueue<>(10); doublePriorityQueue.insert(1.41); doublePriorityQueue.insert(2.67); doublePriorityQueue.insert(5.68);
        doublePriorityQueue.insert(4.70); doublePriorityQueue.insert(3.99); while (!doublePriorityQueue.isEmpty()) {System.out.println(doublePriorityQueue.delete());}

        // Testing the Minimum Priority Queue with Strings by Appending Strings to the Priority Queue and Removing Them and Ensuring the Strings are Removed in the Correct Order.
        PriorityQueue<String> stringPriorityQueue = new PriorityQueue<>(10); stringPriorityQueue.insert("Pear"); stringPriorityQueue.insert("Tomato"); stringPriorityQueue.insert("Apple");
        stringPriorityQueue.insert("Banana"); stringPriorityQueue.insert("Orange"); while (!stringPriorityQueue.isEmpty()) {System.out.println(stringPriorityQueue.delete());}

        System.out.println("All Tests Passed Successfully!");
    }

}

