package models;

import lombok.Data;

import org.joda.time.DateTime;

import enum_package.SchoolType;

@Data
public class SchoolRegistration {
	private String name;
	private Long schoolRegistrationId;
	private String schoolUserName;
	private String schooleEmail;
	private String schoolPrincipleName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String pinCode;
	private String officeNumber1;
	private String officeNumber2;
	private String country;
	private int noOfShift;
	private Long schoolCategoryId;
	private Long schoolBoardId;
	private SchoolType schoolType;
	private DateTime createdAt;
	private DateTime updatedAt;
	private boolean isActive;
}
