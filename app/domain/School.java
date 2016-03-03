package domain;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class School {
	@Getter @Setter private String name;
	@Getter @Setter private String schoolRegistrationId;
	@Getter @Setter private String SchoolPrincipleName;
	@Getter @Setter private String addressLineOne;
	@Getter @Setter private String addressLineTwo; 
	@Getter @Setter private String city;
	@Getter @Setter private String state;
	@Getter @Setter private String country;
	@Getter @Setter private String pincode;
	@Getter @Setter private String officeNumberOne;
	@Getter @Setter private String officeNumberTwo;
	@Getter @Setter private int noOfShift;
}
