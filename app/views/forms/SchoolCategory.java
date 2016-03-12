package views.forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import lombok.Data;

@Data
public class SchoolCategory {
	private String schoolCategoryType;
	private String categoryDescription;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!isValidString(schoolCategoryType)) {
			errors.add(new ValidationError("schoolCategoryType", "School Category Type is empty"));
		}

		if (!isValidString(categoryDescription)) {
			errors.add(new ValidationError("boardName", "Category Description is empty"));     
		}
		return (errors.size() > 0) ? errors : null;
	}

	private boolean isValidString(String str) {
		if(str == null || str.isEmpty())
			return false;
		return true;
	}
}
