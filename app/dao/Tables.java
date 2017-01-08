package dao;

public class Tables {
  public static class Board {
    public static String table = "board";
    // mandatory field
    public static String id = "id";
    public static String boardCode = "board_code";
    public static String boardName = "board_name";
    public static String boardDisplayName = "board_display_name";
    public static String affiliatedTo = "affiliated_to";
    public static String createdAt = "created_at";
  }

  public static class InstituteRegistrationRequest {
    public static String table = "institute_registration_request";
    public static String id = "id";
    public static String name = "name";
    public static String email = "email";
    public static String phoneNumber = "phone_number";
    public static String officeNumber = "office_number";
    public static String registrationId = "registration_id";
    public static String contactPersonName = "contact_person_name";
    public static String addressLine1 = "address_line1";
    public static String addressLine2 = "address_line2";
    public static String city = "city";
    public static String state = "state";
    public static String country = "country";
    public static String pinCode = "pin_code";
    public static String noOfInstitute = "no_of_institute";
    public static String groupOfInstitute = "group_of_institute";
    public static String query = "query";
    public static String authToken = "auth_token";
    public static String authTokenGenereatedAt = "auth_token_genereated_at";
    public static String status = "status";
    public static String requestNumber = "request_number";
    public static String isActive = "is_active";
    public static String notificationDone = "notification_done";
    public static String approvalUserName = "approval_user_name";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class HeadInstitute {
    public static String table = "head_institute";
    public static String id = "id";
    public static String name = "name";
    public static String preferedName = "prefered_name";
    public static String chairPersonName = "chairperson_name";
    public static String managingDirector = "managing_director";
    public static String registrationId = "registration_id";
    public static String userName = "user_name";
    public static String email = "email";
    public static String alternativeEmail = "alternative_email";
    public static String addressLine1 = "address_line1";
    public static String addressLine2 = "address_line2";
    public static String city = "city";
    public static String state = "state";
    public static String country = "country";
    public static String pinCode = "pin_code";
    public static String phoneNumber = "phone_number";
    public static String officeNumber = "office_number";
    public static String websiteUrl = "website_url";
    public static String logoUrl = "logo_url";
    public static String groupOfInstitute = "group_of_institute";
    public static String noOfInstitute = "no_of_institute";
    public static String isActive = "is_active";
    public static String addInstituteRequestId = "add_institute_request_id";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Institute {
    public static String table = "institute";
    public static String id = "id";
    public static String name = "name";
    public static String preferedName = "prefered_name";
    public static String chairPersonName = "chairperson_name";
    public static String managingDirector = "managing_director";
    public static String registrationId = "registration_id";
    public static String userName = "user_name";
    public static String phoneNumber = "phone_number";
    public static String officeNumber = "office_number";
    public static String email = "email";
    public static String alternativeEmail = "alternative_email";
    public static String addressLine1 = "address_line1";
    public static String addressLine2 = "address_line2";
    public static String city = "city";
    public static String state = "state";
    public static String country = "country";
    public static String pinCode = "pin_code";
    public static String isHostelFacilitiesAvailable = "is_hostel_facilities_available";
    public static String isHostelCompulsory = "is_hostel_compulsory";
    public static String boardId = "board_id";
    public static String type = "type";
    public static String noOfShift = "no_of_shift";
    public static String classFrom = "class_from";
    public static String classTo = "class_to";
    public static String officeStartTime = "office_start_time";
    public static String officeEndTime = "office_end_time";
    public static String financialStartDay = "financial_start_day";
    public static String financialEndDay = "financial_end_day";
    public static String financialStartMonth = "financial_start_month";
    public static String financialEndMonth = "financial_end_month";
    public static String financialStartYear = "financial_start_year";
    public static String financialEndYear = "financial_end_year";
    public static String currentFinancialYear = "current_financial_year";
    public static String financialStartDate = "financial_start_date";
    public static String financialEndDate = "financial_end_date";
    public static String officeWeekStartDay = "office_week_start_day";
    public static String officeWeekEndDay = "office_week_end_day";
    public static String websiteUrl = "website_url";
    public static String logoUrl = "logo_url";
    public static String isActive = "is_active";
    public static String headInstituteId = "head_institute_id";
    public static String mandatoryInfoReq = "mandatory_info_req";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Login {
    public static String table = "login";
    // mandatory field
    public static String id = "id";
    public static String userName = "user_name";
    public static String password = "password";
    public static String passwordState = "password_state";
    public static String role = "role";
    public static String name = "name";

    // optional field
    public static String type = "type";
    public static String emailId = "email_id";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
    public static String accessRights = "access_rights";
    public static String isActive = "is_active";
    public static String instituteId = "institute_id";
    public static String loginSessionCount = "login_session_count";
  }

  public static class Employee {
    public static String table = "employee";
    // mandatory field
    public static String id = "id";
    public static String name = "name";
    public static String userName = "user_name";
    public static String instituteId = "institute_id";
    public static String gender = "gender";
    public static String phoneNumber = "phone_number";
    public static String requestedUserName = "requested_user_name";
    public static String empCode = "emp_code";

    // optional field
    public static String empPreferedName = "emp_prefered_name";
    public static String empAlternativeEmail = "emp_alternative_email";
    public static String alternativeNumber = "alternative_number";
    public static String jobTitles = "job_titles";
    public static String empEmail = "emp_email";
    public static String joiningDate = "joining_date";
    public static String leavingDate = "leaving_date";
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

  public static class InstituteShiftInfo {
    public static String table = "institute_shift_info";
    public static String id = "id";
    public static String shiftName = "shift_name";
    public static String shiftClassStartTime = "shift_class_start_time";
    public static String shiftClassEndTime = "shift_class_end_time";
    public static String shiftWeekStartDay = "shift_week_start_time";
    public static String shiftWeekEndDay = "shift_week_end_time";
    public static String shiftStartClassFrom = "shift_start_class_from";
    public static String shiftEndClassTo = "shift_end_class_To";
    public static String shiftAttendenceType = "shift_attendence_type";
    public static String instituteId = "institute_id";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
    public static String isActive = "is_active";
  }

  public static class Permissions {
    public static String table = "permissions";
    public static String id = "id";
    public static String permissionName = "permission_name";
    public static String permissionDescription = "permission_description";
    public static String isActive = "is_active";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class UserPermissions {
    public static String table = "user_permissions";
    public static String id = "id";
    public static String userName = "user_name";
    public static String permissionsList = "permissions_list";
    public static String instituteId = "institute_id";
    public static String isActive = "is_active";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Role {
    public static String table = "roles";
    public static String id = "id";
    public static String roleName = "role_name";
    public static String roleDescription = "role_description";
    public static String roleAddedBy = "role_added_by";
    public static String permission = "permission";
    public static String instituteId = "institute_id";
    public static String headInstituteId = "head_institute_id";
    public static String isActive = "is_active";
    public static String isEditable = "is_editable";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Group {
    public static String table = "groups";
    public static String id = "id";
    public static String groupName = "group_name";
    public static String groupDescription = "group_description";
    public static String groupAddedBy = "group_added_by";
    public static String instituteId = "institute_id";
    public static String headInstituteId = "head_institute_id";
    public static String isActive = "is_active";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Subject {
    public static String table = "subject";
    public static String id = "id";
    public static String subjectName = "subject_name";
    public static String classId = "class_id";
    public static String instituteId = "institute_id";
    public static String sectionId = "section_id";
    public static String subjectId = "subject_id";
    public static String subjectCode = "subject_code";
    public static String isActive = "is_active";
    public static String createdBy = "created_by";
    public static String description = "description";
    public static String recommendedBook = "recommended_book";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Class {
    public static String table = "class";
    public static String id = "id";
    public static String className = "class_name";
    public static String instituteId = "institute_id";
    public static String classStartTime = "class_start_time";
    public static String classEndTime = "class_end_time";
    public static String noOfPeriod = "no_of_period";
    public static String parentClassId = "parent_class_id";
    public static String parentClassName = "parent_class_name";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
    public static String isActive = "is_active";
    public static String userName = "user_name";
  }

  public static class Timetable {
    public static String table = "timetable";
    public static String id = "id";
    public static String day = "day";
    public static String daySeq = "day_seq";
    public static String periodNo = "period_no";
    public static String periodName = "period_name";
    public static String startTime = "start_time";
    public static String endTime = "end_time";
    public static String duration = "duration";
    public static String numberOfDays = "number_of_days";
    public static String instituteId = "institute_id";
    public static String classId = "class_id";
    public static String sectionId = "section_id";
    public static String sameAsPreviousPeriod = "same_as_previous_period";
    public static String timeTableUpdatedBy = "time_table_updated_by";
    public static String isActive = "is_active";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class ClassCurriculum {
    public static String table = "class_curriculum";
    public static String id = "id";
    public static String instituteId = "institute_id";
    public static String classId = "class_id";
    public static String sectionId = "section_id";
    public static String professorId = "professor_id";
    public static String subjectId = "subject_id";
    public static String timetableId = "timetable_id";
    public static String professorCategory = "professor_category";
    public static String subjectAllocated = "subject_allocated";
    public static String professorAllocated = "professor_allocated";
    public static String slotAllocated = "slot_allocated";
    public static String startTime = "start_time";
    public static String endTime = "end_time";
    public static String updatedBy = "updated_by";
    public static String isActive = "is_active";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }

  public static class Attendance {
    public static String table = "attendance";
    public static String id = "id";
    public static String date = "attendance_date";
    public static String instituteId = "institute_id";
    public static String classId = "class_id";
    public static String sectionId = "section_id";
    public static String subjectId = "subject_id";
    public static String periodId = "period_id";
    public static String studentsAttendance = "students_attendance";
    public static String attendanceTakenBy = "attendance_taken_by";
    public static String attendanceUpdatedBy = "attendance_updated_by";
    public static String noOfPresentStudents = "no_of_present_students";
    public static String noOfAbsentStudents = "no_of_absent_students";
    public static String totalNoOfStudents = "total_no_of_students";
    public static String isActive = "is_active";
    public static String createdAt = "created_at";
    public static String updatedAt = "updated_at";
  }
}
