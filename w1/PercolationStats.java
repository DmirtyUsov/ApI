import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private final double[] confid95interval;
    private final double stddev;
    private final double mean;

    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("n or trials < 1");
        }
        double[] thresholds = new double[trials];
 
        for (int i = 0; i < trials; i++)
        {
            Percolation sites = new Percolation(n);
            while (!sites.percolates())
            {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                sites.open(row, col);
            }
            thresholds[i] = (double) sites.numberOfOpenSites() / (n * n);
        }

        stddev = StdStats.stddev(thresholds);
        mean = StdStats.mean(thresholds);
        double temp = 1.96 * stddev() / Math.sqrt(trials); 
        confid95interval = new double[] {mean - temp, mean + temp};
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return confid95interval[0];
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return confid95interval[1];
    }

    // test client
   public static void main(String[] args)
   {
        PercolationStats experiment = new PercolationStats(
           Integer.parseInt(args[0]),
           Integer.parseInt(args[1])
        );
        System.out.println("Mean                    = " + experiment.mean());
        System.out.println("StdDev                  = " + experiment.stddev());
        System.out.println("95% confidence interval = [" + experiment.confidenceLo() + ", " + experiment.confidenceHi() + "]");

   }
}
