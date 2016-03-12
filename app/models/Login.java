package models;

import java.util.ArrayList;
import java.util.List;

import views.formdata.LoginForm;
//import views.formdata.SchoolFormData;


public class Login {
	private long id;
private String username;
private String password;

public Login(){
}

public Login(long id , String username, String password)
{
  this.setid(id);
  this.username=username;
  this.password=password;
}

public String getusername()
{
  return username;
}

public void setusername(String username)
{
  this.username=username;
}

private void setid(long id )
{
this.id=id;	
}

private long getid()
{
  return id;
}



public void setpassword(String password)
{
  this.password=password;
}

public String getpassword()
{
 return password; 
}







public static LoginForm makeLoginFormData(long id) {
    for (Login student : allStudents) {
      if (student.getid() == id) {
        return new LoginForm(student.username,student.password);
      }
    }
    throw new RuntimeException("Couldn't find student");
  }
  
  public static Login makeInstance(LoginForm formData) {
    Login student = new Login();
    student.username = formData.username;
    student.password = formData.password;
    
    return student;
  }
  
  
  /** Fake a database of students. */
  private static List<Login> allStudents = new ArrayList<>();

  /** Populate the fake database with both valid and invalid students, just for tutorial purposes.*/
  static {
   
  }
  
  /**
   * Find a student instance given the ID.
   * @param id The id of the student.
   * @return The Student instance, or throws a RuntimeException.
   */
  public static Login getById(long id) {
    for (Login student : allStudents) {
      if (student.getid() == id) {
        return student;
      }
    }
    throw new RuntimeException("Couldn't find student");
  }

}