package arrow.mems.constant;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

@Named
public class CommonConstants {
  public static final String DEFAULT_ENCODING = "UTF8";

  public static final class FileExtensions {
    public static final String FLV = "flv";
    public static final String AVI = "avi";
    public static final String MP4 = "mp4";
    public static final String WMV = "wmv";
  }
  public static final class MimeTypes {
    public static final String VIDEO_FLV = "video/x-flv";
    public static final String VIDEO_AVI = "video/divx";
    public static final String VIDEO_MP4 = "video/mp4";

  }
  public static final class MDevTypeConstants {
    // Master device type
    public static final int MEDICAL_EQUIPMENT = 1;
    public static final int MEASUREMENT_DEVICE = 2;
    public static final int PARTSLIST = 3;
  }

  public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  public static final String REGEX_TEL = "^[0-9\\s]*+$";
  public static final String REGEX_TEL_FAX_VIETNAM = "^[0-9-\\+ ]*$";
  public static final String REGEX_WHITE_SPACE = "^[\\s]";
  public static final String REGEX_NUMBER = "^[1-9]+$";

  public static final String DAY_MONTH_YEAR_FORMAT = "dd/MM/yyyy";
  public static final String MONTH_YEAR_FORMAT = "MM/yyyy";
  public static final String YEAR_FORMAT = "yyyy";
  public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
  public static final String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
  public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

  // Bean validations
  public static final int PASSWORD_MIN_LENGTH = 4;
  public static final int PASSWORD_MAX_LENGTH = 32;
  public static final int LOGIN_NAME_MAX_LENGTH = 16;
  public static final int LOGIN_NAME_MIN_LENGTH = 6;
  public static final int TEXT_MAX_LENGTH = 255;
  public static final int CODE_MAX_LENGTH = 32;

  // List search devices master
  public static final int PULLDOWN_MANUFACTORY_CONTACT_PERSON = 1;
  public static final int PULLDOWN_MANUFACTORY_COUNTRY = 2;
  public static final int PULLDOWN_MANUFACTORY_CATALOG = 3;
  public static final int PULLDOWN_MANUFACTORY_SPECIFICATION = 4;
  public static final int PULLDOWN_MANUFACTORY_NOTICE = 5;

  public static final int PULLDOWN_NUMBER_CONDITION = 5;

  // List type of search (AND , OR )
  public static final int PULLDOWN_ALL_TYPE = 1;
  public static final int PULLDOWN_ANY_TYPE = 0;

  // List type of operator
  public static final int PULLDOWN_EQUAL_TYPE = 1;
  public static final int PULLDOWN_NOT_EQUAL_TYPE = 2;
  public static final int PULLDOWN_CONTAIN_STRING_TYPE = 3;
  public static final int PULLDOWN_CONTAIN_STRING_TYPE_EXACT_CASE = 4;
  public static final int PULLDOWN_GREATER_THAN_TYPE = 5;
  public static final int PULLDOWN_GREATER_THAN_OR_EQUAL_TYPE = 6;
  public static final int PULLDOWN_LESS_THAN_TYPE = 7;
  public static final int PULLDOWN_LESS_THAN_OR_EQUAL_TYPE = 8;


  public static final int ACTIVE = 0;
  public static final int DELETE = 1;
  public static final int ENABLE = 0;
  public static final int DISABLE = 1;

  public static final int FLAG_TRUE = 1;
  public static final int FLAG_FALSE = 0;
  // Upload message
  public static final String UPLOAD_UNSUCCESSFUL = "Unsuccessful";
  public static final String UPLOAD_SUCCESSFUL = "Successful";



  public static final String MODULE_MASTER_DEVICE = "MDM";

  // Schedule : check type
  public static final int TIME_PERIOD_MONTH = 3;
  public static final int TIME_OF_USING = 4;
  public static final String REPAIR = "Repair";
  public static final String REPLY = "Reply";
  public static final String PASSED_LABEL = "PASSED";
  public static final String NG_LABEL = "NG";

  public static final int PASSED = 1;
  public static final int NG = 0;

