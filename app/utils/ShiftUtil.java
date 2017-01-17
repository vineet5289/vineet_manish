package utils;

import java.util.ArrayList;
import java.util.List;

import views.forms.institute.FirstTimeInstituteUpdateForm;
import views.forms.institute.InstituteShiftAndClassTimingInfoForm;

public class ShiftUtil {
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

	public static InstituteShiftAndClassTimingInfoForm generateShift(FirstTimeInstituteUpdateForm firstTimeSchoolUpdate) {
		int numberOfShift = firstTimeSchoolUpdate.getNumberOfShift();
		InstituteShiftAndClassTimingInfoForm schoolShiftAndClassTimingInfoForm = new InstituteShiftAndClassTimingInfoForm();
//		schoolShiftAndClassTimingInfoForm.setNumberOfShift(numberOfShift);
//		if(numberOfShift == 1) {
//			InstituteShiftAndClassTimingInfoForm.Shift shift = new InstituteShiftAndClassTimingInfoForm.Shift();
//			shift.setShiftName(shiftName.get(0));
//			shift.setShiftClassStartTime(firstTimeSchoolUpdate.getSchoolOfficeStartTime());
//			shift.setShiftClassEndTime(firstTimeSchoolUpdate.getSchoolOfficeEndTime());
//			shift.setShiftWeekStartDay(firstTimeSchoolUpdate.getSchoolOfficeWeekStartDay());
//			shift.setShiftWeekEndDay(firstTimeSchoolUpdate.getSchoolOfficeWeekEndDay());
//			shift.setShiftStartClassFrom(firstTimeSchoolUpdate.getSchoolClassFrom());
//			shift.setShiftEndClassTo(firstTimeSchoolUpdate.getSchoolClassTo());
////			shift.setShiftAttendenceType(AttendanceTypeEnum.of(firstTimeSchoolUpdate.getAttendenceType()).name());
//			schoolShiftAndClassTimingInfoForm.getShifts().add(shift);
//		} else {
//			for(int i = 0; i < numberOfShift; i++) {
//				InstituteShiftAndClassTimingInfoForm.Shift shift = new InstituteShiftAndClassTimingInfoForm.Shift();
//				shift.setShiftName(shiftName.get(i+1));
//				shift.setShiftClassStartTime(firstTimeSchoolUpdate.getSchoolOfficeStartTime());
//				shift.setShiftClassEndTime(firstTimeSchoolUpdate.getSchoolOfficeEndTime());
//				shift.setShiftWeekStartDay(firstTimeSchoolUpdate.getSchoolOfficeWeekStartDay());
//				shift.setShiftWeekEndDay(firstTimeSchoolUpdate.getSchoolOfficeWeekEndDay());
//				shift.setShiftStartClassFrom(firstTimeSchoolUpdate.getSchoolClassFrom());
//				shift.setShiftEndClassTo(firstTimeSchoolUpdate.getSchoolClassTo());
////				shift.setShiftAttendenceType(AttendanceTypeEnum.of(firstTimeSchoolUpdate.getAttendenceType()).name());
//				schoolShiftAndClassTimingInfoForm.getShifts().add(shift);
//			}
//		}
		return schoolShiftAndClassTimingInfoForm;
	}
}
