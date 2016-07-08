package views.forms;

import lombok.Data;

@Data
public class SessionUsers {
	public String userName;
	public String accessRights;
	public String role;
	public String authToken;
}
