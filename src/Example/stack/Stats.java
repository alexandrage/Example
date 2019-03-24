package Example.stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Stats {

	
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}