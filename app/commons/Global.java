package commons;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {

	@Override
	public void onStart(Application application) {
		NotificationInitializer notificationInitializer = application.injector().instanceOf(NotificationInitializer.class);
		notificationInitializer.init(application);
	}
}
