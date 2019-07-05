package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private long SEED;
    private Random RANDOM;


    // Part of world initialization. Fills the world with empty tiles.
    public void fillWithEmptyTiles(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* Generates one rectangular room.

     */
    public void generateOneRoom(TETile[][] world, Position pos, int height, int width) {
        for (int i = 0; i < height + 2; i++) {
            int yi = pos.y + i;

            for (int j = 0; j < width + 2; j++) {
                int xj = pos.x + j;

                // if theres already Tileset.FLOOR in the tile, continue;
                if (checkExistingFloor(world, xj, yi)) {
                    continue;
                } // checks the bottom and top bound
                else if (i == 0 || i == height + 1) {
                    world[xj][yi] = Tileset.WALL;
                } /* checks the left and right */
                else if (j == 0 || j == width + 1 ) {
                    world[xj][yi] = Tileset.WALL;
                }
                else {
                    world[xj][yi] = Tileset.FLOOR;
                }
            }
        }
    }

    public boolean checkExistingFloor(TETile[][] world, int x, int y) {
        if (world[x][y].equals(Tileset.FLOOR)) {
            return true;
        } else {
            return false;
        }
    }

    public void generateRooms(TETile[][] world, List hallways) {
        LinkedListDeque<Position> positionsToConnect = new LinkedListDeque<>();

        for (int i = 0; i < RandomUtils.uniform(RANDOM, 20, 50); i++) {
            Position randomRoomPos = new Position(RandomUtils.uniform(RANDOM, 1, WIDTH-8), RandomUtils.uniform(RANDOM, 1, HEIGHT-7));
            int roomHeight = RandomUtils.uniform(RANDOM, 3, 7);
            int roomWidth = RandomUtils.uniform(RANDOM, 3, 8);
            generateOneRoom(world, randomRoomPos, roomHeight, roomWidth);

            Position posToConnect = getRandomBoxPos(randomRoomPos, roomHeight, roomWidth);
            positionsToConnect.addLast(posToConnect);

            if (positionsToConnect.size() > 1) {
                connect(world, hallways, posToConnect, positionsToConnect.removeFirst());
            }
        }
    }
    // Connects to points with a hallway
    public void connect(TETile[][] world, List hallways, Position pos1, Position pos2) {
        int randomXY = RandomUtils.uniform(RANDOM, 0, 1);

        int xMin = getMin(pos1.x, pos2.x);
        int xMax = getMax(pos1.x, pos2.x);
        int yMin = getMin(pos1.y, pos2.y);
        int yMax = getMax(pos1.y, pos2.y);

        if (randomXY == 0) {
            createHallwayX(world, hallways, xMin, xMax, yMax);
            createHallwayY(world, hallways, xMax, yMin, yMax);
        } else {
            createHallwayY(world, hallways, xMax, yMin, yMax);
            createHallwayX(world, hallways, xMin, xMax, yMax);
        }
    }

    public void createHallwayX(TETile[][] world, List hallways, int xStart, int xEnd, int y) {

        for (int i = xStart; i < xEnd + 1; i++) {
            world[i][y] = Tileset.FLOOR;
            hallways.add(new Position(i, y));
            if (world[i][y+1] == Tileset.NOTHING) {
                world[i][y+1] = Tileset.WALL;
            }
            if (world[i][y-1] == Tileset.NOTHING) {
                world[i][y-1] = Tileset.WALL;
            }
        }

        for (int i = -1; i < 2; i++) {
            // END OF HALLWAY
            if (world[xEnd+1][y+i] == Tileset.NOTHING) {
                world[xEnd+1][y+i] = Tileset.WALL;
            }
            // START OF HALLWAY
            if (world[xStart-1][y+i] == Tileset.NOTHING) {
                world[xStart-1][y+i] = Tileset.WALL;
            }
        }
    }


    public void createHallwayY(TETile[][] world, List hallways, int x, int yStart, int yEnd) {
        for (int i = yStart; i < yEnd + 1; i++) {
            world[x][i] = Tileset.FLOOR;
            hallways.add(new Position(x, i));
            if (world[x+1][i] == Tileset.NOTHING) {
                world[x+1][i] = Tileset.WALL;
            }
            if (world[x-1][i] == Tileset.NOTHING) {
                world[x-1][i] = Tileset.WALL;
            }
        }

        for (int i = -1; i < 2; i++) {
            // END OF HALLWAY
            if (world[x+i][yEnd+1] == Tileset.NOTHING) {
                world[x+i][yEnd+1] = Tileset.WALL;
            }
            // START OF HALLWAY
            if (world[x+i][yStart-1] == Tileset.NOTHING) {
                world[x+i][yStart-1] = Tileset.WALL;
            }
        }
    }

    public void createRandomHallway(TETile[][] world, List<Position> hallways) {
        Position hallwayStartPos = hallways.get(RandomUtils.uniform(RANDOM, hallways.size()));
        int hallwayEndX = RandomUtils.uniform(RANDOM, hallwayStartPos.x - 6, hallwayStartPos.x + 6);
        int hallwayEndY = RandomUtils.uniform(RANDOM, hallwayStartPos.y - 6, hallwayStartPos.y + 6);

        if (hallwayEndX < 1) { hallwayEndX = 1; };
        if (hallwayEndX > WIDTH - 2) { hallwayEndX = WIDTH - 2; };
        if (hallwayEndY < 1) { hallwayEndY = 1; };
        if (hallwayEndY > HEIGHT - 2) { hallwayEndY = HEIGHT - 2; };

        Position hallwayEndPos = new Position(hallwayEndX, hallwayEndY);

        connect(world, hallways, hallwayStartPos, hallwayEndPos);
    }

    public void createRandomHallways(TETile[][] world, List hallways) {
        int amount = RandomUtils.uniform(RANDOM, 0, 30);

        for (int i = 0; i < amount; i++) {
            createRandomHallway(world, hallways);
        }
    }


    public Position getRandomBoxPos(Position pos, int height, int width) {
        int x = RandomUtils.uniform(RANDOM, pos.x, pos.x + width+1);
        int y = RandomUtils.uniform(RANDOM, pos.y, pos.y + height+1);

        return new Position(x, y);
    }

    public int getMin(int x1, int x2) {
        if (x1 >= x2) { return x2; } else { return x1; }
    }

    public int getMax(int x1, int x2) {
        if (x1 < x2) { return x2; } else { return x1;}
    }


    public void generateWorld(long seed) {
        SEED = SEED;
        RANDOM = new Random(SEED);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        GenWorld testWorld = new GenWorld();
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        fillWithEmptyTiles(world);
        List<Position> hallways = new ArrayList<>();
        generateRooms(world, hallways);
        createRandomHallways(world, hallways);
        ter.renderFrame(world);
    }

    public TETile[][] generateAndReturnWorld(long seed) {
        SEED = SEED;
        RANDOM = new Random(SEED);
        TERenderer ter = new TERenderer();

        GenWorld testWorld = new GenWorld();
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        fillWithEmptyTiles(world);
        List<Position> hallways = new ArrayList<>();
        generateRooms(world, hallways);
        createRandomHallways(world, hallways);

        return world;
    }


/*
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        GenWorld testWorld = new GenWorld();
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        testWorld.generateWorld(world);

        ter.renderFrame(world);
    }*/
}
