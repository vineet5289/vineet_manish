package security.authorization;

import be.objectify.deadbolt.java.ConfigKeys;

public enum HandlerKeys {
	DEFAULT(ConfigKeys.DEFAULT_HANDLER_KEY),
	EDITEMPLOYEE("editemployeehandler"),
	CUSTOME("defaulthandlerkey");
	public final String key;

    private HandlerKeys(final String key) {
        this.key = key;
    }
}
