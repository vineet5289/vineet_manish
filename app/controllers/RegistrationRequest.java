package controllers;

import play.data.Form;
import play.mvc.Result;
import views.forms.*;
import views.forms.LoginForm;
import views.forms.RegisterSchool;
import views.forms.SchoolFormData;
import views.html.registerIndex;
import views.html.newuser;

public class RegistrationRequest extends CustomController {

	public  Result registrationRequest() {
		//Form<RegisterSchool> registerSchoolForm = Form.form(RegisterSchool.class);
		System.out.println("&&&&&&&&&&&&&&&&&&&");
		return redirect(routes.RegistrationRequest.newUser());
	}

	public Result postRegistrationRequest() {
		return ok("done");
	}
	
	
	
	
	
	
	
	public  Result newUser() {
	    Form<SchoolFormData> formData = Form.form(SchoolFormData.class);
	    System.out.println("dkjhdjkegfjgehfjgef");
	    Form<RegisterSchool> signupData = Form.form(RegisterSchool.class);
	    //Form<RegistrationFormData> regFormData = Form.form(RegistrationFormData.class);
	   // SearchFormData searchFormData = new SearchFormData();
	    //Form<SearchFormData> searchForm = Form.form(SearchFormData.class).fill(searchFormData);
	    return ok(newuser.render(formData,signupData));
	  }
}
