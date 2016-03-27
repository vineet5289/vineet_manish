package views.forms.school;

import lombok.Data;

@Data
public class DisplayClassForm {
	public long id;
	public String className;
	public long schoolId;
	public String classStartTime;
	public String classEndTime;
	public int noOfPeriod;
	public String parentClass;
}
