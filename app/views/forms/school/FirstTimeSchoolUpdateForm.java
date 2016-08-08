package views.forms.school;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.ValidationError;
import lombok.Data;

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
	private Date schoolFinancialStartDate;
	private Date schoolFinancialEndDate;

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
