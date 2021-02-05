package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * 1.生成随机不覆盖房间
 * TODO 2.把房间之外的空地用迷宫填满
 * TODO 3.将所有相邻的迷宫和房间连接起来，同时也增加少量的连接
 * TODO 4.移除掉所有的死胡同。
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
                finalWorldFrame = loadGame(input);
                break;
            case 'q':
            default:
                if (choice != 'q') {
                    System.out.println("Only input n,l,q plz.");
                }
                System.exit(0);
                break;
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


        return world;
    }

    /**
     * Load a previous game.
     */
    private TETile[][] loadGame(String input) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeEdge(world);

        long seed = getSeed(input);
        return world;
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

        for (int i = 0; i < WIDTH; i += WIDTH - 1) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.WALL;
            }
        }

        for (int i = 0; i < HEIGHT; i += HEIGHT - 1) {
            for (int j = 0; j < WIDTH; j++) {
                world[j][i] = Tileset.WALL;
            }
        }
    }

    /**
     * Get seed from String input.
     */
    private long getSeed(String input) {
        return Long.parseLong(input.substring(1, input.indexOf('s')));
    }
}
