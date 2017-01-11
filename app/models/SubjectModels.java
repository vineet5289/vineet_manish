package models;

import lombok.Data;

@Data
public class SubjectModels {
  private long sId;
  private String sName;
  private long classId;
  private long insId;
  private long secId;
  private long subId;
  private long sCode;
  private String description;
  private String recommendedBook;
}
