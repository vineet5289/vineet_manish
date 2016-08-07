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
}