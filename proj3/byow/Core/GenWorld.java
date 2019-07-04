package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class GenWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final long SEED = 9223372036854774800L;
    private static final Random RANDOM = new Random(SEED);


    public void fillWithEmptyTiles(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* Generates one rectangular room.

     */
    public void generateOneRoom(TETile[][] world, Position pos, int roomSize) {
        for (int i = 0; i < roomSize + 2; i++) {
            int yi = pos.y + i;

            for (int j = 0; j < roomSize + 2; j++) {
                int xj = pos.x + j;

                // if theres already Tileset.FLOOR in the tile, continue;
                if (checkExistingFloor(world, xj, yi)) {
                    continue;
                } // checks the bottom and top bound
                else if (i == 0 || i == roomSize + 1) {
                    world[xj][yi] = Tileset.WALL;
                } /* checks the left and right */
                else if (j == 0 || j == roomSize + 1 ) {
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

    public void generateRooms(TETile[][] world) {

        for (int i = 0; i < RandomUtils.uniform(RANDOM, 20, 50); i++) {
            Position randomRoomPos = new Position(RANDOM.nextInt(WIDTH-5), RANDOM.nextInt(HEIGHT-5));
            generateOneRoom(world, randomRoomPos, RandomUtils.uniform(RANDOM, 2, 5));
        }

    }







    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        GenWorld testWorld = new GenWorld();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        testWorld.fillWithEmptyTiles(world);
        testWorld.generateRooms(world);

        ter.renderFrame(world);
    }
}
