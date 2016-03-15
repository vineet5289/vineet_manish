package models;

import java.util.HashMap;
import java.util.Map;

import enum_package.SchoolCateroryEnum;

public class SchoolCategory {
	private static Map<Long, String> schoolCaterorys = new HashMap<Long, String>();

	static {
		schoolCaterorys.put(1L, SchoolCateroryEnum.RESIDENCIAL.name());
		schoolCaterorys.put(2L, SchoolCateroryEnum.NON_RESIDENCIAL.name());
		schoolCaterorys.put(3L, SchoolCateroryEnum.BOTH.name());
	}

	public static Map<Long, String> getSchoolCategoryList() {
		return schoolCaterorys;
	}
}
