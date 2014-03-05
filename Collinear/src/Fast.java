import java.util.Arrays;

public class Fast {
	public static void main(String[] args)
	{
		String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point P[] = new Point[N];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        // read in the input
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            P[i] = new Point(x, y);
            P[i].draw();
        }
        
        for(int i=0;i<N;i++)
        {
        	Arrays.sort(P,0,N);
        	Arrays.sort(P,0,N,P[i].SLOPE_ORDER);
        	for(int j=1;j<N-3;j++)
        	{
        		int k=1;
        		while(P[0].slopeTo(P[j]) == P[0].slopeTo(P[j+k]))
        		{
        			k++;
        			if(j+k>=N)
        				break;
        		}
        		if(P[0].compareTo(P[j]) < 0)
        			k=0;
        		if(k>=3)
        		{
        			
        			StdOut.print(P[0]+" -> "+P[j]);
        			P[0].drawTo(P[j]);
        			for(int a=j+1;a<j+k;a++)
        			{
        				StdOut.print(" -> "+P[a]);
            			P[a-1].drawTo(P[a]);
        			}
        			StdOut.println();
        		}
        	}
        }
	}
}