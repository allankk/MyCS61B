package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    private boolean[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF gridConnection;
    private WeightedQuickUnionUF noBackWash;
    private int openSitesCount;
    private int topNode;
    private int topNodeBackWash;
    private int bottomNode;


    public Percolation(int N){
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        openSitesCount = 0;
        gridSize = N;
        gridConnection = new WeightedQuickUnionUF((N*N) + 2);
        noBackWash = new WeightedQuickUnionUF(N*N + 1);
        topNodeBackWash = noBackWash.count() - 1;
        topNode = gridConnection.count() - 1;
        bottomNode = gridConnection.count() - 2;

        grid = new boolean[N][N];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = false;
            }
        }
    }


    // open the site
    public void open(int row, int col) {
        int UP = row -1;
        int DOWN = row + 1;
        int LEFT = col - 1;
        int RIGHT = col + 1;

        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        int openedNum = xyTo1D(row, col);

        if (UP >= 0 && isOpen(UP, col)) {
            gridConnection.union(openedNum, xyTo1D(UP, col));
            noBackWash.union(openedNum, xyTo1D(UP, col));
        }

        if (DOWN <= gridSize - 1 && isOpen(DOWN, col)) {
            gridConnection.union(openedNum, xyTo1D(DOWN, col));
            noBackWash.union(openedNum, xyTo1D(DOWN, col));
        }

        if (LEFT >= 0 && isOpen(row, LEFT)) {
            gridConnection.union(openedNum, xyTo1D(row, LEFT));
            noBackWash.union(openedNum, xyTo1D(row, LEFT));
        }

        if (RIGHT <= gridSize-1 && isOpen(row, RIGHT)) {
            gridConnection.union(openedNum, xyTo1D(row, RIGHT));
            noBackWash.union(openedNum, xyTo1D(row, RIGHT));
        }

        // If top row is opened, connects it to an imaginary top node
        if (row == 0) {
            gridConnection.union(openedNum, topNode);
            noBackWash.union(openedNum, topNodeBackWash);
        }

        // If bottom row is opened, connects it to an imaginary bottom node
        if (row == gridSize - 1) {
            gridConnection.union(openedNum, bottomNode);
        }

        openSitesCount++;

    }

    public boolean isOpen(int row, int col) {
        if (grid[row][col] == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull(int row, int col) {
        int searchFor = xyTo1D(row, col);

        // checks if searched node is connected to an imaginary top node
        if (noBackWash.connected(topNodeBackWash, searchFor)){
                return true;

        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {

        if (gridConnection.connected(topNode, bottomNode)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Percolation example = new Percolation(5);
    }

    public int xyTo1D(int x, int y) {
        return (gridSize * x) + y;
    }

}
