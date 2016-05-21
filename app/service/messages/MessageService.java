package service.messages;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageService {
	void processMessage(JsonNode message);
}
