package arrow.mems.ui;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;



public class ScreenContext implements Serializable {
  private String screenCode;
  private String screenName;
  private String screenPath;

  private Map<String, Object> attributes;

  private static final String WEB_INF_PAGES = "/WEB-INF/pages/";
  private static final String DEFAULT_SUFFIX = ".xhtml";

  public ScreenContext(String screenCode, Map<String, Object> attrs) {
    this(screenCode);
    this.setAttributes(attrs);
  }

  public ScreenContext(String screenCode) {
    this.screenPath = StringUtils.EMPTY;
    if (ScreenCodes.screenCodes().containsKey(screenCode)) {
      this.screenCode = screenCode;
      this.screenPath = this.buildCurrentFormPath();
      this.screenName = ScreenCodes.screenCodes().get(screenCode);
    }
  }

  public static String buildPath(String screenCode, String formName) {
    return (new StringBuilder()).append(ScreenContext.WEB_INF_PAGES).append(formName).append("/").append(formName).append("_").append(screenCode)
        .append(ScreenContext.DEFAULT_SUFFIX).toString();
  }

  private String buildCurrentFormPath() {
    if (ScreenCodes.screenCodes().containsKey(this.screenCode))
      return ScreenContext.buildPath(this.screenCode, ScreenCodes.screenCodes().get(this.screenCode));

    return StringUtils.EMPTY;
  }

  public String getScreenName() {
    return this.screenName;
  }

  public void setScreenName(String pScreenTitle) {
    this.screenName = pScreenTitle;
  }

  public String getScreenCode() {
    return this.screenCode;
  }

  public void setScreenCode(String pScreenCode) {
    this.screenCode = pScreenCode;
  }

  public String getScreenPath() {
    return this.screenPath;
  }

  public void setScreenPath(String pScreenPath) {
    this.screenPath = pScreenPath;
  }

  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  public void setAttributes(Map<String, Object> pAttributes) {
    this.attributes = pAttributes;
  }

  public static final ScreenContext LOGIN = new ScreenContext(ScreenCodes.LOGIN);
  public static final ScreenContext HOME = new ScreenContext(ScreenCodes.HOME);
}
