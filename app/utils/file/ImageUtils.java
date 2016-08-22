package utils.file;

import play.Play;

public class ImageUtils {
	private final static String PROFILE_IMAGE_CREATE_STORAGE_PATH = Play.application().configuration().getString("file.image.profileImageCreatePath");
	private final static String PROFILE_IMAGE_URL_PATH = Play.application().configuration().getString("file.image.urlPath");

	public static String getProfileImageCreatePath() {
		return PROFILE_IMAGE_CREATE_STORAGE_PATH;
	}

	public static String getImageURLPath() {
		return PROFILE_IMAGE_URL_PATH;
	}

	public static String getExtension(String fileName) {
		String ext = "jpg";
		if (fileName.toLowerCase().endsWith("png")) {
			ext = "png";
		} else if (fileName.toLowerCase().endsWith("gif")) {
			ext = "gif";
		}
		return ext;
	}
}
