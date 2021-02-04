package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * This for generic rooms.
 */
public class Room {
    private static int roomNumber;
    private static int tryTimes;
    private static final int goalNumber = 22;

    public static void generateRoom(TETile[][] world, long seed) {
        roomNumber = 0;
        tryTimes = 0;
        Random ran = new Random(seed);

        while (roomNumber < goalNumber && tryTimes < 300000) {
            tryTimes++;
            int x = RandomUtils.uniform(ran, 1, Game.WIDTH - 1);
            int y = RandomUtils.uniform(ran, 1, Game.HEIGHT - 1);
            int roomWidth = RandomUtils.uniform(ran, 4, 12);
            int roomHeight = RandomUtils.uniform(ran, 4, 12);
            if (!isOverLap(world, x, roomWidth, y, roomHeight)) {
                for (int i = x; i < x + roomWidth; i += roomWidth - 1) {
                    for (int j = y; j < y + roomHeight; j++) {
                        world[i][j] = Tileset.WALL;
                    }
                }
                for (int i = y; i < y + roomHeight; i += roomHeight - 1) {
                    for (int j = x; j < x + roomWidth; j++) {
                        world[j][i] = Tileset.WALL;
                    }
                }
                for (int i = x + 1; i < x + roomWidth - 1; i++) {
                    for (int j = y + 1; j < y + roomHeight - 1; j++) {
                        world[i][j] = Tileset.FLOOR;
                    }
                }
                roomNumber++;
            }
        }
    }

    private static boolean isOverLap(TETile[][] world, int x, int width, int y, int height) {
        for (int i = x - 1; i < x + width + 1; i++) {
            for (int j = y - 1; j < y + height + 1; j++) {
                if (world[i][j] == Tileset.WALL || world[i][j] == Tileset.FLOOR)
                    return true;
            }
        }
        return false;
    }
}
