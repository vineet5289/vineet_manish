package controllers.message;

import javax.inject.Inject;

import views.html.listen;
import play.mvc.WebSocket;
import controllers.CustomController;
import play.mvc.Result;
import service.socket.SocketService;
import controllers.CustomController;

public class NotificationListeningController extends CustomController {
	private SocketService socketService;

	@Inject
	public NotificationListeningController(SocketService socketService) {
		this.socketService = socketService;
	}

//	public LegacyWebSocket<String> listeningSocket() {
//		System.out.println("************* NotificationListeningController listeningSocket");
//		return socketService.createRegisteredSocket();
//	}

	public Result listen() {
		System.out.println("************* NotificationListeningController listen");
		return ok(listen.render());
	}
}
