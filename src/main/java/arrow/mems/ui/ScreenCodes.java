package arrow.mems.ui;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class ScreenCodes {
  // define form code
  public static final String HOME = "000";
  public static final String LOGIN = "001";
  public static final String FORGOT_PASSWORD = "060";
  public static final String RECOVER_PASSWORD_FORM = "061";
  public static final String NOTIFICATION_FORM_CODE = "050";
  public static final String PERSON_MANAGEMENT = "MMI07";
  public static final String ADDRESS_MANAGEMENT = "MMI06";
  public static final String USER_MANAGEMENT = "MAU05";
  public static final String USER_MANAGEMENT_DETAIL = "MAU06";
  public static final String HUMAN_RESOURCE_MANAGEMENT = "MMI05";
  public static final String MASTER_DEVICES_MANAGEMENT_LIST_SEARCH = "MDM01";
  public static final String MASTER_DEVICES_MANAGEMENT_GENERAL_INFO_DEVICE = "MDM02_01";
  public static final String MASTER_DEVICES_MANAGEMENT_CHECKLIST_DEVICE = "MDM02_02";
  public static final String MASTER_DEVICES_MANAGEMENT_DOCUMENT = "MDM02_03";
  public static final String MASTER_DEVICES_MANAGEMENT_PARTLIST_DEVICE = "MDM02_04";
  public static final String MASTER_DEVICES_MANAGEMENT_MANUFACTURER = "MDM02_05";
  public static final String MASTER_DEVICES_MANAGEMENT_SUPPLIER = "MDM02_06";
  public static final String MASTER_DEVICES_MANAGEMENT_GENERAL = "MDM02";
  public static final String LIST_SEARCH_DEVICE = "MPM01";
  public static final String MASTER_DASHBOARD_MANAGEMENT_ENGINEER_SUPPLIER = "MDB06";
  public static final String MASTER_DASHBOARD_MANAGEMENT_HOSPITAL_MANAGER = "MDB01";
  public static final String MASTER_DASHBOARD_MANAGEMENT_HOSPITAL_ENGINEER = "MDB03";
  public static final String MASTER_DASHBOARD_MANAGEMENT_HOSPITAL_ACCOUNTANT = "MDB02";
  public static final String MASTER_DASHBOARD_MANAGEMENT_MANAGER_SYSTEM = "MDB10";
  public static final String MASTER_DASHBOARD_MANAGEMENT_SUPPLIER_MANAGER = "MDB04";
  public static final String MASTER_DASHBOARD_MANAGEMENT_MANUFACTURER_MANAGER = "MDB07";
  public static final String MASTER_DASHBOARD_MANAGEMENT_MANUFACTURER_ENGINEER = "MDB09";
  public static final String MASTER_DASHBOARD_MANAGEMENT_SUPPLIER_ACCOUNTANT = "MDB05";
  public static final String MASTER_DASHBOARD_MANAGEMENT_MANUFACTURER_ACCOUNTANT = "MDB08";

  public static final String UPDATE_USEAGE_HOUR_COUNT = "MUH01";
  public static final String VIEW_MANUAL_DOCUMENT = "MMM02";
  public static final String PRINT_QR_CODE = "MAS03";
  public static final String MANAGE_PRESET_PHRASES = "XIS01";
  public static final String REPAIR_HISTORY = "MRR02";

  public static final String USER_MANAGEMENT_EDIT_PROFILE = "edit_current_user";
  public static final String USER_MANAGEMENT_CHANGE_PASSWORD = "change_password";
  public static final String USER_MANAGEMENT_ADD_USER = "add";
  public static final String USER_MANAGEMENT_LIST_USER = "list";
  public static final String CORPORATE_MANAGEMENT = "MMI02";
  public static final String HOSPITAL_MANAGEMENT = "MFM01";
  public static final String HOSPITAL_DEPT_MANAGEMENT = "MFM02";
  public static final String BUDGET_MANAGEMENT = "MFM03";
  public static final String OFFICE_MANAGEMENT = "MMI03";
  public static final String APPROVE_FEATURE = "MMIX";
  public static final String APPROVE_SUMMARY = "MMIS";
  public static final String INPUT_REPAIR_REQUEST = "MRR01";
  public static final String PRINT_REPAIR_REQUEST = "MRR05";
  public static final String AUTH_SUPERVISOR = "MAP04";
  public static final String VIEW_DETAIL_FROM_QRCODE = "MAP03";
  public static final String VIEW_REPAIR_REQUEST = "MRR03";
  private static Map<String, String> screenCodeMaps = new HashMap<String, String>();;

  public static Map<String, String> screenCodes() {
    // add form screen
    ScreenCodes.screenCodeMaps.put(ScreenCodes.HOME, "home");
    ScreenCodes.screenCodeMaps.put(ScreenCodes.LOGIN, "login");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.RECOVER_PASSWORD_FORM, "recover_password");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.FORGOT_PASSWORD, "forgot_password");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.NOTIFICATION_FORM_CODE, "notification");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.PERSON_MANAGEMENT, "person_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.ADDRESS_MANAGEMENT, "address_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.USER_MANAGEMENT, "user_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.USER_MANAGEMENT_EDIT_PROFILE, "user_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.USER_MANAGEMENT_CHANGE_PASSWORD, "user_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.USER_MANAGEMENT_ADD_USER, "user_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.HUMAN_RESOURCE_MANAGEMENT, "human_resource_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_LIST_SEARCH, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.CORPORATE_MANAGEMENT, "corporate_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.OFFICE_MANAGEMENT, "office_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_GENERAL_INFO_DEVICE, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_CHECKLIST_DEVICE, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_DOCUMENT, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_PARTLIST_DEVICE, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_MANUFACTURER, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_SUPPLIER, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DEVICES_MANAGEMENT_GENERAL, "master_devices_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_ENGINEER_SUPPLIER, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_HOSPITAL_MANAGER, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_HOSPITAL_ENGINEER, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_HOSPITAL_ACCOUNTANT, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_MANAGER_SYSTEM, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_SUPPLIER_MANAGER, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_MANUFACTURER_MANAGER, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_MANUFACTURER_ENGINEER, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_SUPPLIER_ACCOUNTANT, "master_dashboard_management");
    
    ScreenCodes.screenCodeMaps.put(ScreenCodes.MASTER_DASHBOARD_MANAGEMENT_MANUFACTURER_ACCOUNTANT, "master_dashboard_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.UPDATE_USEAGE_HOUR_COUNT, "update_useage_hour_count");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.LIST_SEARCH_DEVICE, "list_search_device");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.USER_MANAGEMENT_DETAIL, "user_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.HUMAN_RESOURCE_MANAGEMENT, "human_resource_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.CORPORATE_MANAGEMENT, "corporate_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.HOSPITAL_MANAGEMENT, "hospital_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.HOSPITAL_DEPT_MANAGEMENT, "hospital_department_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.BUDGET_MANAGEMENT, "budget_management");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.APPROVE_FEATURE, "approve_feature");
    ScreenCodes.screenCodeMaps.put(ScreenCodes.APPROVE_SUMMARY, "approve_summary");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.AUTH_SUPERVISOR, "supervisor_login");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.INPUT_REPAIR_REQUEST, "input_repair_request");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.PRINT_REPAIR_REQUEST, "input_repair_request");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.VIEW_DETAIL_FROM_QRCODE, "view_detail_from_QR_code");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.VIEW_MANUAL_DOCUMENT, "view_manual_document");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.PRINT_QR_CODE, "print_QR_code");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.VIEW_REPAIR_REQUEST, "view_repair_request");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.MANAGE_PRESET_PHRASES, "manage_preset_phrases");

    ScreenCodes.screenCodeMaps.put(ScreenCodes.REPAIR_HISTORY, "repair_history");

    return ScreenCodes.screenCodeMaps;
  }
}
