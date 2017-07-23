import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int top = 0;
    private final int bottom;
    private final WeightedQuickUnionUF qu;
    private final int n;
    private final boolean[] openedCells;
    private int numberOfOpenedCells;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        qu = new WeightedQuickUnionUF(n);
        bottom = n ^ 2;

        openedCells = new boolean[bottom + 1];
        //open top and bottom
        openedCells[0] = true;
        openedCells[bottom] = true;

        for (int i = 1; i <= n; i++) {
            qu.union(0, i);
            qu.union(bottom, bottom - i);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int current = getIndex(row, col);
        if (openedCells[current]) {
            return;
        }
        openedCells[current] = true;
        numberOfOpenedCells++;

        connect(row, col, current, row - 1, col);
        connect(row, col, current, row + 1, col);
        connect(row, col, current, row, col - 1);
        connect(row, col, current, row, col + 1);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = getIndex(row, col);
        return openedCells[index];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = getIndex(row, col);
        return qu.connected(top, index);
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfOpenedCells;
    }       // number of open sites

    public boolean percolates() {
        return qu.connected(top, bottom);
    }            // does the system percolate?

    private int getIndex(int row, int col) {
        return (n * row - 1) + col;
    }

    private void connect(int row, int col, int current, int row2, int col2) {
        if (connectable(row, col, row2, col2)) {
            qu.union(current, getIndex(row2, col2));
        }
    }

    private boolean connectable(int row1, int col1, int row2, int col2) {
        return isOpen(row1, col1) && isOpen(row2, col2)
                && (col1 == col2 && Math.abs(row1 - row2) == 1 || row1 == row2 && Math.abs(col1 - col2) == 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
    }  // test client (optional)
}