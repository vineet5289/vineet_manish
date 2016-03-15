package views.forms;

import lombok.Data;

import enum_package.SchoolTypeEnum;

@Data
public class SchoolRegistration {
	private String name;
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
	private SchoolTypeEnum schoolType;
}
