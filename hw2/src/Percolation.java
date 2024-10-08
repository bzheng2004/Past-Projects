import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] avaliable;
    private int size;
    private WeightedQuickUnionUF unionFind;
    private int open;
    private int top;
    private int bot;


    public Percolation(int N) {
        avaliable = new boolean[N][N];
        size = N;
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        open = 0;
        top = N * N;
        bot = N * N + 1;
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            avaliable[row][col] = true;
            open++;
            if (row == 0) {
                unionFind.union(row * size + col, top);
            }
            if (row == size - 1) {
                unionFind.union(row * size + col, bot);
            }
            if (row > 0 && isOpen(row - 1, col)) {
                unionFind.union(row * size + col, (row - 1) * size + col);
            }
            if (row < size - 1 && isOpen(row + 1, col)) {
                unionFind.union(row * size + col, (row + 1) * size + col);
            }
            if (col < size - 1 && isOpen(row, col + 1)) {
                unionFind.union(row * size + col, row * size + col + 1);
            }
            if (col > 0 && isOpen(row, col - 1)) {
                unionFind.union(row * size + col, row * size + col - 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return avaliable[row][col];
    }

    public boolean isFull(int row, int col) {
        return unionFind.connected(row * size + col, top);
    }

    public int numberOfOpenSites() {
        return open;
    }

    public boolean percolates() {
        return unionFind.connected(top, bot);
    }
}
