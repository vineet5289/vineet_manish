package dao;

public class Tables {
	public static class Board {
		public static String table = "board";
		//mandatory field
		public static String id = "id";
		public static String boardCode = "board_code";
		public static String boardName = "board_name";
		public static String boardDisplayName = "board_display_name";
		public static String affiliatedTo = "affiliated_to";
		public static String createdAt = "created_at";
	}

	public static class schoolRegistrationRequest {
		public static String table = "school_registration_request";
		public static String id = "id";
		public static String schoolName = "school_name";
		public static String schoolEmail = "school_email";
		public static String mobileNumber = "mobile_number";
		public static String alternativeNumber= "alternative_number";
		public static String schoolRegistrationId = "school_registration_id";
		public static String query = "query";
		public static String schoolAddressLine1 = "school_address_line1";
		public static String schoolAddressLine2 = "school_address_line2";
		public static String city = "city";
		public static String state = "state";
		public static String country = "country";
		public static String pinCodeField = "pin_code";
		public static String statusField = "status";
		public static String authTokenField = "auth_token";
		public static String authTokenGenereatedAtField = "auth_token_genereated_at";
		public static String requestNumberField = "request_number";
		public static String createdAtField = "created_at";
		public static String updatedAtField = "updated_at";
		public static String alertDoneField = "alert_done";
		public static String isActiveField = "is_active";
		public static String contractPersonNameField = "contract_person_name";
		public static String approvalUserName = "approval_user_name";
	}

	public static class School {
		public static String table = "school";
		public static String id = "id";
		public static String name = "name";
		public static String schoolRegistrationId = "school_registration_id";
		public static String schoolUserName = "school_user_name";
		public static String schoolEmail = "school_email";
		public static String addressLine1 = "address_line1";
		public static String addressLine2 = "address_line2";
		public static String city = "city";
		public static String state = "state";
		public static String country = "country";
		public static String pinCode = "pin_code";
		public static String phoneNumber = "phone_number";
		public static String officeNumber = "office_number";
		public static String schoolCategory = "school_category";
		public static String schoolBoardId = "school_board_id";
		public static String schoolType = "school_type";
		public static String createdAt = "created_at";
		public static String updatedAt = "updated_at";
		public static String isActive = "is_active";
		public static String addSchoolRequestId = "add_school_request_id";
		public static String noOfShift = "no_of_shift";
		public static String schoolStartClass = "school_start_class";
		public static String schoolEndClass = "school_end_class";
		public static String schoolStartTime = "school_start_time";
		public static String schoolEndTime = "school_end_time";
		public static String schoolFinancialStartDate = "school_financial_start_date";
		public static String schoolFinancialEndDate = "school_financial_end_date";
		public static String schoolCurrentFinancialYear = "school_current_financial_year";
		public static String schoolCurrentFinancialStartMonth = "school_current_financial_start_month";
		public static String schoolCurrentFinancialEndMonth = "school_current_financial_end_month";
		public static String schoolWebsiteUrl = "school_website_url";
		public static String schoolLogoUrl = "school_logo_url";
	}

	public static class LoginTable{
		public static String table = "login";
		//mandatory field
		public static String id = "id";
		public static String userName = "user_name";
		public static String password = "password";
		public static String passwordState = "password_state";
		public static String role = "role";
		public static String name = "name";
		
		//optional field
		public static String emailId = "email_id";
		public static String createdAt = "created_at";
		public static String updatedAt = "updated_at";
		public static String accessRights = "access_rights";
		public static String isActive = "is_active";
		public static String schoolId = "school_id";
	}

	public static class Employee {
		public static String table = "employee";
		//mandatory field
		public static String id = "id";
		public static String name = "name";
		public static String userName = "user_name";
		public static String schoolId = "school_id";
		public static String gender = "gender";
		public static String phoneNumber = "phone_number";
		public static String requestedUserName = "requested_user_name";
		public static String empCode = "emp_code";

		//optional field
		public static String alternativeNumber = "alternative_number";
		public static String jobTitles = "job_titles";
		public static String empEmail = "emp_email";
		public static String joiningDate = "joining_date";
		public static String leavingDate = "leaving_date";
		public static String departments = "departments";
		public static String dob = "dob";
		public static String addressLine1 = "address_line1";
		public static String addressLine2 = "address_line2";
		public static String city = "city";
		public static String state = "state";
		public static String pinCode = "pin_code";
		public static String country = "country";
		public static String createdAt = "created_at";
		public static String updatedAt = "updated_at";
		public static String isActive = "is_active";
	}

	public static class SchoolShiftInfo {
		public static String table = "school_shift_info";
		public static String id = "id";
		public static String shiftName = "shift_name";
		public static String shiftClassStartTime = "shift_class_start_time";
		public static String shiftClassEndTime = "shift_class_end_time";
		public static String shiftStartDate = "shift_start_date";
		public static String shiftEndDate = "shift_end_date";
		public static String shiftStartMonth = "shift_start_Month";
		public static String shiftEndMonth = "shift_end_Month";
		public static String shiftWeekStartTime = "shift_week_start_time";
		public static String shiftWeekEndTime = "shift_week_end_time";
		public static String shiftStartClassName = "shift_start_class_name";
		public static String shiftEndClassName = "shift_end_class_name";
		public static String shiftAttendenceType = "shift_attendence_type";
		public static String schoolId = "school_id";
		public static String createdAt = "created_at";
		public static String updatedAt = "updated_at";
		public static String isActive = "is_active";
	}
}
