import java.io.*;
import java.util.*;
public class Toy 
{ 
 
 
public static int[] getMaxToys(int input1,int[] input2)
{
int end, money = input1, toys = 0;
end = input2.length;
for (int i = 0; i < end; i++) {
if (money - input2[i] >= 0) {
money -= input2[i];
toys++;
}
else break;
}
int res[] = {toys, money};
System.out.println("\n" + toys + " " + money + "\n");
return res;
}

public static int getMin(int[] sequence, int n, int sum, int min) {
	int N = sequence.length;
	int[] binary = new int[(int) Math.pow(2, N)];
	int max = sum - min;
	for (int i = 0; i < Math.pow(2, N); i++) 
	{
		int b = 1;
		binary[i] = 0;
		int num = i, count = 0;
		while (num > 0) 
		{
			if (num % 2 == 1)
				count++;
			binary[i] += (num % 2) * b;
			num /= 2;
			b = b * 10;
		}
		if (count == n) 
		{
			int c = 0, s = 0;
			for (int j = 0; j < N; j++) 
			{
				if (binary[i] % 10 == 1) {
					c++;
					s += sequence[j];
				}
				binary[i] /= 10;
			}
			if (c == n) {
				if (s == sum)
					return 0;
				if (s > sum)
					break;
				else
					max = s;
			}
		}
	}
	return sum - max;
}
public static void main(String args[])
{
int mon = 50;
int[] arr1 = {1,12,5,111,200,1000,10,9,6,7,4};
Arrays.sort(arr1);
int[] arr2 = new int[arr1.length];

long startTime = System.nanoTime();

System.arraycopy( arr1, 0, arr2, 0, arr1.length );
int[] n = getMaxToys(mon, arr1);
int ans = getMin(arr2, n[0], mon, n[1]);
long endTime = System.nanoTime();
long duration = (endTime - startTime);
System.out.println(duration + "  " + ans);
}
}