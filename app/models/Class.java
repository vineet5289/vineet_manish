package models;

import java.util.Date;

import lombok.Data;

@Data
public class Class {
	private long id;
	private String className;
	private Long schoolId;
	private String classStartTime;
	private String classEndTime;
	private int noOfPeriod;
	private Date created_at;
	private boolean isActive;
	private String parentClassName;
	private String userName;
}
