package controllers.message;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.Result;
import service.messages.MessageService;
import controllers.CustomController;

public class NotificationController extends CustomController {
	MessageService messageService;

	@Inject
	public NotificationController(MessageService messageService) {
		this.messageService = messageService;
	}
	public Result notification() {
		System.out.println("NotificationController.notification");
		JsonNode message = request().body().asJson();
		messageService.processMessage(message);
		System.out.println("NotificationController.notification");
		return ok("message added");
	}
}
