package enum_package;

import java.util.HashMap;
import java.util.Map;

public enum FileUploadStatus {
	successfullyProfilePicUpdate("Your profile image has been updated successfully."),
	unsuccessfullyProfilePicUpdate("Some problem occur during upload of your profile image"),;

	private String value;
	private final static Map<FileUploadStatus, String> statusToValue = new HashMap<FileUploadStatus, String>(FileUploadStatus.values().length);
	private FileUploadStatus(String value) {
		this.value = value;
	}

	static {
		for(FileUploadStatus idps : FileUploadStatus.values()) {
			statusToValue.put(idps, idps.value);
		}
	}

	public static String of(FileUploadStatus key) {
		String statusMessage = statusToValue.get(key);
		if(statusMessage == null)
			return "";
		return statusMessage;
	}
}
