package models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import views.forms.SchoolFormData;

public class SchoolType {
	long id;
	String Name;
	
	
	
	public SchoolType(long id , String Name)
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
	
	public void setname(String name)
	{
	this.Name=name;
	}
	
	public String getname()
	{
	 return Name;
	}
	
    public static Map<String,Boolean> makeSchoolTypeMap(SchoolFormData school)
    {
    	Map<String,Boolean> schooltypeMap=new TreeMap<String,Boolean>();
    	for(SchoolType schooltype: allSchoolTypes)
    	{
    	schooltypeMap.put(schooltype.getname(),(school== null) ? false :(school.schooltype != null && school.schooltype.equals(schooltype.getname())));
    	}
       return schooltypeMap;    	

    }
    
    
    /**
	   * @return A list of school board .
	   */
	
	public static List<String> getSchoolTypeList()
	{
		
	String[] nameArray={"Govt.","Private"};
	return Arrays.asList(nameArray);
	
	}
	
	
	/**
	   * Return the SchoolBoard instance in the database with name 'schoolboard' or null if not found.
	   * @param schoolboard 
	   * @return The SchoolBoard instance, or null.
	   */
	  public static SchoolType findSchoolType(String schooltypeName) {
	    for (SchoolType schooltype : allSchoolTypes) {
	      if (schooltypeName.equals(schooltype.getname())) {
	        return schooltype;
	      }
	    }
	    return null;
	  }
	
	  /**
	   * Define a default schoolboard name, used for form display.
	   * @return The default Schoolboard.
	   */
	  public static SchoolType getDefaultschooltype() {
	    return findSchoolType("Choose School Type");
	  }

	  @Override
	  public String toString() {
	    return String.format("[School Type %s]", this.Name);
	  }
    
    private static List<SchoolType> allSchoolTypes=new ArrayList<>();
    static
    {
    	allSchoolTypes.add(new SchoolType(1L,"Government"));
    	allSchoolTypes.add(new SchoolType(2L,"Private"));
    }
}
