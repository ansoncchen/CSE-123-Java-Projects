// Anson Chen
// 09/29/2025
// CSE 123 
// C0: Search Engine
// TA: Ishita

import java.io.*;
import java.util.*;

// This class allows users to find and rate books within BOOK_DIRECTORY
// containing certain terms
public class SearchClient {
    public static final String BOOK_DIRECTORY = "./books";
    private static final Random RAND = new Random();

    // Some class constants you can play around with to give random ratings to the uploaded books!
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;
    public static final int MIN_NUM_RATINGS = 1;
    public static final int MAX_NUM_RATINGS = 100;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        List<Media> media = new ArrayList<>(loadBooks());

        Map<String, Set<Media>> index = createIndex(media);

        System.out.println("Welcome to the CSE 123 Search Engine!");
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println("What would you like to do? [Search, Rate, Quit]");
            System.out.print("> ");
            command = console.nextLine();

            if (command.equalsIgnoreCase("search")) {
                searchQuery(console, index);
            } else if (command.equalsIgnoreCase("rate")) {
                addRating(console, media);
            } else if (!command.equalsIgnoreCase("quit")) {
                System.out.println("Invalid command, please try again.");
            }
        }
        System.out.println("See you next time!");
    }

    /**
     * Behavior:
     * - Creates an inverted index from a list of Media objects. The index maps each unique word 
     *   (token) to a set of all Media objects containing that word. All words are converted to 
     *   lowercase before they are added to the index, so this is case-insensitive.
     * Exceptions:
     * - N/A
     * Returns:
     * - Map<String, Set<Media>>: An inverted index mapping lowercase tokens to the
     *   sets of media where they appear.
     * Parameters:
     * - docs: A list of all Media objects to be indexed.
     */
    public static Map<String, Set<Media>> createIndex(List<Media> docs) {
        Map<String, Set<Media>> invertedIndex = new TreeMap<>();
        for (Media doc : docs) {
            for (String docContentToken : doc.getContent()) {
                docContentToken = docContentToken.toLowerCase();

                // This check if this is the first time we've seen this specific token, and adds
                // a new token key with its associated media that has that token if it is. 
                if (!invertedIndex.containsKey(docContentToken)) {
                    Set<Media> docWithToken = new HashSet<>();
                    docWithToken.add(doc);
                    invertedIndex.put(docContentToken, docWithToken);

                // The token already exists in the index, so we add the current media to its
                // matching token.
                } else {
                    invertedIndex.get(docContentToken).add(doc);
                }
            }
        }
        return invertedIndex;
    }

    /**
     * Behavior:
     * - This method allows the user to search for media based on a query string. It first attempts
     *   to find matches in titles and artists, and if no matches are found, it searches for matches
     *   in the content tokens. The query is also case-insensitive to match the search index. 
     * Returns:
     * - Set<Media>: a set of media that match the search query
     * Exceptions:
     * - if query is null or empty, returns an empty set
     * Parameters:
     * - index: an inverted index mapping terms to the Set of media containing those terms
     * - query: the search query string
     */
    public static Set<Media> search(Map<String, Set<Media>> index, String query) {
        //Handles null query case
        if (query == null) { 
            return new TreeSet<Media>();
        }

        //Handles empty query case
        String normalizedQuery = query.trim();
        if (normalizedQuery.isEmpty()) {
            return new TreeSet<Media>();
        }

        //Makes the query case-insensitive so it can match the case-insensitive search index/metadata
        normalizedQuery = normalizedQuery.toLowerCase();
        Set<Media> results = new TreeSet<Media>();

        //Primary Search for Title/Artist Match
        Set<Media> allValues = new HashSet<>();
        for (Set<Media> valueQueries : index.values()) {
            allValues.addAll(valueQueries);
        }
        for (Media value : allValues) {
            //Check for title match (case-insensitive)
            if (value.getTitle().toLowerCase().equals(normalizedQuery)) { 
                results.add(value);
            //Check for artist match only if no title match by looping through list of artists (also case-insensitive)
            } else { 
                for (String artist : value.getArtists()) {
                    if (artist.toLowerCase().equals(normalizedQuery)) {
                        results.add(value);
                    }   
                }
            }
        }
        
        // Secondary Fallback Search for Content Token Match (if no results from primary search)
        if (results.isEmpty()) {
            //Splits query into tokens based on whitespace
            for (String keyQuery : normalizedQuery.split("\\s+")) { 
                //checks if each token in query matches to something in the indexd
                if (index.containsKey(keyQuery)) {
                    Set<Media> contentQueries = index.get(keyQuery);
                    results.addAll(contentQueries);
                }
            }   
        }
        return results;
    }
    
    // Allows the user to search a specific query using the provided 'index' to find appropraite
    //  Media entries.
    //
    // Parameters:
    //   console - the Scanner to get user input from. Should be non-null
    //   index - an inverted index mapping terms to the Set of media containing those terms.
    //           Should be non-null
    public static void searchQuery(Scanner console, Map<String, Set<Media>> index) {
        System.out.println("Enter query:");
        System.out.print("> ");
        String query = console.nextLine();

        Set<Media> result = search(index, query);
        
        if (result.isEmpty()) {
            System.out.println("\tNo results!");
        } else {
            for (Media m : result) {
                System.out.println("\t" + m.toString());
            }
        }
    }

    // Allows the user to add a rating to one of the options wthin 'media'
    //
    // Parameters:
    //   console - the Scanner to get user input from. Should be non-null.
    //   media - list of all media options loaded into the search engine. Should be non-null.
    public static void addRating(Scanner console, List<Media> media) {
        for (int i = 0; i < media.size(); i++) {
            System.out.println("\t" + i + ": " + media.get(i).toString());
        }
        System.out.println("What would you like to rate (enter index)?");
        System.out.print("> ");
        int choice = Integer.parseInt(console.nextLine());
        if (choice < 0 || choice >= media.size()) {
            System.out.println("Invalid choice");
        } else {
            System.out.println("Rating [" + media.get(choice).getTitle() + "]");
            System.out.println("What rating would you give?");
            System.out.print("> ");
            int rating = Integer.parseInt(console.nextLine());
            media.get(choice).addRating(rating);
        }
    }

    // Loads all books from BOOK_DIRECTORY. Assumes that each book starts with two lines -
    //      "Title: " which is followed by the book's title
    //      "Author: " which is followed by the book's author
    //
    // Returns:
    //   A list of all book objects corresponding to the ones located in BOOK_DIRECTORY
    public static List<Media> loadBooks() throws FileNotFoundException {
        List<Media> ret = new ArrayList<>();
        
        File dir = new File(BOOK_DIRECTORY);
        for (File f : dir.listFiles()) {
            Scanner sc = new Scanner(f, "utf-8");
            String title = sc.nextLine().substring("Title: ".length());
            List<String> author = List.of(sc.nextLine().substring("Author: ".length()));

            Media book = new Book(title, author, sc);

            // Adds random ratings to 'book' based on the class constants. 
            // Feel free to comment this out.
            int minRating = RAND.nextInt(MAX_RATING - MIN_RATING + 1) + MIN_RATING;
            addRatings(minRating, Math.min(MAX_RATING,RAND.nextInt(MAX_RATING - minRating + 1) + minRating),
                        RAND.nextInt(MAX_NUM_RATINGS - MIN_NUM_RATINGS) + MIN_NUM_RATINGS, book);
            ret.add(book);
        }

        return ret;
    }

    // Adds ratings to the provided media numRatings amount of times. Each rating is a random int
    // between minRating and maxRating (inclusive).
    private static void addRatings(int minRating, int maxRating, int numRatings, Media media) {
        for (int i = 0; i < numRatings; i++) {
            media.addRating(RAND.nextInt(maxRating - minRating + 1) + minRating);
        }
    }
}
