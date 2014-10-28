package arrow.mems.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import arrow.framework.faces.util.LabelKeySelectItem;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.CompareOperatorConstants;
import arrow.mems.constant.MemsDataType;
import arrow.mems.constant.PartEstimationTypes;

public class SelectItemGenerator {
  private static volatile Object myLock = new Object(); // must be declared volatile

  public static enum ListType {
    ACCOUNT_TYPE, SEARCH_CONDITION, YES_NO, MASTER_DEVICES_TYPE, DATA_TYPE, PART_ESTIMATION_TYPE, COMPARE_OPERATOR, COMPARE_SEARCH_TYPE, SCHEDULE_TASK, STATUS_ACTIONBILL
  }

  private static List<SelectItem> accountTypes;
  private static List<SelectItem> searchDeviceConditions;
  private static List<SelectItem> compareOperator;
  private static List<SelectItem> compareSearchType;
  private static List<SelectItem> yesNo;
  private static List<SelectItem> masterDevicesType;
  private static List<SelectItem> scheduleTask;
  private static List<SelectItem> statusActionBill;
  private static List<SelectItem> dataTypes;
  private static List<SelectItem> partEstimationTypes;
  private static Map<String, SelectItem> mapDataTypes;

  public static Map<String, SelectItem> getMapDataTypes() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.mapDataTypes == null) {
        SelectItemGenerator.mapDataTypes = new HashMap<String, SelectItem>();
        SelectItemGenerator.mapDataTypes.put(MemsDataType.ACTION_LOG, new LabelKeySelectItem(MemsDataType.ACTION_LOG, "actionLog"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.ADDRESS, new LabelKeySelectItem(MemsDataType.ADDRESS, "address"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.ALERT_BY_COUNT, new LabelKeySelectItem(MemsDataType.ALERT_BY_COUNT, "alertByCount"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.CORPORATE, new LabelKeySelectItem(MemsDataType.CORPORATE, "corporate"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.CHECK_ITEM, new LabelKeySelectItem(MemsDataType.CHECK_ITEM, "checklistItem"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.CHECK, new LabelKeySelectItem(MemsDataType.CHECK, "checkList"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.CONTRACTS, new LabelKeySelectItem(MemsDataType.CONTRACTS, "contract"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.COUNT_RECORD, new LabelKeySelectItem(MemsDataType.COUNT_RECORD, "countRecord"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.DEVICE, new LabelKeySelectItem(MemsDataType.DEVICE, "device"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.HOSPITAL, new LabelKeySelectItem(MemsDataType.HOSPITAL, "hospital"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.HOSPITAL_DEPARTMENT, new LabelKeySelectItem(MemsDataType.HOSPITAL_DEPARTMENT,
            "hospitalDepartment"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.MDEVICE, new LabelKeySelectItem(MemsDataType.MDEVICE, "deviceType"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.MDEV_CHECKLIST, new LabelKeySelectItem(MemsDataType.MDEV_CHECKLIST, "checklistDeviceType"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.MDEV_CHECKLIST_ITEM, new LabelKeySelectItem(MemsDataType.MDEV_CHECKLIST_ITEM,
            "checklistItemDeviceType"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.OFFICE, new LabelKeySelectItem(MemsDataType.OFFICE, "manufacturerOffice"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.PART_ESTIMATE, new LabelKeySelectItem(MemsDataType.PART_ESTIMATE, "partEstimate"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.PART_ORDER, new LabelKeySelectItem(MemsDataType.PART_ORDER, "partOrder"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.PERSON, new LabelKeySelectItem(MemsDataType.PERSON, "manufacturerPerson"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.PERSON_CLASS, new LabelKeySelectItem(MemsDataType.PERSON_CLASS, "personClass"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.ROLE_MASTER, new LabelKeySelectItem(MemsDataType.ROLE_MASTER, "roleMaster"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.PRESET_PHRASES_MEANING, new LabelKeySelectItem(MemsDataType.PRESET_PHRASES_MEANING,
            "presetPhrase"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.SCHEDULE, new LabelKeySelectItem(MemsDataType.SCHEDULE, "schedule"));
        SelectItemGenerator.mapDataTypes.put(MemsDataType.SCHEDULE_ITEM, new LabelKeySelectItem(MemsDataType.SCHEDULE_ITEM, "scheduleItem"));
      }
    }
    return SelectItemGenerator.mapDataTypes;
  }

  public static List<SelectItem> getListSelectItem(final ListType listType) {
    switch (listType) {
      case PART_ESTIMATION_TYPE:
        return SelectItemGenerator.getPartEstimationTypes();
      case ACCOUNT_TYPE:
        return SelectItemGenerator.getAccountTypes();
      case SEARCH_CONDITION:
        return SelectItemGenerator.getSearchDeviceConditions();
      case MASTER_DEVICES_TYPE:
        return SelectItemGenerator.getMasterDevicesType();
      case COMPARE_OPERATOR:
        return SelectItemGenerator.getCompareOperator();
      case COMPARE_SEARCH_TYPE:
        return SelectItemGenerator.getCompareSearchType();
      case YES_NO:
        return SelectItemGenerator.getYesNoList();
      case DATA_TYPE:
        return SelectItemGenerator.getListDataType();
      case SCHEDULE_TASK:
        return SelectItemGenerator.getScheduleTasks();
      case STATUS_ACTIONBILL:
        return SelectItemGenerator.getStatusActionBill();
      default:
        return null;
    }
  }

  private static List<SelectItem> getPartEstimationTypes() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.partEstimationTypes == null) {
        SelectItemGenerator.partEstimationTypes = new ArrayList<>();
        SelectItemGenerator.partEstimationTypes.add(new LabelKeySelectItem(PartEstimationTypes.REPAIR, "repair"));
        SelectItemGenerator.partEstimationTypes.add(new LabelKeySelectItem(PartEstimationTypes.REPLY, "reply"));
      }
    }
    return SelectItemGenerator.partEstimationTypes;
  }

  private static List<SelectItem> getListDataType() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.dataTypes == null) {
        SelectItemGenerator.dataTypes = new ArrayList<>(SelectItemGenerator.getMapDataTypes().values());
      }
    }
    return SelectItemGenerator.yesNo;
  }

  private static List<SelectItem> getYesNoList() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.yesNo == null) {
        SelectItemGenerator.yesNo = new ArrayList<SelectItem>();
        SelectItemGenerator.yesNo.add(new LabelKeySelectItem(0, "no"));
        SelectItemGenerator.yesNo.add(new LabelKeySelectItem(1, "yes"));
      }
    }
    return SelectItemGenerator.yesNo;
  }

  private static List<SelectItem> getAccountTypes() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.accountTypes == null) {
        SelectItemGenerator.accountTypes = new ArrayList<>();
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(0, "licenseManager"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(1, "hospitalManager"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(2, "hospitalAccountant"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(3, "hospitalEngineer"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(4, "supplierManager"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(5, "supplierAccountant"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(6, "supplierEngineer"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(7, "manufactureManager"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(8, "manufactureAccountant"));
        SelectItemGenerator.accountTypes.add(new LabelKeySelectItem(9, "manufactureEngineer"));
      }
    }
    return SelectItemGenerator.accountTypes;
  }

  private static List<SelectItem> getSearchDeviceConditions() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.searchDeviceConditions == null) {
        SelectItemGenerator.searchDeviceConditions = new ArrayList<>();
        SelectItemGenerator.searchDeviceConditions.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_CONTACT_PERSON,
            "manufactureContactPerson"));
        SelectItemGenerator.searchDeviceConditions.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_COUNTRY, "country"));
        SelectItemGenerator.searchDeviceConditions.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_CATALOG, "catalog"));
        SelectItemGenerator.searchDeviceConditions.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_SPECIFICATION, "specification"));
        SelectItemGenerator.searchDeviceConditions.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_NOTICE, "notice"));

      }
    }
    return SelectItemGenerator.searchDeviceConditions;
  }

  private static List<SelectItem> getCompareOperator() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.compareOperator == null) {
        SelectItemGenerator.compareOperator = new ArrayList<>();
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_EQUAL_TYPE, CompareOperatorConstants.EQUAL_TYPE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_NOT_EQUAL_TYPE,
            CompareOperatorConstants.NOT_EQUAL_TYPE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_CONTAIN_STRING_TYPE,
            CompareOperatorConstants.CONTAIN_STRING_TYPE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_CONTAIN_STRING_TYPE_EXACT_CASE,
            CompareOperatorConstants.CONTAIN_STRING_TYPE_EXACT_CASE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_GREATER_THAN_TYPE,
            CompareOperatorConstants.GREATER_THAN_TYPE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_GREATER_THAN_OR_EQUAL_TYPE,
            CompareOperatorConstants.GREATER_THAN_OR_EQUAL_TYPE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_LESS_THAN_TYPE,
            CompareOperatorConstants.LESS_THAN_TYPE));
        SelectItemGenerator.compareOperator.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_LESS_THAN_OR_EQUAL_TYPE,
            CompareOperatorConstants.LESS_THAN_OR_EQUAL_TYPE));
      }
    }
    return SelectItemGenerator.compareOperator;
  }

  private static List<SelectItem> getCompareSearchType() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.compareSearchType == null) {
        SelectItemGenerator.compareSearchType = new ArrayList<>();
        SelectItemGenerator.compareSearchType.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_ALL_TYPE, CompareOperatorConstants.ALL_TYPE));
        SelectItemGenerator.compareSearchType.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_ANY_TYPE, CompareOperatorConstants.ANY_TYPE));
      }
    }
    return SelectItemGenerator.compareSearchType;
  }

  private static List<SelectItem> getMasterDevicesType() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.masterDevicesType == null) {
        SelectItemGenerator.masterDevicesType = new ArrayList<>();
        SelectItemGenerator.masterDevicesType.add(new LabelKeySelectItem(CommonConstants.MDevTypeConstants.MEDICAL_EQUIPMENT, "medicalEquipment"));
        SelectItemGenerator.masterDevicesType.add(new LabelKeySelectItem(CommonConstants.MDevTypeConstants.MEASUREMENT_DEVICE, "measurementDevice"));
        SelectItemGenerator.masterDevicesType.add(new LabelKeySelectItem(CommonConstants.MDevTypeConstants.PARTSLIST, "parts"));
      }
    }
    return SelectItemGenerator.masterDevicesType;
  }

  private static List<SelectItem> getScheduleTasks() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.scheduleTask == null) {
        SelectItemGenerator.scheduleTask = new ArrayList<>();
        SelectItemGenerator.scheduleTask.add(new LabelKeySelectItem(CommonConstants.TASK_DONE, "doneTask"));
        SelectItemGenerator.scheduleTask.add(new LabelKeySelectItem(CommonConstants.TASK_DOING, "doingTask"));
        SelectItemGenerator.scheduleTask.add(new LabelKeySelectItem(CommonConstants.TASK_NOT_START, "notStartTask"));
      }
    }
    return SelectItemGenerator.scheduleTask;
  }

  private static List<SelectItem> getStatusActionBill() {
    synchronized (SelectItemGenerator.myLock) {
      if (SelectItemGenerator.statusActionBill == null) {
        SelectItemGenerator.statusActionBill = new ArrayList<>();
        SelectItemGenerator.statusActionBill.add(new LabelKeySelectItem(CommonConstants.BILL_NOT_PAID, "notPaid"));
        SelectItemGenerator.statusActionBill.add(new LabelKeySelectItem(CommonConstants.BILL_PAID, "paid"));
      }
    }
    return SelectItemGenerator.statusActionBill;
  }
}
