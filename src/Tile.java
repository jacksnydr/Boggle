import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * The class Tile represents a single tile on a board.
 * It is a generic class that takes a type parameter E.
 * <p>
 * The class has a visited field that is used to mark the tile as visited
 * when traversing the board.
 */
public class Tile<E> {
    private final @NotNull E data;
    private final int row, col;
    private boolean visited;

    Tile(@NotNull E data, int row, int col) {
        this.data = data;
        this.row = row;
        this.col = col;
        this.visited = false;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    void reset() {
        visited = false;
    }

    boolean isFresh() {
        return !visited;
    }

    void setVisited() {
        this.visited = true;
    }

    public String toString() {
        return data.toString();
    }

    public void paint(@NotNull Graphics2D g2, Rectangle2D.@NotNull Double r, int scaleFactor) {
        g2.draw(r);
        String s = data.toString();

        @NotNull Font font = g2.getFont().deriveFont(16f);
        @NotNull AffineTransform at = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        g2.setFont(font.deriveFont(at));

        FontMetrics fm = g2.getFontMetrics();
        double posx = r.x + ((r.width - fm.stringWidth(s)) / 2);
        double posy = r.y + (((r.height - fm.getHeight()) / 2) + fm.getAscent());
        g2.drawString(s, (int) posx, (int) posy);
    }

}
