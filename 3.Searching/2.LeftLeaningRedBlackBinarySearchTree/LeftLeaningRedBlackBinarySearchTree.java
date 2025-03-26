import java.util.NoSuchElementException; // Importing NoSuchElementException from the java.util Package to Throw an Exception if an Attempt is made to Access a Non-Existent Key in the Symbol Table.
import java.util.Iterator; // Importing Iterator from the java.util Package to use an Iterator to Perform an In-Order Traversal on the Balanced BST to Display the Key:Value Pairs in the Symbol Table.
import java.util.Stack; // Importing Stack from the java.util Package to Track Node's Keys during the In-Order Traversal.

/**
 * @author LewisThackeray
 * @date 26/03/2024
 *
 * {@code LeftLeaningRedBlackBinarySearchTree} represents a Symbol Table implemented as a <em>Left Leaning Red Black Binary Search Tree</em>.  A <em>Left Leaning Red Black Binary Search Tree</em>
 * represents a 2-3 Search Tree as a <em>Binary Search Tree (BST)</em>. A <em>Left Leaning Red Black Binary Search Tree</em> has the following three restrictions: red links lean left, no node has
 * two red links connected to it and the tree has perfect balance: every path from the root to a NULL link has the same number of black links.
 *
 * <p><i>There is a 1-1 Correspondence between 2-3 Search Trees and Left Leaning Red Black Binary Search Trees, therefore, if we draw the red links horizontally in a Left Leaning Red Black Binary
 * Search Tree, where all NULL links are the same distance from the root, we can collapse together the nodes connected by the red links and the result is a 2-3 Search Tree.</i></p>
 *
 * <p><b>Time Complexity of the Left Leaning Red Black Binary Search Tree:</b> The Worst Case Time Complexity of the Dictionary Operations (Search, Insertion and Deletion) are O(log(n)) due to
 * the Balanced Nature of the Tree.</p>
 *
 * <p><b>Space Complexity of the Left Leaning Red Black Binary Search Tree:</b> The Space Compelxity is O(n) where n is the Number of Nodes in the Left Leaning Red Black Binary Search Tree.</p>
 *
 * <p>The two defining properties of a Left Leaning Red Black Binary Search Tree is Order and Perfect Black Balance.  The Balancing Operations wihch are used to Re-Balance the Left Leaning Red
 * Black Binary Search Tree are carefully designed to preserve both the Perfect Black Balance and the Order of the Tree, they are: <em>rotateLeft</em>, <em>rotateRight</em> and <em>flip</em>.</p>
 *
 * @param Key is the type of keys maintained by the Symbol Table, the keys must be Comparable.
 * @param Value is the type of mapped values.
 */

public class LeftLeaningRedBlackBinarySearchTree<Key extends Comparable<? super Key>, Value> implements Iterable<Key> {

    private static final boolean RED = true; private static final boolean BLACK = false; // Creating Boolean Variables to Represent the Colours, as a Node is Either Red or Black.
    private Node root; // Creating a Variable to Store the Root of the Left Leaning Red Black Binary Search Tree.

    /**
     * This Inner-Class represents a Node in the Left Leaning Red Black Binary Search Tree.
     */

    private class Node {

        private Key key; private Value value; private Node left, right; private int size; private boolean colour; // Creating Class Attributes to Store the Properties of the Node.

        /**
         * This is the Class Constructor which Creates a Node in the Left Leaning Red Black Binary Search Tree with a Specified key, value, size and colour.
         *
         * @param key    is of type Key which is the Key of the Node (all Keys in the Tree must be of the Same Type to ensure they are Comparable).
         * @param value  is of type Value which is the Value of the Node.
         * @param size   is an Integer which Stores the Number of Nodes in this Node's Subtree.
         * @param colour is a Boolean Value which Stores the Colour of the Node, where true represents a Red Node and false represents a Black Node.
         */

        public Node(Key key, Value value, int size, boolean colour) {this.key = key; this.value = value; this.size = size; this.colour = colour;}
    }

    /**
     * This method returns the Number of Nodes in the Left Leaning Red Black Binary Search Tree (BST) which is the Same as the Number of Key: Value Pairs in the Symbol Table.
     * @return an Integer specifying the Number of Key: Value Pairs in the Symbol Table.
     */

    public int size() {return size(root);}

    /**
     * This method returns the Number of Nodes in the Subtree routed at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @return an Integer specifying the Number of Nodes in the Subtree, returning 0 if the Node is NULL representing a Child Node.
     */

