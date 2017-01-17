package models;

import lombok.Data;

@Data
public class TimetableModel {
  private long insId;
  private long cId;
  private long secId;
  private long tId; // this is period id
  private String day; // Day like Monday
  private int daySeq; // Sequence number like 1=> Sunday, 2 => Mondat see WeekDayEnum
  private int periodNo; // this is period number
  private String periodName; //Period name
  private String startTime; //period start time
  private String endTime;// period end time
  private int duration; // period duration
  private boolean sameAsPreviousPeriod; //this will show that current period is same perious perido
  private String timeTableUpdatedBy; // institute user who edit information

  private long profId;
  private long subId;
  private String profName;
  private String subName;
}
