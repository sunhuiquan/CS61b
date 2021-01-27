package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * This for generic rooms.
 */
public class Room {
    private static int roomNumber;
    private static final int goalNumber = 7;

    public static void generateRoom(TETile[][] world, long seed) {
        roomNumber = 0;
        Random ran = new Random(seed);

        while (roomNumber < goalNumber) {
            int x = RandomUtils.uniform(ran, Game.WIDTH);
            int y = RandomUtils.uniform(ran, Game.HEIGHT);
            int roomWidth = RandomUtils.uniform(ran, 10);
            int roomHeight = RandomUtils.uniform(ran, 10);
            if (!isOverLap(world, x, roomWidth, y, roomHeight)) {
                for (int i = x; i < x + roomWidth; i++) {
                    for (int j = y; j < y + roomHeight; j++) {
                        world[i][j] = Tileset.WALL;
                    }
                }
                roomNumber++;
            }
        }
    }

    private static boolean isOverLap(TETile[][] world, int x, int width, int y, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (world[i][j] == Tileset.WALL)
                    return true;
            }
        }
        return false;
    }
}
