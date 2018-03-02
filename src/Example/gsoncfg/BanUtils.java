package Example.gsoncfg;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

public class BanUtils {
	public static Bans setBans(String folder, String name) {
		Gson gson = new Gson();
		File file = getFile(folder, name);
		String s = "{}";
		try {
			s = FileUtils.readFileToString(file, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gson.fromJson(s, Bans.class);
	}

	public static File getFile(String folder, String name) {
		File file = new File(folder, name);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}