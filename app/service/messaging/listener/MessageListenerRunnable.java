package service.messaging.listener;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class MessageListenerRunnable implements Runnable {

	private MessageReceiver messageReceiver;
	private Channel channelForConsume;
	private String consumerQueueName;
	
	public MessageListenerRunnable( MessageReceiver messageReceiver, Channel channelForConsume, String consumerQueueName) {
		this.messageReceiver = messageReceiver;
		this.channelForConsume = channelForConsume;
		this.consumerQueueName = consumerQueueName;
	}

	@Override
	public void run() {
		System.out.println("message MessageListenerRunnable ");
		QueueingConsumer consumer = new QueueingConsumer(channelForConsume);
		try {
			channelForConsume.basicConsume(consumerQueueName, true, consumer);
		} catch (IOException e) {
			System.out.println("Error creating basic consume: " + consumerQueueName + " " + e);
		}
		while (true) {
			System.out.println("MessageListenerRunnable.run" );
			Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
			} catch (ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
				System.out.println("Message listener for " + consumerQueueName + " is shutting down, reason: " + " " + e);
				return;
			}
			String messageToDeliver = new String(delivery.getBody());
			messageReceiver.receive(messageToDeliver);
			System.out.println("Delivered message to: " + consumerQueueName);
		}
	}

}
