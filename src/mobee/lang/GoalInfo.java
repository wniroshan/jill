package mobee.lang;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface GoalInfo {
	String[] hasPlans();
}
