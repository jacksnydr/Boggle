import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * The class represents a trie data structure.
 * It is used to store a collection of words and to search for words in the collection.
 * <p>
 * A trie is like the binary tree data structures we have seen. Instead of each node
 * having exactly zero or two children, each node has anywhere between
 * zero and 26 children. We could use an array of 26 elements to store the children,
 * but a Hashtable is more flexible and easier to use.
 * <p>
 * As a small example, let's see how to store the words "cat", "can", and "bat" in a trie.
 * The trie would look like this:
 * <pre>
 * { b= { a= { t= {}}},
 *   c= { a= { t={}, n={}}}
 * }
 *
 *                     root
 *                     /  \
 *                   b      c
 *                 /          \
 *               a              a
 *             /              /   \
 *           t               t     n
 *
 *
 *
 * </pre>
 * The root node has two children, one for 'b' and one for 'c'. The 'b' node has one child,
 * 'a', which has one child, 't', and so on. Note that 'ca' is shared by 'cat' and 'can' and
 * stored only once.
 * <p>
 * The class implements the WordCollection interface, which has two methods:
 * contains and possiblePrefix. The first method checks if a word is in the collection,
 * and the second checks if a word is a prefix of a word in the collection.
 * <p>
 * A small complication is that a string could be both a complete word and a
 * prefix of another word. For example, if the collection contains "cat" and "cats",
 * then "cat" is both a word and a prefix. The boolean instance variable endsHere
 * would be set to true for the node representing the last character of "cat" but
 * would be false for the nodes representing the 'c' and 'a' characters.
 *
 */
public class Trie implements WordCollection {
    private boolean endsHere;
    private final @NotNull Hashtable<Character,Trie> children;

    /**
     * Constructs an empty trie.
     */
    Trie () {
        this.endsHere = false;
        this.children = new Hashtable<>();
    }

    /**
     * Constructs a trie with the given words.
     */
    Trie (@NotNull String @NotNull [] words) {
        this();
        for (@NotNull String w : words) insert(w);
    }

    /**
     * Constructs a trie with the words in the given string.
     * The words are separated by whitespace.
     */
    Trie (@NotNull String words) {
        this(words.split("\\s+"));
    }

    /**
     * Constructs a trie with the words in the given file.
     * The words are separated by whitespace.
     */
    Trie (@NotNull File file) throws IOException {
        this();
        @NotNull Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) insert(scanner.next());
    }

    /**
     * Inserts a word into the trie. If the word is empty, the endsHere variable
     * is set to true. Otherwise, the first character of the word is used to find
     * the child node. If the child node does not exist, it is created with
     * an empty trie. The method is then called recursively to insert
     * the rest of the word.
     */
    void insert (@NotNull String s) {
        if (s.isEmpty()) {
            this.endsHere = true;
            return;
        }

        char firstChar = s.charAt(0);

        Trie child = children.get(firstChar);
        if (child == null) {
            child = new Trie();
            children.put(firstChar, child);
        }

        child.insert(s.substring(1));
    }

    /**
     * Searches if the given word is a full word or a prefix of a word in the trie
     * depending on the boolean fullWord. If the string is empty, there are two
     * possibilities: either we are looking for a full word and hence the endsHere
     * variable should be set, or we are looking for a prefix and then we succeed.
     * <p>
     * Otherwise, the first character of the
     * string is used to find the child node. If the child node does not exist, the
     * method returns false. Otherwise, the method is called recursively with the
     */
    boolean search (@NotNull String s, boolean fullWord) {
        if (s.isEmpty()) {
            return !fullWord || this.endsHere;
        }

        char firstChar = s.charAt(0);
        Trie child = children.get(firstChar);
        if (child == null) {
            return false;
        }

        return child.search(s.substring(1), fullWord);
    }

    public boolean contains(@NotNull String s) {
        return search(s,true);
    }

    public boolean possiblePrefix (@NotNull String s) {
        return search(s,false);
    }

    public String toString () {
        return children.toString();
    }
}
