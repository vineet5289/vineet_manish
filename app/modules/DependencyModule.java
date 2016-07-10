package modules;

import javax.inject.Singleton;

import play.libs.Akka;
import play.libs.akka.AkkaGuiceSupport;
import service.messages.MessageService;
import service.messages.impl.MessageServiceImpl;
import service.messaging.MessagingConnectionHandler;
import service.messaging.MessagingPublisherService;
import service.messaging.impl.MessagingConnectionHandlerImpl;
import service.messaging.impl.MessagingPublisherServiceImpl;
import service.socket.SocketManager;
import service.socket.SocketService;
import service.socket.impl.SocketManagerImpl;
import service.socket.impl.SocketServiceImpl;
import akka.actor.TypedActor;
import akka.actor.TypedProps;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.rabbitmq.client.ConnectionFactory;

public class DependencyModule  extends AbstractModule implements AkkaGuiceSupport {

	@Override
	protected void configure() {
		bind(MessageService.class).to(MessageServiceImpl.class).in(Singleton.class);
		bind(MessagingConnectionHandler.class).to(MessagingConnectionHandlerImpl.class).in(Singleton.class);
		bind(SocketService.class).to(SocketServiceImpl.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	public SocketManager createSocketManager() {
		return (SocketManager) TypedActor.get(Akka.system())
				.typedActorOf(new TypedProps<SocketManagerImpl>(SocketManager.class, SocketManagerImpl.class));
	}

	@Provides
	@Singleton
	public ConnectionFactory createConnectionFactory() {
		return new ConnectionFactory();
	}

	@Provides
	@Singleton
	public MessagingPublisherService<String> getMessagingPublisherService() {
		return new MessagingPublisherServiceImpl();
	}
}
