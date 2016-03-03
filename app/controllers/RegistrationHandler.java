package controllers;

import java.io.IOException;

import play.mvc.Result;
import request_parser.Parser;
import request_parser.ParserFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class RegistrationHandler extends CustomController {
	public Result registor() throws JsonParseException, JsonMappingException, IOException {
		JsonNode requestBody = request().body().asJson();
		if(requestBody == null) {
			return badRequest("server received bad registration request. Registration request should be json");
		}

		String registrationCategory = requestBody.findPath("registrationCategory").textValue();
		if(registrationCategory == null || registrationCategory.isEmpty()) {
			return badRequest("server error registration category should not be null or empty");
		}

		String actualRequest = requestBody.findPath("actualRequest").textValue();
		if(actualRequest == null || actualRequest.isEmpty()) {
			return badRequest("server error registration values is empty in request body");
		}

		Parser parser = ParserFactory.getParser(registrationCategory);
		parser.parseBody(actualRequest);
		return ok("it's ok man " + requestBody);
	}
}
