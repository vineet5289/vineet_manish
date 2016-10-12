package views.forms.employee;

import lombok.Data;

@Data
public class EmployeeDetailsForm {

	//inforation used for internal purpose
	public long id;
	public boolean isActiveEmployee;
	public long instituteId;
	public String requestedUserName;

	//public information for every user inside institute
	public String name;
	public String gender;
	public String jobTitles;

	//information that is visable to only authroized person
	public String userName;
	public String phoneNumber;
	public String empCode;
	public String empPreferedName;
	public String empAlternativeEmail;
	public String alternativeNumber;
	public String empEmail;
	public String joiningDate;
	public String dob;
	public String addressLine1;
	public String addressLine2;
	public String city;
	public String state;
	public String pinCode;
	public String country;

	//just only for deleted employee
	public String leavingDate;
}