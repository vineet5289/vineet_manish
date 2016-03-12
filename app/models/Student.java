package models;

import java.util.ArrayList;
import java.util.List;

import views.forms.SchoolFormData;

/**
 * Simple model class to represent students.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Methods to facilitate form display (makeStudentFormData).
 * <li> Some fields and methods to "fake" a database of Students, including valid and invalid.
 * </ul> 
 */
public class Student {
  private long id;
  private String schoolname;
  private String principalname;
  private String password;
  private String schoolregistrationid;
  private String address;
  private String contact;
  
  private String city;
  private String pincode;
  private State state;
  private SchoolBoard schoolboard;
  private SchoolType schooltype;
  private List<Hobby> hobbies = new ArrayList<>(); // Hobbies are optional.
  private GradeLevel level;
  private GradePointAverage gpa;
  private List<Major> majors = new ArrayList<>(); // Majors are optional.

  /** Model entities typically want to have a no-arg constructor. */
  public Student() {
  }
  //student.schoolname, student.principalname,student.password, student.level, student.gpa, student.hobbies, student.majors,student.city,student.state,student.contact,student.pincode,student.schoolboard,student.schooltype,student.address,student.schoolregistrationid
  public Student(long id, String schoolname , String principalname , String password ,GradeLevel level,GradePointAverage gpa,List<Hobby> hobbies,List<Major> majors,String city,State state,String contact,String pincode,SchoolBoard schoolboard,SchoolType schooltype,String address,String schoolregistrationid) {
    this.setId(id);
    this.schoolname = schoolname;
    this.principalname=principalname;
    this.password = password;
    this.level = level;
    this.gpa = gpa;
  }
  
  /**
   * @return the id
   */
  private long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  private void setId(long id) {
    this.id = id;
  }

  public boolean hasHobby(String hobbyName) {
    for (Hobby hobby : this.hobbies) {
      if (hobbyName.equals(hobby.getName()))
        return true;
    }
    return false;
  }

  public void addHobby(Hobby hobby) {
    this.hobbies.add(hobby);
  }

  public boolean hasMajor(String majorName) {
    for (Major major : this.getMajors()) {
      if (majorName.equals(major.getName()))
        return true;
    }
    return false;
  }

  public String toString() {
    return String.format("[Student schoolname: '%s' principalname:'%s' Password: '%s' city:'%s' contact:'%s' state:'%s' pincode:'%s' schoolboard:'%s' schooltype:'%s' address:'%s'  Hobbies: %s Grade Level: %s GPA: %s Majors: %s]", this.getName(),
        this.getPassword(), this.hobbies, this.level, this.gpa, this.getMajors());
  }

  /**
   * @return the name
   */
  public String getName() {
    return schoolname;
  }

  /**
   * @param name the name to set
   */
  public void setName(String schoolname) {
    this.schoolname = schoolname;
  }
  
  /**
   * @return Principal name
   */
  public String getprincipalName() {
	    return principalname;
	  }

