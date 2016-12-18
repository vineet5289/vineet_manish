package service.messaging.impl;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.inject.ApplicationLifecycle;
import service.messaging.MessagingConnectionHandler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Singleton
public class MessagingConnectionHandlerImpl implements MessagingConnectionHandler{

	private ApplicationLifecycle applicationLifeycle;
	private ConnectionFactory connectionFactory;
	private Connection connection;
	public static final String EXCHANGE_TYPE = "fanout";
	public static final String  RABBIT_MQ_CONNECTION_URI = "amqp://127.0.0.1";

	@Inject
	public MessagingConnectionHandlerImpl(ConnectionFactory connectionFactory, ApplicationLifecycle applicationLifeycle) {
		this.connectionFactory = connectionFactory;
		this.applicationLifeycle = applicationLifeycle;
	}

	@Override
	public void initConnection() throws Exception {
		connectionFactory.setUri(RABBIT_MQ_CONNECTION_URI);
		connection = connectionFactory.newConnection();
		applicationLifeycle.addStopHook(() -> {
		connection.close();
		return CompletableFuture.completedFuture(null);
		});
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public Channel createChannel() throws IOException {
		return connection.createChannel();
	}

}
