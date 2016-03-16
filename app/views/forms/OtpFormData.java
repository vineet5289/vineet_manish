package views.forms;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class OtpFormData {
	public String referencekey="";
	public String otppwd="";

	public OtpFormData(){
		
	}

	public OtpFormData(String referencekey ,String otppwd)
	{
	  this.referencekey=referencekey;
	  this.otppwd=otppwd;
	}


	  /**
	   * Validates Form<StudentFormData>.
	   * Called automatically in the controller by bindFromRequest().
	   * 
	   * Validation checks include:
	   * <ul>
	   * <li> Name must be non-empty.
	   * <li> Password must be at least five characters.
	   *
	   * @return Null if valid, or a List[ValidationError] if problems found.
	   */
	  public List<ValidationError> validate() {

	    List<ValidationError> errors = new ArrayList<>();

	    if (referencekey == null || referencekey.length() == 0) {
	      errors.add(new ValidationError("referencekey", "No referencekey was given."));
	    }
	    
	     if (otppwd == null || otppwd.length() == 0) {
	      errors.add(new ValidationError("password", "No password was given."));
	    } 



	    if(errors.size() > 0)
	      return errors;

	    return null;
	  }
	}



