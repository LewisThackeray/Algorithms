import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Bag.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Bag that doesn't Exist.

public class Bag_ResizingArray<Item> implements Iterable<Item> { // This Class implements a Bag using a Resizable Array, utilising Generics to improve the Reusability of my Code.

    private static final int initialBagCapacity = 8; // Declaring and Defining a Variable that Stores the Initial Size of the Bag.
    private Item[] bagItems; // Declaring an Array which will Store the Items in my Bag.
    private int N; // Declaring a Variable which will Store the Number of Items in the Bag.

    public Bag_ResizingArray() {bagItems = (Item[]) new Object[initialBagCapacity]; N = 0;} // Creating the Constructor for the Class which Defines an Array of type Item which has initialBagCapacity elements and Defines the Current Number of Items in the Bag.

    public void resize(int newCapacity) { // This method is responsible for Dynamically Changing the Size of the Array based on the Number of Items in the Bag to prevent Overflow Errors and Wasted Memory.
        assert newCapacity > N; // Using an Assertion to Check if newCapacity is >= N - if newCapacity < N then the program throws an AssertionError.
        Item[] newArray = (Item[]) new Object[newCapacity]; for (int i = 0; i < N; i++) {newArray[i] = bagItems[i];} // Declaring and Defining a New Array of type Item which has newCapacity elements and then Copying the Items from the Old Array to the New Array.
        bagItems = newArray; // Updating bagItems so that it stores the New Array.
    }

    public boolean isFull() {return N == bagItems.length;} // This method is responsible for Testing if the Bag is Full.  This is done by checking if the Number of Elements = the Size of the Array.

    public boolean isEmpty() {return N == 0;} // This method is responsible for Testing if the Bag is Empty.  This is done by checking if the Number of Elements = 0.

    public void add(Item item) { // This method is responsible for Adding an Element to the Bag.
        if (isFull()) resize(2 * bagItems.length); // Checking if the Bag is Full and if it is - we double the Size of the Array.
        bagItems[N++] = item; // Storing the Item in the Next Element in the Array and Incrementing N which Stores the Number of Elements in the Bag.
    }

    public Iterator<Item> iterator() {return new ArrayIterator();} // This method returns an Instance of the ArrayIterator Class.

    private class ArrayIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Array in any Order.
        private int index = 0; // Declaring a Variable that is Used to track the Index of the Element in the Array.
        public boolean hasNext() {return index < N;} // This method is responsible for Testing if there is another element in the Bag to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Element in the Array.
            if (!hasNext()) throw new NoSuchElementException(); // Checking whether the Iterator has reached the End of the Queue - Throwing a NoSuchElementException if it has.
            return bagItems[index++]; // Returning the Element in the Array and then Incrementing the index Variable to Examine the Next Element.
        }
    }

    public static void main(String[] args) { // Creating some Unit Tests to Test the Bag_ResizingArray Class.
        Bag_ResizingArray<String> cars = new Bag_ResizingArray<String>(); // Creating an Object of the Bag_ResizingArray Class of type String.
        cars.add("Volvo"); cars.add("Mercedes"); cars.add("Nissan"); // Adding Items to the Bag.

        for (String s : cars) System.out.println(s); // Displaying the Bag after the Items have been Added.

    }

}