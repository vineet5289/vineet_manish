package views.forms.school;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;

@Data
public class FirstTimeSchoolUpdateForm {
	private int numberOfShift;
	private boolean isHostelFacilitiesAvailable;
	private boolean isHostelCompulsory;
	private String schoolOfficeWeekStartDay;
	private String schoolOfficeWeekEndDay;
	private String schoolClassFrom;
	private String schoolClassTo;
	private String schoolOfficeStartTime;
	private String schoolOfficeEndTime;
	private String schoolFinancialStartDate;
	private String schoolFinancialEndDate;
	private String DateFormat;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		
		if(numberOfShift < 0) {
			errors.add(new ValidationError("numberOfShift", "Number Of Shift should be greater then one."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
	
}
