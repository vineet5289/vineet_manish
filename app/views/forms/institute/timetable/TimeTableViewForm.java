package views.forms.institute.timetable;

import lombok.Data;

@Data
public class TimeTableViewForm {
  public long duration;
  public long periodId;
  public long classId;
  public long sectionId;
  public long instituteId;
  public int periodNo;
  public String periodName;
  public String periodStartTime;
  public String periodEndTime;
  public long professorId;
  public String professorName;
  public long subjectId;
  public String subjectName;
  public String sameAsPreviousPeriod;
  public String day;
}
