package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    int[][] graph;
    int N;
    int top;
    private boolean p;
    int bot;
    private int openSize;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF blue;
    public Percolation(int N) {
        p = false;
        top = N * N + 1;
        this.N = N;
        openSize = 0;
        graph = new int[N][N];
        uf = new WeightedQuickUnionUF(N * N + 3);
        blue = new WeightedQuickUnionUF(N * N + 2);
    }
    private int CoordtoInt(int row, int col) {
        return row * N + col + 1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        graph[row][col] = CoordtoInt(row, col);
        openSize++;

        if (row == 0) {
            uf.union(top, CoordtoInt(row, col));
            blue.union(top, CoordtoInt(row, col));
        }

        if (row == N - 1) {
            uf.union(bot, CoordtoInt(row, col));
        }

        if (row - 1 > -1 && isOpen(row - 1, col)) {
            uf.union(CoordtoInt(row, col), CoordtoInt(row - 1, col));
            blue.union(CoordtoInt(row, col), CoordtoInt(row - 1, col));
        }
        if (row + 1 < N && isOpen(row + 1, col)) {
            uf.union(CoordtoInt(row, col), CoordtoInt(row + 1, col));
            blue.union(CoordtoInt(row, col), CoordtoInt(row + 1, col));
        }
        if (col - 1 > -1 && isOpen(row, col - 1)) {
            uf.union(CoordtoInt(row, col), CoordtoInt(row, col - 1));
            blue.union(CoordtoInt(row, col), CoordtoInt(row, col - 1));
        }
        if (col + 1 < N && isOpen(row, col + 1)) {
            uf.union(CoordtoInt(row, col), CoordtoInt(row, col + 1));
            blue.union(CoordtoInt(row, col), CoordtoInt(row, col + 1));
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return graph[row][col] != 0;
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return blue.connected(top, CoordtoInt(row, col));
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    };
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bot);
    }
}
