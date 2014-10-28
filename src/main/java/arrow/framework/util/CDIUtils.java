package arrow.framework.util;

import java.lang.annotation.Annotation;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

public class CDIUtils {
  public static BeanManager getBeanManager() {
    return CDI.current().getBeanManager();
  }

  public static boolean isContextActive(final Class<? extends Annotation> scopeClass) {
    try {
      return CDIUtils.getBeanManager().getContext(scopeClass).isActive();
    }

    catch (final ContextNotActiveException e) {
      return false;
    }
  }

  public static boolean isConversationContextActive() {
    return CDIUtils.isContextActive(ConversationScoped.class);
  }

  public static boolean isTransactionContextActive() {
    return CDIUtils.isContextActive(TransactionScoped.class);
  }
}
