package arrow.framework.validator;

import java.util.Locale;
import java.util.ResourceBundle;

import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

import arrow.framework.util.i18n.ArrowResourceBundle;

public class ArrowResourceBundleLocator implements ResourceBundleLocator {

  @Override
  public ResourceBundle getResourceBundle(Locale locale) {
    return ArrowResourceBundle.getBundle();
  }

}
