package request_parser;

import java.io.IOException;

import play.libs.Json;
import validation.StringValidation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import domain.School;

public class SchoolPrinciple implements Parser{
	public SchoolPrinciple() {
		errors = Json.newObject();
		school = new School();
		stringValidation = new StringValidation();
	}

	public ObjectNode parseBody(String body) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readValue(body, JsonNode.class);
		if(actualObj == null) {
			errors.put("", "");
		}
		validate(actualObj);
		return errors;
	}

	private void validate(JsonNode actualObj) {
		String name = actualObj.findPath("name").asText();
		String schoolRegistrationId = actualObj.findPath("schoolRegistrationId").asText();
		String SchoolPrincipleName = actualObj.findPath("SchoolPrincipleName").asText();
		String addressLineOne = actualObj.findPath("addressLineOne").asText();
		String addressLineTwo = actualObj.findPath("addressLineTwo").asText();
		String city = actualObj.findPath("city").asText();
		String state = actualObj.findPath("state").asText();
		String country = actualObj.findPath("country").asText();
		String pincode = actualObj.findPath("pincode").asText();
		String officeNumberOne = actualObj.findPath("officeNumberOne").asText();
		String officeNumberTwo = actualObj.findPath("officeNumberTwo").asText();
		int noOfShift;
	}

	private ObjectNode errors;
	private School school;
	private StringValidation stringValidation;
}
