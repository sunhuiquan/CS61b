package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Stack;
import org.junit.rules.DisableOnDebug;

import java.util.Random;

/**
 * To generate a puzzle ultimately.
 */
public class Puzzle {
    public static void generatePuzzle(TETile[][] world, long seed) {
        initializerPuzzle(world);
        connectedPuzzle(world, seed);
    }

    private static void initializerPuzzle(TETile[][] world) {
        for (int i = 1; i < Game.WIDTH - 1; i += 2) {
            for (int j = 1; j < Game.HEIGHT - 1; j += 2) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.WATER;
                }
            }
        }
        for (int i = 1; i < Game.WIDTH - 1; i++) {
            for (int j = 1; j < Game.HEIGHT - 1; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    private static void connectedPuzzle(TETile[][] world, long seed) {
        Stack<Position> stack = new Stack<>();
        Position pos = new Position(1, 1);
        stack.push(pos);

        while (!stack.isEmpty()) {
            pos = stack.pop();
            int x = pos.getX();
            int y = pos.getY();

            Random ran = new Random(seed);
            while (!isDeadEnd(world, pos)) {
                int cho = RandomUtils.uniform(ran, 4); // Only can be 0, 1, 2, 3
                switch (cho) {
                    case 0: // up
                        break;
                    case 1: // down
                        break;
                    case 2: // left
                        break;
                    case 3: // right
                        break;
                }
            }
        }
    }

    private static boolean isDeadEnd(TETile[][] world, Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (((y + 1 < Game.HEIGHT) && (world[x][y + 1] == Tileset.WATER)) ||
                ((y - 1 > 0) && (world[x][y - 1] == Tileset.WATER)) ||
                ((x + 1 < Game.WIDTH) && (world[x + 1][y] == Tileset.WATER)) ||
                ((x - 1 > 0) && (world[x - 1][y] == Tileset.WATER))) {
            return false;
        }
        return true;
    }
}
