import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KV {

	String key;
	String val;
	String clas;

	KV() {
		key = "undeclared";
	}

	private class KVClass {
		public String val, cls;

		KVClass(String val, String cls) {
			this.val = val;
			this.cls = cls;
		}
	}

	private <T> KVClass getValIfArray(Object val) {
		List<T> l = new ArrayList<T>();
		int arrLen = Array.getLength(val);
		String res = "", cls = "";
		// System.out.println(val.getClass().getComponentType().getName());
		if (arrLen > 0) {
			Class<?> c = ((Object) Array.get(val, 0)).getClass();
			cls = "[" + c.getName();
			for (int i = 0; i < Array.getLength(val); i++) {
				l.add((T) Array.get(val, i));
			}
		}
		res = l.toString();
		return new KVClass(res, cls);
	}

	<T> void put(String _key, Object _val) {
		key = _key;
		System.out.println(_val.getClass().getName() + "|" + _val.getClass().getName());

		clas = _val.getClass().getName();
		if (_val.getClass().isArray()) {
			KVClass kvc = getValIfArray(_val);
			clas = kvc.cls;
			val = kvc.val;
		} else {
			val = String.valueOf(_val);
		}
	}
	
	private <T> T[] getArrayIfArray(String val, String clas) throws ClassNotFoundException {
		Class<T> c = (Class<T>) Class.forName(clas.substring(1));
		val = val.substring(1, val.length() - 1);
		String[] starr = val.split(", ");
		int arrLen = starr.length;
		System.out.println("Length: " + arrLen);
		List<T> l = new ArrayList<T>();
		System.out.println(c.getName());
		for (int i = 0; i < starr.length; i++) {
			Object o = classBasedResult(c, starr[i]);
			l.add((T) o);
			System.out.println(o);
		}
		T[] res = (T[]) Array.newInstance(c, l.size());
		return l.toArray(res);
	}

	<T> T get(String key) {
		Class<T> c = null;
		System.out.println("New Set:\n" + key + ", " + val + ", " + clas);
		T t = null;
		try {
			if (clas.charAt(0) == '[') {
				return (T) getArrayIfArray(val, clas);
			} else {
				c = (Class<T>) Class.forName(clas);
				t = (T) classBasedResult(c, val);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}

	private <T> T classBasedResult(Class<T> cls, String res) {
		String name = cls.getSimpleName();
		Boolean b;
		if (Number.class.isAssignableFrom(cls) || cls.getName().equals("java.lang.Boolean")) {
			try {
				if (res.equals(""))
					return (T) null;
				Method m = cls.getDeclaredMethod("valueOf", String.class);
				Object o = m.invoke(cls, res);
				System.out.println("Called valueof, returned: " + o);
				return (T) o;
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return (T) res;
	}

	public static void main(String[] args) {

		boolean[] arr = { true, false, true };
		int[] arr2 = { 1, 2, 3 };
		// int[] arr = {1,2,3};
		// KV[] kvarr = new KV[5];

		KV z = new KV(), y = new KV(), x = new KV();
		// z.put("3", kvarr);
		z.put("2", 4);
		System.out.println(z.get("2"));

		y.put("34", arr);
		System.out.println(y.get("34"));
		x.put("34", arr2);
		Integer[] barr = x.get("34");
		System.out.println(x.get("34"));
		/*
		 * System.out.println(y.get("34")); x.put("34r", arr2);
		 * System.out.println(x.get("34r"));
		 * 
		 * System.out.println(Arrays.toString(arr)); //String.s /* KV a = new
		 * KV(), b = new KV(), c = new KV(), d = new KV();
		 * 
		 * a.put("a", false); b.put("1", null); float f = 2.65f; c.put("v", f);
		 * d.put("abg", new KV()); boolean aa = a.get("a"); boolean bb =
		 * b.get("1"); String cc = c.get("v");
		 * 
		 * System.out.println(aa); System.out.println(bb);
		 * System.out.println(cc);
		 */
	}
}
