import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Stack.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Stack that doesn't Exist.

class Stack_Array<Item> implements Iterable<Item> { // This Class implements a Stack using an Array, utilising Generics to Improve the Reusability of my Code.
    private Item stackItems[]; // Declaring an Array which will Store the Items in my Stack.
    int maxCapacity = 10; // Declaring and Defining a Variable that Stores the Maximum Number of Elements which can be Stored in the Array.
    int N; // Declaring a Variable which will Store the Number of Items in the Stack.

    public Stack_Array() {stackItems = (Item[]) new Object[maxCapacity];} // Creating the Constructor for the Class which Defines an Array of type Item which has maxCapacity Elements.

    public boolean isEmpty() {return N == 0;} // This method is responsible for Testing if the Stack is Empty.  This is done by checking if the Number of Elements = 0.

    public boolean isFull() {return N == maxCapacity;} // This method is responsible for Testing if the Stack is Full.  This is done by checking if the Number of Elements = the Size of the Array.

    public void push(Item item) { // This method is responsible for Pushing a New Item onto the Top of the Stack.
        if (isFull()) {throw new NoSuchElementException("OVERFLOW ERROR: You cannot Push an Element onto a Full Stack.");} // Checking if the Stack is Full and if it is - we Throw a NoSuchElementException.
        stackItems[N++] = item; // Incrementing the Variable which Stores the Number of Items in the Stack and Storing the Item in the Next Element in the Array.
    }

    public Item peek() { // This method is responsible for Returning, but not Removing, the Top Item from the Stack.
        if (isEmpty()) {throw new NoSuchElementException("UNDERFLOW ERROR: You cannot Peek an Element from an Empty Stack.");} return stackItems[N - 1]; // Checking if the Stack is Empty - if it is we Throw a NoSuchElementException - otherwise we Return the Top Item from the Stack.
    }

    public Item pop() { // This method is responsible for Removing and Returning the Top Item from the Stack.
        if (isEmpty()) {throw new NoSuchElementException("UNDERFLOW ERROR: You cannot Pop an Element from an Empty Stack.");} // Checking if the Stack is Empty - if it is we Throw a NoSuchElementException.
        Item item = stackItems[N - 1]; // Defining and Declaring a Variable which Stores the Top Item from the Stack.
        stackItems[N - 1] = null; // Deleting the Reference to the Removed Item in the Array allows the Java Garbage Collector to Reclaim the Memory - hence avoiding Loitering.
        N--; // Decrementing the Variable which Stores the Number of Items in the Stack.
        return item; // Returning the Item we Removed from the Top of the Stack.
    }

    public Iterator<Item> iterator() {return new ReverseArrayIterator();} // This method returns an instance of the ReverseArrayIterator Class.

    private class ReverseArrayIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Array in LIFO Order.
        private int index; // Declaring a Variable that is Used to track the Index of the Item in the Array.
        public ReverseArrayIterator() {index = N - 1;} // Creating the Constructor for the Inner-Class which Defines the index Variable setting its value to the Index of the Item at the Top of the Stack.
        public boolean hasNext() {return index >= 0;} // This method is responsible for Testing if there is another element in the Stack to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Item in the Array.
            if (!hasNext()) {throw new NoSuchElementException();} return stackItems[index--]; // Checking if the Iterator has reached the End of the Stack - Returning a NoSuchElementException if it has - otherwise, Returning the Next Element in the Array.
        }
    }

    @Override // Overriding the toString() method provided in the Superclass as I want to provide an Implementation Specific to my Stack.
    public String toString() { // This method is responsible for Representing the Stack as a String to make the Operations on the Stack easier to understand during Debugging and Testing.
        StringBuilder myStringBuilder = new StringBuilder(); // Creating an Object of the StringBuilder Class to Create the String.
        if (!isEmpty()) { // If the Stack is not Empty - we can Creating a String Representing the Stack.
            for (int i = N - 1; i >= 0; i--) { // Using a for Loop to Iterate through the Stack.
                if (i == N - 1) {myStringBuilder.append("|").append(stackItems[N - 1]).append("| => TOP OF THE STACK \n");} else if (i == 0) {myStringBuilder.append("|").append(stackItems[0]).append("| => BOTTOM OF THE STACK \n");} // If we are Examining an Item which is at the Top or Bottom of the Stack - we should Mark it Clearly.
                else {myStringBuilder.append("|").append(stackItems[i]).append("| \n");} // If we are not Examining the Top or Bottom Item, we Display the Item without any Additional Text.
            }
        } return myStringBuilder.toString(); // Returning the Representation String of the Array back to the Caller.
    }

    public static void main(String[] args) { // Creating some Unit Tests to Test the Stack_Array Class.
        Stack_Array<String> cars = new Stack_Array<String>(); // Creating an Object of the Stack_Array Class of Type String.
        cars.push("Volvo"); cars.push("Mercedes"); cars.push("Nissan"); // Pushing Items onto the Stack.
        System.out.println("Top Element: " + cars.peek()); // Peeking the Top Item on the Stack.
        System.out.println("Cars Stack: \n" + cars); // Displaying the Stack after the Items have been Pushed onto the Stack.

        System.out.println("Popped: " + cars.pop()); System.out.println("Popped: " + cars.pop()); System.out.println("Popped: " + cars.pop()); // Popping the Three Items Off the Stack.
    }
}