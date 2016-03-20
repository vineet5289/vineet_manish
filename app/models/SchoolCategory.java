package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enum_package.SchoolCateroryEnum;

public class SchoolCategory {
	private static List<String> schoolCaterorys = new ArrayList<String>();

	static {
		schoolCaterorys.add(SchoolCateroryEnum.RESIDENCIAL.name());
		schoolCaterorys.add(SchoolCateroryEnum.NON_RESIDENCIAL.name());
		schoolCaterorys.add(SchoolCateroryEnum.BOTH.name());
	}

	public static List<String> getSchoolCategoryList() {
		return schoolCaterorys;
	}
}
