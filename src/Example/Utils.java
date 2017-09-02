package Example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {
	public static List<String> s(String st, int spl) {
		String str = st.substring(1, st.length() - 1);
		String[] arrWords = str.split(" ");
		ArrayList<String> arrPhrases = new ArrayList<String>();
		StringBuilder stringBuffer = new StringBuilder();
		int cnt = 0;
		int index = 0;
		int length = arrWords.length;
		while (index != length) {
			if (cnt + arrWords[index].length() <= spl) {
				cnt += arrWords[index].length() + 1;
				stringBuffer.append(arrWords[index]).append(" ");
				index++;
			} else {
				arrPhrases.add(stringBuffer.toString());
				stringBuffer = new StringBuilder();
				cnt = 0;
			}
		}
		if (stringBuffer.length() > 0) {
			arrPhrases.add(stringBuffer.toString());
		}

		List<String> list = new ArrayList<String>();
		Iterator<String> it = arrPhrases.iterator();
		while (it.hasNext()) {
			String elem = (String) it.next();
			list.add(elem);
		}
		return list;
	}
}
