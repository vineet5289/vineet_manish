package views.forms.institute.timetable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import models.ClassModels;
import models.EmployeeModels;
import models.SubjectModels;

@Data
public class TimeTableCreateForm {
  private long instituteId;
  private long classId;
  private long sectionId;
  private List<Subject> subjects;
  private List<Professor> professors;

  @Data
  public static class Subject {
    private long sId;
    private String sName;
  }

  @Data
  public static class Professor {
    private long pId;
    private String pName;
  }

  public static String bindData(List<EmployeeModels> employeeList, List<SubjectModels> subjectList, ClassModels classDetails) throws JsonProcessingException {
    List<TimeTableCreateForm.Subject> subjects = new ArrayList<TimeTableCreateForm.Subject>();
    for(SubjectModels sub : subjectList) {
      TimeTableCreateForm.Subject subject = new TimeTableCreateForm.Subject();
      subject.setSId(sub.getSId());
      subject.setSName(sub.getSName());
      subjects.add(subject);
    }

    List<TimeTableCreateForm.Professor> professors = new ArrayList<TimeTableCreateForm.Professor>();
    for(EmployeeModels emp : employeeList) {
      TimeTableCreateForm.Professor professor = new TimeTableCreateForm.Professor();
      professor.setPId(emp.getId());
      professor.setPName(emp.getName());
      professors.add(professor);
    }
    TimeTableCreateForm timeTableCreateForm = new TimeTableCreateForm();
    timeTableCreateForm.setInstituteId(classDetails.getInsId());
    timeTableCreateForm.setClassId(classDetails.getId());
    timeTableCreateForm.setSubjects(subjects);
    timeTableCreateForm.setProfessors(professors);
    ObjectMapper mapper = new ObjectMapper();
    String output = mapper.writeValueAsString(timeTableCreateForm);
    return output;
  }
}
