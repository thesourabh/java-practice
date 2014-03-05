public class Board {
	private int N;
	private int[][] board;
	public Board(int[][] blocks) {
		N = blocks.length;
		board = new int[N][];
		for(int i = 0; i < N; i++)
			board[i] = blocks[i].clone();
	}
	public int dimension()
	{	return N;	}
	
	public int hamming()
	{
		int hamms = 0;
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if( (board[i][j] != i*N + j + 1) && (board[i][j] != 0) )
					hamms++;
		return hamms;
	}
	public int manhattan()
	{
		int mans = 0;
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if( (board[i][j] != i*N + j + 1)  && (board[i][j] != 0) )
				{
					int a = 0, copy = board[i][j] - 1;
					while (copy >= N)
					{
						a++;
						copy -= N;
					}
					mans += Math.abs(a - i) + Math.abs(copy - j);
				}
		return mans;
	}
	public boolean isGoal()
	{
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++)
				if( (board[i][j] != i*N + j + 1) && (board[i][j] != 0))
					return false;
		return true;
	}
	public Board twin()
	{

		int [][] b = new int[N][];
		for(int i = 0; i < N; i++)
			b[i] = board[i].clone();
		for(int i=0;i<N;i++)
			for(int j=0;j<N-1;j++)
				if( (b[i][j]!=0) && (b[i][j+1]!=0) )
				{
					int temp = b[i][j];
					b[i][j] = b[i][j+1];
					b[i][j+1] = temp;
					return new Board(b);
				}
		return new Board(b);
	}
	public boolean equals(Object y)
	{
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board b = (Board) y;
		if (b.N != this.N) return false; 
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++)
				if(b.board[i][j] != this.board[i][j])
					return false;
		return true;
	}
	
	public Iterable<Board> neighbors()
	{
		int i = 0, j = 0;
		boolean flag = false;
		Stack<Board> s = new Stack<Board>();
		for(i = 0; i < N; i++)
		{
			for(j = 0; j < N; j++)
				if (board[i][j] == 0)
				{
					flag = true;
					break;
				}
			if (flag)
				break;
		}
		if(i - 1 >= 0)
			s.push(new Board(blockSwitch(i,j,i-1,j)));
		if(i + 1 < N)
			s.push(new Board(blockSwitch(i,j,i+1,j)));
		if(j - 1 >= 0)
			s.push(new Board(blockSwitch(i,j,i,j-1)));
		if(j + 1 < N)
			s.push(new Board(blockSwitch(i,j,i,j+1)));
		return s;
	}
	private int[][] blockSwitch(int x1, int y1, int x2, int y2)
	{
		int [][] b = new int[N][];
		for(int i = 0; i < N; i++)
			b[i] = board[i].clone();
		int temp = b[x1][y1];
		b[x1][y1] = b[x2][y2];
		b[x2][y2] = temp;
		return b;
	}
	public String toString()
	{
		String str = "" + N + "\n";
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
				str += " "+board[i][j];
			str += "\n";
		}
		return str;
	}
	public static void main(String[] args)
	{
		int x[][] = {{8,1,3},{4,0,2},{7,6,5}};
		int y[][] = {{1,2,3},{4,5,6},{7,8,0}};
		int z[][] = {{1,2,3},{4,5,6},{7,8,0}};
		Board b = new Board(x);
		Board b1 = new Board(y);
		Board b2 = new Board(z);
		StdOut.println(b.dimension() + "   " + b.hamming() + "  " + b.manhattan()+"  "+b.isGoal());
		StdOut.println(b1.equals(b2));
		for (Board s : b.neighbors())
			StdOut.println(s.toString());
	}
}