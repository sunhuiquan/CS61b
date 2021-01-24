package byog.lab5;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        int row = world.length;
        int col = world[0].length;
        int xOff = p.getX();
        int yOff = p.getY();
        int maxLen = s + 2 * (s - 1);

        for (int h = yOff; h < yOff + s; h++) {
            for (int w = xOff; w < xOff + maxLen; w++) {
                int blank = (maxLen - s - 2 * (h - yOff)) / 2;
                if ((w >= (xOff + blank)) && (w + blank < maxLen)) {
                    world[w][h] = t;
                }
            }
        }

        for (int h = yOff + s; h < yOff + 2 * s; h++) {
            for (int w = xOff; w < xOff + maxLen; w++) {
                int blank = h - yOff - s;
                if ((w >= (xOff + blank)) && ((w + blank < maxLen))) {
                    world[w][h] = t;
                }
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(0, 0);
        addHexagon(world, p, 4, Tileset.WATER);

        ter.renderFrame(world);
    }
}
