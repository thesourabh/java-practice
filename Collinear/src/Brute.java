public class Brute {
	public static void main(String[] args)
	{
		String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point P[] = new Point[N];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            P[i] = new Point(x, y);
        }
        for(int i = 0; i < N; i++)
        {
        	for(int j = 0; j < N; j++)
        	{
        		if(P[j].equals(P[i])) continue;
                for(int k = 0; k < N; k++)
                {
                	if(P[k].equals(P[j]) || P[k].equals(P[i])) continue;
                	for(int l = 0; l < N; l++)
                	{
                		if(P[l].equals(P[k]) || P[l].equals(P[j]) || P[l].equals(P[i]) ) continue;
                		if( ( P[l].slopeTo(P[k])==P[l].slopeTo(P[j]) ) && ( P[l].slopeTo(P[j])==P[l].slopeTo(P[i]) ) 
                				&& P[l].compareTo(P[k]) < 0 && P[k].compareTo(P[j]) < 0 && P[j].compareTo(P[i]) < 0)
                		{
                			StdOut.println(P[i]+" -> "+P[j]+" -> "+P[k]+" -> "+P[l]);
                			P[l].drawTo(P[i]);
                		}
                	}
                }
        		
        	}
        }
	}
}