package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final Random RANDOM = new Random(23);

    // @param s size of the hexagon
    // @param i row number
    public static int hexRowWidth(int s, int i) {
        int offsetI = i;

        if (i >= s) {
            offsetI = 2 * s - 1 - offsetI;
        }

        return s + 2 * offsetI;
    }

    public static int hexRowOffset(int s, int i) {
        int offsetI = i;
        if (i >= s) {
            offsetI = 2 * s - 1 - offsetI;
        }
        return -offsetI;
    }

    /*
    @source https://sp19.datastructur.es/materials/lab/lab12/drawhexagon.txt
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 22, 22, 22, RANDOM);
        }
    }


    public void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    // finds the starting position for the hex directly above.
    public Position findTopPosition(Position oldHex, int s) {
        int x = oldHex.x;
        int y = oldHex.y + s * 2;

        return new Position(x, y);
    }

    public Position findTopRight(Position oldHex, int s) {
        int x = oldHex.x + 2 * s - 1;
        int y = oldHex.y + s;

        return new Position(x, y);
    }

    public Position findBottomRight(Position oldHex, int s) {
        int x = oldHex.x + 2 * s - 1;
        int y = oldHex.y - s;

        return new Position(x, y);
    }

    public void drawColumnOfRandomHexes(TETile[][] world, int N, int size, Position pos) {

        Position currentPos = pos;

        for (int i = 0; i < N; i++) {
            this.addHexagon(world, currentPos, size, randomTile());
            currentPos = findTopPosition(currentPos, size);
        }

    }




    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.WATER;
            case 5: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    public void drawHexagonOfHexagons(TETile[][] world, int size, Position startPos) {
        for (int i = 0; i < 3; i++) {
            this.drawColumnOfRandomHexes(world, 3 + i, size, startPos);
            startPos = this.findBottomRight(startPos, size);
        }

        startPos = this.findTopPosition(startPos, size);


        for (int i = 0; i < 2; i++) {
            this.drawColumnOfRandomHexes(world, 4 - i, size, startPos);
            startPos = this.findTopRight(startPos, size);
        }

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        int size = 3;

        HexWorld hexagons = new HexWorld();
        Position startCoordinates = new Position(12, 12);
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexWorld[x][y] = Tileset.NOTHING;
            }
        }

        hexagons.drawHexagonOfHexagons(hexWorld, size, startCoordinates);



        /*
        hexagons.addHexagon(hexWorld, startCoordinates, size, Tileset.WALL);
        Position top = hexagons.findTopPosition(startCoordinates, size);
        hexagons.addHexagon(hexWorld, top, size, Tileset.AVATAR);
        Position topRight = hexagons.findTopRight(startCoordinates, size);
        hexagons.addHexagon(hexWorld, topRight, size, Tileset.SAND);
        Position bottomRight = hexagons.findBottomRight(startCoordinates, size);
        hexagons.addHexagon(hexWorld, bottomRight, size, Tileset.TREE);
        */

        ter.renderFrame(hexWorld);

    }


}
