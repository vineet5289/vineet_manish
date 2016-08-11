package utils;

import java.util.Calendar;
import java.util.Date;

import enum_package.MonthEnum;
import enum_package.WeekDayEnum;

public class DateUtiles {

	public static String getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		return MonthEnum.of(month).toString();
	}

	public static int getMonth(String monthName) {
		return MonthEnum.of(monthName);
	}

	public static String getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return WeekDayEnum.of(dayOfWeek).toString();
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	public static String getFinancialYear(Date startDate, Date endDate) {
		StringBuilder sb = new StringBuilder();
		sb.append(getMonth(startDate));
		sb.append(" ");		
		sb.append(getYear(startDate));
		sb.append("-");
		sb.append(getMonth(endDate));
		sb.append(" ");	
		sb.append(getYear(endDate));
		return sb.toString();
	}

	public static String getFinancialYear(String startMonth, int startYear, String endMonth, int endYear) {
		StringBuilder sb = new StringBuilder();
		sb.append(startMonth);
		sb.append(" ");		
		sb.append(startYear);
		sb.append("-");
		sb.append(endMonth);
		sb.append(" ");	
		sb.append(endYear);
		return sb.toString();
	}

	public static String getDate(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		if(day < 10)
			sb.append("0");
		sb.append(day);
		sb.append("/");
		if(month < 10)
			sb.append("0");
		sb.append(month);
		sb.append("/");
		sb.append(year);
		return sb.toString();
	}

	public static String[] parseDate(String date) {
		if(date == null || date.trim().isEmpty())
			return null;
		String[] value = date.trim().replaceAll("[\t,]", " ").replaceAll("( )+", " ").split("[ ]");
		System.out.println(value.length);
		if(value.length != 3)
			return null;
		return value;
	}
}