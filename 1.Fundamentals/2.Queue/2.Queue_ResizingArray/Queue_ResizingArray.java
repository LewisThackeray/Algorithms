import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Queue.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Queue that doesn't Exist.

class Queue_ResizingArray<Item> implements Iterable<Item> { // This Class implements a Queue using a Resizable Array, utilising Generics to improve the Reusability of my Code.

    private static final int initialQueueCapacity = 8; // Declaring and Defining a Variable that Stores the Initial Size of the Queue.
    private Item[] queueItems; // Declaring an Array which will Store the Items in my Queue.
    private int N; // Declaring a Variable which will Store the Number of Items in the Queue.
    private int first; // Declaring a Variable which will Store the Index of the First Item in the Queue.
    private int last; // Declaring a Variable which will Store the Index of the Last Item in the Queue.

    public Queue_ResizingArray() {queueItems = (Item[]) new Object[initialQueueCapacity]; N = 0; first = 0; last = 0;} // Creating the Constructor for the Class which Defines an Array of type Item which has initialQueueCapacity elements.

    private void resize(int newCapacity) { // This method is responsible for Dynamically Changing the Size of the Array based on the Number of Items in the Queue to prevent Overflow Errors and Wasted Memory.
        assert newCapacity > N; // Using an Assertion to Check if newCapacity is >= N - if newCapacity < N then the program throws an AssertionError.
        Item[] newArray = (Item[]) new Object[newCapacity]; for (int i = 0; i < N; i++) {newArray[i] = queueItems[(first + i) % queueItems.length];} // Declaring and Defining a New Array of type Item which has newCapacity elements and then Copying the Items from the Old Array to the New Array.
        queueItems = newArray; first = 0; last = N; // Updating queueItems so that it stores the New Array and Updating the Variables which Store the Indexes of the First and Last Elements in the Queue.
    }

    public boolean isFull() {return N == queueItems.length;} // This method is responsible for Testing if the Queue is Full.  This is done by checking if the Number of Elements = the Size of the Array.

    public boolean isEmpty() {return N == 0;} // This method is responsible for Testing if the Queue is Empty.  This is done by checking if the Number of Elements = 0.

    public void enqueue(Item item) { // This method is responsible for Enqueueing an Item onto the End of the Queue.
        if (isFull()) {resize(2 * queueItems.length);} // Checking if the Queue is Full and if it is - we double the Size of the Array.
        queueItems[last++] = item; // Storing the Item in the Next Element in the Array and Updating the last Variable to Reflect the New Position of the Last Element in the Array.
        if (last + 1 == queueItems.length) {last = 0;} // If the Item Added to the Queue fills the Last Vacant Position in the Array, we set the Last Variable to 0.
        N++; // Incrementing the Variable which Stores the Number of Items in the Queue.
    }

    public Item peek() { // This method is responsible for Returning, but not Removing, the Item at the Start of the Queue.
        if (isEmpty()) {throw new NoSuchElementException("ERROR: Queue Underflow.");} return queueItems[first]; // Checking if the Queue is Empty - if it is we Throw a NoSuchElementException - otherwise we Return the Item at the Start of the Queue.
    }

    public Item dequeue() { // This method is responsible for Removing and Returning the Item at the Start of the Queue.
        if (isEmpty()) {throw new NoSuchElementException("ERROR: Queue Underflow.");} // Checking if the Queue is Empty - if it is we Throw a NoSuchElementException.
        Item item = queueItems[first]; // Defining and Declaring a Variable which Stores the Item at the Start of the Queue.
        queueItems[first] = null; // Deleting the Reference to the Removed Item in the Array allows the Java Garbage Collector to Reclaim the Memory - hence avoiding Loitering.
        N--; // Decrementing the Variable which Stores the Number of Items in the Queue.
        if (first == queueItems.length) {first = 0;} else {first++;} // If the Index of the First Item in the Queue becomes the Last Position in the Array
        return item; // Returning the Item Removed from the Front of the Queue to the Caller.
    }

    public Iterator<Item> iterator() {return new ArrayIterator();} // This method returns an Instance of the ArrayIterator Class.

    private class ArrayIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Array in FIFO Order.
        private int index = 0; // Declaring a Variable that is Used to track the Index of the Item in the Array.
        public boolean hasNext() {return index < N;} // This method is responsible for Testing if there is another element in the Queue to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Item in the Array.
            if (!hasNext()) {throw new NoSuchElementException();} // Checking whether the Iterator has reached the End of the Queue - Throwing a NoSuchElementException if it has.
            Item item = queueItems[(index + first) % queueItems.length]; index++; return item; // Returning the Next Element in the Array and then Incrementing the index Variable to Examine the Next Item.
        }
    }

    @Override // Overriding the toString() method provided in the Superclass as I want to provide an Implementation Specific to my Queue.
    public String toString() { // This method is responsible for Representing the Queue as a String to make the Operations on the Queue easier to understand during Debugging and Testing.
        StringBuilder myStringBuilder = new StringBuilder(); // Creating an Object of the StringBuilder Class to Create the String.
        if (!isEmpty()) { // If the Queue is not Empty - we can Create a String Representation of the Queue.
            myStringBuilder.append("START => |"); for (int i = 0; i < N; i++) {myStringBuilder.append(queueItems[i]).append("|");} myStringBuilder.append(" <= END\n"); // Iterating through the Queue to Create an Accurate Representation, Clearly Marking the Start and End of the Queue.
        }
        return myStringBuilder.toString(); // Returning the Representation String of the Queue back to the Caller.
    }

    public static void main(String[] args) { // Creating some Unit Tests to Test the Queue_ResizingArray Class.
        Queue_ResizingArray<String> cars = new Queue_ResizingArray<String>(); // Creating an Object of the Queue_ResizingArray Class of Type String.
        cars.enqueue("Volvo"); cars.enqueue("Mercedes"); cars.enqueue("Nissan"); // Enqueueing Items onto the Queue.
        System.out.println("Front Element: " + cars.peek()); // Peeking the Item at the Start of the Queue.
        System.out.println("Cars Queue: \n" + cars); // Displaying the Queue after the Items have been Enqueued.

        System.out.println("Dequeued: " + cars.dequeue()); System.out.println("Dequeued: " + cars.dequeue()); System.out.println("Dequeued: " + cars.dequeue()); // Dequeueing the Three Items Off the Queue.
    }
}