package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;

public class ParseString {
	public static Map<String, String> getMap(String value) {
		Map<String, String> parsedMap = null;
		if(value == null || value.isEmpty())
			return null;
		try {
			parsedMap = Splitter.on(",").withKeyValueSeparator("=").split(value);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return parsedMap;
	}

	public static List<Long> getList(String value) {
		List<Long> parsedList = new ArrayList<Long>();
		if(value == null || value.isEmpty())
			return null;

		String[] schooldIds = value.split(",");
		try {
			for(String schoolId : schooldIds) {
				long l = Long.parseLong(schoolId);
				parsedList.add(l);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return parsedList;
	}
}
