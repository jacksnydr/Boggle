import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class GamesTest {
    private static Trie dict;

    long timeIt (@NotNull Runnable r) {
        long t0 = System.currentTimeMillis();
        r.run();
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }

    void report (@NotNull Boggle game, long t) {
        @NotNull HashSet<String> ws = game.getFoundWords();
        System.out.printf("Found %d words in %d ms!%n", ws.size(), t);
        for (String w : ws) System.out.println(w);
    }

    @BeforeAll
    static void setup () throws IOException {
        dict = new Trie(new File("A8/commonwords.txt"));
    }

    @Test
    void game1() {
        char[] @NotNull [] chars = {
                {'E', 'I', 'L', 'A'},
                {'T', 'P', 'A', 'G'},
                {'R', 'E', 'T', 'O'},
                {'H', 'T', 'A', 'Y'}
        };

        @NotNull Boggle game = new Boggle(chars, dict);
        System.out.println(game);
        game.show();
        report(game, timeIt(game::findWords));
    }

}
