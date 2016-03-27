package views.forms.students_guardian;

import lombok.Data;

@Data
public class StudentsGuardianRegistrationFrom {
	//students
	public String studentFirstName;
	public String studentMiddleName;
	public String studentLastName;
	public String studentEmail;
	public String studentPhoneNumber;
	public String studentAddress1;
	public String studentAddress2;
	public String studentCity;
	public String studentState;
	public String studentCountry;
	public String studentPincode;

	//father
	public String fatherFirstName;
	public String fatherMiddleName;
	public String fatherLastName;

	public String motherFirstName;
	public String motherMiddleName;
	public String motherLastName;

	public String email1;
	public String email1BelongTo;
	public String email2;
	public String email2BelongTo;
	public String phoneNumber1;
	public String phoneNumber1BelongTo;
	public String phoneNumber2;
	public String phoneNumber2BelongTo;
	public String fatherAddress1;
	public String fatherAddress2;
	public String fatherCity;
	public String fatherState;
	public String fatherCountry;
	public String fatherPincode;

	public String motherAddress1;
	public String motherAddress2;
	public String motherCity;
	public String motherState;
	public String motherCountry;
	public String motherPincode;

	//guardian
	public String guardianFirstName;
	public String guardianMiddleName;
	public String guardianLastName;
	public String guardianEmail;
	public String guardianPhoneNumber;
	public String guardianAddress1;
	public String guardianAddress2;
	public String guardianCity;
	public String guardianState;
	public String guardianCountry;
	public String guardianPincode;

	public boolean isParentsAddressSame;
	public boolean isStudentParentsAddressSame;
	public boolean isParentsMobileNumberSame;
	public boolean isParentsEmailSame;
	public boolean guardianAsFather;
	public boolean guardianAsMother;
	public boolean guardianAsSeperate;
	public boolean isStudentGuardianAddressSame;
}
