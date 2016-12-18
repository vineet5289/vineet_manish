package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstituteSpecificFiledValidation {
	private static Pattern instituteBoardPatter;
	private static final String INSTITUTE_BOARD_PATTERN = "^([ \t]*[a-z. ]+[ \t]*)+$";

	private static Pattern instituteCategoryPatter;
	private static final String INSTITUTE_CATEGORY_PATTERN = "^([ \t]*[a-z ]+[ \t]*)+$";

	private static Pattern instituteTypePatter;
	private static final String INSTITUTE_TYPE_PATTERN = "^([ \t]*[a-z ]+[ \t]*)+$";

	private static Pattern instituteNamePatter;
	private static final String INSTITUTE_NAME_PATTERN = "^[a-z]{1,}[a-z0-9 ._-[\\(.+\\)]\t&]*$";

	static {
		instituteNamePatter = Pattern.compile(INSTITUTE_NAME_PATTERN, Pattern.CASE_INSENSITIVE);
		instituteBoardPatter = Pattern.compile(INSTITUTE_BOARD_PATTERN, Pattern.CASE_INSENSITIVE);
		instituteCategoryPatter = Pattern.compile(INSTITUTE_CATEGORY_PATTERN, Pattern.CASE_INSENSITIVE);
		instituteTypePatter = Pattern.compile(INSTITUTE_TYPE_PATTERN, Pattern.CASE_INSENSITIVE);
	}

	public static boolean isValidBoard(String board) {
		if(board == null)
			return false;
		Matcher matcher = instituteBoardPatter.matcher(board);
		return matcher.matches();
	}

	public static boolean isValidCategory(String category) {
		if(category == null)
			return false;
		Matcher matcher = instituteCategoryPatter.matcher(category);
		return matcher.matches();
	}

	public static boolean isValidInstituteType(String schoolType) {
		if(schoolType == null)
			return false;
		Matcher matcher = instituteTypePatter.matcher(schoolType);
		return matcher.matches();
	}

	/*
	* 1. Institute @name: 1. Only special char .,&, (, ) bracket allow
	*             2. 1st char of name must bhe a-Z t.e vineet, v&1, v1, St. vineet (Singh)
	*
	* */
	public static boolean isValidInstituteName(String name) {
		if(org.apache.commons.lang3.StringUtils.isBlank(name)) {
			return false;
		}
		Matcher matcher = instituteNamePatter.matcher(name.trim());
		return matcher.matches();
	}
}
