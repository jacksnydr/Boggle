import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * The class represents an inefficient collection of words maintained as a list.
 * <p>
 * The class implements the WordCollection interface, which has two methods:
 * contains and possiblePrefix. The first method checks if a word is in the collection,
 * and the second checks if a word is a prefix of a word in the collection.
 */
public class WordList implements WordCollection {
    private final @NotNull List<String> words;

    /**
     * Constructs a word list with the given list of words.
     */
    public WordList (@NotNull List<String> words) {
        this.words = words;
    }

    /**
     * Constructs a word list with the given array of words.
     */
    public WordList (@NotNull String[] words) {
        this(List.of(words));
    }

    /**
     * Constructs a word list with the words in the given string.
     */
    public WordList (@NotNull String words) {
        this(words.split("\\s+"));
    }

    /**
     * Constructs a word list with the words in the given file.
     */
    public WordList (@NotNull File file) throws IOException {
        this(Files.readAllLines(file.toPath()));
    }

    /**
     * Returns true if the word is in the collection. This will be inefficient
     * (O(N)) for large collections.
     */
    public boolean contains (@NotNull String w) {
        return words.contains(w);
    }

    /**
     * Returns true if the word is a prefix of a word in the collection.
     * This will be inefficient (O(N)) for large collections.
     */
    public boolean possiblePrefix (@NotNull String w) {
        for (String word : words) {
            if (word.startsWith(w)) {
                return true;
            }
        }
        return false;
    }
}
