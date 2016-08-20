package views.forms.institute;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import lombok.Data;

@Data
public class ClassForm {
	public Long schoolId;
	public String userName;
	public List<AddClass> classes = new ArrayList<AddClass>();

	@Data
	public static class AddClass {
		public String className; // acctual class 
		public String classStartTime; //
		public String classEndTime; //
		public int noOfPeriod; //
		public String nameOfSection; //
		private boolean isActive;
		private List<String> sectionNames = new ArrayList<String>();
	}

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		for(AddClass c : classes) {
			if(c.nameOfSection != null && !c.nameOfSection.trim().isEmpty()) {
				String[] sections = c.nameOfSection.trim().split(",");
				for(String section : sections) {
					String afterTrim = section.trim();
					if(!afterTrim.isEmpty())
						c.getSectionNames().add(afterTrim);
				}
			}
		}

		if(errors.size() > 0)
			return errors;
		return null;
	}
}
