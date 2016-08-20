package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SubjectForm {

	public List<SubjectInfo> subjectsInfo = new ArrayList<SubjectInfo>();
	public long classId;
	public long schoolId;
	public String userName;

	@Data
	public static class SubjectInfo {
		public String subjectName;
		public String subjectCode;
	}
}
