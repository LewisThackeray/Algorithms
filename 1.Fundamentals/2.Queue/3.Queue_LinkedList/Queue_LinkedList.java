import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Queue.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Queue that doesn't Exist.

class Queue_LinkedList<Item> implements Iterable<Item> { // This Class implements a Queue using a Linked List, utilising Generics to improve the Reusability of my Code.

    private int N; // Declaring a Variable which will Store the Number of Items in my Queue.
    private Node first; // Declaring a Variable which will Store the Item at the Front of the Queue (the First Node in the Linked List).
    private Node last; // Declaring a Variable which will Store the Item at the Back of the Queue (the Last Node in the Linked List).

    private class Node {private Item item; private Node next;} // Creating an Inner-Class used to Represent a Node in the Linked List, where the Linked List is Singly-Linked.

    private boolean check() { // This method is responsible for ensuring that the Queue maintains its Inegrity and Invariants during its Operation by Performing Checks to Validate the State of the Queue.
        if (N < 0) return false; // Checking the Number of Items in the Queue is not Negative - Returning false if it is.
        if (N == 0) return first == null && last == null; // Checking if the Number of Items in the Queue = 0 - if it does then we Return true if both first and last are null in an Empty List.
        if (N == 1) return first != null && last != null && first == last && first.next == null; // Checking if the Number of Items in the Queue = 1 - if it does then we Return true if the first and last are not null, they point to the same Node, and the Node should not have a next Element.
        if (first == null || last == null || first == last || first.next == null || last.next != null) return false; // Checking that the Structure of the Linked List remains Consistent - and Returning false if there are any Inconsistencies.
        return true; // If there are no Internal Invariants, true is Returned to the Caller.
    }

    public Queue_LinkedList() {first = null; last = null; N = 0; assert check();} // Creating the Constructor for the Class which Assigns Values to the Class Attributes and uses an Assertion to Check for Internal Invariants.

    public boolean isEmpty() {return first == null;} // This method is responsible for Testing if the Queue is Empty.  This is done by checking if the First Node in the Linked List = null.

    public void enqueue(Item item) { // This method is responsible for Enqueueing an Item onto the Back of the Queue.
        Node oldLast = last; last = new Node(); last.item = item; last.next = null; // Adding a New Item to the Linked List used to Represent the Data in the Queue.
        if (isEmpty()) {first = last;} else {oldLast.next = last;} // If this is the First and Only Node in the Linked List the first and last Variables should both store the same Node, otherwise the New Node should be added to the End of the Linked List.
        N++; // Incrementing the Variable which Stores the Number of Items in the Queue (the Number of Nodes in the Linked List).
        assert check(); // Using an Assertion to Check for Internal Invariants.
    }

    public Item peek() { // This method is responsible for Returning, but not Removing, the Item at the Front of the Queue.
        if (isEmpty()) {throw new NoSuchElementException("ERROR: Stack Underflow.");} return first.item; // Checking if the Queue is Empty - if it is we Throw a NoSuchElementException - otherwise we Return the Item at the Front of the Queue (the Data of the First Node in the Linked List).
    }

    public Item dequeue() { // This method is responsible for Removing and Returning the Item at the Front of the Queue.
        if (isEmpty()) throw new NoSuchElementException("ERROR: Stack Underflow."); // Checking if the Queue is Empty - if it is we Throw a NoSuchElementException.
        Item item = first.item; // Declaring and Defining a Variable that Stores the Item at the Front of the Queue.
        first = first.next; // Updating the first Variable to Store the Next Node in the Linked List, therefore, the Second Item in the Linked List now becomes the First Item in the Linked List, and the Java Garbage Collector Reclaims the Memory Associated with the First Node.
        N--; // Decrementing the Variable which Stores the Number of Items in the Queue.
        if (isEmpty()) last = null; // If the only element in the Queue is Dequeued then the value of the last Variable is set to null to allow the Java Garbage Collector to Reclaim the Memory avoiding Loitering.
        assert check(); // Using an Assertion to Check for Internal Invariants.
        return item; // Returning the Item at the Front of the Queue to the Caller.
    }

    public Iterator<Item> iterator() {return new LinkedListIterator();} // This method returns an Instance of the LinkedListIterator Class.

    private class LinkedListIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Linked List in FIFO Order.
        private Node current = first; // Declaring and Defining a Variable to store the Current Node being Examined in the Linked List.
        public boolean hasNext() {return current != null;} // This method is responsible for Testing if there is another Item in the Stack to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Item in the Stack.
            if (!hasNext()) throw new NoSuchElementException(); // Checking if the Iterator has reached the End of the Queue - if it has we Throw a NoSuchElementException.
            Item item = current.item; current = current.next; return item; // Getting the Item in the Queue and Updating the current Variable to Point to the Next Node in the Linked List.
        }
    }

    @Override // Overriding the toString() method provided in the Superclass as I want to provide an Implementation Specific to my Queue.
    public String toString() { // This method is responsible for Representing the Queue as a String to make the Operations on the Queue easier to understand during Debugging and Testing.
        StringBuilder myStringBuilder = new StringBuilder(); // Creating an Object of the StringBuilder Class to Create the String.
        if (!isEmpty()) { // If the Queue is not Empty - we can Create a String Representation of the Queue.
            int count = 0; // Declaring and Defining a Variable to Store a Count, allowing us to Track the Number of Elements that we have Iterated through in the Queue.
            for (Item item : this) { // Iterating through the Queue creating the Representation.
                if (count == 0) {myStringBuilder.append("START => |").append(item).append("|");} else if (count == N - 1) {myStringBuilder.append(item).append("| <= END\n");} else {myStringBuilder.append(item).append("|");} count++; // Iterating through the Queue to Create an Accurate Representation, Clearly Marking the Start and End of the Queue.  Remembering to Increment the Count after each Iteration.
            }
        } return myStringBuilder.toString(); // Returning the Representation String of the Queue back to the Caller.
    }

    public static void main(String[] args) { // Creating some Unit Tests to Test the Queue_ResizingArray Class.
        Queue_LinkedList<String> cars = new Queue_LinkedList<String>(); // Creating an Object of the Queue_LinkedList Class of Type String.
        cars.enqueue("Volvo"); cars.enqueue("Mercedes"); cars.enqueue("Nissan"); // Enqueueing Items onto the Queue.
        System.out.println("Front Element: " + cars.peek()); // Peeking the Item at the Start of the Queue.
        System.out.println("Cars Queue: \n" + cars); // Displaying the Queue after the Items have been Enqueued.

        System.out.println("Dequeued: " + cars.dequeue()); System.out.println("Dequeued: " + cars.dequeue()); System.out.println("Dequeued: " + cars.dequeue()); // Dequeueing the Three Items Off the Queue.
    }
}