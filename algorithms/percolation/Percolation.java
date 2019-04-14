import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

public class Percolation {
    private static final byte BLOCKED_STATE = 0;
    private static final byte OPEN_STATE = 1;

    private static final int IMAGINARY_TOP_SITE = 0;
    private static final int IMAGINARY_BOTTOM_SITE = 1;

    // Hold state for all sites.
    private byte[][] grid;

    private int openSiteCount;

    private WeightedQuickUnionUF weightedQuickUnionUF;

    // Create n-by-n grid. All sites are blocked initially.
    public Percolation(int n) {
        if (n < 2) {
            // Ensure grid at least 2x2 to simplify boundary checks.
            throw new IllegalArgumentException();
        }
        grid = new byte[n][n];
        openSiteCount = 0;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        row = getZeroIndexedCoordinate(row);
        col = getZeroIndexedCoordinate(col);

        if (grid[row][col] == BLOCKED_STATE) {
            openSiteCount++;
            grid[row][col] = OPEN_STATE;

            Set<Integer> openNeighborSites = getUFIndexesOfOpenNeighbors(row, col);

            for (Integer neighborUFIndex :
                    openNeighborSites) {
                weightedQuickUnionUF.union(neighborUFIndex, calculateUFIndexOfSite(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        row = getZeroIndexedCoordinate(row);
        col = getZeroIndexedCoordinate(col);

        return grid[row][col] == OPEN_STATE;
    }

    public boolean isFull(int row, int col) {
        row = getZeroIndexedCoordinate(row);
        col = getZeroIndexedCoordinate(col);

        return weightedQuickUnionUF.connected(calculateUFIndexOfSite(row, col), IMAGINARY_TOP_SITE);
    }

    public int numberOfOpenSites() {
        return openSiteCount;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(IMAGINARY_TOP_SITE, IMAGINARY_BOTTOM_SITE);
    }

    private int getZeroIndexedCoordinate(int coordinate) {
        if (coordinate < 1 || coordinate > grid.length) {
            throw new IllegalArgumentException();
        }
        return coordinate - 1;
    }

    private Set<Integer> getUFIndexesOfOpenNeighbors(int row, int col) {
        Set<Integer> openNeighborSites = new HashSet<>();

        // Check above.
        if (row == 0) {
            // site belongs to top row. Add imaginary top site.
            openNeighborSites.add(IMAGINARY_TOP_SITE);
        } else {
            if (grid[row - 1][col] == OPEN_STATE) {
                openNeighborSites.add(calculateUFIndexOfSite(row - 1, col));
            }
        }

        // Check left.
        if (col > 0 ) {
            if (grid[row][col - 1] == OPEN_STATE) {
                openNeighborSites.add(calculateUFIndexOfSite(row, col - 1));
            }
        }

        // check right.
        if (col < grid.length - 1) {
            if (grid[row][col + 1] == OPEN_STATE) {
                openNeighborSites.add(calculateUFIndexOfSite(row, col + 1));
            }
        }

        // check below.
        if (row == grid.length - 1) {
            // site belongs to bottom row. Add imaginary bottom site.
            openNeighborSites.add(IMAGINARY_BOTTOM_SITE);
        } else {
            if (grid[row + 1][col] == OPEN_STATE) {
                openNeighborSites.add(calculateUFIndexOfSite(row + 1, col));
            }
        }

        return openNeighborSites;
    }

    private int calculateUFIndexOfSite(int row, int col) {
        // First and second indexes of UF sites are reserved for "Imaginary Top" and "Imaginary Bottom".
        return row * grid.length + col + 2;
    }

    public static void main(String[] args) {

    }

}
