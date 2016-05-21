package service.socket.impl;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.F;
import play.mvc.WebSocket;
import service.socket.SocketManager;
import service.socket.SocketService;

public class SocketServiceImpl implements SocketService {

	private SocketManager socketManager;
	
	@Inject
	public SocketServiceImpl(SocketManager socketManager) {
		System.out.println("SocketServiceImp");
		this.socketManager = socketManager;
	}

	@Override
	public WebSocket<String> createRegisteredSocket() {
		return WebSocket.withActor(new F.Function<ActorRef, Props>() {

			@Override
			public Props apply(ActorRef out) throws Throwable {
				return Props.create(RegisteredSocketActor.class, socketManager, out);
			}
		});

	}

}
