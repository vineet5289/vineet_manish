package views.forms;

import java.util.ArrayList;
import java.util.List;
import play.data.validation.ValidationError;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterSchool {
	private String schoolName="";
	private String principalName="";
	private String email="";
	private String address="";
	private String contact="";
	private String schoolRegId="";
	private String question="";
	
	private static Pattern emailNamePtrn = Pattern.compile(
		    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	public RegisterSchool()
	{
		
	}
	
	public RegisterSchool(String schoolName,String principalName,String email,String address,String contact,String schoolRegId,String question){
		this.schoolName=schoolName;
		this.address=address;
		this.principalName=principalName;
		this.email=email;
		this.contact=contact;
		this.schoolRegId=schoolRegId;
		this.question=question;
	}
	
	 public static boolean validateEmailAddress(String email){
         
	        Matcher mtch = emailNamePtrn.matcher(email);
	        if(mtch.matches()){
	            return true;
	        }
	        return false;
	 }
	
	public List<ValidationError> validate() {

		  List<ValidationError> errors = new ArrayList<>();

		  if (schoolName == null || schoolName.length() == 0) {
		    errors.add(new ValidationError("schoolName", "No name was given."));
		  }
		  
		   if (principalName == null || principalName.length() == 0) {
		    errors.add(new ValidationError("principalname", "No name was given."));
		  }
		   
		   if (address == null || address.length() == 0) {
			    errors.add(new ValidationError("address", "No name was given."));
			  }
		   if (contact == null || contact.length() == 0) {
			    errors.add(new ValidationError("mobile", "No name was given."));
			  }
		   
		   if(validateEmailAddress(email)==false)
		   {
			   errors.add(new ValidationError("Email","check your email"));
		   }
		   
		   
		   
		   
				   
		  if(errors.size() > 0)
		    return errors;

		  return null;
		}
}
