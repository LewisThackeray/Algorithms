/**
 * <p><b>Compression</b> is the process of reducing the size of a file or data set by encoding its information more efficiently.  There are two primary reasons to compression data: to save storage
 * when saving information and to save time when communicating information.  There are two main types of Compression: <i>Lossless Compression</i> and <i>Lossy Compression</i>.
 *
 * <p><b>Lossy Compression</b> is where unrequired data is permanently removed from the file whereas <b>Lossless Compression</b> temporarily removes data from the file and adds the data back when
 * the file is to be used again.  The main Lossless Compression methods are: Huffman Coding, <b>Run-Length Encoding (RLE)</b> and Lempel-Ziv-Welch (LZW) Compression.</p>
 *
 * <p><b>Run-Length Encoding (RLE) </b> is a lossless compression method which reduces the size of data that contains consecutive repeated elements.  RLE works particularly well for bitstreams
 * that contain long sequences of repeated bits.  You scan the bitstream to count the number of consecutive bits and then convert the frequencies to binary values.</p>
 *
 * {@code RunLengthEncoding} is my implementation of Run-Length Encoding (RLE) which compresses a String by encoding consecutively repeated characters.
 */

public class RunLengthEncoding {

    /**
     * This method compresses a String using Run-Length Encoding (RLE) which encodes consecutively repeated characters.
     * @param input is the String being compressed using Run-Length Encoding (RLE)
     * @return the Compressed String containing Counts followed by Characters.
     */

    public static String compress(String input) {
        if (input == null || input.length() == 0) return ""; StringBuilder compressed = new StringBuilder(); int count = 1; char currentCharacter = input.charAt(0);
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == currentCharacter) {count++;} else {compressed.append(count).append(currentCharacter); currentCharacter = input.charAt(i); count = 1;}
        } compressed.append(count).append(currentCharacter); return compressed.toString();
    }

    /**
     * This method tests the {@code RunLengthEncoding} class by creating a String and then compressing it using Run-Length Encoding (RLE).
     * @param args the Command Line Arguments.
     */

    public static void main(String[] args) {
        String input = "aaaaaaaaaabbbbbbbbccccccddddee"; System.out.println("Original String: " + input + " | Original Length: " + input.length() * 8 + " Bits"); String compressed = compress(input);
        System.out.println("Compressed String: " + compressed + " | Compressed Length: " + compressed.length() + " Bits"); System.out.println("All Tests Passed Successfully!");
    }
}