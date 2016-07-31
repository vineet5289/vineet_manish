package models;
import java.util.HashMap;
import java.util.Map;

import enum_package.StateEnum;


public class State {
	public static Map<String, String> states =new HashMap<String, String>();
	static {
		states.put(StateEnum.KARNATAKA.name(), "Karnataka");
		states.put(StateEnum.GOA.name(), "Goa");
		states.put(StateEnum.KERALA.name(), "Kerala");
	}
}
