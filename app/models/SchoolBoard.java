package models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import views.formdata.SchoolFormData;
/**
 * Represent Board to which School belongs. 
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (makeSchoolBoardMap, etc.).
 * <li> Some fields and methods to "fake" a database of GPAs.
 * </ul>
 */

public class SchoolBoard {
	private long id;
	private String Name;
	
	public SchoolBoard(long id , String Name)
	{
		this.id=id;
		this.Name=Name;
	}
	public void setid(long id)
	{
		this.id=id;
	}
	
	public long getid()
	{
		return id;
	}
	
	public void setname(String Name)
	{
		
		this.Name=Name;
	}
	public String getname()
	{
		return Name;
	}
	
	
	/**
	   * Create a map of School Board name -> boolean where the boolean is true if the Board Name corresponds to the school.
	   * @param school A school with a Board type ABC etc.
	   * @return A map of Board to boolean indicating which one is the school's Board.
	   */
	
	public static Map<String,Boolean> makeSchoolBoardMap(SchoolFormData school)
	{
		Map<String, Boolean> schoolboardMap=new TreeMap<String , Boolean>();
		for(SchoolBoard schoolboard:allSchoolBoards)
		{
			//gpaMap.put(gpa.getName(),  (student == null) ? false : (student.gpa != null && student.gpa.equals(gpa.getName())));
		    schoolboardMap.put(schoolboard.getname(),(school == null) ? false :(school.schoolboard != null && school.schoolboard.equals(schoolboard.getname())));
		}
		return schoolboardMap;
	}
	
	/**
	   * @return A list of school board .
	   */
	
	public static List<String> getSchoolboardList()
	{
		
	String[] nameArray={"CBSE","ICSE","UP BOARD","MP BOARD"};
	return Arrays.asList(nameArray);
	
	}
	
	
	/**
	   * Return the SchoolBoard instance in the database with name 'schoolboard' or null if not found.
	   * @param schoolboard 
	   * @return The SchoolBoard instance, or null.
	   */
	  public static SchoolBoard findSchoolBoard(String schoolboardName) {
	    for (SchoolBoard schoolboard : allSchoolBoards) {
	      if (schoolboardName.equals(schoolboard.getname())) {
	        return schoolboard;
	      }
	    }
	    return null;
	  }
	
	  /**
	   * Define a default schoolboard name, used for form display.
	   * @return The default Schoolboard.
	   */
	  public static SchoolBoard getDefaultschoolboard() {
	    return findSchoolBoard("Choose School Board");
	  }

	  @Override
	  public String toString() {
	    return String.format("[School Board %s]", this.Name);
	  }
	private static List<SchoolBoard> allSchoolBoards=new ArrayList<>();
	
	
	 /** Instantiate the fake database of SchoolBoards. */
	  static {
	    allSchoolBoards.add(new SchoolBoard(1L, "CBSE"));
	    allSchoolBoards.add(new SchoolBoard(2L, "ICSE"));
	    allSchoolBoards.add(new SchoolBoard(3L, "UP BOARD"));
	    allSchoolBoards.add(new SchoolBoard(4L, "MP BOARD"));
	    }


	

}





