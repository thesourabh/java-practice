import java.io.*;
import java.util.*;
public class Toys 
{ 
public static int[] getMaximumToysWithMaxSaving1(int input1,int[] input2)
{
int end, money = input1, toys = 0;
Arrays.sort(input2);
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
public static int[] getMaximumToysWithMaxSaving2(int input1,int[] input2)
{
int end, money = input1, toys = 0;
end = input2.length;
for (int i = 0; i < end; i++) {
	int min = Integer.MAX_VALUE, jVal = -1;
	for (int j = 0; j < end; j++) {
		if (input2[j] < min) {
			min = input2[j];
			jVal = j;
		}
	}
	input2[jVal] = Integer.MAX_VALUE;
	if (money - min >= 0) {
		money -= min;
		toys++;
	}
	else break;
}
int res[] = {toys, money};
System.out.println("\n" + toys + " " + money + "\n");
return res;
}
public static int[] getMaximumToysWithMaxSaving3(int input1,int[] input2)
{
int end, money = input1, toys = 0;
end = input2.length;
int end2 = end;
for (int i = 0; i < end2; i++) {
	int min = Integer.MAX_VALUE, jVal = -1;
	for (int j = 0; j < end; j++) {
		if (input2[j] < min) {
			min = input2[j];
			jVal = j;
		}
	}
	end--;
	input2[jVal] = input2[end];
	input2[end] = Integer.MAX_VALUE;
	if (money - min >= 0) {
		money -= min;
		toys++;
	}
	else break;
}
int res[] = {toys, money};
System.out.println("\n" + toys + " " + money + "\n");
return res;
}

public static void main(String args[])
{
int mon = 50;
int[] arr1 = {1,12,5,111,200,1000,10,9,6,7,4};
long startTime = System.nanoTime();
getMaximumToysWithMaxSaving2(mon, arr1);
long endTime = System.nanoTime();
long duration = (endTime - startTime);
System.out.println(duration);
int[] arr2 = {1,12,5,111,200,1000,10,9,6,7,4};
startTime = System.nanoTime();
getMaximumToysWithMaxSaving3(mon, arr2);
endTime = System.nanoTime();
duration = (endTime - startTime);
System.out.println(duration);
int[] arr3 = {1,12,5,111,200,1000,10,9,6,7,4};
startTime = System.nanoTime();
getMaximumToysWithMaxSaving3(mon, arr3);
endTime = System.nanoTime();
duration = (endTime - startTime);
System.out.println(duration);
int[] arr4 = {1,12,5,111,200,1000,10,9,6,7,4};
startTime = System.nanoTime();
getMaximumToysWithMaxSaving1(mon, arr4);
endTime = System.nanoTime();
duration = (endTime - startTime);
System.out.println(duration);
}

}