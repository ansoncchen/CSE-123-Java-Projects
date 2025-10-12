// Anson Chen
// 09/29/2025
// CSE 123 
// C0: Search Engine
// TA: Ishita

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("Book string, list constructor")
    public void testBookStringList() {
        Book book = new Book("Title", List.of("Author 1", "Author 2"), new Scanner("Content"));

        // Testing getTitle returns correct title
        assertEquals("Title", book.getTitle());

        // Testing getArtists returns correct list of authors
        assertEquals(List.of("Author 1", "Author 2"), book.getArtists());

        // Testing toString returns correct format when no ratings added
        assertEquals("Title by [Author 1, Author 2]", book.toString());

        // Testing getContent returns correct content
        assertEquals(List.of("Content"), book.getContent());
    }

    @Test
    @DisplayName("getNumRatings")
    public void testNumRatings() {
        Book book = new Book("Title", List.of("Author"), new Scanner("Content"));
        
        // Initially, there should be no ratings
        assertEquals(0, book.getNumRatings());

        book.addRating(1);

        // Now, there should be one rating
        assertEquals(1, book.getNumRatings());

        book.addRating(1);

        // Now, there should be two ratings
        assertEquals(2, book.getNumRatings());
        
    }

    @Test
    @DisplayName("getAvgRating")
    public void testAvgRatings() {
        Book book = new Book("Title", List.of("Author"), new Scanner("Content"));

        // Initially, average rating should be 0.0 since there are no ratings
        assertEquals(0.0, book.getAverageRating());

        book.addRating(4);

        // Now, average rating should be 4
        assertEquals(4.0, book.getAverageRating());

        book.addRating(5);

        // Now, average rating should be 4.5
        assertEquals(4.5, book.getAverageRating());
        
    }

    @Test
    @DisplayName("createIndex tests")
    public void testInvertedIndex() {
        Book mistborn = new Book("Mistborn", List.of("Brandon Sanderson"),
                                 new Scanner("Epic fantasy worldbuildling content"));
        Book farenheit = new Book("Farenheit 451", List.of("Ray Bradbury"),
                                  new Scanner("Realistic \"sci-fi\" content"));
        Book hobbit = new Book("The Hobbit", List.of("J.R.R. Tolkein"),
                               new Scanner("Epic fantasy quest content"));
        
        List<Media> books = List.of(mistborn, farenheit, hobbit);
        Map<String, Set<Media>> index = SearchClient.createIndex(books);
        
        // Testing that the index contains expected keys
        assertFalse(index.containsKey("sci-fi"));
        assertTrue(index.containsKey("\"sci-fi\""));

        // Testing that the index maps words to correct book sets
        Set<Book> expected = Set.of(mistborn, hobbit);
        assertEquals(expected, index.get("fantasy"));
    }
}
