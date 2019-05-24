package hw2;

public class PercolationFactory {
    public hw2.Percolation make(int N) {
        return new hw2.Percolation(N);
    }
}
