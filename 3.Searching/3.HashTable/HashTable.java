import java.util.Random; // Importing Random from the java.util Package to Randomly Choose a Hash Function to Use from the Collection of Hash Functions.

/**
 * @author LewisThackeray
 * @date 27/03/2025
 *
 * {@code HashTable} implements a <em>Hash Table</em> which represents a Symbol Table of Generic Key: Value Pairs where the <em>Hash Table</em> is implemented as a Pair of Parallel Arrays where
 * the Key in keys[i] has a corresponding Value in values[i].  {@code HashTable} uses Linear Probing as the Collision Resolution Technique.
 *
 * <p><i>When a collision occurs, linear probing resolves it by checking the next available slot in the hash table.  It searches linearly through the table by incrementing the index by one for
 * each subsequent probe wrapping around to the beginning of the table if necessary.  The equation for linear probing is: <b>h(k,i) = (h'(k) + i) % m</b> where <b>h(k,i)</b> is the probe position,
 * <b>h'(k)</b> is the hash value for key k, <b>i</b> is the probe number, <b>k</b> is the key and <b>m</b> is the number of slots in the hash table.</i></p>
 *
 * <p><i>There are three primary methods to create hash functions: the multiplication method, the division method and universal hashing.  The problem with the multiplication and division methods
 * is that you have to be careful with your choices for the number of slots in the hash table (m), for the division method, and the constant A, for the multiplication method.  If A and m are
 * chosen so that all the keys hash to the same slot, the time complexity of the dictionary operations rise from O(1) to O(n).  Therefore, a Denial of Service (DoS) Attack can pick values for A
 * and m which cause all of the keys to hash to the same slot. To overcome this, universal hashing should be used.
 *
 * Universal Hashing selects a hash function randomly from a collection of hash functions at runtime.  Due to the randomisation, the algorithm can behave differently on each execution, even for
 * the same input.  This approach guarantees good average-case performance, regardless of what keys are provided as the input to the hash function.  There are more hash functions in the colleciton
 * than the number of slots in the hash table and the colleciton is said to be universal if: for any two distinct keys, the probability that they hash to the same slot, using a hash function
 * randomly chosen from the collection is 1/m where m is the number of slots in the hash table.</i></p>
 *
 * @param Key is the type of keys maintained by the Symbol Table, the keys must be Comparable.
 * @param Value is the type of mapped values.
 */

public class HashTable<Key extends Comparable<Key>, Value> {

    private static final int INITIAL_CAPACITY = 16; // Creating a Constant for the Initial Capacity of the Hash Table.
    private Key keys[]; private Value values[]; // Creating Parallel Arrays to Store the Key: Value Pairs in the Symbol Table Implementation.
    private int numberOfPairs; private int numberOfSlots; // Creating Variables to Store the Number of Pairs and the Number of Slots in the Hash Table.
    private int param1, param2; // Creating Variables to Store the Parameters for Universal Hashing.

    /**
     * This is an Overloaded Class Constructor which Initialises an Empty Hash Table with INITIAL_CAPACITY Slots.
     */

    public HashTable() {this(INITIAL_CAPACITY);}

    /**
     * This is an Overloaded Class Constructor which Initialises an Empty Hash Table with the Specified Capacity and Randomly Generates the Parameters for Universal Hashing.
     * @param capacity is an Integer specfying the Number of Slots in the Hash Table.
     * @throws IllegalArgumentException if the capacity is invalid integer.
     */

    @SuppressWarnings("unchecked") public HashTable(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("The Capacity cannot be Negative!");
        numberOfSlots = capacity; numberOfPairs = 0; keys = (Key[]) new Comparable[capacity]; values = (Value[]) new Comparable[capacity];
        Random random = new Random(); param1 = random.nextInt(numberOfSlots - 1) + 1; param2 = random.nextInt(numberOfSlots); // Initialising the Universal Hashing Parameters.
    }

    /**
     * This method returns the Hash Value for a Specified Key using Universal Hashing.
     * @param key of type Key is the Key to be Hashed.
     * @return an Integer representing the Slot where the Key: Value Pair will be Stored in the Hash Table (between 0 and numberOfSlots - 1).
     */

    private int hash(Key key) {int primeNumber = 15485863; return (((param1 * key.hashCode() + param2) % primeNumber) + primeNumber) % numberOfSlots;}

    /**
     * This method resizies the Hash Table to the Specified Capacity.
     * @param newCapacity is an Integer Specifying the Updated Number of Slots in the Hash Table.
     */

