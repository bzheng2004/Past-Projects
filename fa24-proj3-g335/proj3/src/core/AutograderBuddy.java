package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

import static java.lang.Long.parseLong;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {

        // Optional: Complete this method if you are submitting to the autograder
        TETile[][] world = new TETile[50][50];
        long seed = parseLong(input);
        World currWorld = new World(seed);

        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                world[x][y] = Tileset.WALL;
            }
        }

        String[] command = input.split(":");
        for (String line : command) {
            if (line.endsWith(":q")) {
                // process the world
                // save the world
                // quit the world
                return world;
            }
            else {
                // process the world
            }
        }
        return world;
    }


    public void save() {
        throw new RuntimeException("Please fill out save!");
    }

    public void process(TETile[][] world, String command) {

    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
