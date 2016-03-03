package request_parser;

import java.io.IOException;

import play.mvc.Http.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Parser {
	public ObjectNode parseBody(String body) throws JsonParseException, JsonMappingException, IOException;
}
