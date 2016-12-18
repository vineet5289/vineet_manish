package security.authorization;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

import play.mvc.With;
import be.objectify.deadbolt.java.ConfigKeys;
import be.objectify.deadbolt.java.actions.Restrict;

@With(CustomRestrictAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface CustomRestrict {
	RoleGroup[] value();
	Restrict config();
}
