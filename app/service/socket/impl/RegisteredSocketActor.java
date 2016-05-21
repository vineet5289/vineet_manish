package service.socket.impl;

import service.socket.SocketManager;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class RegisteredSocketActor extends UntypedActor{
	private SocketManager socketManager;
	private ActorRef out;
	public RegisteredSocketActor(SocketManager socketManager, ActorRef out) {
		this.socketManager = socketManager;
		this.out = out;
	}

	@Override
	public void preStart() throws Exception {
		System.out.println("RegisteredSocketActor preStart + start");
		socketManager.register(getSelf());
		System.out.println("RegisteredSocketActor preStart + end ");
	}

	@Override
	public void postStop() throws Exception {
		socketManager.unReqister(getSelf());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("RegisteredSocketActor message received " + message);
		if(message instanceof String) {
			System.out.println("*********** RegisteredSocketActor");
			message = message + "RegisteredSocketActor";
			out.tell(message, self());
		} else {
			unhandled(message);
		}
		System.out.println("message publish RegisteredSocketActor" + message);
	}

}
