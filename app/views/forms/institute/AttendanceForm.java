package views.forms.institute;

import lombok.Data;

@Data
public class AttendanceForm {
  public String date;
  public long instituteId;
  public long classId;
  public long sectionId;
  public String period;
  public String noOfPresentStudents;
  public String noOfAbsentStudents;
  public String userName;
  public String isActive;
}
