package controllers;

import java.io.IOException;

import play.mvc.Result;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import controllers.routes;

public class RegistrationHandler extends CustomController {

	public Result preRegistorSchool(String regCat) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("***" + regCat);
		if(regCat == null || regCat.isEmpty()) {
			flash("error", "Something wrong with your registration request.");
			return redirect(routes.SRPController.index());
		}
		System.out.println("===> " + regCat);
		return ok(regCat);
	}

	public Result postRegistorSchool(String regCat) throws JsonParseException, JsonMappingException, IOException {
		if(regCat == null || !regCat.isEmpty()) {
			flash("error", "Something wrong with your registration request.");
			return redirect(routes.SRPController.index());
		}

//		System.out.println(reg);
		return ok();
	}
}
