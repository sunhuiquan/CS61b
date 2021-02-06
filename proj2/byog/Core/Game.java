package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * 1.生成随机不覆盖房间
 * 2.把房间之外的空地用迷宫填满
 * 3.将所有相邻的迷宫和房间连接起来，同时也增加少量的连接
 * 4.移除掉所有的死胡同。
 * 5.玩家位置和终点位置
 * 6.处理输入wasd和胜利
 * TODO 7.l和:功能
 * TODO 8.q功能
 */

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final String saveFilename = "Saves.txt";

    private long seed;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }


    /**
     * Method used for test. The game should should not start from the main menu.
     */
    public TETile[][] playWithInputString(String input) {
        TETile[][] finalWorldFrame = null;
        char choice = (char) input.charAt(0);
        switch (choice) {
            case 'n':
                finalWorldFrame = newGame(input);
                break;
            case 'l':
                finalWorldFrame = loadGame();
                break;
        }
        input = input.substring(1);

        initPlayer(finalWorldFrame, seed);
        while (true) {
            char c = (char) input.charAt(0);
            if (c == 'q') {
                break;
            } else if (c == ':') {
                saveGame(finalWorldFrame);
            } else if (c == 'l') {
                finalWorldFrame = loadGame();
            } else {
                movePlayer(finalWorldFrame, c);
                if (Player.pos.getX() == Player.des.getX() && Player.pos.getY() == Player.des.getY()) {
                    System.out.println("You win.");
                    break;
                }
            }
            input = input.substring(1);
        }

        return finalWorldFrame;
    }

    /**
     * Play a new game.
     */
    private TETile[][] newGame(String input) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeEdge(world);

        seed = getSeed(input);
        Room.generateRoom(world, seed);
        Puzzle.generatePuzzle(world, seed);
        Room.openRooms(world, seed);
        Room.getRidOfDeadEnd(world);
        Room.clearWall(world);

        return world;
    }

    /**
     * Load a previous game.
     */
    private TETile[][] loadGame() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        return world;
    }

    /**
     * Save a game to file(Saves.txt).
     */
    private void saveGame(TETile[][] world) {
    }

    /**
     * Initiate a Player object.
     */
    private void initPlayer(TETile[][] world, long seed) {
        Random ran = new Random(seed);
        while (true) {
            int x = RandomUtils.uniform(ran, 1, Game.WIDTH);
            int y = RandomUtils.uniform(ran, 1, Game.HEIGHT);
            if (world[x][y] == Tileset.FLOOR) {
                Player.pos = new Position(x, y);
                Player.playerTile = Tileset.PLAYER;
                break;
            }
        }
        while (true) {
            int x = RandomUtils.uniform(ran, 1, Game.WIDTH);
            int y = RandomUtils.uniform(ran, 1, Game.HEIGHT);
            if (world[x][y] == Tileset.WALL) {
                Player.des = new Position(x, y);
                Player.destinationTile = Tileset.UNLOCKED_DOOR;
                break;
            }
        }

        world[Player.pos.getX()][Player.pos.getY()] = Tileset.PLAYER;
        world[Player.des.getX()][Player.des.getY()] = Tileset.UNLOCKED_DOOR;
    }

    /**
     * Move a player.
     */
    private void movePlayer(TETile[][] world, char c) {
        int x = Player.pos.getX();
        int y = Player.pos.getY();
        switch (c) {
            case 'w':
                if (world[x][y + 1] != Tileset.WALL) {
                    world[x][y] = Tileset.FLOOR;
                    world[x][y + 1] = Player.playerTile;
                    Player.pos.setX(x);
                    Player.pos.setY(y + 1);
                }
                break;
            case 's':
                if (world[x][y - 1] != Tileset.WALL) {
                    world[x][y] = Tileset.FLOOR;
                    world[x][y - 1] = Player.playerTile;
                    Player.pos.setX(x);
                    Player.pos.setY(y - 1);
                }
                break;
            case 'a':
                if (world[x - 1][y] != Tileset.WALL) {
                    world[x][y] = Tileset.FLOOR;
                    world[x - 1][y] = Player.playerTile;
                    Player.pos.setX(x - 1);
                    Player.pos.setY(y);
                }
                break;
            case 'd':
                if (world[x + 1][y] != Tileset.WALL) {
                    world[x][y] = Tileset.FLOOR;
                    world[x + 1][y] = Player.playerTile;
                    Player.pos.setX(x + 1);
                    Player.pos.setY(y);
                }
                break;
        }
    }

    /**
     * Initialize the edge of the whole map.
     */
    private void initializeEdge(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < WIDTH; i += WIDTH - 2) {
            for (int j = 0; j < HEIGHT - 1; j++) {
                world[i][j] = Tileset.WALL;
            }
        }

        for (int i = 0; i < HEIGHT; i += HEIGHT - 2) {
            for (int j = 0; j < WIDTH - 1; j++) {
                world[j][i] = Tileset.WALL;
            }
        }
    }

    /**
     * Get seed from String input.
     */
    private long getSeed(String input) {
        int i = 1;
        for (; i < input.length(); i++) {
            if (!(input.charAt(i) >= '0' && input.charAt(i) <= '9')) {
                break;
            }
        }
        long seed = Long.parseLong(input.substring(1, i));
        input = input.substring(i);
        return seed;
    }
}
