import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Stack.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Iterm in the Stack that doesn't Exist.

class Stack_LinkedList<Item> implements Iterable<Item> { // This Class implements a Stack using a Linked List, utilising Generics to improve the Reusability of my Code.

    private int N; // Declaring a Variable which will Store the Number of Items in my Stack.
    private Node first; // Declaring a Variable of type Node which stores the Node at the Top of the Stack.

    private class Node {private Item item; private Node next;} // Creating an Inner-Class used to Represent a Node in the Linked List, where the Linked List is Singly-Linked.

    private boolean check() { // This method is responsible for ensuring that the Stack maintains its Integrity and Invariants during its Operation by Performing Checks to Validate the State of the Stack.
        if ((N == 0 && first != null) || (N > 0 && (first == null || (N == 1 && first.next != null)))) return false; // Checking the Size of the Stack and Verifying the Consistency of the Nodes in the Stack - if there are Inconsistencies, then false is Returned.
        int numberOfNodes = 0; for (Node i = first; i != null; i = i.next) {numberOfNodes++; if (numberOfNodes > N) return false;} // Counting the Number of Nodes in the Linked List to Enusre the Number of Nodes is Equal to N.
        return numberOfNodes == N; // If the value of N is correct, then true is Returned from the method.
    }

    public Stack_LinkedList() {first = null; N = 0; assert check();} // Creating the Constructor for the Class which Assigns Values to the Class Attributes and uses an Assertion to Check for Internal Invariants.

    public boolean isEmpty() {return first == null;} // This method is responsible for Testing if the Stack is Empty.  This is done by checking if the Node at the Top of the Stack is null.

    public void push(Item item) { // This method is responsible for Pushing a New Items onto the Top of the Stack.
        Node old = first; first = new Node(); first.item = item; first.next = old; N++; assert check();  // Adding a New Item to the Linked List used to Represent the Data in the Stack and using an Assertion to Check for Internal Invariants.
    }

    public Item peek() { // This method is responsible for Returning, but not Removing, the Top Item of the Stack.
        if (isEmpty()) {throw new NoSuchElementException("ERROR: Stack Underflow.");} return first.item; // Checking if the Stack is Empty - if it is we Throw a NoSuchElementException - otherwise we Return the Data in the First Node of the Linked List (the Item at the Top of the Stack).
    }

    public Item pop() { // This method is responsible for Removing and Returning the Top Item of the Stack.
        if (isEmpty()) {throw new NoSuchElementException("ERROR: Stack Underflow.");} // Checking if the Stack is Empty - if it is we Throw a NoSuchElementException.
        Item item = first.item; // Defining and Declaring a Variable which Stores the Top Item of the Stack.
        first = first.next; // Updating the first Variable to Store the Next Node in the Linked List, therefore, the Second Item in the Linked List now becomes the First Item in the Linked List, and the Java Garbage Collector Reclaims the Memory Associated with the First Node.
        N--; // Decrementing the Variable which Stores the Number of Items in the Stack.
        assert check(); // Using an Assertion to Check for Internal Invariants.
        return item; // Returning the Item we Removed from the Top of the Stack.
    }

    @Override // Overriding the toString() method provided in the Superclass as I want to provide an Implementation Specific to my Stack.
    public String toString() { // This method is responsible for Representing the Stack as a String to make the Operations on the Stack easier to understand during Debugging and Testing.
        StringBuilder myStringBuilder = new StringBuilder(); // Creating an Object of the StringBuilder Class to Create the String.
        if (!isEmpty()) { // Provided the Stack is Not Empty, we can Create a String Representation of the Stack.
            int count = 0; // Declaring and Defining a Variable to Store a Count, allowing us to Track the Number of Elements that we have Iterated through in the Stack.
            for (Item item : this) { // Iterating through the Stack creating the Representation.
                if (count == 0) {myStringBuilder.append("|").append(item).append("| => TOP OF THE STACK \n");} else if (count == N - 1) {myStringBuilder.append("|").append(item).append("| => BOTTOM OF THE STACK \n");} // Using Selection Statements to Clearly Mark the Top and Bottom of the Stack.
                else {myStringBuilder.append("|").append(item).append("|\n");} // If the Item is not the Top or Bottom Item - we do not need to Mark the Item.
                count++; // Incrementing the Counter allowing us to Check when we are Examining the Top or Bottom Items in the Stack so that we can Mark them accordingly.
            }
        } return myStringBuilder.toString(); // Returning the Representation String of the Linked List back to the Caller.
    }

    public Iterator<Item> iterator() {return new ReverseLinkedListIterator();} // This method Returns as Instance of the ReverseLinkedListIterator Class.

    private class ReverseLinkedListIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Linked List in LIFO Order.
        private Node current = first; // Declaring and Defining a Variable to store the Current Node being Examined in the Linked List.
        public boolean hasNext() {return current != null;} // This method is responsible for Testing if there is another Item in the Stack to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Item in the Stack.
            if (!hasNext()) {throw new NoSuchElementException();} // Checking if the Iterator has reached the End of the Stack - if it has we Throw a NoSuchElementException.
            Item item = current.item; current = current.next; return item; // Getting the Item in the Stack and Updating the current Variable to Point to the Next Node in the Linked List.
        }
    }
    public static void main(String[] args) { // Creating some Unit Tests to Test the Stack_LinkedList Class.
        Stack_LinkedList<String> cars = new Stack_LinkedList<String>(); // Creating an Object of the Stack_LinkedList Class of Type String.
        cars.push("Volvo"); cars.push("Mercedes"); cars.push("Nissan"); // Pushing Items onto the Stack.
        System.out.println("Top Element: " + cars.peek()); // Peeking the Top Item of the Stack.
        System.out.println("Cars Stack: \n" + cars); // Displaying the Stack after the Items have been Pushed onto the Stack.
        System.out.println("Popped: " + cars.pop()); System.out.println("Popped: " + cars.pop()); System.out.println("Popped: " + cars.pop()); // Popping the Three Items Off the Stack.
    }
}
