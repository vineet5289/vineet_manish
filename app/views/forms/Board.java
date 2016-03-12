package views.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import play.data.validation.ValidationError;

@Data
public class Board {
	private String boardCode;
	private String boardName;
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<>();
		if (!isValidString(boardCode)) {
			errors.add(new ValidationError("boardCode", "Board Code is empty"));
		}

		if (!isValidString(boardName)) {
			errors.add(new ValidationError("boardName", "Board Name is empty"));     
		}
		return (errors.size() > 0) ? errors : null;
	}

	private boolean isValidString(String str) {
		if(str == null || str.isEmpty())
			return false;
		return true;
	}
}