  // Approval type
  public static final String APPROVAL = "Approval";
  public static final String REJECT = "Reject";
  public static final String REVISE = "Revise";
  public static final String NONE = "None";
  public static Map<String, String> MAP_APPROVAL_TYPE;
  static {
    CommonConstants.MAP_APPROVAL_TYPE = new HashMap<String, String>();
    CommonConstants.MAP_APPROVAL_TYPE.put(CommonConstants.APPROVAL, CommonConstants.APPROVAL);
    CommonConstants.MAP_APPROVAL_TYPE.put(CommonConstants.APPROVAL, CommonConstants.APPROVAL);
    CommonConstants.MAP_APPROVAL_TYPE.put(CommonConstants.REVISE, CommonConstants.REVISE);
    CommonConstants.MAP_APPROVAL_TYPE.put(CommonConstants.NONE, CommonConstants.NONE);
  }

  // Update database false;
  public static final int UPDATE_UNSUCCESSFUL = 0;

  public static final String NOT_START = "Not start";
  public static final String NOT_VISITED = "Not visited";
  public static final String VISITED = "Visited";
  public static final String WAIT_PARTS = "Wait parts";
  public static final String COMPLETED = "Completed";

  // Params map for print repair
  public static final String PRINT_REPAIR_CURRENT_DATE = "${currentDate}";
  public static final String PRINT_REPAIR_DEPARTMENT_NAME = "${nameOfDepartment}";
  public static final String PRINT_REPAIR_REQUEST_DATE = "${requestDate}";
  public static final String PRINT_REPAIR_DOCUMENT_NUMBER = "${docNumber}";
  public static final String PRINT_REPAIR_OCCUR_DATE = "${occurDate}";
  public static final String PRINT_REPAIR_CUSTOMER_NAME = "${customerName}";
  public static final String PRINT_REPAIR_CUSTOMER_EMAIL = "${customerEmail}";
  public static final String PRINT_REPAIR_HEAD_PERSON = "${headPerson}";
  public static final String PRINT_REPAIR_PRODUCT_NAME = "${productName}";
  public static final String PRINT_REPAIR_PRODUCT_NUMBER = "${productNumber}";
  public static final String PRINT_REPAIR_DATE_ACCEPTANCE_REPAIR = "${dateAcceptanceRepair}";
  public static final String PRINT_REPAIR_DEVICE_NUMBER = "${deviceNumber}";
  public static final String PRINT_REPAIR_RESPONSE_PERSON = "${responsePerson}";
  public static final String PRINT_REPAIR_EXPIRATION_DATE = "${expirationDate}";
  public static final String PRINT_REPAIR_FEEDBACK_PROBLEMS = "${feedbackProblem}";
  public static final String PRINT_REPAIR_DESCRIPTIPON_ERROR = "${descriptionError}";
  public static final String PRINT_REPAIR_CONTENT_REPAIR = "${contentRepair}";
  public static final String PRINT_REPAIR_PREVENTION_DESCRIPTION = "${preventionDescription}";
  public static final String PRINT_REPAIR_CAUSE_PREVENTION = "${causePrevention}";
  public static final String PRINT_REPAIR_DEVICE_NAME_MEASURE1 = "${deviceNameMeasure1}";
  public static final String PRINT_REPAIR_DEVICE_NAME_MEASURE2 = "${deviceNameMeasure2}";
  public static final String PRINT_REPAIR_SERIAL_NO1 = "${serialNo1}";
  public static final String PRINT_REPAIR_SERIAL_NO2 = "${serialNo2}";
  public static final String PRINT_REPAIR_PARTORDER_NAME = "${partOrderName}";
  public static final String PRINT_REPAIR_CATEGORY_NAME = "${catName}";
  public static final String PRINT_REPAIR_TOTAL_ITEM = "${totalItem}";
  public static final String PRINT_REPAIR_ITEM_PRICE = "${itemPrice}";
  public static final String PRINT_REPAIR_NUMBER_DATE_OF_REPAIR = "${numberDate}";
  public static final String PRINT_REPAIR_FEE_PARTS = "${fee_parts}";
  public static final String PRINT_REPAIR_FEE_ACTUAL = "${fee_actual}";
  public static final String PRINT_REPAIR_FEE_SHIPPING = "${fee_shipping}";
  public static final String PRINT_REPAIR_TOTALBILL = "${totalBill}";
  public static final String PRINT_REPAIR_PAID_OR_NOTPAID = "${paidOrNotPaid}";
  public static final String PRINT_REPAIR_FINISH_DATE = "${finishDate}";
  public static final String PRINT_REPAIR_OFFICE_NAME_REQUEST_REPAIR = "${officeNameRequestRepair}";
  public static final String PRINT_CHECK = "${check}";
  public static final String PRINT_REPAIR_PERIODIC_CHECK = "${periodicCheck}";
  public static final String PRINT_REPAIR_MAINTENCE = "${maintenance}";
  public static final String PRINT_REPAIR_REPAIR = "${repair}";
  public static final String PRINT_REPAIR_IMAGE_FILE = "${imageFile}";
  public static final String PRINT_REPAIR_TOTAL_PRICE = "${totalPrice}";

