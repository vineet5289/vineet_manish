package models;

import enum_package.TeacherAppointment;
import lombok.Data;

@Data
public class ClassCurriculumModel {
  private long id;
  private long instituteId;
  private long classId;
  private long sectionId;
  private long professorId;
  private long subjectId;
  private long timetableId;
  private TeacherAppointment appointmentCat;
  private boolean subjectAllocated;
  private boolean slotAllocated;
  private boolean professorAllocated;
  private String slotStartTime;
  private String slotEndTime;
}
