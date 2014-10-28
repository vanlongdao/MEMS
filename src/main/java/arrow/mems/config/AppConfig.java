package arrow.mems.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;

import org.omnifaces.cdi.Eager;
import org.omnifaces.util.Faces;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.util.Instance;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.AccountTypeConstants;
import arrow.mems.ui.ScreenCodes;
import arrow.mems.util.SelectItemGenerator;
import arrow.mems.util.SelectItemGenerator.ListType;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ApplicationScoped
@Eager
public class AppConfig implements Serializable {
  @Inject
  ArrowLogger logger;
  private List<String> publicScreens;

  // TODO: update API_KEY before release
  public static final String API_KEY = "blahblahblah_ShouldChangeBeforeRelease";
  public static final String DOCUMENT_FILE_TYPE_ALLOWED = "([^\\s]+(\\.(?i)(pdf|doc|docx))$)";
  public static final String DOCUMENT_FILE_TYPE_ALLOWED_EXPLAIN = ".pdf, .doc, .docx";
  public static final String VIDEO_FILE_TYPE_ALLOWED = "([^\\s]+(\\.(?i)(flv|avi|mpeg|mkv|mp4|m4v|wmv))$)";
  public static final String VIDEO_FILE_TYPE_ALLOWED_EXPLAIN = ".flv, .avi, .mpeg, .mkv, .mp4, .m4v, .wmv";

  private static final String[] DOCS_FILE_EXTENTIONS = new String[] {"pdf", "doc"};
  private static final String[] VIDEO_FILE_EXTENTIONS = new String[] {"flv", "avi", "mpeg", "mkv", "mp4", "m4v", "wmv"};
  private static final String[] IMAGE_FILE_EXTENTIONS = new String[] {"png", "jpg", "bmp"};

  public String getDocumentFileTypesAllowed() {
    return ArrowStrUtils.buildExpresion(AppConfig.DOCS_FILE_EXTENTIONS);
  }

  public String getVideoFileTypesAllowed() {
    return ArrowStrUtils.buildExpresion(AppConfig.VIDEO_FILE_EXTENTIONS);
  }

  public String getImageFileTypesAllowed() {
    return ArrowStrUtils.buildExpresion(AppConfig.IMAGE_FILE_EXTENTIONS);
  }

  public int getDocFileSizeLimit() {
    return AppConfig.DOCUMENT_FILE_SIZE_ALLOWED;
  }

  public int getImageFileSizeLimit() {
    return AppConfig.IMAGE_FILE_SIZE_ALLOWED;
  }

  public int getVideoFileSizeLimit() {
    return AppConfig.VIDEO_FILE_SIZE_ALLOWED;
  }

  // 10MB
  public static final int DOCUMENT_FILE_SIZE_ALLOWED = 10240000;

  // 10MB
  public static final int IMAGE_FILE_SIZE_ALLOWED = 10240000;
  // 100MB
  public static final int VIDEO_FILE_SIZE_ALLOWED = 102400000;

  public static final String UPLOAD_DIR_PARAM_NAME = "arrow.mems.UPLOAD_DIR";
  private static final String UPLOAD_TEMP_DIR_PARAM_NAME = "arrow.mems.UPLOAD_TEMP_DIR";
  public static final String QRCODE_FOLDER = "QRCode";
  public static final int QUATITY_DECIMAL_PLACES = 2;
  public static final int MONEY_DECIMAL_PLACES = 2;
  public static final int PERCENT_DECIMAL_PLACES = 2;
  public static String QRCODE_EXTENSION = ".png";

  public String getUploadDir() {
    return Faces.getInitParameter(AppConfig.UPLOAD_DIR_PARAM_NAME);
  }

  public String getUploadTempDir() {
    return Faces.getInitParameter(AppConfig.UPLOAD_TEMP_DIR_PARAM_NAME);
  }

  /**
   * List of screen codes that user can access without authentication
   *
   * @return
   */
  public List<String> getPublicScreens() {
    if (this.publicScreens == null) {
      this.publicScreens = new ArrayList<String>();

      // init data
      this.publicScreens.add(ScreenCodes.FORGOT_PASSWORD);
      this.publicScreens.add(ScreenCodes.RECOVER_PASSWORD_FORM);
    }
    return this.publicScreens;
  }

  public static final String DEFAULT_THEME = "cupertino";


  public List<SelectItem> getAccountTypes() {
    return SelectItemGenerator.getListSelectItem(ListType.ACCOUNT_TYPE);
  }

  public List<SelectItem> getConditionSearchDevices() {
    return SelectItemGenerator.getListSelectItem(ListType.SEARCH_CONDITION);
  }

  public List<SelectItem> getCompareOperator() {
    return SelectItemGenerator.getListSelectItem(ListType.COMPARE_OPERATOR);
  }

  public List<SelectItem> getCompareSearchType() {
    return SelectItemGenerator.getListSelectItem(ListType.COMPARE_SEARCH_TYPE);
  }

  public List<SelectItem> getMasterDevicesType() {
    return SelectItemGenerator.getListSelectItem(ListType.MASTER_DEVICES_TYPE);
  }

  public List<SelectItem> getYesNoList() {
    return SelectItemGenerator.getListSelectItem(ListType.YES_NO);
  }

  @Inject
  private Conversation conversation;

  public void conversationInitialized(@Observes @Initialized(ConversationScoped.class) ServletRequest payload) {
    this.conversation.setTimeout(3600000L); // 60 minutes
  }

  public void conversationDestroyed(@Observes @Destroyed(ConversationScoped.class) ServletRequest payload) {
    this.logger.debugf("Conversation is destroyed {}", payload);
  }

  public boolean isEnableColumnToggler() {
    return Boolean.valueOf(Faces.getInitParameter("arrow.mems.ENABLE_COLUMN_TOGGLER"));
  }


  private static Map<Integer, String> mapAccountTypeAndDashboard;
  static {
    AppConfig.mapAccountTypeAndDashboard = new HashMap<Integer, String>();
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_MANAGER_HOSPITAL, "MDB01");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_ACCOUNTANT_HOSPITAL, "MDB02");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_ENGINEER_HOSPITAL, "MDB03");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_ENGINEER_SUPPLIER, "MDB06");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_MANAGER_SUPPLIER, "MDB04");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_MANAGER_MANUFACTURE_MDB07, "MDB07");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_MANAGER_MANUFACTURE_MDB09, "MDB09");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_ACCOUNTANT_SUPPLIER, "MDB05");
    AppConfig.mapAccountTypeAndDashboard.put(AccountTypeConstants.ACCOUNT_MANAGER_MANUFACTURE_MDB08, "MDB08");
  }

  public static String getDashboardOfAccount(int accountType) {
    return AppConfig.mapAccountTypeAndDashboard.get(accountType);
  }

  // Action type in action_log
  public static final String ACTION_TYPE_CHECK = "check";
  public static final String ACTION_TYPE_PERIODIC_CHECK = "periodic check";
  public static final String ACTION_TYPE_MAINTENACE = "maintence";
  public static final String ACTION_TYPE_REPAIR = "repair";


  public static int getMaxRowsPerPage() {
    if (Instance.get(UserCredential.class).getPreferences().getTheme().equals("mems800"))
      return 10;
    return 20;
  }

  public int getMaxWidth() {
    if (Instance.get(UserCredential.class).getPreferences().getTheme().equals("mems800"))
      return 755;
    return 1355;
  }
}
