package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.util.Arrays;

public class PercolationStats {
    private double mean;
    private double confidenceLow;
    private double confidenceHigh;
    private double stddev;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        mean = -1;
        confidenceLow = -1;
        confidenceHigh = -1;
        stddev = -1;
        double[] result = new double[T];
        for ( int i = 0; i < N; i++) {
            Percolation exp = pf.make(N);
            while (!exp.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                exp.open(row, col);
            }
            result[i] = (double) exp.numberOfOpenSites() / (double) N;
        }
        mean = Arrays.stream(result).sum() / T;
        stddev = StdStats.stddev(result);
        confidenceHigh = mean + 1.96 * Math.sqrt(stddev / T);
        confidenceLow = mean - 1.96 * Math.sqrt(stddev / T);
    }  // perform T independent experiments on an N-by-N grid
    public double mean() {
        return mean;
    }                                         // sample mean of percolation threshold
    public double stddev() {
        return stddev;
    }                                         // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return confidenceLow;
    }                           // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confidenceHigh;
    }                                 // high endpoint of 95%
}
