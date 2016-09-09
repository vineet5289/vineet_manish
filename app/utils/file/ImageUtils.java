package utils.file;

import javax.inject.Inject;

import play.Configuration;


public class ImageUtils {
	@Inject
	private static Configuration configuration;
	
	private final static String PROFILE_IMAGE_CREATE_STORAGE_PATH = configuration.getString("file.image.profileImageCreatePath");
	private final static String PROFILE_IMAGE_URL_PATH = configuration.getString("file.image.urlPath");

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
