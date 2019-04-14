public class Percolation {
    private static final byte BLOCKED_STATE = 0;
    private static final byte OPEN_STATE = 1;

    // Hold state for all sites.
    private byte[][] grid;

    private int openSiteCount;



    // Create n-by-n grid. All sites are blocked initially.
    public Percolation(int n) {
        grid = new byte[n][n];
        openSiteCount = 0;
    }

    public void open(int row, int col) {
        if (! areRowAndColValid(row, col)) {
            throw new IllegalArgumentException();
        }

        if (grid[row - 1][col - 1] == BLOCKED_STATE) {
            openSiteCount++;
        }

        grid[row - 1][col - 1] = OPEN_STATE;

    }

    public boolean isOpen(int row, int col) {
        if (! areRowAndColValid(row, col)) {
            throw new IllegalArgumentException();
        }

        return grid[row - 1][col - 1] == OPEN_STATE;
    }

    public boolean isFull(int row, int col) {
        if (! areRowAndColValid(row, col)) {
            throw new IllegalArgumentException();
        }

        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int numberOfOpenSites() {
        return openSiteCount;
    }

    public boolean percolates() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private boolean areRowAndColValid(int row, int col) {
        return 1 <= row && row <= grid.length && 1 <= col && col <= grid[0].length;
    }

    public static void main(String[] args) {

    }

}
