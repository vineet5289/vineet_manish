package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchoolSpecificFiledValidation {
	private static Pattern schoolBoardPatter;
	private static final String SCHOOL_BOARD_PATTERN = "^([ \t]*[a-z. ]+[ \t]*)+$";

	private static Pattern schoolCategoryPatter;
	private static final String SCHOOL_CATEGORY_PATTERN = "^([ \t]*[a-z ]+[ \t]*)+$";

	private static Pattern schoolTypePatter;
	private static final String SCHOOL_TYPE_PATTERN = "^([ \t]*[a-z ]+[ \t]*)+$";

	private static Pattern schoolNamePatter;
	private static final String SCHOOL_NAME_PATTERN = "^[a-z]{1,}[a-z0-9 ._-[\\(.+\\)]\t]*$";

	static {
		schoolNamePatter = Pattern.compile(SCHOOL_NAME_PATTERN, Pattern.CASE_INSENSITIVE);
		schoolBoardPatter = Pattern.compile(SCHOOL_BOARD_PATTERN, Pattern.CASE_INSENSITIVE);
		schoolCategoryPatter = Pattern.compile(SCHOOL_CATEGORY_PATTERN, Pattern.CASE_INSENSITIVE);
		schoolTypePatter = Pattern.compile(SCHOOL_TYPE_PATTERN, Pattern.CASE_INSENSITIVE);
	}

	public static boolean isValidBoard(String board) {
		if(board == null)
			return false;
		Matcher matcher = schoolBoardPatter.matcher(board);
		return matcher.matches();
	}

	public static boolean isValidCategory(String category) {
		if(category == null)
			return false;
		Matcher matcher = schoolCategoryPatter.matcher(category);
		return matcher.matches();
	}

	public static boolean isValidSchoolType(String schoolType) {
		if(schoolType == null)
			return false;
		Matcher matcher = schoolTypePatter.matcher(schoolType);
		return matcher.matches();
	}

	public static boolean isValidSchoolName(String name) {
		if(name == null)
			return false;
		Matcher matcher = schoolNamePatter.matcher(name.trim());
		return matcher.matches();
	}
}
