// Anson Chen
// 10/06/2025
// CSE 123 
// P0: Ciphers
// TA: Ishita
// This class represents a Caesar Shift cipher, a substitution cipher where each
// character is replaced by a character some number of positions down. 
public class CaesarShift extends Substitution {
    /**
     * Behavior: 
     * - Constructs a CaesarShift cipher object by createing an encoding key by shifting
     *   each character by `shift` amount, wrapping to start if necessary.
     * Exceptions: 
     * - Throws an IllegalArgumentException if the provided `shift` is negative.
     * Returns: 
     * - N/A (constructor)
     * Parameters: 
     * - shift: an integer representing the number of positions to shift each character.
     *   Must be non-negative.
     */
    public CaesarShift(int shift) {
        if (shift < 0) {
            throw new IllegalArgumentException("Shift amount cannot be negative.");
        }
        
        // Loop through all possible character values to create the shifted key
        char[] caesarShiftEncode = new char[TOTAL_CHARS];
        for (int i = 0; i < TOTAL_CHARS; i++) {
            // Calculate the new character position accounting for wrap-around 
            int shiftedChar = (i + shift) % (TOTAL_CHARS);
            caesarShiftEncode[i] = (char)(shiftedChar + MIN_CHAR);
        }
        // Converts char array to string and encodes the new key in the substitution superclass
        setEncoding(new String(caesarShiftEncode));
    }
}