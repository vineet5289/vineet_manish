package views.forms.school;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SubjectForm {
	public List<SubjectInfo> subjectsInfo = new ArrayList<SubjectInfo>();
	@Data
	public static class SubjectInfo {
		public String subjectName;
		public String classId;
	}
}