  public static final String PRINT_REPAIR_FLAG_POSITION_DEVICE = "${positionDevice}";
  public static final String PRINT_REPAIR_FLAG_POSITION_DEVICE_MEASURE = "${positionDeviceMeasure}";
  public static final int REPAIR_MODE_USE_SCHEDULE = 1;
  public static final int REPAIR_MODE_NOT_USE_SCHEDULE = 0;

  public static final int USE_COUNT_SCHEDULE = 0;
  public static final int USE_SCHEDULE_ITEM = 1;

  public static Map<String, Object> buildMapPrintRepairContent() {
    final Map<String, Object> newMap = new HashMap<String, Object>();
    newMap.put(CommonConstants.PRINT_REPAIR_CURRENT_DATE, CommonConstants.PRINT_REPAIR_CURRENT_DATE);
    newMap.put(CommonConstants.PRINT_REPAIR_DEPARTMENT_NAME, CommonConstants.PRINT_REPAIR_DEPARTMENT_NAME);
    newMap.put(CommonConstants.PRINT_REPAIR_REQUEST_DATE, CommonConstants.PRINT_REPAIR_REQUEST_DATE);
    newMap.put(CommonConstants.PRINT_REPAIR_DOCUMENT_NUMBER, CommonConstants.PRINT_REPAIR_DOCUMENT_NUMBER);
    newMap.put(CommonConstants.PRINT_REPAIR_OCCUR_DATE, CommonConstants.PRINT_REPAIR_OCCUR_DATE);
    newMap.put(CommonConstants.PRINT_REPAIR_CUSTOMER_NAME, CommonConstants.PRINT_REPAIR_CUSTOMER_NAME);
    newMap.put(CommonConstants.PRINT_REPAIR_CUSTOMER_EMAIL, CommonConstants.PRINT_REPAIR_CUSTOMER_EMAIL);
    newMap.put(CommonConstants.PRINT_REPAIR_HEAD_PERSON, CommonConstants.PRINT_REPAIR_HEAD_PERSON);
    newMap.put(CommonConstants.PRINT_REPAIR_PRODUCT_NAME, CommonConstants.PRINT_REPAIR_PRODUCT_NAME);
    newMap.put(CommonConstants.PRINT_REPAIR_PRODUCT_NUMBER, CommonConstants.PRINT_REPAIR_PRODUCT_NUMBER);
    newMap.put(CommonConstants.PRINT_REPAIR_DATE_ACCEPTANCE_REPAIR, CommonConstants.PRINT_REPAIR_DATE_ACCEPTANCE_REPAIR);
    newMap.put(CommonConstants.PRINT_REPAIR_DEVICE_NUMBER, CommonConstants.PRINT_REPAIR_DEVICE_NUMBER);
    newMap.put(CommonConstants.PRINT_REPAIR_RESPONSE_PERSON, CommonConstants.PRINT_REPAIR_RESPONSE_PERSON);
    newMap.put(CommonConstants.PRINT_REPAIR_EXPIRATION_DATE, CommonConstants.PRINT_REPAIR_EXPIRATION_DATE);
    newMap.put(CommonConstants.PRINT_REPAIR_FEEDBACK_PROBLEMS, CommonConstants.PRINT_REPAIR_FEEDBACK_PROBLEMS);
    newMap.put(CommonConstants.PRINT_REPAIR_DESCRIPTIPON_ERROR, CommonConstants.PRINT_REPAIR_DESCRIPTIPON_ERROR);
    newMap.put(CommonConstants.PRINT_REPAIR_CONTENT_REPAIR, CommonConstants.PRINT_REPAIR_CONTENT_REPAIR);
    newMap.put(CommonConstants.PRINT_REPAIR_PREVENTION_DESCRIPTION, CommonConstants.PRINT_REPAIR_CURRENT_DATE);
    newMap.put(CommonConstants.PRINT_REPAIR_CAUSE_PREVENTION, CommonConstants.PRINT_REPAIR_CAUSE_PREVENTION);
    newMap.put(CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE1, CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE1);
    newMap.put(CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE2, CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE2);
    newMap.put(CommonConstants.PRINT_REPAIR_SERIAL_NO1, CommonConstants.PRINT_REPAIR_SERIAL_NO1);
    newMap.put(CommonConstants.PRINT_REPAIR_SERIAL_NO2, CommonConstants.PRINT_REPAIR_SERIAL_NO2);
    newMap.put(CommonConstants.PRINT_REPAIR_PARTORDER_NAME, CommonConstants.PRINT_REPAIR_PARTORDER_NAME);
    newMap.put(CommonConstants.PRINT_REPAIR_CATEGORY_NAME, CommonConstants.PRINT_REPAIR_CATEGORY_NAME);
    newMap.put(CommonConstants.PRINT_REPAIR_TOTAL_ITEM, CommonConstants.PRINT_REPAIR_TOTAL_ITEM);
    newMap.put(CommonConstants.PRINT_REPAIR_ITEM_PRICE, CommonConstants.PRINT_REPAIR_ITEM_PRICE);
    newMap.put(CommonConstants.PRINT_REPAIR_NUMBER_DATE_OF_REPAIR, CommonConstants.PRINT_REPAIR_NUMBER_DATE_OF_REPAIR);
    newMap.put(CommonConstants.PRINT_REPAIR_FEE_PARTS, CommonConstants.PRINT_REPAIR_FEE_PARTS);
    newMap.put(CommonConstants.PRINT_REPAIR_FEE_ACTUAL, CommonConstants.PRINT_REPAIR_FEE_ACTUAL);
    newMap.put(CommonConstants.PRINT_REPAIR_FEE_SHIPPING, CommonConstants.PRINT_REPAIR_FEE_SHIPPING);
    newMap.put(CommonConstants.PRINT_REPAIR_TOTALBILL, CommonConstants.PRINT_REPAIR_TOTALBILL);
    newMap.put(CommonConstants.PRINT_REPAIR_PAID_OR_NOTPAID, CommonConstants.PRINT_REPAIR_PAID_OR_NOTPAID);
    newMap.put(CommonConstants.PRINT_REPAIR_OFFICE_NAME_REQUEST_REPAIR, CommonConstants.PRINT_REPAIR_OFFICE_NAME_REQUEST_REPAIR);
    newMap.put(CommonConstants.PRINT_CHECK, CommonConstants.PRINT_CHECK);
    newMap.put(CommonConstants.PRINT_REPAIR_PERIODIC_CHECK, CommonConstants.PRINT_REPAIR_PERIODIC_CHECK);
    newMap.put(CommonConstants.PRINT_REPAIR_MAINTENCE, CommonConstants.PRINT_REPAIR_MAINTENCE);
    newMap.put(CommonConstants.PRINT_REPAIR_REPAIR, CommonConstants.PRINT_REPAIR_REPAIR);
    newMap.put(CommonConstants.PRINT_REPAIR_TOTAL_PRICE, CommonConstants.PRINT_REPAIR_TOTAL_PRICE);
    newMap.put(CommonConstants.PRINT_REPAIR_IMAGE_FILE, CommonConstants.PRINT_REPAIR_IMAGE_FILE);
    newMap.put(CommonConstants.PRINT_REPAIR_FINISH_DATE, CommonConstants.PRINT_REPAIR_FINISH_DATE);
    newMap.put(CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE, CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE);
    newMap.put(CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE_MEASURE, CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE_MEASURE);
    return newMap;
  }

  // Schedule status task in dashboard
  public static final int TASK_DONE = 1;
  public static final int TASK_DOING = 2;
  public static final int TASK_NOT_START = 3;

  // code of action type master
  public static final String TYPE_MAINTENANCE = "0201";
  // Name status of action bill
  public static final int BILL_NOT_PAID = 0;
  public static final int BILL_PAID = 1;

  // Dashboard codes
  public static final String MASTER_DASHBORAD_MANUFACTURER_ENGINNER = "09";
  public String dashboardManufacturerEngineer = CommonConstants.MASTER_DASHBORAD_MANUFACTURER_ENGINNER;

  public String getDashboardManufacturerEngineer() {
    return this.dashboardManufacturerEngineer;
  }

}
