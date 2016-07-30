package views.forms.employee;

import lombok.Data;

@Data
public class AddEmployeeForm {

	//compulsory field
	private String empName;
	private String empPhoneNumber;
	private String department;

	//optional field
	private String empCode;
	private String empEmail;
	private String jobTitle;
}
