package models;
import java.util.ArrayList;
import java.util.List;

import enum_package.SchoolTypeEnum;

public class SchoolType {
	
	private static List<String> allSchoolTypes=new ArrayList<String>();
    static
    {
    	allSchoolTypes.add(SchoolTypeEnum.GOVERMENT.name());
    	allSchoolTypes.add(SchoolTypeEnum.PRIVATE.name());
    }

    public static List<String> getSchoolTypeList() {
    	return allSchoolTypes;
    }
}
