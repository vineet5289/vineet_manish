package models;

import lombok.Data;

@Data
public class ClassModels {
  private long id;
  private String cName;
  private long insId;
  private String classStartTime;
  private String classEndTime;
  private int noOfPeriod;
  private long pClassId;
  private String pClassName;
  private String userName;
}
