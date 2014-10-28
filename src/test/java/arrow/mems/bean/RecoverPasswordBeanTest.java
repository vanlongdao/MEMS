package arrow.mems.bean;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.testng.annotations.Test;


public class RecoverPasswordBeanTest {
  @Test
  public void testRecoverPassword() {
    final Validator validator = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory().getValidator();
    final RecoverPasswordBean bean = new RecoverPasswordBean();
    bean.setNewPassword("123456");
    bean.setReNewPassword("12345678");
    final Set<ConstraintViolation<RecoverPasswordBean>> errors = validator.validate(bean);
    Assertions.assertThat(errors).isNotEmpty();
    Assertions.assertThat(errors.iterator().next().getMessage()).isEqualTo("test");
  }
}
