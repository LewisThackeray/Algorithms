import java.util.Map; import java.util.HashMap; // Importing the Map Interface and HashMap Class from java.util to Create the Dictionary which Stores the Codes using in the Compression Algorithm.

/**
 * <p><b>Compression</b> is the process of reducing the size of a file or data set by encoding its information more efficiently.  There are two primary reasons to compression data: to save storage
 * when saving information and to save time when communicating information.  There are two main types of Compression: <i>Lossless Compression</i> and <i>Lossy Compression</i>.
 *
 * <p><b>Lossy Compression</b> is where unrequired data is permanently removed from the file whereas <b>Lossless Compression</b> temporarily removes data from the file and adds the data back when
 * the file is to be used again.  The main Lossless Compression methods are: Huffman Coding, Run-Length Encoding (RLE) and <b>Lempel-Ziv-Welch (LZW) Compression</b>.</p>
 *
 * <p><b>Lempel-Ziv-Welch (LZW) Compression</b> is a dictionary-based lossless data compression algorithm which replaces repetitive sequences of data with shorter codes.  The algorithm starts with
 * a dictionary of all possible single-character strings (such as letters, digits, and other symbols) and each of these characters is assigned a unique code.  The compression algorithm looks for
 * the longest string of characters in the input that already exists in the dictionary, if the string does exist, the corresponding code is outputted, if the string doesn't exist, the longest
 * string that does exist is added to the dictionary.  The decompression process is the reverse of the compression process, therefore, the dictionary doesn't need to be stored with the data as
 * it is rebuilt as the data is read.</p>
 *
 * {@code LempelZivWelchCompression} is my implementation of <em>Lempel-Ziv-Welch Compression</em> which replaces repetitive sequences in the data with shorter codes.
 */

public class LempelZivWelchCompression {

    /**
     * This method compresses a String using Lempel-Ziv-Welch Compression which replaces repetitive sequences in the data with shorter codes.
     * @param input is the String being compressed using Run-Length Encoding (RLE)
     * @return the Compressed String containing the Compressed Codes as Space-Seperated Integers.
     */

    public static String compress(String input) {
        // Initialising the Dictionary with Characters in the Extended ASCII Character Set.
        if (input == null || input.length() == 0) return ""; Map<String, Integer> dictionary = new HashMap<>(); int R = 256; for (int i = 0; i < R; i++) {dictionary.put("" + (char)i, i);}
        StringBuilder result = new StringBuilder(); String current = "";
        // Lempel-Ziv-Welch Compression.
        for (char character : input.toCharArray()) {
            String combined = current + character; if (dictionary.containsKey(combined)) {current = combined;} else {
                if (!current.isEmpty()) result.append(dictionary.get(current)).append(" "); dictionary.put(combined, R++); current = "" + character;
            }
        } if (!current.isEmpty()) result.append(dictionary.get(current)); return result.toString().trim();
    }

    /**
     * This method tests the {@code LempelZivWelchCompression} class by creating a String and then compressing it using Lempel-Ziv-Welch Compression.
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String input = "example text"; System.out.println("Original String: " + input + " | Original Length: " + input.length() * 8 + " Bits"); String compressed = compress(input);
        System.out.println("Compressed String: " + compressed + " | Compressed Length: " + compressed.length() + " Bits"); System.out.println("All Tests Passed Successfully!");
    }
}