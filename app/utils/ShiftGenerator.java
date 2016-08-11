package utils;

import java.util.ArrayList;
import java.util.List;

import views.forms.school.FirstTimeSchoolUpdateForm;
import views.forms.school.SchoolShiftAndClassTimingInfoForm;

public class ShiftGenerator {
	private static final List<String> shiftName = new ArrayList<String>()
	{{
	    add("Class Details");
	    add("First Shift Details");
	    add("Second Shift Details");
	    add("Third Shift Details");
	    add("Fourth Shift Details");
	    add("Fifth Shift Details");
	    add("Sixth Shift Details");
	    add("Seventh Shift Details");
	    add("Eight Shift Details");
	    add("Ninth Shift Details");
	    add("Tenth Shift Details");
	}};

	public static SchoolShiftAndClassTimingInfoForm generateShift(FirstTimeSchoolUpdateForm firstTimeSchoolUpdate) {
//		private String shiftAttendenceType;
		int numberOfShift = firstTimeSchoolUpdate.getNumberOfShift();
		SchoolShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfoForm = new SchoolShiftAndClassTimingInfoForm();
		schoolShiftAndClassTimingInfoForm.setNumberOfShift(numberOfShift);
		if(numberOfShift == 1) {
			SchoolShiftAndClassTimingInfoForm.Shift shift = new SchoolShiftAndClassTimingInfoForm.Shift();
			shift.setShiftName(shiftName.get(0));
			shift.setShiftClassStartTime(firstTimeSchoolUpdate.getSchoolOfficeStartTime());
			shift.setShiftClassEndTime(firstTimeSchoolUpdate.getSchoolOfficeEndTime());
			shift.setShiftWeekStartDay(firstTimeSchoolUpdate.getSchoolOfficeWeekStartDay());
			shift.setShiftWeekEndDay(firstTimeSchoolUpdate.getSchoolOfficeWeekEndDay());
			shift.setShiftStartClassFrom(firstTimeSchoolUpdate.getSchoolClassFrom());
			shift.setShiftEndClassTo(firstTimeSchoolUpdate.getSchoolClassTo());
			schoolShiftAndClassTimingInfoForm.getShifts().add(shift);
		} else {
			for(int i = 0; i < numberOfShift; i++) {
				SchoolShiftAndClassTimingInfoForm.Shift shift = new SchoolShiftAndClassTimingInfoForm.Shift();
				shift.setShiftName(shiftName.get(i+1));
				shift.setShiftClassStartTime(firstTimeSchoolUpdate.getSchoolOfficeStartTime());
				shift.setShiftClassEndTime(firstTimeSchoolUpdate.getSchoolOfficeEndTime());
				shift.setShiftWeekStartDay(firstTimeSchoolUpdate.getSchoolOfficeWeekStartDay());
				shift.setShiftWeekEndDay(firstTimeSchoolUpdate.getSchoolOfficeWeekEndDay());
				shift.setShiftStartClassFrom(firstTimeSchoolUpdate.getSchoolClassFrom());
				shift.setShiftEndClassTo(firstTimeSchoolUpdate.getSchoolClassTo());
				schoolShiftAndClassTimingInfoForm.getShifts().add(shift);
			}
		}
		return schoolShiftAndClassTimingInfoForm;
	}
}
