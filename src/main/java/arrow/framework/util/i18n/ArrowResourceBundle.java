package arrow.framework.util.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import arrow.framework.util.Instance;

public class ArrowResourceBundle extends ResourceBundle {

  /**
   * A Map containing the combined resources
   */
  private Map<String, Object> combined;

  private void loadBundleKeysOnce() {
    if (this.combined != null)
      return;
    final List<ResourceBundle> bundles = this.getBundlesForCurrentLocale();
    for (final ResourceBundle bundle : bundles) {
      final Enumeration<String> keys = bundle.getKeys();
      String key = null;
      while (keys.hasMoreElements()) {
        key = keys.nextElement();
        this.combined.put(key, bundle.getObject(key));
      }
    }
  }

  @Override
  public Enumeration<String> getKeys() {
    this.loadBundleKeysOnce();
    return new ResourceBundleEnumeration(this.combined.keySet(), null);
  }


  @Override
  protected Object handleGetObject(final String key) {
    for (final ResourceBundle littleBundle : this.getBundlesForCurrentLocale()) {
      try {
        return littleBundle.getObject(key);
      } catch (final MissingResourceException mre) {
      }
    }

    return null; // superclass is responsible for throwing MRE
  }

  @Override
  public Locale getLocale() {
    return ArrowResourceBundle.getCurrentLocale();
  }

  /**
   * @return a ArrowResourceBundle
   */
  public static ResourceBundle getBundle() {
    return ResourceBundle.getBundle(ArrowResourceBundle.class.getName());
  }

  // private static Locale currentLocale = SysConfig.getDefaultLocale();

  public static Locale getCurrentLocale() {
    if (Instance.get(LocaleSelector.class).getLocale() == null)
      return Locale.getDefault();

    // else get current locale
    return Instance.get(LocaleSelector.class).getLocale();
  }

  private final Map<Locale, List<ResourceBundle>> bundleCache = new ConcurrentHashMap<Locale, List<ResourceBundle>>();

  private List<ResourceBundle> getBundlesForCurrentLocale() {
    final Locale locale = ArrowResourceBundle.getCurrentLocale();

    List<ResourceBundle> bundles = this.bundleCache.get(locale);
    if (bundles == null) {
      bundles = this.loadBundlesForCurrentLocale();
      this.bundleCache.put(locale, bundles);
    }

    return bundles;
  }

  private List<ResourceBundle> loadBundlesForCurrentLocale() {
    final Locale locale = ArrowResourceBundle.getCurrentLocale();
    final List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();

    // load all available resource bundles
    bundles.add(Utf8ResourceBundle.getBundle("labels", locale));
    bundles.add(Utf8ResourceBundle.getBundle("messages", locale));
    bundles.add(Utf8ResourceBundle.getBundle("field_labels", locale));
    bundles.add(Utf8ResourceBundle.getBundle("jsf_messages", locale));

    // built-in bundles
    // Bean Validation 1.1 (JSR-349)
    // bundles.add(ResourceBundle.getBundle("ValidationMessage", locale));

    // JSF 2.2 (JSR-344)
    // bundles.add(ResourceBundle.getBundle("javax.faces.Messages", locale));

    // To override built-in bundles
    // http://stackoverflow.com/questions/2668161/when-to-use-message-bundle-and-resource-bundle

    return Collections.unmodifiableList(bundles);
  }
}
