package utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import play.Play;

public class LogoUtils {

	private static String IMAGE_TEMP_PATH = Play.application().configuration().getString("image.temp");

//	public static File copyImageFileToTemp(File file, String fileName) throws IOException {
//        final File fileTo = new File(IMAGE_TEMP_PATH+fileName);
//        crop(file).toFile(fileTo);
//        //FileUtils.copyFile(file, fileTo);
//        return fileTo;
//    }

//	public static void writeFileWithImage(File file, BufferedImage image) throws IOException {
//        String ext = "jpg";
//        if (file.getName().toLowerCase().endsWith("png")) {
//            ext = "png";
//        } else if (file.getName().toLowerCase().endsWith("gif")) {
//            ext = "gif";
//        }
//        ImageIO.write(image, ext, file);
//    }

}
