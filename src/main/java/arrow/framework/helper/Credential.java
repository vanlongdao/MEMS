package arrow.framework.helper;

import java.io.Serializable;
import java.util.Locale;

public abstract class Credential implements Serializable {
  private Locale currentLocale;

  public Locale getCurrentLocale() {
    return this.currentLocale;
  }

  public void setCurrentLocale(Locale pCurrentLocale) {
    this.currentLocale = pCurrentLocale;
  }
}
