import java.io.*;
import java.util.*;
public class CandidateCode 
{ 

	public static void print(List<Tuple> jobs) {
		for(int i = 0; i < jobs.size(); i++) {
			System.out.println(jobs.get(i).x + "  " + jobs.get(i).y);
		}
	}


public static class Tuple { 
  public int x; 
  public int y; 
  public Tuple(int x, int y) { 
    this.x = x;
    this.y = y;
  } 
} 


	public static void sort(List<Tuple> jobs) {
		for(int i = 0; i < jobs.size(); i++) {
			for(int j = 0; j < jobs.size() - 1; j++) {
				if (jobs.get(j).x > jobs.get(j + 1).x) {
					int x = jobs.get(j).x;
					int y = jobs.get(j).y;
					jobs.get(j).x = jobs.get(j + 1).x;
					jobs.get(j).y = jobs.get(j + 1).y;
					jobs.get(j + 1).x = x;
					jobs.get(j + 1).y = y;
				}
			}
		}
	}
    public static int jobMachine(String[] input1)
	{
		List<Tuple> jobs = new ArrayList<Tuple>();
        for(int i = 0; i < input1.length; i++) {
        	String[] link = input1[i].split("#");
        	int x, y, p;
        	p = link[0].indexOf("P");
        	if (p < 0)	x = Integer.parseInt(link[0].substring(0, link[0].indexOf("A")));
        	else		x = Integer.parseInt(link[0].substring(0, link[0].indexOf("P"))) + 12;

        	if (x >= 24) x -= 12;

        	p = link[1].indexOf("P");
        	if (p < 0)	y = Integer.parseInt(link[1].substring(0, link[1].indexOf("A")));
        	else		y = Integer.parseInt(link[1].substring(0, link[1].indexOf("P"))) + 12;

        	if (y >= 24) y -= 12;

        	y = y - x;

        	jobs.add(new Tuple(x, y));
        }
		sort(jobs);
        int count = 0;
		List<Tuple> fin = new ArrayList<Tuple>();
		for (int start = 5; start < 23;)
		{
			List<Tuple> curr = new ArrayList<Tuple>();
			for(int i = 0; i < jobs.size(); i++)
			{
				if (jobs.get(i).x == start)
				{
					curr.add(jobs.get(i));
					jobs.remove(jobs.get(i));
				}
			}
			if (curr.size() < 1)
			{
				start++;
				continue;
			}
			Tuple min = new Tuple(start,9999);
			for(int i = 0; i < curr.size(); i++)
			{
				if (curr.get(i).y < min.y)
				{
					min = curr.get(i);
				}
			}
			int max_diff = 0;
			for(int i = 0; i < jobs.size(); i++)
			{
				if (jobs.get(i).x < start)
					continue;
				int diff = (min.x + min.y) - (jobs.get(i).x + jobs.get(i).y);
				if (diff > max_diff)
				{
					max_diff = diff;
					min = jobs.get(i);
					start = jobs.get(i).x;
				}
			}
			if ((min.x + min.y) > 23)
				break;
			fin.add(min);
			start += min.y;
		}
		return (fin.size() * 500);
	}

	public static void main(String[] args)
	{
	String s[] = {"6AM#12PM","11AM#1PM","7AM#3PM","7AM#10AM","10AM#12PM","2PM#4PM","1PM#4PM","8AM#9AM"};
	System.out.println("Ans: " + jobMachine(s));
	}
}
