public class Subset
{
	public static void main(String[] args)
	{
		int k=0;
		RandomizedQueue<String> R = new RandomizedQueue<String>();
		while(!StdIn.isEmpty())
			R.enqueue(StdIn.readString());
		k = Integer.parseInt(args[0]);
		while(k>0)
		{
			StdOut.println(R.dequeue());
			k--;
		}
	}
}