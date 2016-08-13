package models;
import java.util.HashMap;
import java.util.Map;

import enum_package.SchoolTypeEnum;

public class SchoolType {
	
	public static Map<String, String> schoolTypeToValue = new HashMap<String, String>();
//https://www.angloinfo.com/india/how-to/page/india-family-schooling-education-school-system
    static
    {
    	schoolTypeToValue.put(SchoolTypeEnum.govermentinstitute.name(), "Government School");
    	schoolTypeToValue.put(SchoolTypeEnum.privateinstitute.name(), "Private School");
    	schoolTypeToValue.put(SchoolTypeEnum.internationalinstitute.name(), "International schools");
    	schoolTypeToValue.put(SchoolTypeEnum.openinstitute.name(), "National open schools");
    	schoolTypeToValue.put(SchoolTypeEnum.specialneeninstitute.name(), "Special needs schools");
    	
    }
}
