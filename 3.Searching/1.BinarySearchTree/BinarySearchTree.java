import java.util.NoSuchElementException; // Importing the NoSuchElementException Clas from the java.util Package to Throw a Runtime Error when Accessing a Non-Existent Item in the Priority Queue.
import java.util.Iterator; // Importing the Iterator Class from the java.util Package to use an Iterator to Display the Key: Value Pairs in the Symbol Table by Performing an In-Order Traversal on the Binary Search Tree.
import java.util.Stack; // Importing Stack Class from the java.util Package to Track Node's Keys during the In-Order Traversal.

/**
 * @author LewisThackeray
 * @date 25/03/2025
 *
 * {@code BinarySearchTree} represents a Symbol Table implemented as a <em>Binary Search Tree (BST)</em>.  A <b>Binary Search Tree</b> is a binary tree where each node has a comparable key and an
 * associated value the satisfies the restriction that the key in any node is larger than the keys in all the nodes in that node's left subtree and smaller than the keys in all the nodes in that
 * node's right subtree.
 *
 * This implementation contains a series of nodes where each node contains a Key, a Value, a Link to the Node's Left Subtree, a Link to the Node's Right Substree and the Number of Nodes in the
 * Node's Subtree.  This implementation uses Generics to Ensure the Flexibility of my Implementation so that Values can be of any Data Type, Keys cannot as they need to be Comparable.
 *
 * <p><b>Time Complexity of the Binary Search Tree:</b> The Average Case Time Complexity is O(log(n)), however, the if the BST is Unbalanced, the Worst Case Time Complexity is O(n).</p>
 *
 * <p><b>Space Complexity of the Binary Search Tree:</b> The Space Complexity is O(n) where n is the Number of Nodes in the BST.</p>
 *
 * @param Key is the type of keys maintained by the Symbol Table, the keys must be Comparable.
 * @param Value is the type of mapped values.
 *
 */

public class BinarySearchTree<Key extends Comparable<? super Key>, Value> implements Iterable<Key> {

    private Node root; // Creating a Class Attribute to Store the Root of the Binary Search Tree.

    /**
     * This Inner-Class represents a Node and Stores the Properties of a Node in a Binary Search Tree (BST).
     */

    private class Node {

        private Key key; private Value value; private Node left,right; private int size; // Creating Class Attributes to Store the Node's Properties.

        /**
         * Creating the Class Constructor to Create a Node in the Binary Search Tree (BST) with a Specified key, value and size.
         * @param key is of type Key which is the Key of the Node (all Keys in the BST must be of the Same Type to ensure they are Comparable).
         * @param value is of type Value which is the Value of the Node.
         * @param size is an Integer which Stores the Number of Nodes in this Node's Subtree.
         */
        public Node(Key key, Value value, int size) {this.key = key; this.value = value; this.size = size;}
    }

    /**
     * This method returns the Number of Nodes in the Binary Search Tree (BST) which is the Same as the Number of Key: Value Pairs in the Symbol Table.
     * @return an Integer specifying the Number of Key: Value Pairs in the Symbol Table.
     */

    public int size() {return size(root);}

    /**
     * This method returns the Number of Nodes in the Subtree routed at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @return an Integer specifying the Number of Nodes in the Subtree, returning 0 if the Node is Null representing a Child Node.
     */

    private int size(Node root) {if (root == null) return 0; return root.size;}

    /**
     * This method tests if the Binary Search Tree has No Nodes and so if the Symbol Table is Empty.
     * @return a boolean value where true is returned if the Symbol Table is Empty, false otherwise.
     */

    public boolean isEmpty() {return size() == 0;}

    /**
     * This method Inserts a new Key-Value Pair to the Binary Search Tree or Updates the Value if the Key already exists.
     * @param key of type Key is the key to be Inserted into the Binary Search Tree.
     * @param value of type Value is the value Associated with the Key.
     * @throws IllegalArgumentException if the key Passed into the Method is NULL.
     */

