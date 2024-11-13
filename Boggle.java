import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;

/*
 * The class represents a Boggle game. It maintains a board of tiles and a
 * dictionary of legal words. There are two implementations of dictionaries:
 * either as a list of words or as a trie data structure.
 */
public class Boggle {
    private final @NotNull Board<Character> board;
    private final @NotNull WordCollection dict;
    private final @NotNull HashSet<String> foundWords;

    /**
     * Constructs an instance of the game with the given board and dictionary.
     */
    public Boggle(@NotNull Board<Character> board, @NotNull WordCollection dict) {
        this.board = board;
        this.dict = dict;
        this.foundWords = new HashSet<>();
    }

    /**
     * Constructs an instance of the game with the given 2D array of characters
     * and dictionary.
     */
    @SuppressWarnings("unchecked")
    public Boggle(char[] @NotNull [] chars, @NotNull WordCollection dict) {
        Tile<Character>[] @NotNull [] tiles = new Tile[chars.length][chars.length];
        for (int r = 0; r < chars.length; r++) {
            for (int c = 0; c < chars.length; c++) {
                tiles[r][c] = new Tile<>(chars[r][c], r, c);
            }
        }
        this.board = new Board<>(tiles);
        this.dict = dict;
        this.foundWords = new HashSet<>();
    }

    /**
     * Constructs an instance of the game with a random board of the given size
     * and dictionary.
     */
    @SuppressWarnings("unchecked")
    public Boggle(int size, @NotNull WordCollection dict) {
        Tile<Character>[] @NotNull [] tiles = new Tile[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                char letter = (char) (new Random().nextInt(26) + 'A');
                tiles[r][c] = new Tile<>(letter, r, c);
            }
        }
        this.board = new Board<>(tiles);
        this.dict = dict;
        this.foundWords = new HashSet<>();
    }

    /**
     * The method takes a current position on the board a string representing a
     * prefix of a word that has been found so far. The character at the current
     * position is added to the prefix to form a new string. If that string is a
     * legal word in the dictionary (and is longer than two characters),
     * it is added to the set of found words. Then we check if the string is a
     * prefix of any word in the dictionary. If it is, we recursively call the
     * method on all the neighbors of the current position.
     * <p>
     * Two important notes:
     * <ul>
     *     <li> The words in the dictionary are all lower case, so we need to convert
     *     the string to lower case before checking if it is a legal word or a prefix.
     *     <li> Since the current tile is a neighbor of each of its neighbors, we need to
     *      mark the current tile as visited before calling the method recursively on
     *      its neighbors to avoid infinite loops. After the recursive call,
     *      we reset the tile to its original state.
     * </ul>
     */
    public void findWordsFromPos(@NotNull Tile<Character> tile, @NotNull String s) {

        String curr = s + tile;
        String lowercaseCurr = curr.toLowerCase();
        boolean wasFresh = tile.isFresh();
        tile.setVisited();

        if (lowercaseCurr.length() > 2 && dict.contains(lowercaseCurr)) {
            foundWords.add(lowercaseCurr);
        }

        if (dict.possiblePrefix(lowercaseCurr)) {
            for (@NotNull Tile<Character> neighbor : board.getNeighbors(tile.getRow(), tile.getCol()).toList()) {
                if (neighbor.isFresh()) {
                    findWordsFromPos(neighbor, curr);
                }
            }
        }

        if (wasFresh) {
            tile.reset();
        }
    }

    /**
     * The method finds all the words on the board by calling the findWordsFromPos
     * method on each tile on the board.
     */
    public void findWords() {
        foundWords.clear();
        for (@NotNull Tile<Character> tile : board) findWordsFromPos(tile, "");
    }

    public @NotNull HashSet<String> getFoundWords() {
        return foundWords;
    }

    public String toString() {
        return board.toString();
    }

    public void show () {
        @NotNull JDialog jf = new JDialog();
        jf.setModal(true);
        @NotNull JScrollPane panel = new JScrollPane(new BoardPanel<>(board));
        jf.setLayout(new BorderLayout());
        jf.add(BorderLayout.CENTER, panel);
        jf.pack();
        jf.setVisible(true);
    }

}
