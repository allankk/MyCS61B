package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static Random r = new Random(400);

    private static KDTree buildLectureTree() {
        Point a = new Point(2, 3);
        Point b = new Point(4, 2);
        Point c = new Point(4, 2);
        Point d = new Point(4, 5);
        Point e = new Point(3, 3);
        Point f = new Point(1, 5);
        Point g = new Point(4, 4);

        List<Point> points = List.of(a, b, c, d, e, f, g);
        KDTree testTree = new KDTree(points);
        return testTree;
    }

    @Test
    public void nearest() {

        KDTree kd = buildLectureTree();

        Point actual = kd.nearest(0, 1);
        Point expected = new Point(2, 3);

        assertEquals(expected, actual);
    }

    public static void testDistance() {
        Point goal = new Point(0, 7);
        Point a = new Point(2, 3);
        Point f = new Point(1, 5);

        double distance1 = a.distance(goal);
        double distance2 = f.distance(goal);

        System.out.println("Distance from [0, 7] to [2, 3]" + distance1);
        System.out.println("Distance from [0, 7] to [1, 5]" + distance2);


    }

    //return random points;
    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    // return N random points
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            points.add(randomPoint());
        }

        return points;
    }


    @Test
    public void testWith1000Points() {
        List<Point> points1000 = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);

        List<Point> queries200 = randomPoints(200);
        for (Point p : queries200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    public void timeWithNPointsQQueries(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        KDTree kd = new KDTree(points);
        List<Point> queries = randomPoints(queryCount);

        Stopwatch sw = new Stopwatch();
        for (Point p : queries) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapesed for " + queryCount + "" +
                " queries and " + pointCount + " points: " + sw.elapsedTime());
    }


    @Test
    public void timeWith10000Points2000Queries() {
        List<Point> points = randomPoints(10000);
        KDTree kd = new KDTree(points);
        List<Point> queries2000 = randomPoints(2000);

        Stopwatch sw = new Stopwatch();
        for (Point p : queries2000) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapsed for 2000 queries nad 10000 points: " + sw.elapsedTime());
    }

    @Test
    public void timeWithVariousNumbersOfPoints() {
        List<Integer> pointCounts = List.of(100, 1000, 10000, 100000, 1000000);
        for (int N : pointCounts) {
            timeWithNPointsQQueries(N, 1000);
        }
    }

    @Test
    public void compareTimingInSpec() {
        List<Point> randomPoints = randomPoints(100000);
        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);

        List<Point> queryPoints = randomPoints(10000);

        Stopwatch sw = new Stopwatch();
        for (Point p : queryPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        double time = sw.elapsedTime();
        System.out.println("Naive 10000 queries on 100000 points: " + time);

        sw = new Stopwatch();
        for (Point p : queryPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        time = sw.elapsedTime();
        System.out.println("KDTree 10000 queries on 100000 points: " + time);
    }




    public static void main(String[] args) {
        testDistance();
    }
}