    /**
	* @param name Principal name to set
	*/
	  public void setprincipalName(String principalname) {
	    this.principalname = principalname;
	  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getaddress() {
	    return address;
	  }

	  /**
	   * @param name the name to set
	   */
	  public void setaddress(String address) {
	    this.address = address;
	  }
	  
	  
	  public String getcity() {
		    return city;
		  }

		  /**
		   * @param name the name to set
		   */
		  public void setcity(String city) {
		    this.city = city;
		  }
		  
		  public String pincode() {
			    return pincode;
			  }

			  /**
			   * @param name the name to set
			   */
			  public void setpincode(String pincode) {
			    this.pincode = pincode;
			  }
			  
			  public String getcontact() {
				    return contact;
				  }

				  /**
				   * @param name the name to set
				   */
				  public void setcontact(String contact) {
				    this.contact = contact;
				  }
				  
				  public String getschoolregistrationid() {
					    return schoolregistrationid;
					  }

					  /**
					   * @param name the name to set
					   */
					  public void setschoolregistrationid(String regid) {
					    this.schoolregistrationid = regid;
					  }

  /**
   * @return the level
   */
  public GradeLevel getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(GradeLevel level) {
    this.level = level;
  }

  /**
   * @return the gpa
   */
  public GradePointAverage getGpa() {
    return gpa;
  }
  /**
   * @param gpa the gpa to set
   */
  public void setGpa(GradePointAverage gpa) {
    this.gpa = gpa;
  }

  /**
   * @param gpa the gpa to set
   */
  public void setschoolboard(SchoolBoard schoolboard) {
    this.schoolboard = schoolboard;
  }
  
  /**
   * @return the gpa
   */
  public SchoolBoard getschoolboard() {
    return schoolboard;
  }

  /**
   * @param gpa the gpa to set
   */
  public void setschooltype(SchoolType schooltype) {
    this.schooltype = schooltype;
  }
  
  public SchoolType getschooltype() {
	    return schooltype;
	  }
  
  public State getstate() {
	    return state;
	  }
  
  public void setstate(State state) {
	    this.state = state;
	  }

  /**
   * @return the majors
   */
  public List<Major> getMajors() {
    return majors;
  }

  /**
   * @param majors the majors to set
   */
  public void setMajors(List<Major> majors) {
    this.majors = majors;
  }

  public void addMajor(Major major) {
    this.majors.add(major);
  }

  /**
   * Return a StudentFormData instance constructed from a student instance.
   * @param id The ID of a student instance.
   * @return The StudentFormData instance, or throws a RuntimeException. 
   */
  public static SchoolFormData makeStudentFormData(long id) {
    for (Student student : allStudents) {
      if (student.getId() == id) {
        return new SchoolFormData(student.schoolname, student.principalname,student.password,student.schoolregistrationid,student.address, student.city,student.state,student.pincode,student.hobbies,student.contact,student.schoolboard,student.level, student.gpa, student.majors,student.schooltype);
      }
    }
    throw new RuntimeException("Couldn't find student");
  }
  
  //String schoolname , String principalname , String password , String schoolregistrationid ,String address, String city , State state , String pincode ,List<Hobby> hobbies,String contact ,SchoolBoard schoolboard,GradeLevel level, GradePointAverage gpa,List<Major> majors , SchoolType schooltype
  /**
   * Returns a Student instance created from the form data.
   * Assumes that the formData has been validated.
   * The ID field is not assigned or managed in this application.
   * @param formData The student form data.
   * @return A student instance. 
   */
  public static Student makeInstance(SchoolFormData formData) {
    Student student = new Student();
    student.schoolname = formData.schoolname;
    student.principalname=formData.principalname;
    student.address=formData.address;
    student.password = formData.password;
    for (String hobby : formData.hobbies) {
      student.hobbies.add(Hobby.findHobby(hobby));
    }
    student.level = GradeLevel.findLevel(formData.level);
    student.gpa = GradePointAverage.findGPA(formData.gpa);
    student.schoolboard = SchoolBoard.findSchoolBoard(formData.schoolboard);
    student.schooltype = SchoolType.findSchoolType(formData.schooltype);
    student.state = State.findState(formData.state);
    for (String major : formData.majors) {
      student.majors.add(Major.findMajor(major));
    }
    return student;
  }
  
  
  /** Fake a database of students. */
  private static List<Student> allStudents = new ArrayList<>();

  /** Populate the fake database with both valid and invalid students, just for tutorial purposes.*/
  static {
    // Valid student. No optional data supplied.
   // allStudents.add(new Student(1L,"Joe Good", "mypassword", GradeLevel.findLevel("Freshman"), GradePointAverage.findGPA("4.0")));
    // Valid student with optional data.
    //allStudents.add(new Student(2L,"DEF SCHOOL","Alice Good", "mypassword", GradeLevel.findLevel("Sophomore"), GradePointAverage.findGPA("4.0")));
    //getById(2L).addHobby(Hobby.findHobby("Residential"));
    //getById(2L).addHobby(Hobby.findHobby("DayScholar"));
    //getById(2L).addMajor(Major.findMajor("Both"));
    //getById(2L).addMajor(Major.findMajor("Physics"));
    // Invalid student. Password is too short.
    //allStudents.add(new Student(3L,"LMN school","Frank Bad", "pass", GradeLevel.findLevel("Freshman"), GradePointAverage.findGPA("4.0")));
  }
  
  /**
   * Find a student instance given the ID.
   * @param id The id of the student.
   * @return The Student instance, or throws a RuntimeException.
   */
  public static Student getById(long id) {
    for (Student student : allStudents) {
      if (student.getId() == id) {
        return student;
      }
    }
    throw new RuntimeException("Couldn't find student");
  }

}
