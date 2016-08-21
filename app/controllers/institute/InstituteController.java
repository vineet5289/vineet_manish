package controllers.institute;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import actors.MessageActor;
import actors.SchoolRequestActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import models.Country;
import models.State;
import play.data.Form;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import security.institute.HeadInstituteBasicAuthCheck;
import views.forms.institute.AddInstituteBranchForm;
import views.html.viewClass.School.addBranchHeadInstitute;
import controllers.CustomController;
import controllers.routes;
import dao.school.AddBranchDAO;
import enum_package.InstituteDaoProcessStatus;
import enum_package.SessionKey;

public class InstituteController extends CustomController {

	final ActorSystem actorSystem = ActorSystem.create("srp");
	final ActorRef mailerActor;
	final ActorRef messageActor = actorSystem.actorOf(MessageActor.props());
	@Inject
	public InstituteController(@Named("srp") ActorRef mailerActor) {
		this.mailerActor = mailerActor;
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result switchBranch() {
		return ok("===> switch branch done");
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result preAddInstituteBranch() {
		Form<AddInstituteBranchForm> addInstituteBranchForm = Form.form(AddInstituteBranchForm.class);
		List<String> countries = Country.getCountries();
		return ok(addBranchHeadInstitute.render(addInstituteBranchForm, countries, State.states));
	}

	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
	public Result postAddInstituteBranch() {
		Form<AddInstituteBranchForm> addInstituteBranchForm = Form.form(AddInstituteBranchForm.class).bindFromRequest();
		if(addInstituteBranchForm == null || addInstituteBranchForm.hasErrors()) {
			flash("error", "Some field are missing or invalid. Please try again.");
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		AddInstituteBranchForm addInstituteBranch = addInstituteBranchForm.get();
		if(addInstituteBranch == null) {
			flash("error", "Some field are missing or invalid. Please try again.");
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		String headInstituteId = session().get(SessionKey.headinstituteid.name());
		SchoolRequestActorProtocol.AddInstituteBranch  addInstituteBranchProtocol = null;
		try {
			AddBranchDAO addBranchDAO = new AddBranchDAO();
			addInstituteBranchProtocol = addBranchDAO.addBranch(addInstituteBranch, Long.valueOf(headInstituteId));
		} catch (NumberFormatException | SQLException exception) {
			flash("error", "Some server exception happen during add branch request. Please try again.");
			exception.printStackTrace();
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		if(addInstituteBranchProtocol.getProcessStatus() != InstituteDaoProcessStatus.branchsuccessfullyadded) {
			flash("error", addInstituteBranchProtocol.getProcessStatus().name());
			return redirect(controllers.institute.routes.InstituteController.preAddInstituteBranch());
		}

		mailerActor.tell(addInstituteBranchProtocol, mailerActor);
		messageActor.tell(addInstituteBranchProtocol, messageActor);

		return redirect(routes.SRPController.headInstituteHome());
	}

//	@Security.Authenticated(HeadInstituteBasicAuthCheck.class)
//	public Result postAddEmployee() {
//		return ok("");
//	}

	public Result upload1() {
		System.out.println("====== in upload 1");
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart picture = body.getFile("fileName");
		  if (picture != null) {
			  System.out.println("inseid herrrrrrrrrr");
		    String fileName = picture.getFilename();
		    System.out.println("filname===> " + fileName);
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    File file1 = new File("/Users/vineet/Pictures/100_0924.JPG");
		    System.out.println("===> file1=>" + file1.getAbsolutePath());
		    try {
		    	System.out.println(file.getAbsolutePath());
		    	System.out.println("========= 1");
				BufferedImage bi = ImageIO.read(file);
				System.out.println("========= 2");
				ImageIO.write(bi, "jpg", new File("/Users/vineet/imdire/abcd1.jpg"));
				System.out.println("========= 3");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
//			file.c
//		    file.
//		    flash("success", "File uploaded");
//		    return ok("File uploaded");
		  } else {
		    flash("error", "Missing file");
//		    return redirect();    
		  }
		  return ok("File uploaded");
		}

	public Result upload2() {
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart picture = body.getFile("fileName");
		  if (picture != null) {
			  System.out.println("inseid herrrrrrrrrr");
		    String fileName = picture.getFilename();
		    System.out.println("filname===> " + fileName);
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    flash("success", "File uploaded");
//		    return ok("File uploaded");
		  } else {
		    flash("error", "Missing file");
//		    return redirect();    
		  }
		  return ok("File uploaded");
		}
}
