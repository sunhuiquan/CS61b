package byog.Core;

/**
 * The coordinate is located with the bottom left point.
 */
public class Position {
    private int x;
    private int y;

    Position() {
        x = 0;
        y = 0;
    }

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
