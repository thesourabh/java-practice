public class RegexText
{
	public static void main(String[] args) {
		String s = "CR0,0,32,44_AL_.jpg";
		String d = s.replaceAll("(\\d+)", "" + Integer.parseInt("$1"));
		System.out.println(s + "\n" + d);
	}
}