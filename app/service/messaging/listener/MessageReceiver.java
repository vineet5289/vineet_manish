package service.messaging.listener;

@FunctionalInterface
public interface MessageReceiver {
	void receive(String message);
}
