import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private boolean[] sites;
    private final WeightedQuickUnionUF ids;
    private final int sizeN;
    private int numberOfOpenSites;
    private final int topVirtual;
    private final int bottomVirtual;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }
        sizeN = n;
        numberOfOpenSites = 0;
        int size = n*n+2; // n-by-n + virtual top and bottom
        sites = new boolean[size];
        ids = new WeightedQuickUnionUF(size);
        topVirtual = 0;
        bottomVirtual = size-1; 
        
        // all sites initially blocked - false
        for (int i = 1; i < bottomVirtual; i++)
        {
            sites[i] = false;
        }
        // union first row (top) with virtual top
        for (int q = 1; q <= sizeN; q++)
        {
            ids.union(topVirtual, q);
        }

        // union bottom row with virtual bottom
        
        for (int p = bottomVirtual-sizeN; p < bottomVirtual; p++)
        {
            ids.union(p, bottomVirtual);
        }
    }

    private int rowcol2id(int row, int col)
    {
        int id = -1; // row or col is outside its prescribed range
        if (row < 1 || row > sizeN || col < 1 || col > sizeN)
        {
            id = -1;
        }
        else
        {
            id = row*sizeN-(sizeN-col);
            // System.out.println("For (" + row +", "+col + ") id="+ id);
        }
        return id;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || row > sizeN || col < 1 || col > sizeN) {
            throw new IllegalArgumentException("row or col is outside its prescribed range");
        }
        return sites[rowcol2id(row, col)];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || row > sizeN || col < 1 || col > sizeN)
        {
            throw new IllegalArgumentException("row or col is outside its prescribed range");
        }

        if (!isOpen(row, col))
        {
            // open site
            int p = rowcol2id(row, col);
            int q = -1;
            sites[p] = true;
            numberOfOpenSites++;
            
            // connect to neighbors (left, right, up, down) open sites
            int[][] toCheck = {{row, col-1}, {row, col+1}, {row-1, col}, {row+1, col}};
            for (int i = 0; i < toCheck.length; i++)
            {
                q = rowcol2id(toCheck[i][0], toCheck[i][1]);
                if (q != -1) // row or col is not outside its prescribed range
                {
                    if (isOpen(toCheck[i][0], toCheck[i][1]))
                    {
                        ids.union(p, q);
                    }

                }

            }
        }
    }
    
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        // A full site is an open site that can be connected
        // to an open site in the top row via a chain of neighboring
        // (left, right, up, down) open sites.
        if (row < 1 || row > sizeN || col < 1 || col > sizeN) {
            throw new IllegalArgumentException("row or col is outside its prescribed range");
        }
        boolean result = false;
        if (isOpen(row, col))
        {
            int q = rowcol2id(row, col);

            result = (ids.find(topVirtual) == ids.find(q));
        }
        return result;
    }

    // does the system percolate?
    public boolean percolates()
    {
        boolean result = false;
        if (numberOfOpenSites > 0) // sites for n = 1
        {
            result = (ids.find(topVirtual) == ids.find(bottomVirtual));
        }
        return result;
    }
}