    public void insert(Key key, Value value) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); root = insert(root, key, value);}

    /**
     * This method recursively Inserts or Updates a Key: Value Pair in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @param key of type Key is the key to be Inserted into the Binary Search Tree.
     * @param value of type Value is the value Assocaited with the Key.
     * @return a Node which is the Root of the Updated Substree after the Key: Value Pair has been Inserted or Updated.
     */

    private Node insert(Node root, Key key, Value value) {
        if (root == null) {return new Node(key, value, 1);} int comparison = key.compareTo(root.key);
        if (comparison < 0) {root.left = insert(root.left, key, value);} else if (comparison > 0) {root.right = insert(root.right, key, value);} else {root.value = value;}
        root.size = size(root.left) + size(root.right) + 1; return root;
    }

    /**
     * This method returns the Value associated with a Specified Key in the Binary Search Tree representation of a Symbol Table.
     * @param key of type Key is the key which we want to find the corresponding value of.
     * @return value of type Value is the corresponding value for the provided key in the Key: Value Pair.
     * @throw IllegalArgumentException if the key is NULL.
     */

    private Value search(Key key) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); return search(root, key);}

    /**
     * This method recursively retrieves the Value associated with the Specified Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @param key of type Key is the key which we want to find the corresponding value of.
     * @return a Value associated with the provided key, or NULL if no value is found.
     */

    private Value search(Node root, Key key) {
        if (root == null) return null; int comparison = key.compareTo(root.key);
        if (comparison < 0) {return search(root.left, key);} else if (comparison > 0) {return search(root.right, key);} else {return root.value;}
    }

    /**
     * This method checks if the Symbol Table contains the Specified key, therefore, it checks if a Node in the Binary Search Tree has a Specified key.
     * @param key of type Key is the key to Check.
     * @return a boolean value where true indicates that the key is in the Symbol Table, false if the key is not in the Symbol Table.
     * @throws IllegalArgumentException if the key is NULL.
     */

    public boolean contains(Key key) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); return search(key) != null;}

    /**
     * This method removes a Node from the Binary Search Tree, equivalent to Deleting a Key: Value Pair from the Symbol Table.
     * @param key of Type Key is the key to remove from the Symbol Table.
     * @throw IllegalArgumentException if the key is NULL.
     */

    public void delete(Key key) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); root = delete(root, key);}

    /**
     * This method recursively deletes the Specified Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @param key of type Key is the key ro remove from the Symbol Table.
     * @return a Node which is the Root of the Updated Substree after the Key: Value Pair has been deleted from the Binary Search Tree.
     */
        private Node delete(Node root, Key key) {
        if (root == null) return null; int comparison = key.compareTo(root.key);
        if (comparison < 0) {root.left = delete(root.left, key);} else if (comparison > 0) {root.right = delete(root.right, key);} else {
            if (root.right == null) return root.left; if (root.left == null) return root.right; Node temp = root; root = min(temp.right); root.right = delete(temp.right, root.key); root.left = temp.left;
        } root.size = size(root.left) + size(root.right) + 1; return root;
    }

    /**
     * This method returns the key of the Node with the Smallest Key in the Binary Search Tree, therefore, it returns the Smallest Key in the Symbol Table.
     * @return key of type Key is the smallest key in the Symbol Table.
     * @throw NoSuchElementException if the Symbol Table is Empty and so the Binary Search Tree contains no Nodes.
     */

    public Key min() {if (isEmpty()) throw new NoSuchElementException("The Binary Search Tree contains No Nodes, therefore, the Symbol Table is Empty!"); return min(root).key;}

    /**
     * This method recursively returns the node with the Smallest Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @return node of type Node which is the Node with the Smallest Key in the Subtree rooted at the Given Node.
     */

    private Node min(Node root) {while (root.left != null) root = root.left; return root;}

    /**
     * This method returns the key of the Node with the Greatest Key in the Binary Search Tree, therefore, it returns the Greatest Key in the Symbol Table.
     * @return key of type Key is the greatest key in the Symbol Table/
     * @throw NoSuchElementException if the Symbol Table is Empty and so the Binary Search Tree contains no Nodes.
     */

    public Key max() {if (isEmpty()) throw new NoSuchElementException("The Binary Search Tree contains No Nodes, therefore, the Symbol Table is Empty!"); return max(root).key;}

    /**
     * This method recursively returns the node with the Largest Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @return node of type Node which is the Node with the Largest Key in the Subtree rooted at the Given Node.
     */

    private Node max(Node root) {if (root.right == null) return root; return max(root.right);}

    /**
     * This method returns an Iterator over the Nodes in the Binary Search Tree, providing the Keys in the Symbol Table in Ascending Order.
     * @return iterator which calls the Iterator interface and iterates over the Keys in the Symbol Table.
     */

    @Override public Iterator<Key> iterator() {return new InOrderTraversal();}

    /**
     * This Inner-Class implements the Iterator Interface to perform an In-Order Traversal on the Binary Search Tree, displaying the Keys in the Symbol Table in Ascending Order.
     */

    private class InOrderTraversal implements Iterator<Key> {

        private Stack<Node> stack = new Stack<>(); // Creating a Stack to Track the Leftmost Nodes and then Process the Right Subtrees when needed.

        /**
         * This is the Class Constructor which Constructs an Iterator and Initialises the Stack with the Leftmost Path.
         */

        public InOrderTraversal() {pushLeft(root);}

        /**
         * This method pushes all Nodes along the Left Path of the Subtree rooted at a Given Node.
         * @param root of type Node is the Node at the Root of the Subtree.
         */

        private void pushLeft(Node root) {while (root != null) {stack.push(root); root = root.left;}}

        /**
         * This method tests whether there are more Nodes to Iterate over in the Binary Search Tree.
         * @return a boolean value where true is returned if there are more Nodes to Iterate over in the Binary Search Tree, false otherwise.
         */

        @Override public boolean hasNext() {return !stack.isEmpty();}

        /**
         * This method returns the next key in the iteration.
         * @return key of type Key which is the next key in the iteration.
         * @throws NoSuchElementException if there are no more keys to Iterate over.
         */

        @Override public Key next() {if (!hasNext()) throw new NoSuchElementException("No More Elements to Iterate Over!"); Node temp = stack.pop(); pushLeft(temp.right); return temp.key;}
    }

    /**
     * This method tests the {@code BinarySearchTree} Class which implements a Symbol Table as a Binary Search Tree.  These Unit Tests test the class by inserting, searching and deleting nodes
     * in the Binary Search Tree.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> myBinarySearchTree = new BinarySearchTree<>(); myBinarySearchTree.insert(2, "Apple"); myBinarySearchTree.insert(5, "Banana"); myBinarySearchTree.insert(15, "Fox");
        myBinarySearchTree.insert(67, "Carrot"); myBinarySearchTree.insert(88, "Pants"); myBinarySearchTree.insert(9, "Cup"); myBinarySearchTree.insert(54, "Phone"); myBinarySearchTree.insert(33, "Pen");

        System.out.println("Size: " + myBinarySearchTree.size()); System.out.println("Contains Key 2? " + myBinarySearchTree.contains(2)); System.out.println("Minimum: " + myBinarySearchTree.min());
        System.out.println("Maximum: " + myBinarySearchTree.max()); System.out.println("In-Order Traversal: "); for (Integer key: myBinarySearchTree) {System.out.println("Key: " + key + "| Value: " + myBinarySearchTree.search(key));}

        System.out.println("All Tests Passed Successfully!");
    }
}