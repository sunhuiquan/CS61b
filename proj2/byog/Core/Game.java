package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.*;
import java.util.Random;

/**
 * 1.生成随机不覆盖房间
 * 2.把房间之外的空地用迷宫填满
 * 3.将所有相邻的迷宫和房间连接起来，同时也增加少量的连接
 * 4.移除掉所有的死胡同。
 * 5.玩家位置和终点位置
 * 6.处理输入wasd和胜利
 * TODO 7.l和:功能
 * 8.q功能
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
        char choice = input.charAt(0);
        switch (choice) {
            case 'n':
                finalWorldFrame = newGame(input);
                initPlayer(finalWorldFrame, seed);
                break;
            case 'l':
                Player.pos = new Position(0, 0);
                Player.des = new Position(0, 0);
                finalWorldFrame = loadGame();
                input = input.substring(1);
                finalWorldFrame[Player.pos.getX()][Player.pos.getY()] = Tileset.PLAYER;
                finalWorldFrame[Player.des.getX()][Player.des.getY()] = Tileset.UNLOCKED_DOOR;
                break;
            case 'q':
                System.exit(0);
                break;
        }

        while (true) {
            char c = input.charAt(0);
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

        StringBuilder words = new StringBuilder(input);
        seed = getSeed(words);
        input = words.toString();
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
        TETile[][] world = new TETile[Game.WIDTH][Game.HEIGHT];
        try {
            FileReader fri = new FileReader(saveFilename);
            BufferedReader in = new BufferedReader(fri);
            for (int i = 0; i < Game.WIDTH; i++) {
                for (int j = 0; j < Game.HEIGHT; j++) {
                    switch (Integer.parseInt(in.readLine())) {
                        case 0 -> world[i][j] = Tileset.WALL;
                        case 1 -> world[i][j] = Tileset.FLOOR;
                        default -> world[i][j] = Tileset.NOTHING;
                    }
                }
            }
            Player.pos.setX(Integer.parseInt(in.readLine()));
            Player.pos.setY(Integer.parseInt(in.readLine()));
            Player.des.setX(Integer.parseInt(in.readLine()));
            Player.des.setY(Integer.parseInt(in.readLine()));
            in.close();
            fri.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return world;
    }

    /**
     * Save a game to file(Saves.txt).
     */
    private void saveGame(TETile[][] world) {
        try {
            FileWriter fwo = new FileWriter(saveFilename);
            BufferedWriter out = new BufferedWriter(fwo);
            for (int i = 0; i < Game.WIDTH; i++) {
                for (int j = 0; j < Game.HEIGHT; j++) {
                    switch (world[i][j].character()) {
                        case '#' -> out.write("0\n");
                        case '·' -> out.write("1\n");
                        default -> out.write("2\n");
                    }
                }
            }
            out.write(Player.pos.getX() + "\n");
            out.write(Player.pos.getY() + "\n");
            out.write(Player.des.getX() + "\n");
            out.write(Player.des.getY() + "\n");
            out.close();
            fwo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private long getSeed(StringBuilder input) {
        int i = 1;
        for (; i < input.length(); i++) {
            if (!(input.charAt(i) >= '0' && input.charAt(i) <= '9')) {
                break;
            }
        }
        long seed = Long.parseLong(input.substring(1, i));
        input.delete(0, i);
        return seed;
    }
}
