package models;
import java.util.HashMap;
import java.util.Map;

import enum_package.SchoolTypeEnum;

public class SchoolType {
	
	public static Map<String, String> schoolTypeToValue = new HashMap<String, String>();
//https://www.angloinfo.com/india/how-to/page/india-family-schooling-education-school-system
    static
    {
    	schoolTypeToValue.put(SchoolTypeEnum.GOVERMENT.name(), "Government School");
    	schoolTypeToValue.put(SchoolTypeEnum.PRIVATE.name(), "Private School");
    	schoolTypeToValue.put(SchoolTypeEnum.INTERNATIONALSCHOOL.name(), "International schools");
    	schoolTypeToValue.put(SchoolTypeEnum.OPENSCHOOL.name(), "National open schools");
    	schoolTypeToValue.put(SchoolTypeEnum.SPECIALNEEDSCHOOL.name(), "Special needs schools");
    	
    }
}
