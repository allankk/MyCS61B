package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    List<Point> points;


    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point nearestPoint = null;
        double nearestPointDistance = Integer.MAX_VALUE;

        for (Point each : points) {
            double xdist = Math.pow(x - each.getX(), 2);
            double ydist = Math.pow(y - each.getY(), 2);

            double distance = Math.sqrt(xdist + ydist);
            if (distance < nearestPointDistance) {
                nearestPoint = each;
                nearestPointDistance = distance;
            }
        }

        return nearestPoint;
    }

}
