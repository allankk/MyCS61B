package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final boolean PRINT_TYPED_KEYS = false;
    BufferedReader reader;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        //StdDraw.setCanvasSize(200, 200);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(100, 100, 100, 100);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(0.5, 0.8, "CS61B Project 3");
        StdDraw.text(0.5, 0.55, "New Game (N)");
        StdDraw.text(0.5, 0.50, "Load Game (L)");
        StdDraw.text(0.5, 0.45, "Quit (Q)");

        GenWorld world = new GenWorld();
        String seed = "";
        long seedLong;


        while (possibleNextInput()) {
            char c = getNextKey();
            if (c == 'N' || c == 'n') {
                System.out.println("n");
                seed = getSeed();
                System.out.println("Seed no. " + seed);
                seedLong = Long.parseLong(seed);

                // delete the last savegame and create a new savegame
                File f = new File("savegame.txt");
                if (f.delete()) {
                    System.out.println("Last savegame deleted, new created");
                } else {
                    System.out.println("failed to delete last savegame");
                }
                try {
                    f.createNewFile();
                } catch (IOException ioe) {
                    System.out.println("IOException when creating a new file");
                }

                // create a filewriter that appends all commands to the savegame
                try {
                    FileWriter fw = new FileWriter("savegame.txt", true);
                    fw.write("n" + seed + "s");
                    fw.close();
                } catch (IOException ioe) {
                    System.out.println("IOException when opening FileWriter");
                }
                world.generateWorld(seedLong);
                break;
            }


            if (c == 'L' || c == 'l') {
                System.out.println("l");
                try {
                    File file = new File("savegame.txt");
                    reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file),
                                    Charset.forName("UTF-8")));
                    String line = reader.readLine();
                    reader.close();
                    world.generateWorld(readSeed(line));

                } catch (IOException ioe) {
                    System.out.println(ioe + "when reading file");
                }
                break;
            }
            if (c == 'Q' || c == 'q') {
                System.out.println("q");
                System.exit(0);
            }
        }
    }

    public long readSeed(String input) {
        InputSource inputSource;
        inputSource = new StringInputDevice(input);

        String seed = "";

        if (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (c == 'n') {
                c = inputSource.getNextKey();
                while ((c != 's' && c!='S') && inputSource.possibleNextInput()) {
                    seed = seed + c;
                    c = inputSource.getNextKey();
                }
                if (c == 's' || c == 'S') {
                    long seedLong = Long.parseLong(seed);
                    return seedLong;
                }
            }
        }
        return 0;
    }

    public String getSeed() {
        StdDraw.clear(Color.black);
        StdDraw.text(0.5, 0.52, "SEED, press S to generate the world");

        String seed = "";
        char c = getNextKey();

        while (c != 's' && c != 'S') {
            seed = seed + c;
            StdDraw.clear(Color.black);
            StdDraw.text(0.5, 0.52, "SEED, press S to generate the world");
            StdDraw.text(0.5, 0.48, seed);

            System.out.println(c);
            c = getNextKey();

        }
        return seed;
    }

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    // @source Josh Hug, CS61B materials
    public TETile[][] interactWithInputString(String input) {
        InputSource inputSource;
        inputSource = new StringInputDevice(input);

        int totalCharacters = 0;
        String seed = "";
        GenWorld world = new GenWorld();
        TETile[][] gameWorld;

        if (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (c == 'n' || c == 'N') {
                c = inputSource.getNextKey();
                while ((c != 's' && c!='S') && inputSource.possibleNextInput()) {
                    seed = seed + c;
                    c = inputSource.getNextKey();
                }
                if (c == 's' || c == 'S') {
                    long seedLong = Long.parseLong(seed);
                    return world.generateAndReturnWorld(seedLong);
                }
            }

            if (c == 'l' || c == 'L') {
                try {
                    File file = new File("savegame.txt");
                    reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file),
                                    Charset.forName("UTF-8")));
                    String line = reader.readLine();
                    reader.close();
                    world.generateWorldWithoutRendering(readSeed(line));
                    return world.getWorld();
                } catch (IOException ioe) {
                    System.out.println(ioe + "when reading file");
                }
            }
        }

        return null;

        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
    }
}
