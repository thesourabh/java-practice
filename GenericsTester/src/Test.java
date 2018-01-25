
public class Test{
	
	public static int binlog( int bits ) // returns 0 for bits=0
	{
	    int log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}

    public static void main(String args[]){
        int n = 16, x = 3;
        System.out.println(Integer.toBinaryString(n));
        String sb = Integer.toBinaryString(n);
        int c = 0;
        int len = sb.length();
        int log = (int) Math.floor(Math.log(n) / Math.log(2));
        System.out.println(log);
        int log2 = binlog(24);
        System.out.println(63 - Long.numberOfLeadingZeros(8));
    }
}