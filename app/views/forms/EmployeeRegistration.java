package views.forms;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeRegistration {
	 private String name; // **
	 private String designation;
	 private String emp_email;
	 private Date joiningDate;
	 private Date leavingDate;
//	 gender enum('M', 'F') NOT NULL,
//	 emp_category enum('TEACHER', 'ACCOUNTENT') NOT NULL,
//	 department varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
	 private Date dob;
	 private String address_line1;
	 private String address_line2;
	 private String city;
	 private String state;
	 private String country;
	 private String pin_code;
	 private String phone_number1;//**
	 private String phone_number2;
}
