// Anson Chen
// 10/06/2025
// CSE 123
// P0: Ciphers
// TA: Ishita
// This class represents a cipher that applies multiple other ciphers to encrypt
// and decrypt text based on a list of ciphers, creating more complex ciphers.
import java.util.*;

public class MultiCipher extends Cipher {
    private List<Cipher> ciphers;

    /**
     * Behavior: 
     * - Constructs a new MultiCipher object with a specified list of ciphers.
     *   The ciphers will be applied the order they appear in the list for
     *   encryption, and reverse for decryption.
     * Exceptions: 
     * - Throws an IllegalArgumentException if the provided list of ciphers is null.
     * Returns: 
     * - N/A (constructor)
     * Parameters: 
     * - ciphers: A List of Cipher objects to be used for encryption/decryption.
     */
    public MultiCipher(List<Cipher> ciphers) {
        if (ciphers == null) {
            throw new IllegalArgumentException("Cipher list cannot be null.");
        }
        // Creates a defensive copy of the list
        this.ciphers = new ArrayList<>(ciphers);
    }

    /**
     * Behavior: 
     * - Encrypts the input string by applying each cipher in the order provided 
     *   to the constructor.
     * Exceptions: 
     * - Throws an IllegalArgumentException if the input string is null.
     * Returns: 
     * - String: The encrypted version of the input string.
     * Parameters: 
     * - input: The String that will be encrypted.
     */
    public String encrypt(String input) {
        //checks if valid input
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }

        String result = input;
        // Encrypts the string using all the ciphers in order
        for (Cipher cipher : ciphers) {
            result = cipher.encrypt(result);
        }
        return result;
    }

    /**
     * Behavior: 
     * - Decrypts the input string by applying each cipher in reverse order.
     * Exceptions: 
     * - Throws an IllegalArgumentException if the input string is null.
     * Returns: 
     * - String: The decrypted version of the input string.
     * Parameters: 
     * - input: The String that will be decrypted.
     */
    public String decrypt(String input) {
        //checks if valid input
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }

        String result = input;
        // Decryption must be the reverse order of encryption
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            result = ciphers.get(i).decrypt(result);
        }
        return result;
    }
}