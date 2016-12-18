package service.file_upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;

import utils.file.ImageUtils;

public class ImageService {
	
	public static String uploadProfilePhoto(File inputFile, String inputFileName, Long userId) {
		String randomUUID = "";
		String filePathPrefix = ImageUtils.getProfileImageCreatePath() + userId;
		String uploadedFilePath = ImageUtils.getImageURLPath() + userId;
		String ext = ImageUtils.getExtension(inputFileName);


		File directory = new File(String.valueOf(filePathPrefix));
	    if (! directory.exists()){
	        directory.mkdir();
	    }
		try {
			randomUUID = UUID.randomUUID().toString().replaceAll("-", "");
		} catch(Exception exception) {
			exception.printStackTrace();
			randomUUID = inputFileName + System.currentTimeMillis();
		}

		try {
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			sb1.append(directory);		sb2.append(uploadedFilePath);
			sb1.append("/profile_");	sb2.append("/profile_");
			sb1.append(randomUUID);		sb2.append(randomUUID);
			sb1.append("." + ext);		sb2.append("." + ext);

			System.out.println("===> " + sb1.toString());
			uploadedFilePath = sb2.toString();
			File outputFile = new File(sb1.toString());
			BufferedImage bi = ImageIO.read(inputFile);
			ImageIO.write(bi, "jpg", outputFile);
			return uploadedFilePath;
		} catch (Exception exception) {
			exception.printStackTrace();
			return "";
		}
	}
}
