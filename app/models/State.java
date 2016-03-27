package models;
import java.util.ArrayList;
import java.util.List;


public class State {
	private static List<String> states =new ArrayList<String>();
	static {
		states.add("Uttar Pradesh");
		states.add("Madhya Pradesh");
		states.add("Delhi");
		states.add("Karnataka");
	}

	public static List<String> getStates() {
		return states;
	}
}
