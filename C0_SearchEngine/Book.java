// Anson Chen
// 09/29/2025
// CSE 123
// C0: Search Engine
// TA: Ishita
import java.util.*;

// This class represents a single book, a type of media, managing its metadata and ratings. 
// Book objects can be compared to one another based on their ratings. 
public class Book implements Media, Comparable<Book> {
    private final String title;
    private final List<String> artists;
    private final List<String> content;
    private final List<Integer> bookRatings;

    /**
     * Behavior:
     * - Constructs a new Book object.
     * Exceptions:
     * - N/A
     * Returns:
     * - N/A (constructor)
     * Parameters:
     * - title: The title of the book.
     * - authors: A list of the book's authors.
     * - content: A Scanner containing the full content of the book, which will be read and stored
     *            word by word.
     */
    public Book(String title, List<String> authors, Scanner content)  {
        this.title = title;
        this.artists = new ArrayList<>(authors);
        this.content = new ArrayList<>();
        this.bookRatings = new ArrayList<>();
        //loops through content to add to the content list seperated by whitespace
        while (content.hasNext()) {
            this.content.add(content.next());
        }
    }

    /**
     * Behavior:
     * - Returns the title of this book object.
     * Exceptions:
     * - N/A
     * Returns:
     * - String: The title of this book.
     * Parameters:
     * - N/A
     */
    public String getTitle() {
        return title;
    }

    /**
     * Behavior:
     * - Returns a copy of the list of authors for this book object.
     * Exceptions:
     * - N/A
     * Returns:
     * - List<String>: A new list containing all authors of this book.
     * Parameters:
     * - N/A
     */
    public List<String> getArtists() {
        return List.copyOf(artists);
    }

    /**
     * Behavior:
     * - Returns a copy of the content of this book as a list of words. The list is exactly the 
     *   same as read but its seperated out based on whitespace for each word.
     * Exceptions:
     * - N/A
     * Returns:
     * - List<String>: A new list containing the words from the book's content.
     * Parameters:
     * - N/A
     */
    public List<String> getContent() {
        return List.copyOf(content);
    }

    /**
     * Behavior:
     * - Adds a new rating to this book's list of ratings.
     * Exceptions:
     * - Throws an IllegalArgumentException if the provided score is negative.
     * Returns:
     * - N/A
     * Parameters:
     * - score: The numerical rating to add.
     */
    public void addRating(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score must be non-negative");
        }
        bookRatings.add(score);
    }

    /**
     * Behavior:
     * - Returns the total number of ratings this book has received.
     * Exceptions:
     * - N/A
     * Returns:
     * - int: The count of ratings.
     * Parameters:
     * - N/A
     */
    public int getNumRatings() {
        return bookRatings.size();
    }

    /**
     * Behavior:
     * - Calculates the average of all ratings for this book.
     * Exceptions:
     * - N/A
     * Returns:
     * - double: The average rating. Returns 0.0 if the book has no ratings.
     * Parameters:
     * - N/A
     */
    public double getAverageRating() {
        if (bookRatings.isEmpty()) {
            return 0.0;
        }
        
        //averages the ratings
        double sum = 0;
        for (Integer rating : bookRatings) {
            sum += rating;
        }
        return sum / bookRatings.size();
    }

    /**
     * Behavior:
     * - Returns a string representation of this book, including its title, authors, and average
     *   rating if any ratings exist.
     * Exceptions:
     * - N/A
     * Returns:
     * - String: The formatted string representation. For example,
     *           "Title by [Author]: 4.50 (10 ratings)".
     * Parameters:
     * - N/A
     */
    @Override
    public String toString() {
        //creates a base string that every book should be able to return
        String returnString = title + " by " + artists.toString();
        // adds the ratings to the base string if there are ratings
        if (!bookRatings.isEmpty()) {
            returnString += ": " + String.format("%.2f", getAverageRating()) +
                " (" + getNumRatings() + " ratings)";
        }
        return returnString;
    }

    /**
     * Behavior:
     * - Compares this book to another book. The comparison is based first on the average rating
     *   (higher is better). If the average ratings are equal, it then compares based on the number
     *   of ratings (more is better).
     * Exceptions:
     * - If no ratings exist for either book, they are considered equal and 0 is returned.
     * Returns:
     * - int: A positive integer if this book is "greater than" the other book, a negative integer 
     *        if it is "less than," and zero if they are equal.
     * Parameters:
     * - otherBook: The other Book object to compare against.
     */
    @Override
    public int compareTo(Book otherBook) {
        // compares average ratings first
        if (this.getAverageRating() > otherBook.getAverageRating()) {
            return 1;
        } else if (this.getAverageRating() < otherBook.getAverageRating()) {
            return -1;
        // then compares number of ratings if average ratings are the same
        } else if (this.getNumRatings() > otherBook.getNumRatings()) {
            return 1;
        } else if (this.getNumRatings() < otherBook.getNumRatings()) {
            return -1;
        // they are considered equal or no ratings exist for either book
        } else {
            return 0;
        }
    }
}