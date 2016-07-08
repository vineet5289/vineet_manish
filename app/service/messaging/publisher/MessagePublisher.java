package service.messaging.publisher;

@FunctionalInterface
public interface MessagePublisher<T> {
	public void publish(T message) throws Exception;
}
