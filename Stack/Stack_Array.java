import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Stack.
import java.util.NoSuchElementException; // Impoting the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Stack that doesn't Exist.

class Stack_Array<Item> implements Iterable<Item> { // This Class implements a Stack using a Resizable Array, utiliising Generics to improve the Reusability of my Code.

    private static final int initialStackCapacity = 8; // Defining a Variable that Stores the Initial Size of the Stack.
    private Item[] stackItems; // Declaring an Array which will Store the Items in my Stack.
    private int N; // Declaring a Variable which will Store the Number of Items in the Stack.

    public Stack_Array() {stackItems = (Item[]) new Object[initialStackCapacity];} // Creating the Constructor for the Class which Defines an Array of type Item which has initialStackCapacity elements.

    private void resize(int newCapacity) { // This method is responsible for Dynamically Changing the Size of the Array based on the Number of Items in the Stack to prevent Overflow Errors and Wasted Memory.
        assert newCapacity >= N; // Using an Assertion to Check if newCapacity is >= N - if newCapacity < N then the program throws an AssertionError. || ** REMEMBER TO ENABLE ASSERTIONS DURING RUNTIME ** ||
        Item[] newStackItems = (Item[]) new Object[newCapacity]; // Declaring an Defining a New Array of type Item which has newCapacity elements.
        for (int i = 0; i < N; i++) {newStackItems[i] = stackItems[i];} stackItems = newStackItems; // Copying the Items from the Old Array to the New Array and Updating the Class Attribute stackItems so that it stores the Updated Array.
    }

    public boolean isFull() {return (N == stackItems.length);} // This method is responsible for Testing if the Stack is Full.  This is done by checking if the Number of Elements = the Size of the Array.

    public boolean isEmpty() {return (N == 0);} // This method is responsible for Testing if the Stack is Empty.  This is done by checking if the Number of Elements = 0.

    public void push(Item item) { // This method is responsible for Pushing a new Item onto the Top of the Stack.
        if (isFull()) resize(2 * stackItems.length); // Checking if the Stack is Full and if it is - we Double the Size of the Array.
        stackItems[N++] = item; // Incrementing the Number of Items in the Stack and Storing the Item in the Next Element in the Array.
    }

    public Item peek() { // This method is responsible for Returning, but not Removing, the Top Item from the Stack.
        if (isEmpty()) throw new NoSuchElementException("ERROR: Stack Underflow."); return stackItems[N-1]; // Checking if the Stack if Empty - if it is we Throw a NoSuchElementException - otherwise we Return the Top Item from the Stack.
    }

    public Item pop() { // This method is responsible for Removing and Returning the Top Item from the Stack.
        if (isEmpty()) throw new NoSuchElementException("ERROR: Stack Underflow."); // Checking if the Stack is Empty - if it is we Throw a NoSuchElementException.
        Item item = stackItems[N-1]; // Defining and Declaring a Variable which Stores the Top Item from the Stack.
        stackItems[N-1] = null; // Deleting the Reference to the Removed Item in the Array allows the Java Garbage Collector to Reclaim the Memory - hence avoiding Loitering.
        N--; // Decrementing the Variable which Stores the Number of Items in the Stack.
        if (N > 0 && N <= stackItems.length / 4) resize(stackItems.length / 2); // If the Size of the Array is Less Than or Equal To 1/4 of the Elements Stored, we Half the Size of the Array to ensure Memory is not unecessarily wasted.
        return item; // Returning the Item we Removed from the Top of the Stack.
    }

    public Iterator<Item> iterator() {return new ReverseArrayIterator();} // This method returns an instance of the ReverseArrayIterator Class.

    private class ReverseArrayIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Array in LIFO Order.
        private int index; // Declaring a Variable that is Used to track the Index of the Item in the Array.
        public ReverseArrayIterator() {index = N - 1;} // Creating the Constructor for the Inner-Class which Defines the index Variable setting its value to the Index of the Item at the Top of the Stack.
        public boolean hasNext() {return index >= 0;} // This method is reponsible for Testing if there is another element in the Stack to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Item in the Array.
            if (!hasNext()) {throw new NoSuchElementException();} return stackItems[index--]; // Checking if the Iterator has reached the End of the Stack - Returning an NoSuchElementException if it has - otherwise, Returning the Next Element in the Array.
        }
    }

    @Override // Overriding the toString() method provided in the Superclass as I want to provide an Implementation for Specific to my Stack.
    public String toString() { // This method is reponsible for Representing the Stack as a String to make the Operations on the Stack easier to understanding during Debugging and Testing.
        StringBuilder myStringBuilder = new StringBuilder(); // Creating an Object of the StringBuilder Class to Create the String.
        if (!isEmpty()) { // If the Stack is not Empty - we can Create a String Representing the Stack.
            myStringBuilder.append("|").append(stackItems[N-1]).append("| => TOP OF THE STACK\n"); for (int i = N - 2; i >= 0; i--) {myStringBuilder.append("|").append(stackItems[i]).append("|\n");} myStringBuilder.append("|").append(stackItems[0]).append("| => BOTTOM OF THE STACK\n"); // Iterating through the Stack creating the Representation, marking the Top and Bottom of the Stack clearly.
        } return myStringBuilder.toString(); // Returning the Representation String of the Array back to the Caller.
    }

    public static void main(String[] args) { // Creating some Unit Tests to Test the Stack_Array Class.
        Stack_Array<String> cars = new Stack_Array<String>(); // Creating an Object of the Stack_Array Class of Type String.
        cars.push("Volvo"); cars.push("Mercedes"); cars.push("Nissan"); // Pushing Items onto the Stack.
        System.out.println("Top Element: " + cars.peek()); // Peeking the Top Item on the Stack.
        System.out.println("Cars Stack: " + cars); // Displaying the Stack after the Items have been Pushed onto the Stack.

        System.out.println("Popped: " + cars.pop()); System.out.println("Popped: " + cars.pop()); System.out.println("Popped: " + cars.pop()); // Popping the Three Items Off the Stack.
    }

}
