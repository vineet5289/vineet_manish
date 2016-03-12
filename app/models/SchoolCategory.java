package models;

import lombok.Data;

import org.joda.time.DateTime;

@Data
public class SchoolCategory {
	private String schoolCategoryType;
	private String categoryDescription;
	private DateTime createdAt;
	private DateTime updatedAt;
}
