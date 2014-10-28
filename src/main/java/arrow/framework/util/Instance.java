package arrow.framework.util;

import org.apache.deltaspike.core.api.provider.BeanProvider;

public class Instance {
  /**
   * Gets the instance of class
   *
   * @param <T> the generic type
   * @param type the type
   * @return the t
   */
  public static <T> T get(final Class<T> type) {
    return BeanProvider.getContextualReference(type);
  }
}
