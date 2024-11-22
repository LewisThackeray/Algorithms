import java.util.Iterator; // Importing the Iterator Class from the java.util Package to Loop through the Bag.
import java.util.NoSuchElementException; // Importing the NoSuchElementException Class from the java.util Package to Raise a Runtime Exception when there is an Attempt to Access an Item in the Bag that doesn't Exist.

public class Bag_LinkedList<Item> implements Iterable<Item> { // This Class implements a Bag using a Linked List, utilising Generics to improve the Reusability of my Code.

    private Node first; // Declaring a Variabe which will Store the First Node in the Linked List.
    private int N; // Declaring a Variable which will Store the Number of Items in the Bag.

    private class Node {private Item item; private Node next;} // Creating an Inner-Class used to Represent a Node in the Linked List, where the Linked List is Singly-Linked.

    public Bag_LinkedList() {first = null; N = 0;} // Creating the Constructor for the Class which Initialises an Empty Bag.

    public boolean isEmpty() {return first == null;} // This method is responsible for Testing if the Bag is Empty.  This is done by checking if the First Node in the Linked List is null.

    public void add(Item item) { // This method is responsible for Adding an Element to the Bag.
        Node oldFirst = first; first = new Node(); first.item = item; first.next = oldFirst; N++; // Creating a New Node to Insert the New Node at the Front of the Linked List and Incrementing the Variable which Stores the Number of Elements in the Bag (the Number of Nodes in the Linked List).
    }

    public Iterator<Item> iterator() {return new LinkedListIterator();} // This method returns an Instance of the LinkedListIterator Class.

    private class LinkedListIterator implements Iterator<Item> { // This Inner-Class is responsible for Iterating over the Linked List.
        private Node current = first; // Declaring and Defining a Variable to store the Current Node being Examined in the Linked List.
        public boolean hasNext() {return current != null;} // This method is responsible for Testing if there is another element in the Bag to Iterate through.
        public Item next() { // This method is responsible for Returning the Next Item in the Bag.
            if (!hasNext()) throw new NoSuchElementException(); // Checking if the Iterator has reached the End of the Bag - if it has we Throw a NoSuchElementException.
            Item item = current.item; current = current.next; return item; // Getting the Element in the Bag and Updating the current Variable to Point to the Next Node in the Linked List.
        }
    }

    public static void main(String[] args) {
        Bag_LinkedList<String> cars = new Bag_LinkedList<String>(); // Creating an Object of the Bag_LinkedList Class of type String.
        cars.add("Volvo"); cars.add("Mercedes"); cars.add("Nissan"); // Adding Items to the Bag.

        for (String s : cars) System.out.println(s); // Displaying the Bag after the Items have been Added.
    }

    /* NOTE: THE ITERATOR WILL OUTPUT ITEMS IN A DIFFERENT ORDER TO MY IMPLEMENTATION IN BAG_RESIZINGARRAY, HOWEVER, THIS DOES NOT MATTER AS IN A BAG THE ORDER OF ELEMENTS IS NOT IMPORTANT. */

}