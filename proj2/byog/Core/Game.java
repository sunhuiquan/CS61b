package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * 1.生成随机不覆盖房间
 * 2.把房间之外的空地用迷宫填满
 * 3.将所有相邻的迷宫和房间连接起来，同时也增加少量的连接
 * 4.移除掉所有的死胡同。
 * TODO 5.玩家位置和终点位置
 * TODO 6.处理输入wasd和胜利
 * TODO 7.l功能
 * TODO 8.:q功能
 */

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private static final String saveFilename = "Saves.txt";

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

        initPlayer(finalWorldFrame);
        while (true) {
            char c = (char) input.charAt(0);
            if (c == 'q') {
                break;
            } else if (c == 's' || c == ':') {
                saveGame(finalWorldFrame);
            } else if (c == 'l') {
                finalWorldFrame = loadGame();
            } else {
                movePlayer(finalWorldFrame, c);
            }
        }

        return finalWorldFrame;
    }

    /**
     * Play a new game.
     */
    private TETile[][] newGame(String input) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeEdge(world);

        long seed = getSeed(input);
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
    private void initPlayer(TETile[][] world) {

    }

    /**
     * Move a player.
     */
    private void movePlayer(TETile[][] world, char c) {

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
        long seed = Long.parseLong(input.substring(1, input.indexOf('s')));
        input = input.substring(input.indexOf('s'));
        return seed;
    }
}
