package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        System.out.println(requestParams);
        /* PARAMETERS
        lrlon
        ullon
        w
        h
        ullat
        lrlat

        Ex. params = {ullon=-122.241632, lrlon=-122.24053, w=892.0, h=875.0, ullat=37.87655, lrlat=37.87548}
         */
        // ROOT_ULLON, ROOT_ULLAT, ROOT_LRLON, ROOT_LRLAT, TILE_SIZE
        /*
            public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;

         */


        // Initialize the variables from requested parameters
        double lrlon = requestParams.get("lrlon");
        double ullon = requestParams.get("ullon");
        double w = requestParams.get("w");
        double h = requestParams.get("h");
        double ullat = requestParams.get("ullat");
        double lrlat = requestParams.get("lrlat");

        // CHECK IF QUERY IS VALID
        if (ullon > ROOT_LRLON || lrlon < ROOT_ULLON) {
            Map<String, Object> results = new HashMap<>();
            System.out.println("hello");
            results.put("query_success", false);
            return results;
        } else if (ullat > ROOT_ULLAT && lrlat < ROOT_LRLAT) {
            Map<String, Object> results = new HashMap<>();
            results.put("query_success", false);
            System.out.println("hello2");
            return results;
        } else if (ullon > lrlon || ullat < lrlat) {
            Map<String, Object> results = new HashMap<>();
            System.out.println("hello3");
            results.put("query_success", false);
            return results;
        }

        // HOW WIDE IMAGE THE USER WANTS
        int SL = 288200;
        double imageWidthInFeet = (ullon - lrlon) * SL;
        double imageFeetPerPixel = Math.abs(imageWidthInFeet / w);

        // CALCULATE THE APPROPRIATE DEPTH
        int depth = 7;
        double LonDPP = SL * Math.abs(ROOT_ULLON-ROOT_LRLON) / TILE_SIZE;

        for (int i = 0; i <= 7; i++) {
            if (LonDPP <= imageFeetPerPixel) {
                depth = i;
                break;
            } else {
                LonDPP = LonDPP / 2;
            }
        }

        // Total number of images and gridlines on an axis
        int totalImages = (int) Math.pow(4, depth);
        int totalXYGrid = (int) Math.pow(2, depth) - 1;

        Map<String, Object> results = findGrid(ullon, ullat, lrlon, lrlat, totalXYGrid, depth, w, h);


        return results;
    }

    // finds the appropriate start and end grid
    private Map<String, Object> findGrid(double x1, double y1, double x2, double y2, int gridNumber, int depth, double w, double h) {
        // one grid size.
        double xGridLength = Math.abs(ROOT_ULLON - ROOT_LRLON) / (gridNumber + 1);
        double yGridLength = Math.abs(ROOT_ULLAT - ROOT_LRLAT) / (gridNumber + 1);

        // find the grid corner grids (doesn't account for negatives yet)
        int startX = (int) ((x1 - ROOT_ULLON) / xGridLength);
        int startY = (int) ((y1 - ROOT_ULLAT) / yGridLength);
        int endX = (int) ((x2 - ROOT_ULLON) / xGridLength);
        int endY = (int) ((y2 - ROOT_ULLAT) / yGridLength);

        if ((ROOT_ULLON + (endX * xGridLength)) < ROOT_LRLON) {
            endX = endX + 1;
        }

        /*System.out.println( "startY " + startY + " and endY " + endY);*/

        // if the grids to be added are less than the width, add more grids
        while ((endX - startX + 1) * TILE_SIZE < w) {
            endX = endX + 1;
        }
        /*
        while ((ROOT_ULLON + (endX * xGridLength)) > x2) {
            endX = endX + 1;
        }*/


        // find the grid corner coordinates
        double coordStartX = ROOT_ULLON + (startX * xGridLength);
        double coordStartY = ROOT_ULLAT + (startY * yGridLength);
        double coordEndX = ROOT_ULLON + (endX * xGridLength);
        double coordEndY = ROOT_ULLAT - ((Math.abs(endY) + 1) * yGridLength);





        Map<String, Object> results = new HashMap<>();
        results.put("raster_ul_lon", coordStartX);
        results.put("depth", depth);
        results.put("raster_lr_lon", coordEndX);
        results.put("raster_lr_lat", coordEndY);
        /// render grid
        String[][] renderedGrid = renderGrid(startX, Math.abs(startY), endX, Math.abs(endY), depth);
        results.put("render_grid", renderedGrid);
        results.put("raster_ul_lat", coordStartY);
        results.put("query_success", true);


        return results;
    }


    private String[][] renderGrid(int startX, int startY, int endX, int endY, int depth) {
        int XGrids = endX - startX;
        int YGrids = Math.abs(endY - startY) + 1;

        String[][] renderedGrid = new String[YGrids][XGrids];

        for (int i = 0; i < YGrids; i++) {
            int X = startX;
            String[] temp = new String[XGrids];
            for (int j = 0; j < XGrids; j++) {
                int Y = startY + i;
                temp[j] = "d" + depth + "_x" + X + "_y" + Y + ".png";
                X = X + 1;
            }
            renderedGrid[i] = temp;
        }

        return renderedGrid;
    }


    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
