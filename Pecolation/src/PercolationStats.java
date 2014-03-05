public class PercolationStats {

	private int num, times, th;
	private double thresh[];
	private double mean,stdev;
	public PercolationStats(int N, int T)
	{
		int i=1,j=1,count = 0;
		if (N <= 0 || T <= 0)
			throw new java.lang.IllegalArgumentException();
		num = N;
		times = T;
		mean = 0.0;
		stdev=0.0;
		thresh = new double[T];
		th = 0;
		while (T>0)
		{
			Percolation p = new Percolation(num);
			count=0;
			while (!p.percolates())
			{
				i = StdRandom.uniform(num)+1;
				j = StdRandom.uniform(num)+1;
				if( !p.isOpen(i,j) )
				{
					p.open(i,j);
					count++;
				}
			}
			thresh[th++] = (float)count/(num*num);
			T--;
		}
	} 
      // perform T independent computational experiments on an N-by-N grid
	public double mean()
	{
		double sum = 0.0;
		for (int i=0;i<times;i++)
			sum+=thresh[i];
		mean = sum/times;
		return mean;
	}            // sample mean of percolation threshold
	public double stddev()
	{
		double sum=0.0;
		if(mean==0.0)
			mean = mean();
		for ( int i = 0; i < times; i++ )
		{
			sum+=(thresh[i]-mean)*(thresh[i]-mean);
		}
		stdev = Math.sqrt(sum/(times-1));
		return stdev;
	}            // sample standard deviation of percolation threshold
	public double confidenceLo()
	{
		if(mean == 0.0)
			mean = mean();
		if(stdev == 0.0)
			stdev = stddev();
		return (mean - (1.96 * stdev)/Math.sqrt(times)  );
		
	}          // returns lower bound of the 95% confidence interval
	public double confidenceHi()
	{
		if(mean == 0.0)
			mean = mean();
		if(stdev == 0.0)
			stdev = stddev();
		return (mean + (1.96 * stdev)/Math.sqrt(times)  );
	}        // returns upper bound of the 95% confidence interval
	public static void main(String[] args)
	{
		int N = 1, T = 1;
		N = 20;//Integer.parseInt(args[0]);
		T = 400;//Integer.parseInt(args[1]);
		PercolationStats p = new PercolationStats(N, T);
		System.out.println("mean\t\t\t= "+p.mean());
		System.out.println("stddev\t\t\t= "+p.stddev());
		System.out.println("95% confidence interval\t= "+p.confidenceLo()+", "+p.confidenceHi());
	}   // test client, described below
}