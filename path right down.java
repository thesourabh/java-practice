import java.io.*;
public class CandidateCode 
{ 
    private static int m, n;
    
    private static int calculate(int[][] b, int i, int j) {
        if ((i == (m - 1)) && (j == (n - 1)))
            return 1;
        int curr = b[i][j];
        int currCount = 0;
        switch (curr) {
            case 0:
                break;
            case 1:
                currCount += calculate(b, i, j + 1);
                break;
            case 2:
                currCount += calculate(b, i + 1, j );
                break;
            case 3:
                currCount += calculate(b, i + 1, j + 1);
                break;
            case 4:
                currCount += calculate(b, i + 1, j);
                currCount += calculate(b, i, j + 1);
                break;
            case 5:
                currCount += calculate(b, i, j + 1);
                currCount += calculate(b, i + 1, j + 1);
                break;
            case 6:
                currCount += calculate(b, i + 1, j);
                currCount += calculate(b, i + 1, j + 1);
                break;
            case 7:
                currCount += calculate(b, i, j + 1);
                currCount += calculate(b, i + 1, j);
                currCount += calculate(b, i + 1, j + 1);
                break;
        }
        return currCount;
    }

    public static int no_of_path(int[] input1,int[] input2)
    {
        m = input1[0];
        n = input1[1];
        int[][] board = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = input2[n * i + j];
            }
        }
        System.out.println();
        return calculate(board, 0, 0);
    }
}