package arrow.framework.cdi.qualifiers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

@Qualifier
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Logout {
  public static final AnnotationLiteral<Logout> LITERAL = new AnnotationLiteral<Logout>() {};
}
