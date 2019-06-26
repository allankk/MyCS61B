package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.lang.reflect.Array;
import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private Stopwatch sw;
    private double timeout;
    private AStarGraph G;
    private Vertex start;
    private Vertex goal;
    private ArrayHeapMinPQ<Vertex> vertices;
    List<WeightedEdge<Vertex>> neighours;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;
    private double elapsedTime;
    private SolverOutcome outcome;
    private int dequeueOperations;


    public AStarSolver(AStarGraph<Vertex> G, Vertex start, Vertex goal, double timeout) {
        sw = new Stopwatch();
        this.start = start;
        this.goal = goal;
        this.G = G;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        dequeueOperations = 0;

        // Get the neighouring edges starting from the start vertex
        List<WeightedEdge<Vertex>> neighours = G.neighbors(start);

        // Create a PQ of vertices in which each vertex has a priority equal to the sum of v's distance
        // to the source plus the heuristic estimate from v to the goal.
        vertices = new ArrayHeapMinPQ<>();
        vertices.add(start, 0);
        distTo.put(start, 0.0);
        addNeighboursToPQ(start);

        /*
        Remove the smallest priority item in PQ and relax all edges outgoing from it until
        the PQ is empty, PQ.getSmallest() is the goal or timeout is exceeded.
         */
        Vertex p;
        while (!vertices.isEmpty() && !vertices.getSmallest().equals(goal) && sw.elapsedTime() < timeout) {
            p = vertices.removeSmallest();
            dequeueOperations++;
            for (WeightedEdge<Vertex> e : G.neighbors(p)) {
                /*// add the current edge and distance to a variable if it hasnt been visited before or has a smaller distance than previously.
                if (distTo.get(e.to()) == null || distTo.get(e.to()) > distTo.get(e.from()) + e.weight()) {
                    distTo.put(e.to(), distTo.get(e.from()) + e.weight());
                    edgeTo.put(e.to(), e.from());
                }*/
                relax(e);
            }
        }

        // RESULTS

        if (explorationTime() > timeout) {
            outcome = SolverOutcome.TIMEOUT;
        } else if (edgeTo.get(goal) == null) {
            outcome = SolverOutcome.UNSOLVABLE;
            elapsedTime = sw.elapsedTime();
        } else {
            outcome = SolverOutcome.SOLVED;
            elapsedTime = sw.elapsedTime();
        }
    }


    private void addNeighboursToPQ(Vertex v) {
        neighours = G.neighbors(v);
        for (WeightedEdge<Vertex> vert : neighours) {
            double totDistance = vert.weight() + G.estimatedDistanceToGoal(vert.to(), goal);
            vertices.add(vert.to(), totDistance);
        }
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (distTo.get(q) == null || distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(e.to(), e.from());
            if (vertices.contains(q)) {
                vertices.changePriority(q, distTo.get(q) + G.estimatedDistanceToGoal(q, goal));
            } else {
                vertices.add(q, distTo.get(q) + G.estimatedDistanceToGoal(q, goal));
            }
        }
    }

    /* Return either SolverOutcome.SOLVED; SolverOutcome.TIMEOUT or SolverOutcome.UNSOLVABLE
        SolverOutcome.SOLVED if able to complete all work
        SolverOutcome.TIMEOUT if solver ran out of time
        SolverOutcome.UNSOLVABLE if priorityqueue became empty
         */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /*
    Returns a list of vertices corresponding to a solution
    if result is TIMEOUT or UNSOLVABLE should return an empty list
     */
    @Override
    public List<Vertex> solution() {
        if (!outcome.equals(SolverOutcome.SOLVED)) {
            return new ArrayList<>();
        }

        List<Vertex> solution = new ArrayList<>();

        Vertex currentVertex = goal;
        solution.add(currentVertex);

        while (currentVertex != start) {
            currentVertex = edgeTo.get(currentVertex);
            solution.add(currentVertex);
        }

        Collections.reverse(solution);
        return solution;
    }

    /*
    Returns the total weight of the solution. If TIMEOUT or UNSOLVABLE, return 0
     */
    @Override
    public double solutionWeight() {
        if (outcome.equals(SolverOutcome.UNSOLVABLE) || outcome.equals(SolverOutcome.TIMEOUT)) {
            return 0;
        }

        return distTo.get(goal);
    }

    /*
    Total number of PQ dequeue operations
     */
    @Override
    public int numStatesExplored() {
        return dequeueOperations;
    }

    /*
    total time spent in seconds by the constructor
     */
    @Override
    public double explorationTime() {
        return elapsedTime;
    }
}
