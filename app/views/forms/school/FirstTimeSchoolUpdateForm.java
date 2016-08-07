package views.forms.school;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.ValidationError;
import lombok.Data;

@Data
public class FirstTimeSchoolUpdateForm {
	private int numberOfShift;
	public boolean isHostelFacilitiesAvailable;
	public boolean isHostelCompulsory;
	public static String schoolOfficeWeekStartDay;
	public static String schoolOfficeWeekEndDay;
	public static String schoolClassFrom;
	public static String schoolClassTo;
	public static String schoolOfficeStartTime;
	public static String schoolOfficeEndTime;
	public static Date schoolFinancialStartDate;
	public static Date schoolFinancialEndDate;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		
		
		if(errors.size() > 0)
			return errors;
		return null;
	}
	
}
