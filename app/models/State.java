package models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import views.formdata.SchoolFormData;


public class State {
	long id;
	String name;
	
	public State(long id , String name)
	{
		this.id=id;
		this.name=name;
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
	  this.name=name;
	}
    
	public String getname()
	{
		return name;
	}
	
	public static Map<String,Boolean> makeStateMap(SchoolFormData school)
	{
		Map<String , Boolean> stateMap=new TreeMap<String,Boolean>();
		for(State state:allStates)
		{
			stateMap.put(state.getname(), (school == null) ? false : (school.state !=null && school.state.equals(state.getname())));
		}
		return stateMap;
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
	  public static State findState(String stateName) {
	    for (State state : allStates) {
	      if (stateName.equals(state.getname())) {
	        return state;
	      }
	    }
	    return null;
	  }
	
	  /**
	   * Define a default schoolboard name, used for form display.
	   * @return The default Schoolboard.
	   */
	  public static State getDefaultstate() {
	    return findState("Choose State");
	  }

	  @Override
	  public String toString() {
	    return String.format("[State %s]", this.name);
	  }
	  
	
	private static List<State> allStates=new ArrayList<>();
	static
	{
		allStates.add(new State(1L,"DELHI"));
    	allStates.add(new State(2L,"UTTAR PRADESH"));
    	allStates.add(new State(3L,"MADHYA PRADESH"));
    	allStates.add(new State(4L,"GOA"));
    	allStates.add(new State(5L,"RAJASTHAN"));
    	allStates.add(new State(6L,"ORISSA"));
    	allStates.add(new State(7L,"WEST BENGAL"));
    	allStates.add(new State(8L,"GUJRAT"));
    	allStates.add(new State(9L,"MAHARASTRA"));
    	allStates.add(new State(10L,"UTTRAKHAND"));
    	allStates.add(new State(11L,"SIKKIM"));
    	allStates.add(new State(12L,"ASSAM"));
    	
    	
    	
		
	}
}
