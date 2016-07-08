package service.messaging;

import java.util.List;

import service.messaging.publisher.MessagePublisher;


public interface MessagingPublisherService<T> {
	void notifyPublishers(T message);

	void registerPublisher(MessagePublisher<T> publisher);

	List<MessagePublisher<T>> getPublishers(); 
}
