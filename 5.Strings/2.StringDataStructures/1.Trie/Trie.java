import java.util.Stack; // Importing the Stack Class from java.util to manage the words in the trie when a word is being deleted from the trie.

/**
 * A <em>Trie</em> is a tree-based data structure used to efficiently store and retrieve strings.  Tries are specialised symbol table implementations that allow for the fast retrieval of strings,
 * where the key is the string represented by the path from the root of the terminal node in the trie.  Each node in a trie represents a letter of a string.  The root node is empty and doesn't
 * store any letters, and each edge represents a connection to another letter.  As previously mentioned, a string is stored as a path in the trie, for example, the string "cat" would have the path:
 * root -> c -> a -> t.  A special marker is used as the last letter to indicate the end of a word as, for instance, "cat" and "cats" can both exist in the trie, therefore, markers are required.
 *
 * {@code Trie} is an implementation of a Trie, containing methods to search for a string in a Trie, insert a string into the Trie and remove a string from the Trie.
 */

public class Trie {

    private Node root; // Creating a Variable which Stores the Root Node of the Trie.

    /**
     * Creating an Inner-Class to Represent a Node in the Trie.
     */

    private class Node {

        private char character; private boolean endOfWordFlag; private Node[] children; // Creating Class Attributes for the Node including its Character, its Flag, and all the Possible Children.

        /**
         * This is the Class Constructor which Creates a Node in the Trie.
         * @param character is a Character which Stores the Character represented by this Node in the Trie.
         */

        public Node(char character) {this.character = character; this.endOfWordFlag = false; this.children = new Node[26];}
    }

    /**
     * This is the Class Constructor which Creates the Empty Root Node in the Trie.
     */

    public Trie() {root = new Node(' ');}

    /**
     * This method inserts a Word into the Trie.
     * @param word is the String to be inserted into the Trie.
     */

    public void insert(String word) {
        if (word == null || word.isEmpty()) return; Node current = root; word = word.toLowerCase();
        for (char character : word.toCharArray()) {int index = character - 'a'; if (current.children[index] == null) current.children[index] = new Node(character); current = current.children[index];}
        current.endOfWordFlag = true;
    }

    /**
     * This method searches for a Word in the Trie.
     * @param word is the String to be searched for in the Trie.
     * @return a Boolean Value where true is returned if the word exists in the Trie, false otherwise.
     */

    public boolean search(String word) {
        if (word == null || word.isEmpty()) return false; Node current = root;
        for (char character : word.toLowerCase().toCharArray()) {int index = character - 'a'; if (current.children[index] == null) return false; current = current.children[index];}
        return current != null && current.endOfWordFlag;
    }

    /**
     * This method deletes a Word from the Trie.
     * @param word is the String to be deleted from the Trie.
     * @return a Boolean Value where true is returned if the Word has been successfully deleted from the Trie, false otherwise.
     */

    public boolean delete(String word) {
        if (word == null || word.isEmpty()) return false; word = word.toLowerCase(); Node current = root; Stack<Node> path = new Stack<>(); int[] indices = new int[word.length()]; int depth = 0;
        // Finding the Word in the Trie and Building the Path.
        for (char character : word.toLowerCase().toCharArray()) {
            int index = character - 'a'; if (current.children[index] == null) return false; path.push(current); indices[depth++] = index; current = current.children[index];
        } if (!current.endOfWordFlag) return false; current.endOfWordFlag = false;
        // Delete the Nodes if they have no other Children.
        depth = word.length() - 1; while (!path.isEmpty() && current != null) {
            boolean hasChildren = false; for (Node child : current.children) {if (child != null) {hasChildren = true; break;}} if (hasChildren || current.endOfWordFlag) break;
        } return true;
    }

    /**
     * This method tests the {@code Trie} class by creating a trie and then inserting, searching for and deleting nodes in the trie.
     * @args the Command Line Arguments.
     */

    public static void main(String[] args) {
        Trie trie = new Trie(); trie.insert("cats"); trie.insert("cat"); trie.insert("cars"); trie.insert("carton"); trie.insert("colours"); trie.insert("crayon"); trie.insert("cactus");
        System.out.println("Is 'cat' present in the Trie?" + trie.search("cat")); System.out.println("Is 'car' present in the Trie?" + trie.search("car")); trie.delete("cat");
        System.out.println("Is 'cat' present in the Trie?" + trie.search("cat")); System.out.println("All Tests Passed Successfully!");
    }

}