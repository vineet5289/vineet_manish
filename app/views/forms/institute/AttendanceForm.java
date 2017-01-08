package views.forms.institute;

import java.util.List;

import lombok.Data;

@Data
public class AttendanceForm {
  public String date;
  public long instituteId;
  public long classId;
  public long sectionId;
  public int periodId;
  public long subjectId;
  public String attendanceTakenBy;
  public String attendanceUpdatedBy;

  public int periodNumber;
  public String periodName;
  public List<StudentsAttendance> studentsAttendance;

  @Data
  public static class StudentsAttendance {
    public String rollNumber;
    public String present = "A";
  }
}
