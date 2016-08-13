package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import enum_package.SchoolClassEnum;
import enum_package.WeekDayEnum;
import lombok.Data;
import models.SchoolBoard;
import models.SchoolType;
import play.data.validation.ValidationError;
import utils.TimeUtiles;
import utils.ValidateFields;

@Data
public class FirstTimeInstituteUpdateForm {
	private int numberOfShift;
	private String hostelFacilitiesIsAvailable;
	private String hostelIsCompulsory;
	private String instituteOfficeWeekStartDay;
	private String instituteOfficeWeekEndDay;
	private String instituteClassFrom;
	private String instituteClassTo;
	private String instituteOfficeStartTime;
	private String instituteOfficeEndTime;
	private String instituteFinancialStartDate;
	private String instituteFinancialEndDate;
	private boolean isHostelFacilitiesAvailable;
	private boolean isHostelCompulsory;
	private String instituteDateFormat;
	private String instituteBoard;
	private String instituteType;
	private List<InstituteShiftAndClassTimingInfoForm.Shift> shifts;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();

		if(numberOfShift < 0) {
			errors.add(new ValidationError("numberOfShift", "Number Of Shift should be greater then one."));
		}

		if(shifts == null || shifts.size() != numberOfShift) {
			errors.add(new ValidationError("shifts", "Please enter shif or class info."));
		}

		if(hostelFacilitiesIsAvailable == null || !(hostelFacilitiesIsAvailable.trim().equalsIgnoreCase("true")
				|| hostelFacilitiesIsAvailable.trim().equalsIgnoreCase("false"))) {
			errors.add(new ValidationError("hostelFacilitiesAvailable", "Please select hostel facilities is available or not."));
		}

		isHostelFacilitiesAvailable = (hostelFacilitiesIsAvailable != null && hostelFacilitiesIsAvailable.trim().equalsIgnoreCase("true"));

		if( isHostelFacilitiesAvailable && (hostelIsCompulsory == null || !(hostelIsCompulsory.trim().equalsIgnoreCase("true")
				|| hostelIsCompulsory.trim().equalsIgnoreCase("false")))) {
			errors.add(new ValidationError("isHostelCompulsory", "Please select hostel is compulsory or optional."));
		}

		isHostelCompulsory = (isHostelFacilitiesAvailable && hostelIsCompulsory != null && hostelIsCompulsory.trim().equalsIgnoreCase("true"));

		if(instituteOfficeWeekStartDay == null || !WeekDayEnum.contains(instituteOfficeWeekStartDay)) {
			errors.add(new ValidationError("schoolOfficeWeekStartDay", "Please select one of the week day from drop down"));
		}

		if(instituteOfficeWeekEndDay == null || !WeekDayEnum.contains(instituteOfficeWeekEndDay)) {
			errors.add(new ValidationError("schoolOfficeWeekEndDay", "Please select one of the week day from drop down"));
		}

		if(instituteClassFrom == null || !SchoolClassEnum.contains(instituteClassFrom)) {
			errors.add(new ValidationError("schoolClassFrom", "Please select start class from drop down"));
		}

		if(instituteClassTo == null || !SchoolClassEnum.contains(instituteClassTo)) {
			errors.add(new ValidationError("schoolClassTo", "Please select end class from drop down"));
		}

		if(!(TimeUtiles.isValidTime(instituteOfficeStartTime))) {
			errors.add(new ValidationError("schoolOfficeStartTime", "Please select office open time."));
		}

		if(!(TimeUtiles.isValidTime(instituteOfficeEndTime))) {
			errors.add(new ValidationError("schoolOfficeEndTime", "Please select closing time."));
		}

		if(instituteFinancialStartDate == null || instituteFinancialStartDate.trim().isEmpty()) {
			errors.add(new ValidationError("schoolFinancialStartDate", "Please select financial year start date."));
		}

		if(instituteFinancialEndDate == null || instituteFinancialEndDate.trim().isEmpty()) {
			errors.add(new ValidationError("schoolFinancialEndDate", "Please select financial year end date."));
		}

		if(!ValidateFields.isValidDateFormat(instituteDateFormat)) {
			errors.add(new ValidationError("schoolDateFormat", "Please select correct date format."));
		}

		if(instituteBoard == null || SchoolBoard.getDisplayNameGivenAffiliatedTo(instituteBoard.trim().toUpperCase()) == null) {
			errors.add(new ValidationError("schoolBoard", "Please enter valid school board without any special characters like @;$."));
		}

		if(instituteType == null || SchoolType.schoolTypeToValue.get(instituteType.trim().toUpperCase())  == null) {
			errors.add(new ValidationError("schoolType", "Please enter valid school type without any special characters like @;$."));
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}

}
