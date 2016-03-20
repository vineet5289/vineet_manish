package models;

import java.util.ArrayList;
import java.util.List;

import enum_package.CountryEnum;


public class Country {
	private static List<String> countries=new ArrayList<String>();
    static
    {
    	countries.add(CountryEnum.INDIA.name());
    }

    public static List<String> getCountries() {
    	return countries;
    }
}
