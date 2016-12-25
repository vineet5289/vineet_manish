package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class InstituteShiftAndClassTimingInfoForm {
	private int numberOfShift;
	private String tabDisplayName;
	private List<Shift> shifts = new ArrayList<Shift>();

	@Data
	public static class Shift {
		private String shiftName;
		private String shiftClassStartTime;
		private String shiftClassEndTime;
		private String shiftWeekStartDay;
		private String shiftWeekEndDay;
		private int shiftStartClassIndex;
		private String shiftStartClassFrom;
		private int shiftEndClassIndex;
		private String shiftEndClassTo;
		private String shiftAttendenceType;
	}
}
