package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import java.awt.Color;
import java.awt.Font;
import java.io.*;


public class Game {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int FONT_SIZE = 20;
    private TERenderer ter;

    public Game() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
    }

    public void initialize() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Calibri", Font.BOLD, FONT_SIZE);
        StdDraw.setFont(font);
        StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 6 / 7, "CS61B: BYOW");
        StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 4 / 7, "(N) New Game");
        StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 3 / 7, "(L) Load Game");
        StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 2 / 7, "(Q) Quit Game");
        StdDraw.show();
        menuHandler();
    }

    public void menuHandler() {
        char c; // most recent character typed by the user
        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                // Switch statements can be useful to replace long if-else statements!
                switch (c) {
                    case 'n':
                        newGame();
                        return;
                    case 'l':
                        loadGame();
                        return;
                    case 'q':
                        quitGame();
                        return;
                    default:
                        break;
                }
            }
        }
    }

    public void newGame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 6 / 7, "CS61B: BYOW");
        StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 4 / 7, "Enter seed followed by S");
        StdDraw.show();

        StringBuilder seedString = new StringBuilder();

        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);
                if (Character.isDigit(c)) {
                    seedString.append(c);
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.WHITE);
                    StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 6 / 7, "CS61B: BYOW");
                    StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 4 / 7, "Enter seed followed by S");
                    StdDraw.setPenColor(Color.YELLOW);
                    StdDraw.text((double) WIDTH / 2, (double) HEIGHT * 2 / 7, seedString.toString());
                    StdDraw.show();
                } else if (c == 's') {
                    World world = new World(Long.parseLong(seedString.toString()));
                    world.generateWorld();
                    return;
                }
            }
        }
    }


    public void loadGame() {
        File saveFile = new File("save.txt");

        if (!saveFile.exists()) {
            System.exit(0);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            long seed = 0;
            int avatarPosX = 0;
            int avatarPosY = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("seed:")) {
                    seed = Long.parseLong(line.split(":")[1].trim());
                } else if (line.startsWith("avatarPosX:")) {
                    avatarPosX = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("avatarPosY:")) {
                    avatarPosY = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            //debug
            System.out.println(seed);
            System.out.println(avatarPosX + ", " + avatarPosY);

            World world = new World(seed);
            world.setAvatarPosX(avatarPosX);
            world.setAvatarPosY(avatarPosY);

            world.generateWorld();
        } catch (IOException e) {
            System.out.println("Error loading");
        }
    }


    public void quitGame() {
        System.exit(0);
    }
}