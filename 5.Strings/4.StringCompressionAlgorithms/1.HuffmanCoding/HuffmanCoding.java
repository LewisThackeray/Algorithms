import java.util.Map; import java.util.HashMap; // Importing the Map Interface and HashMap Class from java.util which Records a Character's Frequency in the String to be Compressed.
import java.util.PriorityQueue; // Importing the PriorityQueue Class from java.util to Ensure the Two Nodes with the Smallest Frequencies are processed next.

/**
 * <p><b>Compression</b> is the process of reducing the size of a file or data set by encoding its information more efficiently.  There are two primary reasons to compression data: to save storage
 * when saving information and to save time when communicating information.  There are two main types of Compression: <i>Lossless Compression</i> and <i>Lossy Compression</i>.
 *
 * <p><b>Lossy Compression</b> is where unrequired data is permanently removed from the file whereas <b>Lossless Compression</b> temporarily removes data from the file and adds the data back when
 * the file is to be used again.  The main Lossless Compression methods are: <b>Huffman Coding</b>, Run-Length Encoding (RLE) and Lempel-Ziv-Welch (LZW) Compression.</p>
 *
 * <p><b>Huffman Coding</b> is a data compression technique that can save a substantial amount of space in natural language files (and other files) by encoding frequently used characters with
 * fewer bits than rarely used characters, thereby lowering the total number of bits used.  The first step of Huffman Coding is to count the frequency of each character in the data, then you create
 * a Priority Queue with each character in the data and its associated frequency.  You then build a Huffman Tree by extracting the two nodes with the smallest frequency and combine them into a new
 * node whose frequency is the the sum of the frequency of the two extracted nodes, and then you insert the new node into the priority queue.  You repeat this process until one node remains in the
 * priority queue, the root of the Huffmam Tree.  Now that you have successfully created the tree and all the nodes are in the correct position, you can now traverse the Huffman Tree to create a
 * Huffman Code for each node, starting at the root if you take a left fork you append 1 to the Huffman Code for that node and if you take the right fork you append a 0.  Each character's Huffman
 * Code is determined by the path taken from the root to the left representing the character.  You then encode the data by replacing each character in the data with its corresponding Huffam Code.
 * The Huffman Tree has to be saved or transmitted with the compressed data so that the compressed data can be decompressed.</p>
 *
 * {@code HuffmanCoding} is my implementation of Huffman Coding which compresses a String by encoding frequently used characters with fewer bits tham rarely used characters.
 */

public class HuffmanCoding {

    /**
     * This is an Inner-Class which implements a Node with the Huffman Tree, where each Node represents a Character and so contains the Character and the Character's Frequency in the String.
     */

    private static class Node implements Comparable<Node> {

        char character; int frequency; Node left, right; // Creating Class Attributes to Store the Character, Frequency and the Child Nodes of this Node in the Huffman Tree.

        /**
         * This is the Class Constructor which creates a Node within the Huffman Tree where each Node represents a Character and so Stores the Character's Frequency in the String, and its Children.
         * @param character is the Character represented by this Node in the Huffman Tree.
         * @param frequency is an Integer which Stores the Frequency of this Character in the String to be Compressed.
         */

        public Node(char character, int frequency) {this.character = character; this.frequency = frequency; this.left = null; this.right = null;}

        /**
         * This method compares this Node to another Node based on their Frequencies for Ordering in the Huffman Tree, ensuring the Node with the Lowest Frequency is Removed Next from the Priority
         * Queue.
         * @param other of type Node is the Node which we are comparing this Node to.
         * @return an Integer where a Negative Integer is returned if this Node's Frequency is Less Than the Other Node's Frequency, a Positive Integer if this Node's Frequency is More Than the
         * Other Node's Frequency or Zero if the Frequencies are Equal.
         */

        @Override public int compareTo(Node other) {return this.frequency - other.frequency;}

        /**
         * This method checks if a Node is a Leaf Node in the Huffman Tree.
         * @return a Boolean Value, where true is returned if this Node is a Leaf Node, false otherwise.
         */

        boolean isLeaf() {return left == null && right == null;}
    }

    /**
     * This method iterates through the String and records each Character's Frequency and stores the results in a Map where the Key is the Character and the Corresponding Value is the Frequency.
     * @param text is the String which we are Compressing using Huffman Coding.
     * @return a Map containing each Character in the String and the Character's Frequency.
     */

    private static Map<Character, Integer> getFrequencies(String text) {
        Map<Character, Integer> map = new HashMap<>(); for (char character : text.toCharArray()) {map.put(character, map.getOrDefault(character, 0) + 1);} return map;
    }

    /**
     * This method constructs the Huffman Tree from the Map returned from the getFrequencies() method.
     * @param a Map containing each Character in the String and the Character's Frequency.
     * @return the Root Node of the Huffman Tree.
     */

    private static Node getHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>(); for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {pq.offer(new Node(entry.getKey(), entry.getValue()));}
        while (pq.size() > 1) {
            Node left = pq.poll(); Node right = pq.poll(); Node parent = new Node('\0', left.frequency + right.frequency); parent.left = left; parent.right = right; pq.offer(parent);
        } return pq.poll();
    }

    /**
     * This method generates the Huffman Code for Characters in the String by Traversing the Huffman Tree.
     * @param root of type Node is the Root of the Huffman Tree.
     * @param code is a String which Stores the Current Huffman Code being Built.
     * @param codes is a Map of the Huffman Codes corresponding to their Characters where the Key is the Character and the Corresponding Value is that Character's Huffman Code.
     */

    private static void generateCodes(Node root, String code, Map<Character, String> codes) {
        if (root == null) return; if (root.isLeaf()) {codes.put(root.character, code.length() > 0 ? code : "1"); return;} generateCodes(root.left, code + "1", codes);
        generateCodes(root.right, code + "0", codes);
    }

    /**
     * This method compresses the Input String using Huffman Coding.
     * @param input of type String is the String to be Compressed.
     * @return the Compressed String as a Sequence of Bits.
     */

    public static String compress(String input) {
        if (input == null || input.length() == 0) return ""; Map<Character, Integer> frequencies = getFrequencies(input); Node root = getHuffmanTree(frequencies);
        Map<Character, String> huffmanCodes = new HashMap<>(); generateCodes(root, "", huffmanCodes); StringBuilder encoded = new StringBuilder();
        for (char character : input.toCharArray()) {encoded.append(huffmanCodes.get(character));} return encoded.toString();
    }

    /**
     * This method tests the {@code HuffmanCoding} Classs by Creating a String and then Compressing the String using Huffman Coding.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String input = "huffman coding example text"; System.out.println("Original Length: " + input.length() * 8 + " Bits"); String compressed = compress(input);
        System.out.println("Updated Length: " + compressed.length() + " Bits");
    }
}