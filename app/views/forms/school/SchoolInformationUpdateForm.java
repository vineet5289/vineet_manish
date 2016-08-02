package views.forms.school;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;

@Data
public class SchoolInformationUpdateForm {
	//for internal use
	private Long id;
	private boolean isActive;

	//non-editable
	private String schoolUserName;
	private String schoolEmail;
	private String state;
	private String country;
	private String schoolBoardName; // set by board id
	private String schoolType;
	private String schoolCurrentFinancialYear;
	private String schoolCurrentFinancialStartMonth;
	private String schoolCurrentFinancialEndMonth;

	//editable
	private String name;
	private String schoolRegistrationId;
	private String city;
	private String pinCode;
	private String addressLine1;
	private String addressLine2;
	private String phoneNumber;
	private String officeNumber;
	private String schoolCategory;
	private int noOfShift;
	private String schoolStartClass;
	private String schoolEndClass;
	private String schoolStartTime;
	private String schoolEndTime;
	private Date schoolFinancialStartDate;
	private Date schoolFinancialEndDate;
	private String schoolWebsiteUrl;
	private String schoolLogoUrl;
	private List<Shift> shifts = new ArrayList<Shift>();

	@Data
	public static class Shift {

		//for internal use
		private boolean isActive;
		//for internal use & non-editable
		private String shiftName;
		//editable
		private String shiftClassStartTime;
		private String shiftClassEndTime;
		private String shiftWeekStartTime;
		private String shiftWeekEndTime;
		private String shiftStartClassName;
		private String shiftEndClassName;
		private String shiftAttendenceType;
		
	}

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
