package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.mvc.Result;
import security.authorization.CustomRestrict;
import be.objectify.deadbolt.java.actions.Dynamic;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Pattern;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import be.objectify.deadbolt.java.models.PatternType;
import security.authorization.CustomPermission;
import security.authorization.RoleGroup;
import security.authorization.MyRole;

public class TestController extends CustomController {
	@SubjectPresent(handlerKey = "defaulthandlerkey")
    public CompletionStage<Result> subjectPresent() {
		System.out.println("======> inside subject present");
        return CompletableFuture.completedFuture(ok("testing"));
    }

	@CustomRestrict(value = {@RoleGroup({MyRole.admin}), @RoleGroup(MyRole.admin)}, config = @Restrict(value={}, content="", handlerKey="defaulthandlerkey", deferred=false))
    public CompletionStage<Result> subjectPresent2() {
		System.out.println("======> inside subjectPresent2 **********");
        return CompletableFuture.completedFuture(ok("testing"));
    }

	@Restrict(value = {@Group({"admin"})})
    public CompletionStage<Result> subjectPresent3() {
		System.out.println("======> inside subjectPresent3 **********");
        return CompletableFuture.completedFuture(ok("testing"));
    }

	@Pattern("student.edit")
    public CompletionStage<Result> subjectPresent4() {
		System.out.println("======> inside subjectPresent4 **********");
        return CompletableFuture.completedFuture(ok("testing"));
    }

	@CustomPermission(value = "student.edit")
    public CompletionStage<Result> subjectPresent5() {
		System.out.println("======> inside subjectPresent5 **********");
        return CompletableFuture.completedFuture(ok("testing"));
    }

	@Dynamic(value = "viewEmployeeProfile", handlerKey="editemployeehandler")
	public CompletionStage<Result> subjectPresent6(Long schoolId) {
		System.out.println("======> inside subjectPresent6 ********** " + schoolId);
		return CompletableFuture.completedFuture(ok("testing"));
	}

	@Pattern(value = "editEmployeeProfile", patternType = PatternType.CUSTOM, handlerKey="editemployeehandler")
	public CompletionStage<Result> subjectPresent7(Long schoolId) {
		System.out.println("======> inside subjectPresent6 ********** " + schoolId);
		return CompletableFuture.completedFuture(ok("testing"));
	}

}
