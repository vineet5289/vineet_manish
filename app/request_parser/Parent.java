package request_parser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Http.RequestBody;

public class Parent implements Parser {

	@Override
	public ObjectNode parseBody(String body) throws JsonParseException, JsonMappingException, IOException{
		return null;
		
	}

}
