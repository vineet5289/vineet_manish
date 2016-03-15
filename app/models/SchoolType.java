package models;
import java.util.HashMap;
import java.util.Map;

import enum_package.SchoolTypeEnum;

public class SchoolType {
	
	private static Map<Long, String> allSchoolTypes=new HashMap<Long, String>();
    static
    {
    	allSchoolTypes.put(1L, SchoolTypeEnum.GOVERMENT.name());
    	allSchoolTypes.put(2L, SchoolTypeEnum.PRIVATE.name());
    }

    public static Map<Long, String> getSchoolTypeList() {
    	return allSchoolTypes;
    }
}
