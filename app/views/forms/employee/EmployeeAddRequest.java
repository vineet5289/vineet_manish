package views.forms.employee;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeAddRequest {
	private String name;
	private String empId; // optional 
	private String contact; 
	private String email; // option
	private Date joiningDate;
	private String requestedUserName;
}
