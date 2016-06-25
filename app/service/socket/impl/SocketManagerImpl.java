package service.socket.impl;

import java.util.ArrayList;
import java.util.List;

import service.socket.SocketManager;
import akka.actor.ActorRef;

public class SocketManagerImpl implements SocketManager {

	List<ActorRef> registeredSockets = new ArrayList<ActorRef>();
	@Override
	public void register(ActorRef socketActor) {
		System.out.println("SocketManagerImpl register start" + registeredSockets.size());
		registeredSockets.add(socketActor);
		// cehck database to publics message
		System.out.println("SocketManagerImpl register end" + registeredSockets.size());
	}

	@Override
	public void unReqister(ActorRef socketActor) {
		System.out.println("SocketManagerImpl unReqister start" + registeredSockets.size());
		if(registeredSockets.contains(socketActor))
			registeredSockets.remove(socketActor);
		System.out.println("SocketManagerImpl unReqister end" + registeredSockets.size());
	}

	@Override
	public void notifyAllWithMessage(String message) { // filter logic to for message notification service
		
		// based on filter find all user and send them message 
		System.out.println("SocketManagerImpl + notifyAllWithMessage " + message);
		registeredSockets.forEach((socketActor) -> socketActor.tell(message, null));
	}

	@Override
	public void notifyActorWithMessage(String message, ActorRef socket) { // filter logic to for message notification service
		socket.tell(message, null);
	}

	@Override
	public List<ActorRef> getRegisteredSockets() {
		return registeredSockets;
	}

}
