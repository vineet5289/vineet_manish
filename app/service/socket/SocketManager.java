package service.socket;

import java.util.List;

import akka.actor.ActorRef;

public interface SocketManager {
	public void register(ActorRef socketActor);

	public void unReqister(ActorRef socketActor);

	public void notifyAllWithMessage(String message);

	public void notifyActorWithMessage(String message, ActorRef socket);

	public List<ActorRef> getRegisteredSockets();
}
