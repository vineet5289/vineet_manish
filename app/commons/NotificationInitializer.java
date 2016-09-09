package commons;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

import com.rabbitmq.client.Channel;

import dao.MessageDAO;
import play.Application;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import play.libs.Akka;
import scala.collection.Seq;
import scala.concurrent.duration.FiniteDuration;
import service.messaging.MessagingConnectionHandler;
import service.messaging.MessagingPublisherService;
import service.messaging.listener.MessageListenerRunnable;
import service.messaging.listener.MessagePersisterActor;
import service.messaging.listener.MessageReceiver;
import service.socket.SocketManager;

public class NotificationInitializer extends Module {

	private MessagingConnectionHandler messagingConnectionHandler;
	private MessagingPublisherService<String> messagingPublisherService;
	private SocketManager socketManager;
	private MessageDAO messageDAO;

	public static final String JSON_MESSAGE_SAVING_CHANNEL = "jsonMessageSavingChannel";
	public static final int NUMBER_OF_CONCURRENT_MESSAGE_PERSISTER = 1;
	public static final String WS_BROADCAST_CHANNEL = "wsBroadcastChannel";

	@Inject
	ActorSystem actorSystem;
	@Inject
	public NotificationInitializer(MessagingConnectionHandler messagingConnectionHandler, MessagingPublisherService<String> messagingPublisherService,
			SocketManager socketManager, MessageDAO messageDAO) {
		this.messagingConnectionHandler = messagingConnectionHandler;
		this.messagingPublisherService = messagingPublisherService;
		this.socketManager = socketManager;
		this.messageDAO = messageDAO;
	}

	public void init(Application application) {
		try {
			messagingConnectionHandler.initConnection();
			Channel messagePersistenceChannel = messagingConnectionHandler.createChannel();
			messagePersistenceChannel.queueDeclare(JSON_MESSAGE_SAVING_CHANNEL, false, false, false, null);
			messagingPublisherService.registerPublisher((message) -> messagePersistenceChannel.basicPublish("",
					JSON_MESSAGE_SAVING_CHANNEL, null, message.getBytes()));

			ActorRef messagePersisterActorRouter = Akka.system()
					.actorOf(Props.create(MessagePersisterActor.class, messageDAO)
							.withRouter(new RoundRobinPool(NUMBER_OF_CONCURRENT_MESSAGE_PERSISTER)));
			
			createConsumerForQueue(messagePersistenceChannel, JSON_MESSAGE_SAVING_CHANNEL,
					(message) -> messagePersisterActorRouter.tell(message, null));

			Channel listeningBrowserPublishChannel = messagingConnectionHandler.createChannel();
			listeningBrowserPublishChannel.exchangeDeclare(WS_BROADCAST_CHANNEL, "fanout");
			messagingPublisherService.registerPublisher((message) -> listeningBrowserPublishChannel
					.basicPublish(WS_BROADCAST_CHANNEL, "", null, message.getBytes()));

			Channel exchangeListenerChannel = messagingConnectionHandler.createChannel();
			String exchangeListenerChannelQueue = exchangeListenerChannel.queueDeclare().getQueue();
			System.out.println("*************** exchangeListenerChannel = " + exchangeListenerChannelQueue);
			exchangeListenerChannel.queueBind(exchangeListenerChannelQueue, WS_BROADCAST_CHANNEL, "");

			createConsumerForQueue(exchangeListenerChannel, exchangeListenerChannelQueue,
					(message) -> socketManager.notifyAllWithMessage(message));

			System.out.println(messagePersistenceChannel.getChannelNumber());
			System.out.println(listeningBrowserPublishChannel.getChannelNumber());
			System.out.println(exchangeListenerChannel.getChannelNumber());
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void createConsumerForQueue(Channel channel, String queueName, MessageReceiver messageReceiver) {
		actorSystem.scheduler().scheduleOnce(new FiniteDuration(0L, TimeUnit.MILLISECONDS),
				new MessageListenerRunnable(messageReceiver, channel, queueName), actorSystem.dispatcher());
	}

	@Override
	public Seq<Binding<?>> bindings(Environment arg0, Configuration arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