    @SuppressWarnings("unchecked") private void resize(int newCapacity) {
        HashTable<Key, Value> temp = new HashTable<>(newCapacity); for (int i = 0; i < numberOfSlots; i++) {if (keys[i] != null) temp.insert(keys[i], values[i]);}
        keys = temp.keys; values = temp.values; numberOfSlots = temp.numberOfSlots;
    }

    /**
     * This method inserts a Key: Value Pair into the Hash Table, however, if the Key already exists in the Hash Table, its Value is Updated with the New Value.
     * @param key of type Key is the Key to be Inserted into the Hash Table.
     * @param value of type Value is the corresponding Value to be Inserted into the Hash Table.
     * @throws IllegalArgumentException if the Key to be Inserted into the Hash Table is NULL.
     */

    public void insert(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!"); if (value == null) {delete(key); return;} // Deleting the Key from the Hash Table if the Value is NULL.
        if (numberOfPairs >= numberOfSlots / 2) resize(2 * numberOfSlots); // If the Hash Table is 50% Full, Double the Table Size.
        int i = hash(key); while(keys[i] != null) {if (keys[i].equals(key)) {values[i] = value; return;} i = (i + 1) % numberOfSlots;} keys[i] = key; values[i] = value; numberOfPairs++;
    }

    /**
     * This method returns the Value associated with a Key in a Key: Value Pair in the Hash Table.
     * @param key of type Key is the Key whose Value is to be Returned.
     * @return a Value which corresponds to the Key passed into the method in the Key: Value Pair, or NULL is returned if the Key is not in the Hash Table.
     * @throws an IllegalArgumentException if the Key passed into the method is NULL.
     */

    public Value search(Key key) {
        if (key == null) throw new IllegalArgumentException("The Key cannot be NULL!");
        int i = hash(key); while(keys[i] != null) {if (keys[i].equals(key)) return values[i]; i = (i + 1) % numberOfSlots;} return null;
    }

    /**
     * This method removes the Specified Key and its associated Value from the Hash Table.
     * @param key of type Key is the Key of the Key: Value Pair to be Removed from the Hash Table.
     * @throws IllegalArgumentException if the Key passed into the method is NULL.
     */

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("The Key cannot be NULL."); if (!contains(key)) return;
        int slot = hash(key); while (!key.equals(keys[slot])) {slot = (slot + 1) % numberOfSlots;} keys[slot] = null; values[slot] = null; numberOfPairs--; // Finding and Deleting the Pair.
        slot = (slot + 1) % numberOfSlots; while (keys[slot] != null) {
            Key keyToRehash = keys[slot]; Value valueToRehash = values[slot]; keys[slot] = null; values[slot] = null; numberOfSlots--; insert(keyToRehash, valueToRehash);
            slot = (slot + 1) % numberOfSlots;
        }
        if (numberOfPairs > 0 && numberOfPairs <= numberOfSlots / 8) resize(numberOfSlots / 2); // If the Hash Table is only 1/8 Full, Halve the Table Size.
    }

    /**
     * This method tests if a Specific Key is present within a Hash Table.
     * @param key of type Key is the Key to Check Memebership of in the Hash Table.
     * @return a Boolean Value where true is returned if the Hash Table contains the Speficied Key, false if the Specified Key is not present in the Hash Table.
     * @throws IllegalArgumentException if the Key passed into the method is NULL.
     */

    public boolean contains(Key key) {if (key == null) throw new IllegalArgumentException("The Key cannot be NULL."); return search(key) != null;}

    /**
     * This method returns the Number of Key: Value Pairs in the Hash Table.
     * @return an Integer representing the Number of Key: Value Pairs in the Hash Table.
     */

    public int size() {return numberOfPairs;}

    /**
     * This method tests if the Hash Table is Empty.
     * @return a Boolean Value where true is returned if the Hash Table is Empty, false otherwise.
     */

    public boolean isEmpty() {return size() == 0;}

    /**
     * This method tests the {@code HashTable} Class which Implements a Symbol Table as a Hash Table - these Unit Tests test the Class by Inserting, Searching and Deleting Nodes in the Hash
     * Table.
     * @param args the Command-Line Arguments.
     */

    public static void main(String[] args) {
        HashTable<Integer, String> hashTable = new HashTable<>(); hashTable.insert(1, "Apple"); hashTable.insert(2, "Orange"); hashTable.insert(5, "Strawberry"); hashTable.insert(87, "Banana");
        hashTable.insert(8463, "Blueberry"); System.out.println("The Value of Key 2 is: " + hashTable.search(2)); hashTable.delete(8463); System.out.println("All Tests Passed Successfully!");
    }
}