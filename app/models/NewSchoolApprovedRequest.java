package models;

import java.sql.Timestamp;

import lombok.Data;
import enum_package.RequestedStatus;

@Data
public class NewSchoolApprovedRequest {
	public Long id;
	public String schoolName="";
	public String principalName="";
	public String principalEmail="";
	public String contact="";
	public String schoolAddress="";
	public String schoolRegistrationId="";
	public String query="";
	public Timestamp requestedAt;
	public RequestedStatus status;
	public Timestamp statusUpdatedAt;
	public String requestNumber="";
}
