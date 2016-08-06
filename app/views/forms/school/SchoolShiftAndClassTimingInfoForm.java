package views.forms.school;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

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
		private String shiftWeekStartTime;
		private String shiftWeekEndTime;
		private String shiftStartClassName;
		private String shiftEndClassName;
		private String shiftAttendenceType;

	}
}
