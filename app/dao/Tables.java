package dao;

public class Tables {
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
		public static String leavingDate = "departments";
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
		public static String isActive = "requested_user_name";
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
	}
}
