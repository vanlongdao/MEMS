package arrow.framework.persistence.qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

import arrow.framework.persistence.ArrowEntity;

@Qualifier
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface EntityPersisted {
  public static class Literal extends AnnotationLiteral<EntityPersisted> implements EntityPersisted {
    private Class<? extends ArrowEntity> value;

    @Override
    public Class<? extends ArrowEntity> value() {
      return this.value;
    }

    public Literal(final Class<? extends ArrowEntity> value) {
      if (value != null) {
        this.value = value;
      }
    }
  }

  Class<? extends ArrowEntity> value();
}
