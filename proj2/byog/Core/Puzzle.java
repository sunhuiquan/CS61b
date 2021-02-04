package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Stack;

/**
 * To generate a puzzle ultimately.
 */
public class Puzzle {
    public static void generatePuzzle(TETile[][] world) {
        initializerPuzzle(world);
        connectedPuzzle(world);
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

    private static void connectedPuzzle(TETile[][] world) {
        Stack<Position> stack = new Stack<>();
        Position pos = new Position(1, 1);
        stack.push(pos);


    }
}