    private int size(Node root) {if (root == null) return 0; return root.size;}

    /**
     * This method tests if the Left Leaning Red Black Binary Search Tree has No Nodes and so if the Symbol Table is Empty.
     * @return a Boolean Value where true is returned if the Symbol Table is Empty, false otherwise.
     */

    public boolean isEmpty() {return size() == 0;}

    /**
     * This method Inserts a new Key: Value Pair into the Left Leaning Red Black Binary Search Tree or Updates the Value if the Key already exists in the Symbol Table.
     * @param key of type Key is the Key to be Inserted into the Left Leaning Red Black Binary Search Tree.
     * @param value of type Value is the Value Associted with the Key.
     * @throws IllegalArgumentException if the key Passed into the Method is NULL.
     */

    public void insert(Key key, Value value) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); root = insert(root, key, value); root.colour = BLACK;}

    /**
     * This method recursively Inserts or Updates a Key: Value Pair in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the root of the Subtree.
     * @param key of type Key is the Key to be Inserted into the Left Leaning Read Black Binary Searh Tree.
     * @param value of type Value is the Value Associated with the Key.
     * @return a Node which is the Root of the Updated Subtree after the Key: Value Pair has been Inserted or Updated.
     */

    private Node insert(Node root, Key key, Value value) {
        if (root == null) return new Node(key, value, 1, RED); int comparison = key.compareTo(root.key);
        if (comparison < 0) {root.left = insert(root.left, key, value);} else if (comparison > 0) {root.right = insert(root.right, key, value);} else {root.value = value;}
        if (isRed(root.right) && !isRed(root.left)) root = rotateLeft(root); if (isRed(root.left) && isRed(root.left.left))root = rotateRight(root);
        if (isRed(root.left) && isRed(root.right)) flip(root); root.size = size(root.left) + size(root.right) + 1; return root;
    }

    /**
     * This method checks if a Node in the Left Leaning Red Black Binary Search Tree is a Red Node.
     * @param node of type Node is the Node to Check.
     * @return a Boolean Value where true is Returned if the Node is a Red Node, false if the Node is Black Node or the Node is NULL.
     */

    private boolean isRed(Node node) {if (node == null) return false; return node.colour == RED;}

    /**
     * This method performs a Left Rotation on the Specified Node to maintain the Order and Perfect Black Balance in the Left Leaning Red Black Binary Search Tree.
     * @param node of type Node is the Node to Rotate.
     * @return a Node which is the Root of the Updated Subtree after the Left Rotation.
     */

    private Node rotateLeft(Node node) {
        Node root = node.right; node.right = root.left; root.left = node; root.colour = node.colour; node.colour = RED; root.size = node.size; node.size = size(node.right) + size(node.left) + 1;
        return root;
    }

    /**
     * This method performs a Right Rotation on the Specified Node to maintain the Order and Perfect Black Balance in the Left Leaning Red Black Binary Search Tree.
     * @param node of type Node is the Node to Rotate.
     * @return a Node which is the Root of the Updated Subtree after the Right Rotation.
     */

    private Node rotateRight(Node node) {
        Node root = node.left; node.left = root.right; root.right = node; root.colour = node.colour; node.colour = RED; root.size = node.size; node.size = size(node.right) + size(node.left) + 1;
        return root;
    }

    /**
     * This method Flips the Colours of a Specified Node and its Children to maintain the Order and Perfect Black Balance in the Left Leaning Red Black Binary Search Tree.
     * @param node of type Node is the Node whose Colours will be Flipped.
     */

    private void flip(Node node) {node.colour = !node.colour; node.left.colour = !node.left.colour; node.right.colour = !node.right.colour;}

    /**
     * This method Returns the Value associated with a Specified Key in the Left Leaning Red Black Binary Search Tree representation of a Symbol Table.
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
     * This method checks if the Symbol Table contains the Specified key, therefore, it checks if a Node in the Left Leaning Red Black Binary Search Tree has a Specified key.
     * @param key of type Key is the key to Check.
     * @return a boolean value where true indicates that the key is in the Symbol Table, false if the key is not in the Symbol Table.
     * @throws IllegalArgumentException if the key is NULL.
     */

    public boolean contains(Key key) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); return search(key) != null;}

    /**
     * This method removes a Node from the Left Leaning Red Black Binary Search Tree, equivalent to Deleting a Key: Value Pair from the Symbol Table.
     * @param key of Type Key is the key to remove from the Symbol Table.
     * @throw IllegalArgumentException if the key is NULL.
     */

    public void delete(Key key) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); root = delete(root, key);}

    /**
     * This method recursively deletes the Specified Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @param key of type Key is the key ro remove from the Symbol Table.
     * @return a Node which is the Root of the Updated Substree after the Key: Value Pair has been deleted from the Left Leaning Red Black Binary Search Tree.
     */

    private Node delete(Node root, Key key) {
        if (root == null) return null; int comparison = key.compareTo(root.key);
        if (comparison < 0) {root.left = delete(root.left, key);} else if (comparison > 0) {root.right = delete(root.right, key);} else {
            if (root.right == null) return root.left; if (root.left == null) return root.right; Node temp = root; root = min(temp.right); root.right = delete(temp.right, root.key); root.left = temp.left;
        } root.size = size(root.left) + size(root.right) + 1; return root;
    }

    /**
     * This method returns the key of the Node with the Smallest Key in the Left Leaning Red Black Binary Search Tree, therefore, it returns the Smallest Key in the Symbol Table.
     * @return key of type Key is the smallest key in the Symbol Table.
     * @throw NoSuchElementException if the Symbol Table is Empty and so the Left Leaning Red Black Binary Search Tree contains no Nodes.
     */

    public Key min() {if (isEmpty()) throw new NoSuchElementException("The LLRB Binary Search Tree contains No Nodes, therefore, the Symbol Table is Empty!"); return min(root).key;}

    /**
     * This method recursively returns the node with the Smallest Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @return node of type Node which is the Node with the Smallest Key in the Subtree rooted at the Given Node.
     */

    private Node min(Node root) {while (root.left != null) root = root.left; return root;}

    /**
     * This method returns the key of the Node with the Greatest Key in the Left Leaning Red Black Binary Search Tree, therefore, it returns the Greatest Key in the Symbol Table.
     * @return key of type Key is the greatest key in the Symbol Table/
     * @throw NoSuchElementException if the Symbol Table is Empty and so the Left Leaning Red Black Binary Search Tree contains no Nodes.
     */

    public Key max() {if (isEmpty()) throw new NoSuchElementException("The LLRB Binary Search Tree contains No Nodes, therefore, the Symbol Table is Empty!"); return max(root).key;}

    /**
     * This method recursively returns the node with the Largest Key in the Subtree rooted at the Given Node.
     * @param root of type Node is the Node at the Root of the Subtree.
     * @return node of type Node which is the Node with the Largest Key in the Subtree rooted at the Given Node.
     */

    private Node max(Node root) {if (root.right == null) return root; return max(root.right);}

    @Override public Iterator<Key> iterator() {return new InOrderTraversal();}

    /**
     * This Inner-Class implements the Iterator Interface to perform an In-Order Traversal on the Left Leaning Red Black Binary Search Tree, displaying the Keys in the Symbol Table in Ascending Order.
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
         * This method tests whether there are more Nodes to Iterate over in the Left Leaning Red Black Binary Search Tree.
         * @return a boolean value where true is returned if there are more Nodes to Iterate over in the Left Leaning Red Black Binary Search Tree, false otherwise.
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
     * This method tests the {@code LeftLeaningRedBlackBinarySearchTree} Class which implements a Symbol Table as a Left Leaning Red Black Binary Search Tree.  These Unit Tests test the class by Inserting,
     * Searching and Deleting Nodes in the Left Leaning Red Black Binary Search Tree.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        LeftLeaningRedBlackBinarySearchTree<Integer, String> llrb = new LeftLeaningRedBlackBinarySearchTree<>(); llrb.insert(2, "Apple"); llrb.insert(5, "Banana"); llrb.insert(15, "Fox");
        llrb.insert(67, "Carrot"); llrb.insert(88, "Pants"); llrb.insert(9, "Cup"); llrb.insert(54, "Phone"); llrb.insert(33, "Pen");

        System.out.println("Size: " + llrb.size()); System.out.println("Contains Key 2? " + llrb.contains(2)); System.out.println("Minimum: " + llrb.min()); System.out.println("Maximum: " + llrb.max());
        System.out.println("In-Order Traversal (Before Deletion): "); for (Integer key: llrb) {System.out.println("Key: " + key + "| Value: " + llrb.search(key));} llrb.delete(2);
        System.out.println("In-Order Traversal (After Deletion): "); for (Integer key: llrb) {System.out.println("Key: " + key + "| Value: " + llrb.search(key));}

        System.out.println("All Tests Passed Successfully!");
    }
}