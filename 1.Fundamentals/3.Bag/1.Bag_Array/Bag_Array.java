import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Bag.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Bag that doesn't Exist.

public class Bag_Array<Item> implements Iterable<Item> { // This Class implements a Bag using an Array, utilising Generics to improve the Reusability of my Code.

    private static final int maxCapacity = 10; // Declaring and Defining a Variable that Stores the Maximum Number of Items which can be Stored in the Array.
    private Item[] bagItems; // Decarling an Array which will Store the Items in my Bag.
    private int N; // Declaring a Variable which will Store the Number of Items in the Bag.

    public Bag_Array() {bagItems = (Item[]) new Object[maxCapacity]; N = 0;} // Creating the Constructor of the Class which Defines an Array of type Item which has maxCapacity Elements and Initialises the Variable Storing the Number of Elements in the Array to 0.

    public boolean isFull() {return N == maxCapacity;} // This method is responsible for Testing if the Bag is Full.  This is done by checking if the Number of Elements = the Maximum Number of Elements in the Array.

    public boolean isEmpty() {return N == 0;} // This method is responsible for Testing if the Bag is Empty.  This is done by checking if the Number of Elements = 0.

    public void add(Item item) { // This method is responsible for Adding an Element to the Bag.
        if (isFull()) {throw new NoSuchElementException("ERROR: Bag Overflow - You cannot Add an Element to a Bag that is Full.");} // Checking if the Bag is Full and if it is we Throw a NoSuchElementException.
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

    public static void main(String[] args) { // Creating some Unit Tests to Test the Bag_Array Class.
        Bag_Array<String> cars = new Bag_Array<String>(); // Creating an Object of the Bag_Array Class of type String.
        cars.add("Volvo"); cars.add("Mercedes"); cars.add("Nissan"); // Adding Items to the Bag.

        for (String s : cars) System.out.println(s); // Displaying the Bag after the Items have been Added.
    }
}