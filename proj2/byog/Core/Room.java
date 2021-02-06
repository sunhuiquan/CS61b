package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * This for generic rooms.
 */
public class Room {
    private static int roomNumber;
    private static final int GOAL_NUMBER = 11;
    private static RoomObj[] rooms;

    public static void generateRoom(TETile[][] world, long seed) {
        rooms = new RoomObj[GOAL_NUMBER];
        roomNumber = 0;
        int tryTimes = 0;
        Random ran = new Random(seed);

        while (roomNumber < GOAL_NUMBER && tryTimes < 1000000) {
            tryTimes++;
            int x = RandomUtils.uniform(ran, 5, Game.WIDTH - 15);
            int y = RandomUtils.uniform(ran, 5, Game.HEIGHT - 15);
            int roomWidth = RandomUtils.uniform(ran, 6, 10);
            int roomHeight = RandomUtils.uniform(ran, 6, 10);
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
                rooms[roomNumber++] = new RoomObj(x, y, roomWidth, roomHeight);
            }
        }
    }

    private static boolean isOverLap(TETile[][] world, int x, int width, int y, int height) {
        for (int i = x - 1; i < x + width + 1; i++) {
            for (int j = y - 1; j < y + height + 1; j++) {
                if (world[i][j] == Tileset.WALL || world[i][j] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void openRooms(TETile[][] world, long seed) {
        for (int i = 0; i < roomNumber; i++) {
            Random ran = new Random(seed);
            int x = rooms[i].x;
            int y = rooms[i].y;
            int w = rooms[i].w;
            int h = rooms[i].h;

            boolean up = false;
            int xx = RandomUtils.uniform(ran, x + 2, x + w - 2);
            if (RandomUtils.bernoulli(ran)) {
                y = y + h;
                up = true;
            }
            world[xx][y] = Tileset.FLOOR;
            openWallUp(world, xx, y, up);

            boolean right = false;
            int yy = RandomUtils.uniform(ran, y + 2, y + h - 2);
            if (RandomUtils.bernoulli(ran)) {
                x = x + w;
                right = true;
            }
            world[x][yy] = Tileset.FLOOR;
            openWallRight(world, xx, y, right);
        }
    }

    private static void openWallUp(TETile[][] world, int x, int y, boolean up) {
        if (x < 0 || y < 0 || x >= Game.WIDTH || y >= Game.HEIGHT) {
            throw new RuntimeException("There shouldn't overflow.");
        }

        if (up) {
            while (world[x - 1][y] == Tileset.WALL && world[x + 1][y] == Tileset.WALL
                    && world[x][y + 1] == Tileset.WALL) {
                y += 1;
                world[x][y] = Tileset.FLOOR;
            }
        } else {
            while (world[x - 1][y] == Tileset.WALL && world[x + 1][y] == Tileset.WALL
                    && world[x][y - 1] == Tileset.WALL) {
                y -= 1;
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    private static void openWallRight(TETile[][] world, int x, int y, boolean right) {
        if (x < 0 || y < 0 || x >= Game.WIDTH || y >= Game.HEIGHT) {
            throw new RuntimeException("There shouldn't overflow.");
        }

        if (right) {
            while (world[x + 1][y] == Tileset.WALL && world[x][y + 1] == Tileset.WALL
                    && world[x][y - 1] == Tileset.WALL) {
                x += 1;
                world[x][y] = Tileset.FLOOR;
            }
        } else {
            while (world[x - 1][y] == Tileset.WALL && world[x][y + 1] == Tileset.WALL
                    && world[x][y - 1] == Tileset.WALL) {
                x -= 1;
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    public static void getRidOfDeadEnd(TETile[][] world) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 1; i < Game.WIDTH - 1; i++) {
                for (int j = 1; j < Game.HEIGHT - 1; j++) {
                    if (world[i][j] == Tileset.FLOOR) {
                        if (world[i][j + 1] == Tileset.WALL
                                && world[i - 1][j] == Tileset.WALL
                                && world[i + 1][j] == Tileset.WALL) {
                            world[i][j] = Tileset.WALL;
                        } else {
                            if (world[i][j - 1] == Tileset.WALL
                                    && world[i - 1][j] == Tileset.WALL
                                    && world[i + 1][j] == Tileset.WALL) {
                                world[i][j] = Tileset.WALL;

                            } else if (world[i + 1][j] == Tileset.WALL
                                    && world[i][j + 1] == Tileset.WALL
                                    && world[i][j - 1] == Tileset.WALL) {
                                world[i][j] = Tileset.WALL;

                            } else if (world[i - 1][j] == Tileset.WALL
                                    && world[i][j + 1] == Tileset.WALL
                                    && world[i][j - 1] == Tileset.WALL) {
                                world[i][j] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void clearWall(TETile[][] world) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (world[i][j] == Tileset.WALL) {
                    if (!(((i - 1 >= 0) && (world[i - 1][j] == Tileset.FLOOR))
                            || ((i + 1 < Game.WIDTH) && (world[i + 1][j] == Tileset.FLOOR))
                            || ((j - 1 >= 0) && (world[i][j - 1] == Tileset.FLOOR))
                            || ((j + 1 < Game.HEIGHT) && (world[i][j + 1] == Tileset.FLOOR)))) {
                        world[i][j] = Tileset.NOTHING;
                    }
                }
            }
        }
    }
}
