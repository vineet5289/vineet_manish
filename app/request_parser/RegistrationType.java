package request_parser;

import lombok.Data;
import play.data.validation.Constraints.Required;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationType {

	@Required(message = "Category of registration should not be null or empty")
	private String registrationCategory;

//	public String getRegistrationCategory() throws Exception {
//		System.out.println("******11111*******");
//		if(registrationCategory == null || registrationCategory.equalsIgnoreCase(""))
//			throw new Exception("Category of registration should not be null or empty");
//		return registrationCategory;
//	}
//
//	public void setRegistrationCategory(String registrationCategory) throws Exception {
//		System.out.println("*************");
//		if(registrationCategory == null || registrationCategory.equalsIgnoreCase(""))
//			throw new Exception("Category of registration should not be null or empty");
//		this.registrationCategory = registrationCategory;
//	}

//	public List<ValidationError> validate() {
//		System.out.println("=====hello");
//		List<ValidationError> errors = null;
//
//		if(registrationCategory == null || registrationCategory.equalsIgnoreCase("")) {
//            errors = new ArrayList<ValidationError>();
//            errors.add(new ValidationError("registrationCategory", "cannot be null or empty"));
//        }
//        return errors;
//    }
}
