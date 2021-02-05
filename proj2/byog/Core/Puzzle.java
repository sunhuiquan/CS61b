package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Stack;
import org.junit.rules.DisableOnDebug;

import javax.imageio.plugins.tiff.TIFFTagSet;
import java.util.Random;
import java.util.TreeSet;

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
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    private static void connectedPuzzle(TETile[][] world, long seed) {
        Random ran = new Random(seed);
        Stack<Position> stack = new Stack<>();
        while (true) {
            Position pos = findWater(world);
            if (pos == null) {
                return;
            }
            stack.push(pos);
            world[pos.getX()][pos.getY()] = Tileset.FLOOR;

            while (!stack.isEmpty()) {
                pos = stack.pop();
                int x = pos.getX();
                int y = pos.getY();

                boolean flag = true;
                while (!isDeadEnd(world, pos) && flag) {
                    int cho = RandomUtils.uniform(ran, 4); // Only can be 0, 1, 2, 3
                    switch (cho) {
                        case 0: // up
                            if ((y + 2 < Game.HEIGHT) && (world[x][y + 2] == Tileset.WATER)) {
                                world[x][y + 1] = Tileset.FLOOR;
                                world[x][y + 2] = Tileset.FLOOR;
                                if (!isDeadEnd(world, pos)) {
                                    stack.push(pos);
                                }
                                pos.setX(x);
                                pos.setY(y + 2);
                                stack.push(new Position(x, y + 2));
                                flag = false;
                            }
                            break;
                        case 1: // down
                            if ((y - 2 > 0) && (world[x][y - 2] == Tileset.WATER)) {
                                world[x][y - 1] = Tileset.FLOOR;
                                world[x][y - 2] = Tileset.FLOOR;
                                if (!isDeadEnd(world, pos)) {
                                    stack.push(pos);
                                }
                                pos.setX(x);
                                pos.setY(y - 2);
                                stack.push(new Position(x, y - 2));
                                flag = false;
                            }
                            break;
                        case 2: // left
                            if ((x - 2 > 0) && (world[x - 2][y] == Tileset.WATER)) {
                                world[x - 1][y] = Tileset.FLOOR;
                                world[x - 2][y] = Tileset.FLOOR;
                                if (!isDeadEnd(world, pos)) {
                                    stack.push(pos);
                                }
                                pos.setX(x - 2);
                                pos.setY(y);
                                stack.push(new Position(x - 2, y));
                                flag = false;
                            }
                            break;
                        case 3: // right
                            if ((x + 2 < Game.WIDTH) && (world[x + 2][y] == Tileset.WATER)) {
                                world[x + 1][y] = Tileset.FLOOR;
                                world[x + 2][y] = Tileset.FLOOR;
                                if (!isDeadEnd(world, pos)) {
                                    stack.push(pos);
                                }
                                pos.setX(x + 2);
                                pos.setY(y);
                                stack.push(new Position(x + 2, y));
                                flag = false;
                            }
                            break;
                    }
//                    if (!flag) {
//                        helpPrint(world);
//                    }
                }
            }
        }
    }

    private static boolean isDeadEnd(TETile[][] world, Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (((y + 2 < Game.HEIGHT) && (world[x][y + 2] == Tileset.WATER)) ||
                ((y - 2 > 0) && (world[x][y - 2] == Tileset.WATER)) ||
                ((x + 2 < Game.WIDTH) && (world[x + 2][y] == Tileset.WATER)) ||
                ((x - 2 > 0) && (world[x - 2][y] == Tileset.WATER))) {
            return false;
        }
        return true;
    }

    private static Position findWater(TETile[][] world) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (world[i][j] == Tileset.WATER) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private static void helpPrint(TETile[][] world) {
        for (int i = Game.HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < Game.WIDTH; j++) {
                System.out.print(world[j][i].character());
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }
}
