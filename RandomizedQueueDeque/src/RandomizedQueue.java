import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
	private int N;
	private Node first;
	private class Node
	{
		Item item;
		Node next;
	}
	public RandomizedQueue()
	{
		N=0;
		first = null;
	}
	public boolean isEmpty() {	return N == 0;	}
	public int size() {	return N;	}
	public void enqueue(Item item)
	{
		if (item == null)
			throw new NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}
	public Item dequeue()
	{
		if(N==0)
			throw new NoSuchElementException();
		if(N==1)
		{
			N--;
			Item item = first.item;
			first = null;
			return item;
		}
		int n = StdRandom.uniform(N);
		Node p=first;
		Item item;
		for(int i = 0; i < n-1;i++)
			p=p.next;
		item = p.next.item;
		p.next = p.next.next;
		N--;
		return item;
	}
	public Item sample()
	{
		if(N==0)
			throw new NoSuchElementException();
		Node p = first;
		for(int i=0;i<StdRandom.uniform(N);i++)
			p=p.next;
		return p.item;
	}
	public Iterator<Item> iterator()
	{	return new ListIterator();	}
	
	private class ListIterator implements Iterator<Item>
	{
		private Node current = first;
		
		public boolean hasNext() {	return current != null;	}
		public void remove() {	throw new java.lang.UnsupportedOperationException();	}
		public Item next()
		{
			if(N==0 || current == null)
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	private void display()
	{
		for(Node p=first;p!=null;p=p.next)
			StdOut.println(p.item);
	}
	public static void main(String[] args)
	{
		RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
		int ch=-1;
		while(ch!=0)
		{
			StdOut.println("     Welcome\n\n1. Enqueue\n2. Dequeue\n3. Sample\n4. Display\n\n0. Exit\n\n");
			StdOut.print("\nEnter your choice: ");
			ch = StdIn.readInt();
			switch (ch)
			{
			case 1:	d.enqueue(StdIn.readInt());
			break;
			case 2:	StdOut.println(d.dequeue());
			break;
			case 3:	StdOut.println(d.sample());
			break;
			case 4: d.display();
			break;
			}
		}
	}
}