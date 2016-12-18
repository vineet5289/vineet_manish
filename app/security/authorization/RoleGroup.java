package security.authorization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RoleGroup {
	 MyRole[] value() default {};
	 MyRole[] not() default {};
}
