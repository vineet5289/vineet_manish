package service.socket.impl;

import javax.inject.Inject;

import service.socket.SocketManager;
import service.socket.SocketService;
//import play.libs.F.Function;

public class SocketServiceImpl implements SocketService {

	private SocketManager socketManager;
	
	@Inject
	public SocketServiceImpl(SocketManager socketManager) {
		System.out.println("SocketServiceImp");
		this.socketManager = socketManager;
	}

//	@Override
//	public LegacyWebSocket<String> createRegisteredSocket() {
//		return LegacyWebSocket.withActor(new Function<ActorRef, Props>() {
//			@Override
//			public Props apply(ActorRef out) {
//				return Props.create(RegisteredSocketActor.class, socketManager, out);
//			}
//		});
//
//	}

}
