import org.jetbrains.annotations.NotNull;

public interface WordCollection {
    boolean contains (@NotNull String w);
    boolean possiblePrefix (@NotNull String w);
}

