package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.*;
import java.util.*;


public class World extends Game{
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int MIN_ROOMS = 20;
    private static final int MAX_ROOMS = 30;
    private static final int MIN_ROOM_DIMENSION = 4;
    private static final int MAX_ROOM_DIMENSION = 6;
    private static Random random;
    private long seed;
    private int width;
    private int height;
    private int avatarPosX;
    private int avatarPosY;
    private boolean lineOfSightEnabled = false;

    private TERenderer ter;
    private TETile[][] world;
    private List<int[]> roomCenters = new ArrayList<>();


    public World(long seed) {
        this.seed = seed;
        width = WIDTH;
        height = HEIGHT;
        random = new Random(seed);
        ter = new TERenderer();
        ter.initialize(width, height);
        world = new TETile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    public void generateWorld() {
        int numRooms = random.nextInt(MAX_ROOMS - MIN_ROOMS + 1) + MIN_ROOMS;
        placeRooms(numRooms);
        placeHallways();
        placeWalls();
        if (avatarPosX > 0 && avatarPosY > 0) {
            initializeAvatar(avatarPosX, avatarPosY);
        } else {
            initializeAvatar(-1, -1);
        }

        movementListener();
    }


    public void initializeAvatar(int x, int y) {
        if (x >= 0 && y >= 0 && world[x][y].equals(Tileset.FLOOR)) {
            avatarPosX = x;
            avatarPosY = y;
            toggle(x, y);
            ter.renderFrame(world);
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (world[i][j].equals(Tileset.FLOOR)) {
                        avatarPosX = i;
                        avatarPosY = j;
                        toggle(i, j);
                        ter.renderFrame(world);
                        return;
                    }
                }
            }
        }
    }

    public void toggle(int x, int y) {
        if (world[x][y].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.AVATAR;
        } else if (world[x][y].equals(Tileset.AVATAR)) {
            world[x][y] = Tileset.FLOOR;
        }
    }


    public void movementListener() {
        char c;
        boolean colonPressed = false;

        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                if (colonPressed) {
                    if (c == 'q') {
                        saveGame();
                        System.exit(0);
                    } else {
                        colonPressed = false;
                    }
                } else if (c == ':') {
                    colonPressed = true;
                } else {
                    switch (c) {
                        case 'w':
                            moveAvatar(0, 1);
                            break;
                        case 'a':
                            moveAvatar(-1, 0);
                            break;
                        case 's':
                            moveAvatar(0, -1);
                            break;
                        case 'd':
                            moveAvatar(1, 0);
                            break;
                        case 't':
                            toggleLineOfSight();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }


    public void moveAvatar(int offsetX, int offsetY) {
        int newX = avatarPosX + offsetX;
        int newY = avatarPosY + offsetY;

        if (world[newX][newY].equals(Tileset.FLOOR)) {
            toggle(avatarPosX, avatarPosY);
            avatarPosX = newX;
            avatarPosY = newY;
            toggle(avatarPosX, avatarPosY);
            renderWorld();
        }
    }


    public void placeRooms(int numRooms) {
        for (int i = 0; i < numRooms; i++) {
            int roomWidth = random.nextInt(MAX_ROOM_DIMENSION - MIN_ROOM_DIMENSION + 1) + MIN_ROOM_DIMENSION;
            int roomHeight = random.nextInt(MAX_ROOM_DIMENSION - MIN_ROOM_DIMENSION + 1) + MIN_ROOM_DIMENSION;
            int xPosition = random.nextInt(width - roomWidth - 1) + 1;
            int yPosition = random.nextInt(height - roomHeight - 1) + 1;


            if (positionCheck(xPosition, yPosition, roomWidth, roomHeight)) {
                placeFloors(xPosition, yPosition, roomWidth, roomHeight);
                roomCenters.add(new int[]{xPosition + roomWidth / 2, yPosition + roomHeight / 2});
            }
        }
    }


    public void placeFloors(int x, int y, int roomWidth, int roomHeight) {
        for (int i = x; i < x + roomWidth; i++) {
            for (int j = y; j < y + roomHeight; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }


    public void placeHallways() {
        //sort by sum of coordinates to approximate close rooms
        roomCenters.sort(Comparator.comparingInt(a -> a[0] + a[1]));

        for (int i = 0; i < roomCenters.size() - 1; i++) {
            int[] start = roomCenters.get(i);
            int[] end = roomCenters.get(i + 1);
            connectRooms(start[0], start[1], end[0], end[1]);
        }
    }


    public void connectRooms(int x1, int y1, int x2, int y2) {
        int currentX = x1;
        int currentY = y1;

        // horizontal hallway
        while (currentX != x2) {
            if (world[currentX][currentY].equals(Tileset.NOTHING)) {
                world[currentX][currentY] = Tileset.FLOOR;
            }

            if (x2 > currentX) {
                currentX++;
            } else {
                currentX--;
            }
        }

        // vertical hallway
        while (currentY != y2) {
            if (world[currentX][currentY].equals(Tileset.NOTHING)) {
                world[currentX][currentY] = Tileset.FLOOR;
            }

            if (y2 > currentY) {
                currentY++;
            } else {
                currentY--;
            }
        }
    }


    public boolean positionCheck(int x, int y, int roomWidth, int roomHeight) {
        // define a buffer
        int startX = Math.max(0, x - 2);
        int endX = Math.min(width, x + roomWidth + 2);
        int startY = Math.max(0, y - 2);
        int endY = Math.min(height, y + roomHeight + 2);


        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                if (!world[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }


    public void placeWalls() {
        for (int y = 0; y < height; y++) {
            for (int x = width - 1; x >= 0; x--) {
                if (world[x][y].equals(Tileset.FLOOR)) {
                    fourDirectionWallFill(x, y);
                }
            }
        }
    }


    public void fourDirectionWallFill(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && y + j >= 0 && x + i < width && y + j < height) {
                    if (world[x + i][y + j].equals(Tileset.NOTHING)) {
                        world[x + i][y + j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void saveGame() {
        StringBuilder saveData = new StringBuilder();
        saveData.append("seed:").append(seed).append("\n");
        saveData.append("avatarPosX:").append(avatarPosX).append("\n");
        saveData.append("avatarPosY:").append(avatarPosY).append("\n");

        try (FileWriter writer = new FileWriter("save.txt")) {
            writer.write(saveData.toString());
        } catch (IOException e) {
            System.out.println("Error saving");
        }
    }

    public void setAvatarPosX(int x) {
        this.avatarPosX = x;
    }

    public void setAvatarPosY(int y) {
        this.avatarPosY = y;
    }

    public void toggleLineOfSight() {
        lineOfSightEnabled = !lineOfSightEnabled;
        renderWorld();
    }

    public void renderWorld() {
        TETile[][] displayWorld = new TETile[width][height];
        int visibilityRadius = 5;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (lineOfSightEnabled) {
                    if (isInLineOfSight(x, y, visibilityRadius)) {
                        displayWorld[x][y] = world[x][y];
                    } else {
                        displayWorld[x][y] = Tileset.NOTHING;
                    }
                } else {
                    displayWorld[x][y] = world[x][y];
                }
            }
        }

        ter.renderFrame(displayWorld);
    }

    private boolean isInLineOfSight(int x, int y, int radius) {
        int dx = Math.abs(x - avatarPosX);
        int dy = Math.abs(y - avatarPosY);
        return dx * dx + dy * dy <= radius * radius;
    }

}
