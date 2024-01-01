package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Precolation2 {
        // create N-by-N grid, with all sites initially blocked
        int[][] graph;
        int N;
        private int openSize;
        private WeightedQuickUnionUF uf;
        public Precolation2(int N) {
            this.N = N;
            openSize = 0;
            graph = new int[N][N];
            uf = new WeightedQuickUnionUF(-1);
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
                uf.union(-1, CoordtoInt(row, col));
            }
            if (row == N - 1) {

                uf.union(N * N, CoordtoInt(row, col));
            }

            if (row - 1 > -1 && isOpen(row - 1, col)) {
                uf.union(CoordtoInt(row, col), CoordtoInt(row - 1, col));
            }
            if (row + 1 < N && isOpen(row + 1, col)) {
                uf.union(CoordtoInt(row, col), CoordtoInt(row + 1, col));
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
               uf.union(CoordtoInt(row, col), CoordtoInt(row, col - 1));
            }
            if (col + 1 < N && isOpen(row, col + 1)) {
                uf.union(CoordtoInt(row, col), CoordtoInt(row, col + 1));
            }
        }
        // is the site (row, col) open?
        public boolean isOpen(int row, int col) {
            return graph[row][col] != 0;
        }
        // is the site (row, col) full?
        public boolean isFull(int row, int col) {
            return uf.connected(-1, CoordtoInt(row, col));
        }
        // number of open sites
        public int numberOfOpenSites() {
            return openSize;
        };
        // does the system percolate?
        public boolean percolates() {
            return uf.connected(-1, N * N);
        }
}
/**public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    WeightedQuickUnionUF[][] graph;
    private final int N;
    private WeightedQuickUnionUF top;
    private WeightedQuickUnionUF bot;
    private int openSize;
    public Percolation(int N) {
        this.N = N;
        this.top = new WeightedQuickUnionUF(-1);
        this.bot = new WeightedQuickUnionUF(N);
        graph = new WeightedQuickUnionUF[N][N];
//        int count = 0;
//        for (int row = 0; row < N; row++) {
//            for (int col = 0; col < N; col++) {
//                graph[row][col] = new WeightedQuickUnionUF(count);
//                count++;
//            }
//        }
    }
    private int CoordtoInt(int row, int col) {
        return row * N + col;
    }
    //    private int[] InttoCoord(int val) {
//        return new int[] {val / N, val % N};
//    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        graph[row][col] = new WeightedQuickUnionUF(CoordtoInt(row, col));
        openSize++;

        if (row == 0) {
            top.union(-1, CoordtoInt(row, col));
        }
        if (row == N - 1) {
            bot.union(N, CoordtoInt(row, col));
        }

        if (row - 1 > -1 && isOpen(row - 1, col)) {
            graph[row][col].union(CoordtoInt(row, col), CoordtoInt(row - 1, col));
        }
        if (row + 1 < N && isOpen(row + 1, col)) {
            graph[row][col].union(CoordtoInt(row, col), CoordtoInt(row + 1, col));
        }
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            graph[row][col].union(CoordtoInt(row, col), CoordtoInt(row, col - 1));
        }
        if (col + 1 < N && isOpen(row, col + 1)) {
            graph[row][col].union(CoordtoInt(row, col), CoordtoInt(row, col + 1));
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return graph[row][col] != null;
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int i = 0;
        while (i < N) {
            if (graph[row][col].connected(CoordtoInt(row, col), i)) return true;
            i++;
        }
        return false;
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    };
    // does the system percolate?
    public boolean percolates() {
//        ArrayList<Integer> top = new ArrayList<>();
//        ArrayList<Integer> bottom = new ArrayList<>();
//        for (int i = 0; i < N; i++) {
//            if (isOpen(0, i)) top.add(CoordtoInt(0, i));
//            if (isOpen(N - 1, i)) bottom.add(CoordtoInt(N - 1, i));
//        }
//        for (Integer integer : top) {
//            for (Integer value : bottom) {
//                if (graph[InttoCoord(integer)[0]][InttoCoord(integer)[1]]
//                        == graph[InttoCoord(value)[0]][InttoCoord(value)[1]]) {
//                    return true;
//                }
//            }
//        }
//        return false;
        return top.connected(-1, N);
    }
    public static void main(String[] args) {

    }   // use for unit testing (not required)
}*/