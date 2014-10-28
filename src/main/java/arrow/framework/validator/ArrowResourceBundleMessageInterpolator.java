package arrow.framework.validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class ArrowResourceBundleMessageInterpolator extends ResourceBundleMessageInterpolator {

  public ArrowResourceBundleMessageInterpolator() {
    super(new ArrowResourceBundleLocator());
  }

  public ArrowResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {

    super(new ArrowResourceBundleLocator(), true);
  }

  public ArrowResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cacheMessages) {
    super(new ArrowResourceBundleLocator(), cacheMessages);
  }
}
