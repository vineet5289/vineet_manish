package controllers.message;

import javax.inject.Inject;

import controllers.CustomController;
import play.mvc.Result;
import play.mvc.WebSocket;
import service.socket.SocketService;
import views.html.listen;

public class NotificationListeningController extends CustomController {
	private SocketService socketService;

	@Inject
	public NotificationListeningController(SocketService socketService) {
		this.socketService = socketService;
	}

	public WebSocket<String> listeningSocket() {
		System.out.println("************* NotificationListeningController listeningSocket");
		return socketService.createRegisteredSocket();
	}

	public Result listen() {
		System.out.println("************* NotificationListeningController listen");
		return ok(listen.render());
	}
}
