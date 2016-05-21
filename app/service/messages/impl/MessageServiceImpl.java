package service.messages.impl;

import javax.inject.Inject;

import play.libs.Json;
import service.messages.MessageService;
import service.messaging.MessagingPublisherService;

import com.fasterxml.jackson.databind.JsonNode;

import dao.MessageDAO;

public class MessageServiceImpl implements MessageService {

	private MessageDAO messageDAO;
	private MessagingPublisherService<String> messagingPublisherService;

	@Inject
	public MessageServiceImpl(MessageDAO messageDAO, MessagingPublisherService<String> messagingPublisherService) {
		this.messageDAO = messageDAO;
		this.messagingPublisherService = messagingPublisherService;
	}

	@Override
	public void processMessage(JsonNode message) {
		System.out.println("inside MessageServiceImpl.processMessage start");
		String jsonMessage = Json.stringify(message);
		System.out.println(jsonMessage);
		messagingPublisherService.notifyPublishers(jsonMessage);
		System.out.println("Notified publishers with message: " + jsonMessage);
		System.out.println("inside MessageServiceImpl.processMessage end");
	}

}
