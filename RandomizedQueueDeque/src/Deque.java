import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private class Node {
		Item item;
		Node next;
	}
	private Node first,last;
	private int N;
	public Deque()
	{	
		N = 0;
		first = null;
		last = null;
	}
	
	public boolean isEmpty()
	{	return N == 0;	}
	
	public int size()
	{	return N;	}
	
	public void addFirst(Item item)
	{
		if (item == null)
			throw new NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
		if(N==1)
			last = first;
	}
	
	public void addLast(Item item)
	{
		if (item == null)
			throw new NullPointerException();
		last.next = new Node();
		last = last.next;
		last.item = item;
		last.next = null;
		N++;
		if(N==1)
			last = first;
	}
	
	public Item removeFirst()
	{
		if(N==0)
			throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		N--;
		return item;
	}
	
	public Item removeLast()
	{
		if(N==0)
			throw new NoSuchElementException();
		Node p;
		Item item = last.item;
		if (first == last)
		{
			N--;
			return item;
		}
		for(p=first;p.next != last;p=p.next)
			;
		p.next = null;
		last = p;
		N--;
		return item;
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
		Deque<Integer> d = new Deque<Integer>();
		int ch=-1;
		while(ch!=0)
		{
			StdOut.println("     Welcome\n\n1. Add front\n2. Add back\n3. Remove first\n4. Remove last\n5. Display\n\n0. Exit\n\n");
			StdOut.print("\nEnter your choice: ");
			ch = StdIn.readInt();
			switch (ch)
			{
			case 1:	d.addFirst(StdIn.readInt());
			break;
			case 2:	d.addLast(StdIn.readInt());
			break;
			case 3:	StdOut.println(d.removeFirst());
			break;
			case 4:	StdOut.println(d.removeLast());
			break;
			case 5: d.display();
			break;
			}
		}
	}
}