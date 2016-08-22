package controllers.file_controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import service.file_upload.ImageService;
import controllers.CustomController;
import controllers.routes;
import dao.school.SchoolProfileInfoDAO;
import enum_package.FileUploadStatus;
import enum_package.InstituteDaoProcessStatus;
import enum_package.LoginType;
import enum_package.SessionKey;

public class ImageController extends CustomController {

	public Result uploadProfileImage(String userName) {
		MultipartFormData body = request().body().asMultipartFormData();

		String userId = "1";//session().get(SessionKey.of(SessionKey.userid));
		String loginType = session().get(SessionKey.of(SessionKey.logintype));
		if(body == null || userName == null || userName.isEmpty()
				|| userId == null || loginType == null) {
			//retunr
		}

		FilePart picture = body.getFile("fileName");
		FileUploadStatus fileUploadStatus = null;
		if (picture != null) {
			String fileName = picture.getFilename();
			String contentType = picture.getContentType(); 
			File file = picture.getFile();
			String profileImageURL = ImageService.uploadProfilePhoto(file, fileName, 1l);
			if(profileImageURL == null || profileImageURL.isEmpty()) {
				//flash error
			}
			System.out.println("========1");
			if(loginType.equalsIgnoreCase(LoginType.of(LoginType.headinstitute))) {
				System.out.println("=========2 userId=" + userId + ", userName" + userName);
				SchoolProfileInfoDAO schoolProfileInfoDAO = new SchoolProfileInfoDAO();
				fileUploadStatus = schoolProfileInfoDAO.updateProfileImageUrl(profileImageURL,
						Long.valueOf(userId), userName);
			}
			
		}
		if(fileUploadStatus != FileUploadStatus.unsuccessfullyProfilePicUpdate) {
			// flash error return
			//
		}

		//redirect
		if(loginType.equalsIgnoreCase(LoginType.of(LoginType.headinstitute))) {
			flash("success", FileUploadStatus.of(fileUploadStatus));
			return redirect(routes.SRPController.headInstituteHome());
		}
		return ok("uploaded successfully"); 
	}

}
