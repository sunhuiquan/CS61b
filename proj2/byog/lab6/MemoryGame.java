package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public String generateRandomString(int n) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n) {
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        int midWidth = width / 2;
        int midHeight = height / 2;
        StdDraw.clear(Color.black);

        if (!gameOver) {
            Font small = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(small);
            StdDraw.textLeft(1, height - 1, "Rount: " + round);
            StdDraw.text(midWidth, height - 1, playerTurn ? "Type" : "Watch");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        Font big = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(big);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(String.valueOf(letters.charAt(i)));
            StdDraw.pause(750);
            drawFrame(" ");
            StdDraw.pause(750);
        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder sb = new StringBuilder();
        drawFrame(sb.toString());

        while (sb.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            sb.append(key);
            drawFrame(sb.toString());
        }
        StdDraw.pause(500);
        return sb.toString();
    }

    public void startGame() {
        round = 1;
        gameOver = false;

        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(1500);

            String roundString = generateRandomString(round);
            flashSequence(roundString);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);

            if (!userInput.equals(roundString)) {
                gameOver = true;
                drawFrame("Game Over\nFinal level: " + round);
            } else {
                drawFrame("Correctly, move on next round");
                StdDraw.pause(1500);
                round++;
            }
        }
    }
}
