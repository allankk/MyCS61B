package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;
    private static final boolean horizontal = false;
    private static final boolean vertical = true;

    private class Node {
        private Point p;
        private Node leftChild, rightChild; // leftChild also refers to downChild, rightChild also refers to upChild
        private boolean orientation;
        private int compare;


        public Node(Point p, Boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }


    }


    public KDTree(List<Point> points) {

        for (Point p : points) {
            root = add(p, root, horizontal);
        }
    }

    private Node add(Point p, Node n, Boolean orientation) {
        if (n == null) return new Node(p, orientation);
        if (n.p.equals(p)) return n;

        int compare = comparePoints(p, n.p, orientation);
        if (compare < 0) {
            n.leftChild = add(p, n.leftChild, !orientation);
        } else if (compare >= 0) {
            n.rightChild = add(p, n.rightChild, !orientation);
        }

        return n;
    }

    private int comparePoints(Point a, Point b , Boolean orientation) {
        if (orientation == horizontal) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }


    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point (x, y);
        Node best = root;
        Node nearest = nearest(root, goal, root);

        return nearest.p;
    }

    private Node nearest(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;
        if (n == null) return best;
        if (n.p.distance(goal) < best.p.distance(goal)) {
            best = n;
        }
        if (n.orientation == horizontal) {
            if (goal.getX() < n.p.getX()) {
                goodSide = n.leftChild;
                badSide = n.rightChild;
            } else {
                goodSide = n.rightChild;
                badSide = n.leftChild;
            }
        } else {
            if (goal.getY() < n.p.getY()) {
                goodSide = n.leftChild;
                badSide = n.rightChild;
            } else {
                goodSide = n.rightChild;
                badSide = n.leftChild;
            }
        }

        if (goodSide != null) {
            best = nearest(goodSide, goal, best);
        }

        if (badSide == null) return best;
        if (n.orientation == horizontal) {
            Point bestPossiblePoint = new Point(n.p.getX(), goal.getY());
            if (bestPossiblePoint.distance(goal) < best.p.distance(goal)) {
                best = nearest(badSide, goal, best);
            }
        } else if (n.orientation == vertical) {
            Point bestPossiblePoint = new Point(goal.getX(), n.p.getY());
            if (bestPossiblePoint.distance(goal) < best.p.distance(goal)){
                best = nearest(badSide, goal, best);
            }
        }


        return best;
    }
}
