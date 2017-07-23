import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class PercolationStats {

    private int[] percollationThreshold;
    private int trials;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        validate(n, trials);
        this.trials = trials;
        percollationThreshold = new int[n];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n);
                int column = StdRandom.uniform(1, n);
                percolation.open(row, column);
            }
            percollationThreshold[i] = percolation.numberOfOpenSites() / n;
        }

    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(percollationThreshold);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(percollationThreshold);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats stats = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + Arrays.toString(new double[]{stats.confidenceLo(), stats.confidenceHi()}));
    }
}