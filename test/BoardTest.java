import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class BoardTest {

    long timeIt (@NotNull Runnable r) {
        long t0 = System.currentTimeMillis();
        r.run();
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }

    @Test
    void randomList () throws IOException {
        int size = 10;
        @NotNull WordList words = new WordList(new File("A8/commonwords.txt"));
        @NotNull Boggle game = new Boggle(size, words);
        game.show();

        long t = timeIt(game::findWords);
        System.out.printf("Found %d words in %d ms!%n", game.getFoundWords().size(), t);
        for (String w : game.getFoundWords()) System.out.println(w);
    }

    @SuppressWarnings("unchecked")
    @Test
    void smallComparison () throws IOException {
        Boggle game1, game2;

        @NotNull File file = new File("A8/commonwords.txt");
        @NotNull WordList words = new WordList(file);
        @NotNull Trie trie = new Trie(file);

        int size = 3;
        @NotNull String s = "valentine";
        Tile<Character>[] @NotNull [] tiles = new Tile[size][size];
        for (int r=0; r<size; r++)
            for (int c=0; c<size; c++)
                tiles[r][c] = new Tile<>(s.charAt(r*3+c),r,c);

        game1 = new Boggle(new Board<>(tiles),words);
        game2 = new Boggle(new Board<>(tiles),trie);

        game1.show();

        long t1 = timeIt(game1::findWords);
        long t2 = timeIt(game2::findWords);

        System.out.printf("slow --- Found %d words in %d ms!%n", game1.getFoundWords().size(), t1);
        System.out.printf("fast --- Found %d words in %d ms!%n", game2.getFoundWords().size(), t2);

        for (String fs : game1.getFoundWords()) System.out.println(fs);
    }
}
