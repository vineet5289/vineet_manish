package views.forms;

import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

import models.SchoolType;
import models.State;
import models.SchoolBoard;


/**
 * Backing class for the Student data form.
 * Requirements:
 * <ul>
 * <li> All fields are public, 
 * <li> All fields are of type String or List[String].
 * <li> A public no-arg constructor.
 * <li> A validate() method that returns null or a List[ValidationError].
 * </ul>
 */


public class SchoolFormData {
	  public String schoolname = "";
	  public String principalname = "";
	  public String password = "";
	  public String schoolregistrationid="";
	  public String address="";
	  public String city="";
	  public String state="";
	  public String pincode="";
	  public String schooltype = "";
	 
	  public String contact="";

	  public String schoolboard="";
	 
	  
	 



/** Required for form instantiation. */
	 public SchoolFormData(){
	 
	 }
   

/**
 * Creates an initialized form instance. Assumes the passed data is valid. 
 * @param name The name.
 * @param P_name Principal name.
 * @param password The password.
 * @param level The level.
 * @param gpa The GPA.
 * @param hobbies The hobbies.
 * @param majors The majors. 
 * @param schoolType Type of school Govt or Private. 
 */
  public SchoolFormData(String schoolname , String principalname , String password , String schoolregistrationid ,String address, String city , State state , String pincode,String contact ,SchoolBoard schoolboard , SchoolType schooltype) {
  this.schoolname = schoolname;
  this.schoolregistrationid=schoolregistrationid;
  this.principalname=principalname;
  this.address=address;
  this.city=city;
  this.pincode=pincode;
  this.contact=contact;
  this.password = password;
  this.state=state.getname();
  this.schooltype=schooltype.getname();
  this.schoolboard=schoolboard.getname();
  
}

/**
 * Validates Form<StudentFormData>.
 * Called automatically in the controller by bindFromRequest().
 * 
 * Validation checks include:
 * <ul>
 * <li> Name must be non-empty.
 * <li> Password must be at least five characters.
 * <li> Hobbies (plural) are optional, but if specified, must exist in database.
 * <li> Grade Level is required and must exist in database.
 * <li> GPA is required and must exist in database.
 * <li> Majors (plural) are optional, but if specified, must exist in database.
 * </ul>
 *
 * @return Null if valid, or a List[ValidationError] if problems found.
 */
public List<ValidationError> validate() {

  List<ValidationError> errors = new ArrayList<>();

  if (schoolname == null || schoolname.length() == 0) {
    errors.add(new ValidationError("schoolname", "No name was given."));
  }
  
   if (principalname == null || principalname.length() == 0) {
    errors.add(new ValidationError("principalname", "No name was given."));
  }

  if (password == null || password.length() == 0) {
    errors.add(new ValidationError("password", "No password was given."));
  } else if (password.length() < 5) {
    errors.add(new ValidationError("password", "Given password is less than five characters."));
  }
  
  if (schoolboard== null || schoolboard.length() == 0) {
	    errors.add(new ValidationError("schoolboard", "No schoolboard was given."));
	  } else if (SchoolBoard.findSchoolBoard(schoolboard) == null) {
	    errors.add(new ValidationError("schoolboard", "Invalid Schoolboard: " + schoolboard + "."));
	  }
  
  if (schooltype == null || schooltype.length() == 0) {
	    errors.add(new ValidationError("schooltype", "No gpa was given."));
	  } else if (SchoolType.findSchoolType(schooltype) == null) {
	    errors.add(new ValidationError("schooltype", "Invalid GPA: " + schooltype + "."));
	  }
  
  if (state == null || state.length() == 0) {
	    errors.add(new ValidationError("state", "No state was given."));
	  } else if (State.findState(state) == null) {
	    errors.add(new ValidationError("", "Invalid State: " + state + "."));
	  }

 
  if(errors.size() > 0)
    return errors;

  return null;
}
}

