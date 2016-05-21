package service.messaging.impl;

import java.util.ArrayList;
import java.util.List;

import service.messaging.MessagingPublisherService;
import service.messaging.publisher.MessagePublisher;

public class MessagingPublisherServiceImpl implements MessagingPublisherService<String>{

	private List<MessagePublisher<String>> publishers = new ArrayList<>();

	@Override
	public void notifyPublishers(String message) {
		publishers.forEach((publisher) -> sendSilently(publisher, message));
	}

	@Override
	public void registerPublisher(MessagePublisher<String> publisher) {
		publishers.add(publisher);
	}

	@Override
	public List<MessagePublisher<String>> getPublishers() {
		return publishers;
	}

	// This would not be acceptable in production as we should handle messaging errors and send some feedback to the user
	// Basically  the application will not start if it cannot establish the connection properly and for demonstration purposes it's ok to assume that the connection is alive.
	private void sendSilently(MessagePublisher<String> publisher, String message) {
		try {
			publisher.publish(message);
			System.out.println("Message sent to publisher");
		} catch (Exception e) {
			System.out.println("Error publishing message");
		}
	}
}
