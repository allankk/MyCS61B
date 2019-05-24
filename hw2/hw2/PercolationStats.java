package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private PercolationFactory pf;
    private int gridSize;
    private int experiment;
    private double[] fractions;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.gridSize = N;
        this.experiment = T;
        this.pf = pf;

        fractions = new double[experiment];

        for (int i = 0; i < experiment; i++) {
            Percolation pe = pf.make(gridSize);
            while (!pe.percolates()) {
                int j = StdRandom.uniform(1, gridSize + 1);
                int k = StdRandom.uniform(1, gridSize + 1);

                if (!pe.isOpen(j, k)) {
                    pe.open(j, k);
                }
            }
            double fraction = (double) pe.numberOfOpenSites() / (gridSize * gridSize);
            fractions[i] = fraction;
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.mean(fractions);
    }

    public double confidenceLow() {
        return mean() - ((1.96 * Math.pow(stddev(),0.5))/Math.pow(experiment, 0.5));
    }

    public double confidenceHigh() {
        return mean() + ((1.96 * Math.pow(stddev(),0.5))/Math.pow(experiment, 0.5));
    }

}
