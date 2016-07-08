package service.socket;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.WebSocket;

public interface SocketService {
	public WebSocket<String> createRegisteredSocket();
}
