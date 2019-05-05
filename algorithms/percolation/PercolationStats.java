import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        double[] percolationThresholds = new double[trials];

        PercolationExperiment percolationIteration;

        for (int i = 0; i < trials; i++) {
            percolationIteration = new PercolationExperiment(n);
            percolationIteration.run();
            percolationThresholds[i] = percolationIteration.getPercolationThreshold();
        }

        this.mean = StdStats.mean(percolationThresholds);
        this.stddev = StdStats.stddev(percolationThresholds);

        double confidenceEpsilon = (1.96 * this.stddev) / Math.sqrt(trials);
        this.confidenceLo = this.mean - confidenceEpsilon;
        this.confidenceHi = this.mean + confidenceEpsilon;

    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Please provide only grid size (n) and trials (T) as command line arguments.");
            System.exit(1);
        }
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(gridSize, trials);

        // Use %s formatter to print with full precision.
        System.out.printf("%-23s = %s\n", "mean", percolationStats.mean());
        System.out.printf("%-23s = %s\n", "stddev",  percolationStats.stddev());
        System.out.printf("95%% confidence interval = [%s, %s]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }


    private class PercolationExperiment {
        private Percolation percolation;
        private ArrayList<Coordinate> blockedSites;
        private int totalSiteCount;

        private PercolationExperiment(int n){
            percolation = new Percolation(n);
            totalSiteCount = n * n;
            blockedSites = new ArrayList<>(totalSiteCount);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blockedSites.add(new Coordinate(i + 1, j + 1));
                }
            }
        }

        private void run() {
            while (! percolation.percolates()) {
                int randomSiteIndex = StdRandom.uniform(blockedSites.size());
                percolation.open(blockedSites.get(randomSiteIndex).row, blockedSites.get(randomSiteIndex).col);
                blockedSites.remove(randomSiteIndex);
            }
        }

        private double getPercolationThreshold() {
            return percolation.numberOfOpenSites() / (double)totalSiteCount;
        }

        private class Coordinate {
            private Coordinate(int row, int col) {
                this.row = row;
                this.col = col;
            }
            private int row;
            private int col;
        }
    }
}