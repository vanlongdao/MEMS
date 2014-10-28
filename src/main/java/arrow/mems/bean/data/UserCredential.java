package arrow.mems.bean.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import arrow.framework.helper.AuthenticationData;
import arrow.framework.helper.Credential;
import arrow.mems.config.AppConfig;
import arrow.mems.constant.AuthenticationConstants;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.Users;
import arrow.mems.service.UserService;
import arrow.mems.ui.ScreenCodes;
import arrow.mems.ui.ScreenContext;

@Named
@SessionScoped
public class UserCredential extends Credential {
  private AuthenticationData authData;

  @Inject
  UserService userService;

  public Person getPerson() {
    return this.authData != null ? this.authData.getPerson() : null;
  }

  public int getUserId() {
    return this.authData != null ? this.authData.getUserId() : 0;
  }

  public Users getUserInfo() {
    return this.authData != null ? this.authData.getUserInfo() : null;
  }

  public String getOfficeCode() {
    return this.getUserInfo() != null ? this.getUserInfo().getOfficeCode() : null;
  }

  public Preferences getPreferences() {
    return this.preferences;
  }

  public void setPreferences(Preferences pPreference) {
    this.preferences = pPreference;
  }

  private Preferences preferences;


  @PostConstruct
  public void init() {

    // just make sure that the preferences is never null.
    this.preferences = new Preferences();
    this.preferences.setTheme(AppConfig.DEFAULT_THEME);
  }

  public boolean isLoggedIn() {
    final HttpSession currentSession = Faces.getSession(false);
    return ((currentSession != null) && (currentSession.getAttribute(AuthenticationConstants.USER_SESSION_KEY) != null));
  }

  public boolean isSupervisor() {
    return CommonConstants.FLAG_TRUE == this.authData.getUserInfo().getIsSupervisor();
  }

  public void updateAuthData(final AuthenticationData authenticationData) {
    this.authData = authenticationData;
    final HttpSession currentSession = Faces.getSession(false);
    if (currentSession == null)
      throw new IllegalArgumentException("Cannot get session");

    if (authenticationData != null) {
      // Login
      // TODO: update permissions
      currentSession.setAttribute(AuthenticationConstants.USER_SESSION_KEY, "Logged in");

      // setup user preferences
      this.setupUserPreferences(this);
    } else {
      // Logout
      currentSession.removeAttribute(AuthenticationConstants.USER_SESSION_KEY);
    }
  }

  private void setupUserPreferences(UserCredential currentUser) {
    if (StringUtils.isEmpty(currentUser.getUserInfo().getTheme())) {
      currentUser.getPreferences().setTheme(AppConfig.DEFAULT_THEME);
    } else {
      currentUser.getPreferences().setTheme(currentUser.getUserInfo().getTheme());
    }

    // TODO: MEMS-119
    currentUser.getPreferences().setDefaultPage(ScreenCodes.HOME);

    if (currentUser.getCurrentLocale() == null) {
      currentUser.setCurrentLocale(Faces.getDefaultLocale());
    }

    currentUser.getPreferences().setDashboard(AppConfig.getDashboardOfAccount(currentUser.getUserInfo().getAccountType()));
  }

  public void changeTheme() {
    this.userService.addThemeToUser(this.preferences.getTheme(), this.getUserId());
  }


  public static class Preferences {
    private String theme;
    private String defaultPage;
    private String dashboard;

    public String getDashboard() {
      if (StringUtils.isNotEmpty(this.dashboard))
        return ScreenContext.buildPath(this.dashboard, "master_dashboard_management");
      return StringUtils.EMPTY;
    }

    public void setDashboard(String pDashboard) {
      this.dashboard = pDashboard;
    }

    public String getTheme() {
      return this.theme;
    }

    public void setTheme(String pTheme) {
      this.theme = pTheme;
    }

    public String getDefaultPage() {
      return this.defaultPage;
    }

    public void setDefaultPage(String pDefaultPage) {
      this.defaultPage = pDefaultPage;
    }
  }
}
