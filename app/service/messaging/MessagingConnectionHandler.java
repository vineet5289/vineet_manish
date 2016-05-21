package service.messaging;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public interface MessagingConnectionHandler {	
	public void initConnection() throws Exception;
	public Connection getConnection();
	public Channel createChannel() throws IOException;
}
