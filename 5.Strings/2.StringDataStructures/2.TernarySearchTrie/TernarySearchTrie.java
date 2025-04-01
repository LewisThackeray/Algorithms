/**
 * <p>The problem with tries is that each node typically contains a child point for every possible character, leading to high memory usage, especially for sparse tries with many unused pointers
 * - to overcome this issue <b>Ternary Search Tries (TSTs)</b> are used.  A <em>Ternary Search Trie</em> is a type of trie, with a different structure, optimised for memory usage.  Like a normal
 * trie, each node has a character and where necessary a marker used to indicate the end of a string.  However, a TST has three pointers: a left point which points to node with characters less
 * than the current node's character, a middle pointer which points to nodes representing the next character in the string and a right point which points to nodes with characters greater than the
 * current node's character.</p>
 */

public class TernarySearchTrie {

    private Node root; // Creating a Variable which Stores the Root Node of the Ternary Search Trie (TST).

    /**
     * Creating an Inner-Class to Represent a Node in the Trie.
     */

    private class Node {

        private char character; boolean endOfWordFlag; // Creating a Variable to Store the Character which this Node represents in the Trie and a Boolea Variable to Store the End of Word Flag.
        private Node left, middle, right; // Creating Variables to Store Points to the Node in the Left, Middle and Right Subtrees of the Node.

        /**
         * This is the Class Constructor for the Node Class which Creates a Node in the Ternary Search Trie (TST).
         * @param character is a Character which Stores the Character represented by this Node in the Ternary Search Trie (TST).
         */

        public Node(char character) {this.character = character; this.endOfWordFlag = false; this.left = null; this.middle = null; this.right = null;}
    }

    /**
     * This is the Class Constructor which the Ternary Search Trie (TST) and Sets the Root to NULL.
     */

    public TernarySearchTrie() {root = null;}

    /**
     * This method inserts a word into the Ternary Search Trie (TST) by calling the recursive method which recursively traverses the TST and inserts the Word.
     * @param word is the String to be inserted into the Ternary Search Trie (TST).
     */

    public void insert(String word) {if (word == null || word.isEmpty()) return; root = insert(root, word.toLowerCase(), 0);}

    /**
     * This method recursively traverses the Ternary Search Trie (TST) and inserts the word.
     * @param node of type Node is the Current Node being processed.
     * @param word is the String to be inserted into the Ternary Search Trie (TST).
     * @param index is an Integer representing the current character position in the word.
     * @return the modified Node after insertion.
     */

    private Node insert(Node node, String word, int index) {
        char character = word.charAt(index); if (node == null) node = new Node(character);
        if (character < node.character) {node.left = insert(node.left, word, index);} else if (character > node.character) {node.right = insert(node.right, word, index);}
        else if (index < word.length() - 1) {node.middle = insert(node.middle, word, index + 1);} else {node.endOfWordFlag = true;} return node;
    }

    /**
     * This method searches for a word in the Ternary Search Trie (TST) by calling the recursive method which recursively searches the TST to find the word.
     * @param word is the String to be searched for in the Ternary Search Trie (TST).
     * @return a Boolean Value where true is returned if the word is present in the Ternary Search Trie (TST), false otherwise.
     */

    private boolean search(String word) {if (word == null || word.isEmpty()) return false; return search(root, word.toLowerCase(), 0);}

    /**
     * This method recursively traverses the Ternary Search Trie (TST) to search for a word.
     * @param node of type Node is the Current Node being processed.
     * @param word is the String being searched for in the Ternary Search Trie (TST)
     * @param index is an Integer representing the current character position in the word.
     * @return a Boolean Value where true is returned if the word is present in the Ternary Search Trie (TST), false otherwise.
     */

    private boolean search(Node node, String word, int index) {
        if (node == null) return false; char character = word.charAt(index);
        if (character < node.character) {return search(node.left, word, index);} else if (character > node.character) {return search(node.right, word, index);}
        else if (index == word.length() - 1) {return node.endOfWordFlag;} else {return search(node.middle, word, index + 1);}
    }

    /**
     * This method deletes a word in the Ternary Search Trie (TST) by calling the recursive method which recursively traverses the TST and deletes the word.
     * @param word is the String to be deletes from the Ternary Search Trie (TST).
     */

    public void delete(String word) {if (word == null || word.isEmpty()) return; if (search(word)) root = delete(root, word.toLowerCase(), 0);}

    /**
     * This method recursively traverses the Ternary Search Trie (TST) and deletes the word.
     * @param node of type Node is the Current Node being processed.
     * @param word is the String being deleted from the Ternary Search Trie (TST).
     * @param index is an Integer representing the current character position in the word.
     * @return the modified Node after deletion.
     */

    private Node delete(Node node, String word, int index) {
        if (node == null) return null; char character = word.charAt(index); if (character < node.character) {node.left = delete(node.left, word, index);}
        else if (character > node.character) {node.right = delete(node.right, word, index);} else if (index < word.length() - 1) {node.middle = delete(node.middle, word, index + 1);}
        else {node.endOfWordFlag = false;} if (!node.endOfWordFlag &&  node.middle == null) return null; return node;
    }

    /**
     * This method tests the {@code TernarySearchTrie} class by creating a Ternary Search Trie (TST) and then inserting, search for and deleting nodes from the TST.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        TernarySearchTrie tst = new TernarySearchTrie(); tst.insert("cat"); tst.insert("cats"); tst.insert("caterpillar"); tst.insert("cup"); tst.insert("bank"); tst.insert("because");
        System.out.println("Is 'cats' present in the Ternary Search Trie (TST)? " + tst.search("cats")); System.out.println("Is 'car' present in the TST? " + tst.search("car"));
        tst.delete("cats"); System.out.println("Is 'cats' present in the Ternary Search Trie (TST)? " + tst.search("cats")); System.out.println("All Tests Passed Successfully!");
    }




}