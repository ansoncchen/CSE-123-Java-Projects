// Anson Chen
// 10/06/2025
// CSE 123 
// P0: Ciphers
// TA: Ishita
// This class provides implements substitution cipher, where each character
// in the plaintext is replaced with a corresponding character from an encoding key.
// It can serve as a base class for more specific types of substitution ciphers.
import java.util.*;

public class Substitution extends Cipher {
    private char[] encode; // Stores encryption mapping
    private char[] decode; // Stores decryption mapping

    /**
     * Behavior:
     * - Constructs a new, uninitialized Substitution cipher.
     * Exceptions: N/A
     * Returns: N/A (constructor)
     * Parameters: N/A
     */
    public Substitution() {
        encode = null;
        decode = null;
    }

    /**
     * Behavior:
     * - Constructs a new Substitution cipher using the provided string as the
     *   encoding key.
     * Exceptions:
     * - Throws an IllegalArgumentException if the provided cipher string is
     *   invalid based on setEncoding.
     * Returns: N/A (constructor)
     * Parameters:
     * - cipher: The String representing the encoding key.
     */
    public Substitution(String cipher) {
        setEncoding(cipher);
    }

    /**
     * Behavior:
     * - Configures the cipher's encoding and decoding keys based on the provided
     *   key. It checks the alphabet to ensure it's correct length and contains
     *   no duplicate or invalid characters.
     * Exceptions:
     * - Throws an IllegalArgumentException if the cipher string is null,
     * - Throwa an IllegalArgumentException if the cipher string is not equal to TOTAL_CHARS
     * - Throws an IllegalArgumentException if the cipher string contains duplicate or 
     *   out-of-range characters.
     * Returns: N/A
     * Parameters:
     * - cipher: The String representing the substitution alphabet.
     */
    public void setEncoding(String cipher) {
        if (cipher == null || cipher.length() != TOTAL_CHARS) {
            throw new IllegalArgumentException("Cipher key is invalid.");
        }

        this.encode = new char[cipher.length()];
        this.decode = new char[cipher.length()];
        Set<Character> duplicates = new HashSet<>(); // HashSet for O(1) lookup time

        for (int i = 0; i < cipher.length(); i++) {
            char currentChar = cipher.charAt(i);

            // Check for invalid or duplicate characters 
            if (!isCharInRange(currentChar) || duplicates.contains(currentChar)) {
                throw new IllegalArgumentException("Invalid or duplicate char in key.");
            }

            //Sets encode key so that accessing the index of the original characters 
            //will pull up the encoding characters
            encode[i] = currentChar;

            //Sets decode key so that accessing the index of the encoding character 
            //will pull up the original characters
            decode[(int)currentChar - MIN_CHAR] = (char) (i + MIN_CHAR);
            
            //Adds to the duplicates set to make sure it doesn't reappear in the cipher
            duplicates.add(currentChar);
        }
    } 

    /**
     * Behavior:
     * - Encrypts a given string by substituting each character with its
     *   corresponding character from the encoding key.
     * Exceptions:
     * - Throws an IllegalArgumentException if the input string is null.
     * - Throws an IllegalStateException if the encoding key has not been set.
     * Returns:
     * - String: The encrypted version of the input string.
     * Parameters:
     * - input: The String that will be encrypted.
     */
    public String encrypt(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        if (encode == null) {
            throw new IllegalStateException("Encoding key not set.");
        }

        char[] result = new char[input.length()];
        for (int i = 0; i < result.length; i++) {
            // Finds the original character from the encode array
            result[i] = encode[input.charAt(i) - MIN_CHAR];
        }
        return new String(result);
    }

    /**
     * Behavior:
     * - Decrypts a given string by substituting each character with its
     *   corresponding character from the decoding key.
     * Exceptions:
     * - Throws an IllegalArgumentException if the input string is null.
     * - Throws an IllegalStateException if the encoding key has not been set.
     * Returns:
     * - String: The decrypted version of the input string.
     * Parameters:
     * - input: The String that will be decrypted.
     */
    public String decrypt(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        if (decode == null) {
            throw new IllegalStateException("Encoding key not set.");
        }

        char[] result = new char[input.length()];
        for (int i = 0; i < result.length; i++) {
            // Finds the original character from the decode array
            result[i] = decode[input.charAt(i) - MIN_CHAR];
        }
        return new String(result);
    }
}