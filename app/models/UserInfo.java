package models;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfo {
	public String name;
	public String userName;
	public String emailId;
	public String schoolIds;
	public String phoneNumber1;
	public String phoneNumber2;
	public String designation;
	public Date joiningDate;
	public Date leavingDate;
	public String gender;
	public String empCategory;
	public String department;
	public Date dob;
	public String addressLine1Field;
	public String addressLine2Field;
	public String city;
	public String state;
	public String pinCode;
	public String country;
	public String role;
}
