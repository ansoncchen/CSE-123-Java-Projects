// Anson Chen
// 10/06/2025
// CSE 123 
// P0: Ciphers
// TA: Ishita
// This class implements a Caesar Key cipher, a substitution cipher that
// uses a keyword to an encoding key. The keyword form the beginning of the key, 
// followed by the remaining characters in alphabetical order.
import java.util.*;

public class CaesarKey extends Substitution {
    /**
     * Behavior: 
     * - Constructs a CaesarKey cipher generating a new encoding key based on the provided keyword.
     *   The key is formed by taking the unique characters from the input `key` string, and adding 
     *   afterwards all other valid characters in alphabetical order.
     * Exceptions: 
     * - Throws an IllegalArgumentException if the provided `key` is null.
     * - Throws an IllegalArgumentException if the `key` contains duplicate characters.
     * - Throws an IllegalArgumentException if the `key` contains characters outside valid range
     * Returns: 
     * - N/A (constructor)
     * Parameters: 
     * - key: The String keyword used to generate the encoding key.
     */
    public CaesarKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        
        Set<Character> checkChars = new HashSet<>(); // HashSet for O(1) lookup time
        char[] encodeKey = new char [TOTAL_CHARS];

        // Process the initial key and put into encoding key
        for (int i = 0; i < key.length(); i++) {
            char currentChar = key.charAt(i);
            // Validate that the character is within the allowed range and not a duplicate
            if (!isCharInRange(currentChar) || checkChars.contains(currentChar)) {
                throw new IllegalArgumentException("Invalid or duplicate character in key.");
            } 
            checkChars.add(currentChar);
            encodeKey[i] = currentChar;
        }

        // Fill in all remaining characters into encoding key
        int fillIndex = key.length(); // Start filling where the key left off
        char potentialChar = (char) MIN_CHAR;

        // Loop until encodeKey length matches the TOTAL_CHARS, indicating that the key is usable
        while (fillIndex < encodeKey.length) {
            // Check if the potential character was already used put into the key
            if (!checkChars.contains(potentialChar)) {
                encodeKey[fillIndex] = potentialChar;
                fillIndex++; // Move to the next fill index only when we add a character
            }
            potentialChar++; // Always move to the next character
        }

        // Converts char array to string and encodes the new key in the substitution superclass
        setEncoding(new String(encodeKey));
    }
}