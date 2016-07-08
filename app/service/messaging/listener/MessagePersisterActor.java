package service.messaging.listener;

import dao.MessageDAO;
import akka.actor.UntypedActor;

public class MessagePersisterActor extends UntypedActor {

	private MessageDAO messageDao;
	public MessagePersisterActor(MessageDAO messageDao) {
		System.out.println("**********MessagePersisterActor******* 1");
		this.messageDao = messageDao;
		System.out.println("**********MessagePersisterActor******* 1");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			System.out.println("MessagePersisterActor onReceive " + message);
			messageDao.save((String) message);
		} else {
			unhandled(message);
		}
	}
}
