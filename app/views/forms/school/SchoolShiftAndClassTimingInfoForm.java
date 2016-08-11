package views.forms.school;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SchoolShiftAndClassTimingInfoForm {
	private int numberOfShift;
	private List<Shift> shifts = new ArrayList<Shift>();

	@Data
	public static class Shift {
		//for internal use & non-editable
		private String shiftName;
		//editable
		private String shiftClassStartTime;
		private String shiftClassEndTime;
		private String shiftWeekStartDay;
		private String shiftWeekEndDay;
		private String shiftStartClassFrom;
		private String shiftEndClassTo;
		private String shiftAttendenceType;

	}
}
