package arrow.framework.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.model.menu.MenuModel;

import arrow.framework.cdi.qualifiers.Login;
import arrow.framework.cdi.qualifiers.Logout;
import arrow.framework.cdi.qualifiers.RequestParam;
import arrow.framework.util.i18n.Messages;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.config.AppConfig;
import arrow.mems.ui.ScreenCodes;
import arrow.mems.ui.ScreenContext;
import arrow.mems.ui.ScreenParamName;

@Named
@ConversationScoped
public class ScreenMonitor implements Serializable {
  private LinkedList<ScreenContext> screenContexts = new LinkedList<ScreenContext>();

  @Inject
  private Conversation cdiConversation;
  @Inject
  private AppConfig appConfig;

  @PostConstruct
  public void init() {
    if (this.cdiConversation.isTransient()) {
      this.cdiConversation.begin();
    }
    this.initContexts();
  }

  @Inject
  UserCredential currentUser;

  @Inject
  @RequestParam
  private String screenCode;

  @Inject
  @RequestParam
  private String token;

  private ScreenContext getFirstScreenContext(boolean isLoggedIn) {
    if (StringUtils.isNotEmpty(this.screenCode)) {
      // if url contains ?screenCode=NNN
      if (this.isRequiredLoggedIn(this.screenCode)) {
        if (!isLoggedIn)
          return ScreenContext.LOGIN;
        else
          return new ScreenContext(this.screenCode);
      } else {
        final Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(ScreenParamName.TOKEN, this.token);
        return new ScreenContext(this.screenCode, attrs);
      }
    } else {
      if (isLoggedIn)
        return ScreenContext.HOME;
      else
        return ScreenContext.LOGIN;
    }
  }

  /**
   * Return true if the screen required users logged in first.
   *
   * @param pScreenCode
   * @return
   */
  private boolean isRequiredLoggedIn(String pScreenCode) {
    return !this.appConfig.getPublicScreens().contains(pScreenCode);
  }

  private void initContexts() {
    final boolean isLoggedIn = (this.currentUser != null) && this.currentUser.isLoggedIn();
    this.screenContexts.addFirst(this.getFirstScreenContext(isLoggedIn));
  }

  private void doCleanUp() {
    // Clear all ViewScoped beans
    Faces.getViewMap().clear();
  }

  public void openMenu(final String screenCode) {
    this.openScreen(screenCode, true);
  }

  public void openScreen(final String screenCode) {
    this.openScreen(screenCode, false);
  }

  public void openScreen(final String screenCode, final boolean needCleanUp) {
    // reset screenContext
    // this.goHome();
    this.screenContexts.clear();
    this.initContexts();
    final ScreenContext context = new ScreenContext(screenCode);
    this.screenContexts.addFirst(context);
    if (needCleanUp) {
      this.doCleanUp();
    }
  }

  public void switchScreen(String screenCode) {
    final ScreenContext context = new ScreenContext(screenCode);
    this.screenContexts.addFirst(context);
  }

  public void goHome() {
    this.screenContexts.clear();
    this.doCleanUp();
    this.initContexts();
  }

  public ScreenContext getCurrentScreenContext() {
    if (this.screenContexts.isEmpty())
      return null;

    return this.screenContexts.getFirst();
  }

  public void close() {
    if (!this.getCurrentScreenCode().equalsIgnoreCase(ScreenCodes.HOME)) {
      this.screenContexts.removeFirst();
    }
  }

  public void close(boolean needCleanUp) {
    this.close();
    if (needCleanUp) {
      this.doCleanUp();
    }
  }

  /**
   * case 1: If user not logged in, default is LOGIN page.
   * case 1.1: Forgot password
   * case 1.2: Recover password
   * case 2: if user logged in, default is HOME page
   *
   * @return
   */
  public String getCurrentScreenPath() {
    return this.getCurrentScreenContext().getScreenPath();
  }

  public String getCurrentScreenTitle() {
    if (this.getCurrentScreenContext() != null)
      return Messages.get(this.getCurrentScreenContext().getScreenName());
    return StringUtils.EMPTY;
  }

  public String getCurrentScreenCode() {
    if (this.getCurrentScreenContext() != null)
      return this.getCurrentScreenContext().getScreenCode();
    return StringUtils.EMPTY;
  }

  public void observerLogin(@Observes @Login UserCredential currentUser) {
    // go to default page
    this.openScreen(currentUser.getPreferences().getDefaultPage());
  }

  public void observerLogout(@Observes @Logout UserCredential currentUser) {
    // go to default page
    this.goHome();
  }

  public Map<String, Object> getCurrentAttributes() {
    if (this.getCurrentScreenContext() != null)
      return this.getCurrentScreenContext().getAttributes();
    return null;
  }

  public MenuModel getBreadcrumbModel() {
    return (new MenuModelBuilder()).createMenuModel(this.screenContexts);
  }

  public void backToScreen(String screenCode) {}

  public boolean shouldDisplayBreadcrumb() {
    return this.currentUser.isLoggedIn() && (this.screenContexts.size() >= 3);
  }
}
