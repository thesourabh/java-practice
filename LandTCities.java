import java.io.*;
public class CandidateCode 
{ 

    public static int bridge(String[] input1)
    {
        int[] x = new int[input1.length];
        int[] y = new int[input1.length];
        for(int i = 0; i < input1.length; i++) {
        	String[] link = input1[i].split("#");
        	x[i] = Integer.parseInt(link[0]);
        	y[i] = Integer.parseInt(link[1]);
        }
        int maxCount = 0;
        for(int j = -1; j < input1.length; j++) {
        	int count = 0;
        	int curr = 0;
	        for(int i = 0; i < input1.length; i++) {
	        	if (i == j)
	        		continue;
	        	if (y[i] > curr) {
	        		count++;
	        		curr = y[i];
	        	}
	        }
	        if (count > maxCount)
	        	maxCount = count;
        }
        return maxCount;
    }

    public static void main(String[] args) {
    	String vals[] = {"1#5", "2#4", "3#1", "4#5", "5#3", "6#6"};
    	System.out.println(bridge(vals));
    }
}