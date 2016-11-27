package views.forms;

import lombok.Data;

@Data
public class UserHomePageForm {
  public String userName;
  public String name;
  public String imageUrl;
  public String instituteName;
  public String instituteLogoUrl;
  private Long userId;
  private Long instituteId;
}
