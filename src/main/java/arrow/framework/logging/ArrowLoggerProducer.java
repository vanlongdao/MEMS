package arrow.framework.logging;


import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.deltaspike.core.util.ReflectionUtils;
import org.omnifaces.cdi.Startup;


@ApplicationScoped
@Startup
public class ArrowLoggerProducer implements Serializable {
  @Produces
  ArrowLogger produceLog(final InjectionPoint injectionPoint) {
    final Annotated annotated = injectionPoint.getAnnotated();
    if (annotated.isAnnotationPresent(Category.class)) {
      return ArrowLoggerProducer.getLogger(annotated.getAnnotation(Category.class).value());
    } else {
      final Bean<?> bean = injectionPoint.getBean();
      final Class<?> type = ReflectionUtils.getRawType(bean != null ? bean.getBeanClass() : injectionPoint.getMember().getDeclaringClass());
      return ArrowLoggerProducer.getLogger(type);
    }
  }

  /**
   * IMPORTANT NOTE: This method automatically find the correct logger name without providing the
   * class.
   * It involves SLOW OPERATION of accessing the stack trace, so it should only be called from
   * static initialization,
   * and only when injection of logger is not available
   *
   */
  public static ArrowLogger getLogger() {
    final String className = Thread.currentThread().getStackTrace()[2].getClassName();

    return ArrowLoggerProducer.getLogger(className);
  }


  public static ArrowLogger getLogger(final Class<?> clazz) {
    return ArrowLoggerProducer.getLogger(clazz.getName());
  }


  // When use with a different AS, implement the adaptor for the AS's provided logger,
  // and update this method to call that adaptor
  public static ArrowLogger getLogger(final String name) {
    return WildflyLoggerImpl.getLogger(name);
  }

}
