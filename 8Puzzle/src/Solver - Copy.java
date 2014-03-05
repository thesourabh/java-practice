public class Solver
{
	private boolean solvable;
	private int moves;
	private SearchNode curr;
	private class SearchNode
	{
		Board board;
		int moves, manhattan, priority;
		SearchNode prev;
		SearchNode(Board b, int num, SearchNode s)
		{
			board = b;
			moves = num;
			prev = s;
			manhattan = b.manhattan();
			priority = manhattan + moves;
		}
	}
	public Solver(Board initial)
	{
		SearchNode I = new SearchNode(initial, 0, null);
		MinPQ<SearchNode> PQ = new MinPQ<SearchNode>(new java.util.Comparator<SearchNode>() {
            public int compare(SearchNode p, SearchNode q) {	return p.priority - q.priority;	}
            });
		MinPQ<SearchNode> PQTwin = new MinPQ<SearchNode>(new java.util.Comparator<SearchNode>() {
            public int compare(SearchNode p, SearchNode q) {	return p.priority - q.priority;	}
            });
		PQ.insert(I);
		PQTwin.insert(new SearchNode(I.board.twin(), 0, null));
		curr = PQ.delMin();
		SearchNode currTwin = PQTwin.delMin();
		while(!curr.board.isGoal() && !currTwin.board.isGoal())
		{
			for( Board b : curr.board.neighbors())
			{
				if(curr.prev != null)
					if(curr.prev.board.equals(b))
						continue;
				PQ.insert(new SearchNode(b, curr.moves + 1, curr));
			}
			curr = PQ.delMin();
			for( Board b : currTwin.board.neighbors())
			{
				if(currTwin.prev != null)
					if(currTwin.prev.board.equals(b))
						continue;
				PQTwin.insert(new SearchNode(b, currTwin.moves + 1, currTwin));
			}
			currTwin = PQTwin.delMin();
		}
		if(curr.board.isGoal())
		{
			solvable = true;
			moves = curr.moves;
		}
		else
		{
			solvable = false;
			moves = -1;
		}		
	}
	public boolean isSolvable()
	{	return solvable;	}
	
	public int moves()
	{ return moves;	}
	
	public Iterable<Board> solution()
	{
		if(solvable == false)
			return null;
		Stack<Board> sb = new Stack<Board>();
		SearchNode S = curr;
		while(S != null)
		{
			sb.push(S.board);
			S = S.prev;
		}
		return sb;
	}
	
	public static void main(String[] args) {
	    // create initial board from file
		String filename = "C:/Users/Sourabh/Programming/Java/8Puzzle/src/8puzzle/puzzle04.txt";
	    In in = new In(filename);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}