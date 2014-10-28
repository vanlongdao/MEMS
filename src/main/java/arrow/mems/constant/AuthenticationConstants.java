package arrow.mems.constant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AuthenticationConstants {
  public static final String USER_SESSION_KEY = "user_session";
  public static final String COOKIE_LOGIN_NAME = "login_name";
  public static final String COOKIE_TOKEN = "token";
  public static final String SUPERVISOR_SESSION_KEY = "supervisor_id";

  // public static final int COOKIE_LIVE_TIME = 60 * 60 * 24 * 7;
  public static final int COOKIE_LIVE_TIME = 60 * 60; // 1 Hour
  public static final int COOKIE_DELETE_TIME = 0;


  // User is supervisor or not
  public static final int NON_SUPPERVISOR = 0;
  public static final int SUPPERVISOR = 1;
}
