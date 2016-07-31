package dao;

public class TableDetails {
	public static class LoginTable{
		public static String tableName = "login";
		//mandatory field
		public static String idFieldName = "id";
		public static String userNameFieldName = "user_name";
		public static String passwordFieldName = "password";
		public static String passwordStateFieldName = "password_state";
		public static String roleFieldName = "role";
		public static String nameFieldName = "name";
		
		//optional field
		public static String emailIdFieldName = "email_id";
		public static String createdAtFieldName = "created_at";
		public static String updatedAtFieldName = "updated_at";
		public static String accessRightsFieldName = "access_rights";
		public static String isActiveFieldName = "is_active";
		public static String schoolIdFieldName = "school_id";
	}

	public static class Employee {
		public static String tableName = "employee";
		//mandatory field
		public static String idFieldName = "id";
		public static String nameFieldName = "name";
		public static String userNameFieldName = "user_name";
		public static String schoolIdFieldName = "school_id";
		public static String genderFieldName = "gender";
		public static String phoneNumberFieldName = "phone_number";
		public static String requestedUserNameFieldName = "requested_user_name";

		//optional field
		public static String alternativeNumberFieldName = "alternative_number";
		public static String jobTitlesFieldName = "job_titles";
		public static String emp_emailFieldName = "emp_email";
		public static String joiningDateFieldName = "joining_date";
		public static String leavingDateFieldName = "departments";
		public static String departmentsFieldName = "departments";
		public static String dobFieldName = "dob";
		public static String addressLine1FieldName = "address_line1";
		public static String addressLine2FieldName = "address_line2";
		public static String cityFieldName = "city";
		public static String stateFieldName = "state";
		public static String pinCodeFieldName = "pin_code";
		public static String countryFieldName = "country";
		public static String createdAtFieldName = "created_at";
		public static String updatedAtFieldName = "updated_at";
		public static String isActiveFieldName = "requested_user_name";
	}

	public static class schoolRegistrationRequest {
		public static String tableName = "school_registration_request";
		public static String idFieldName = "id";
		public static String schoolNameFieldName = "school_name";
		public static String schoolEmailFieldName = "school_email";
		public static String mobileNumberFieldName = "mobile_number";
		public static String alternativeNumberFieldName= "alternative_number";
		public static String schoolRegistrationIdFieldName = "school_registration_id";
		public static String queryFieldName = "query";
		public static String schoolAddressLine1FieldName = "school_address_line1";
		public static String schoolAddressLine2FieldName = "school_address_line2";
		public static String cityFieldName = "city";
		public static String stateFieldName = "state";
		public static String countryFieldName = "country";
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
