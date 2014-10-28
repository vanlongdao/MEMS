/*
 * package: arrow.framework.util.i18n
 * file: LocaleSelector.java
 * time: Jun 27, 2014
 *
 * @author michael
 */
package arrow.framework.util.i18n;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import arrow.framework.helper.Credential;

@Named
@SessionScoped
public class LocaleSelector implements Serializable {
  @Inject
  private Credential currentUser;

  /**
   * Gets the all supported locales.
   *
   * @return the all supported locales
   */
  public List<Locale> getAllSupportedLocales() {
    return Faces.getSupportedLocales();
  }

  /**
   * Sets the locale.
   *
   * @param l the new locale
   */
  public void setLocale(final Locale l) {
    if (this.currentUser != null) {
      this.currentUser.setCurrentLocale(l);
      Faces.setLocale(l);
    }
  }

  public void setLocale(String lang) {
    final Locale locale = new Locale(lang);
    this.setLocale(locale);
  }

  /**
   * Gets the locale.
   *
   * @return the locale
   */
  public Locale getLocale() {
    if (this.currentUser != null)
      return this.currentUser.getCurrentLocale();
    return Locale.ENGLISH;
  }

}
