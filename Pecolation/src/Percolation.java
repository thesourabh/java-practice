public class Percolation {
	private int NUM;
	private int gridSize;
	private WeightedQuickUnionUF UF;
	private boolean arr[];
	private int to1D (int i, int j)
	{
		return (i*(NUM+1) + j);
	}
	private boolean validInts(int i, int j)
	{
		return (! (i < 1 || i > NUM || j < 1 || j > NUM) );
	}
	public Percolation(int N)
	{
		// create N-by-N grid, with all sites blocked
		NUM = N;
		gridSize = (N+1)*(N+1);
		UF = new WeightedQuickUnionUF ( gridSize + 1);
		arr = new boolean[gridSize+1];
	}
	public void open(int i, int j)
	{
		// open site (row i, column j) if it is not already
		if ( ! validInts (i,j))
			throw new IndexOutOfBoundsException("i or j out of bounds");
		int x = to1D(i,j);
		arr[x]=true;
		if(i==NUM && !percolates())
			UF.union(x, gridSize);
		if (i==1)
			UF.union(x, 0);
		if (validInts(i+1,j))
			if (isOpen(i+1,j))
				UF.union(x, to1D(i+1,j));
		if (validInts(i,j-1))
			if (isOpen(i,j-1))
				UF.union(x, to1D(i,j-1));
		if (validInts(i,j+1))
			if (isOpen(i,j+1))
				UF.union(x, to1D(i,j+1));
		if (validInts(i-1,j))
			if (isOpen(i-1,j))
				UF.union(x, to1D(i-1,j));
	}
	public boolean isOpen(int i, int j)
	{
		// is site (row i, column j) open?
		if (!validInts(i,j))
			throw new IndexOutOfBoundsException("i or j out of bounds");
		return (arr[(to1D(i,j))]);
	}
	public boolean isFull(int i, int j)    // is site (row i, column j) full?
	{
		if (!validInts(i,j))
			throw new IndexOutOfBoundsException("i or j out of bounds");
		return (UF.connected(to1D(i,j), 0));
	}
	public boolean percolates()
	{
		return (UF.connected(0, gridSize));
				// does the system percolate?
	}
}